package com.sales.user;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.DateTimeUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.Menu;
import com.sales.entity.WeixinToken;
import com.sales.weixin.api.WeixinAPI;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.publishMenu,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "发布菜单",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class PublishMenuService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(PublishMenuService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        EntityDao<WeixinToken> weixinTokenDAO = applicationContext.getEntityDAO(EntityNames.weixinToken);
        EntityDao<Menu> menuDAO = applicationContext.getEntityDAO(EntityNames.menu);
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondtion = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondtion);
        List<WeixinToken> weixinTokenList = weixinTokenDAO.inquireByCondition(conditionList);
        if (weixinTokenList != null && !weixinTokenList.isEmpty()) {
            WeixinToken weixinToken = weixinTokenList.get(0);
            if (weixinToken.getAppId() != null && !weixinToken.getAppId().isEmpty() && weixinToken.getAppSecret() != null && !weixinToken.getAppSecret().isEmpty()) {
                WeixinAPI weixinAPI = applicationContext.getWeixinAPI();
                String accessToken = weixinToken.getAccessToken();
                Date date = weixinToken.getExpireTime();
                boolean isValidateAccessToken = false;
                long expireTime;
                String expiresIn = "";
                if (date != null && date.after(new Date())) {
                    if (accessToken != null && !accessToken.isEmpty()) {
                        isValidateAccessToken = true;
                        expireTime = date.getTime();
                    } else {
                        Map<String, String> parameters = new HashMap<String, String>(2, 1);
                        parameters.put("appid", weixinToken.getAppId());
                        parameters.put("secret", weixinToken.getAppSecret());
                        Map<String, String> resultMap = weixinAPI.getAccessToken(parameters);
                        accessToken = resultMap.get("access_token");
                        expiresIn = resultMap.get("expires_in");
                        expireTime = DateTimeUtils.getDateTime(System.currentTimeMillis(), 0, Integer.parseInt(expiresIn), 0, 0);
                        isValidateAccessToken = true;
                    }
                } else {
                    Map<String, String> parameters = new HashMap<String, String>(2, 1);
                    parameters.put("appid", weixinToken.getAppId());
                    parameters.put("secret", weixinToken.getAppSecret());
                    Map<String, String> resultMap = weixinAPI.getAccessToken(parameters);
                    accessToken = resultMap.get("access_token");
                    expiresIn = resultMap.get("expires_in");
                    expireTime = DateTimeUtils.getDateTime(System.currentTimeMillis(), 0, Integer.parseInt(expiresIn), 0, 0);
                    isValidateAccessToken = true;
                }
                if (isValidateAccessToken) {
                    List<Menu> menuList = menuDAO.inquireByCondition(conditionList);
                    String postJSON = this.getPostJson(menuList);
                    Map<String, String> parameters = new HashMap<String, String>(2, 1);
                    parameters.put("access_token", accessToken);
                    Map<String, String> resultMap = weixinAPI.createMenu(parameters, postJSON);
                    if (resultMap != null && resultMap.get("errcode") != null && "0".equals(resultMap.get("errcode"))) {
                        Map<String, String> weinxinTokenMap = new HashMap<String, String>(4, 1);
                        weinxinTokenMap.put("tokenId", String.valueOf(weixinToken.getTokenId()));
                        weinxinTokenMap.put("accessToken", accessToken);
                        weinxinTokenMap.put("expireTime", DateTimeUtils.timestamp2Str(new Timestamp(expireTime)));
                        WeixinToken resultWeixinToken = weixinTokenDAO.update(weinxinTokenMap);
                        if (resultWeixinToken != null) {
                            applicationContext.success();
                        } else {
                            throw new RollBackException("操作失败");
                        }
                    } else {
                        applicationContext.fail();
                    }
                } else {
                    applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
                }
            } else {
                applicationContext.fail(SalesErrorCode.TOKEN_NOT_EXISTS);
            }
        } else {
            applicationContext.fail(SalesErrorCode.TOKEN_NOT_EXISTS);
        }


    }

    private String getPostJson(List<Menu> menuList) {
        List<Menu> parentMenuList = new ArrayList<Menu>(3);
        for (Menu menu : menuList) {
            if (parentMenuList.size() < 3) {
                if (menu.getMenuParent() == -1) {
                    parentMenuList.add(menu);
                }
            }
        }
        List<Menu> childMenuList;
        Map<Long, List<Menu>> menuMap = new HashMap<Long, List<Menu>>();
        for (Menu parentMenu : parentMenuList) {
            for (Menu menu : menuList) {
                if (menu.getMenuParent() == parentMenu.getMenuId() && menu.getMenuParent() != -1) {
                    childMenuList = menuMap.get(parentMenu.getMenuId());
                    if (childMenuList != null) {
                        if (childMenuList.size() < 5) {
                            childMenuList.add(menu);
                        }

                    } else {
                        childMenuList = new ArrayList<Menu>(5);
                        childMenuList.add(menu);
                        menuMap.put(parentMenu.getMenuId(), menuList);
                    }
                }
            }
        }
        StringBuilder jsonBuilder = new StringBuilder(50);
        jsonBuilder.append("{\"button\":[");
        if (parentMenuList != null && !parentMenuList.isEmpty()) {
            for (Menu parentMenu : parentMenuList) {
                childMenuList = menuMap.get(parentMenu.getMenuId());
                if (childMenuList == null || childMenuList.isEmpty()) {
                    jsonBuilder.append("{");
                    if (parentMenu.getMenuType() == 1) {
                        jsonBuilder.append("\"type\":\"click\",");
                        jsonBuilder.append("\"name\":").append("\"").append(parentMenu.getMenuName()).append("\",");
                        jsonBuilder.append("\"key\":").append("\"").append(parentMenu.getMenuKey()).append("\"");
                    } else {
                        jsonBuilder.append("\"type\":\"view\",");
                        jsonBuilder.append("\"name\":").append("\"").append(parentMenu.getMenuName()).append("\",");
                        jsonBuilder.append("\"url\":").append("\"").append(parentMenu.getMenuURL()).append("\"");
                    }
                    jsonBuilder.append("},");
                } else {
                    jsonBuilder.append("{");
                    jsonBuilder.append("\"name\":").append("\"").append(parentMenu.getMenuName()).append("\",");
                    jsonBuilder.append(" \"sub_button\":[");
                    for (Menu menu : childMenuList) {
                        jsonBuilder.append("{");
                        if (menu.getMenuType() == 1) {
                            jsonBuilder.append("\"type\":\"click\",");
                            jsonBuilder.append("\"name\":").append("\"").append(menu.getMenuName()).append("\",");
                            jsonBuilder.append("\"key\":").append("\"").append(menu.getMenuKey()).append("\"");
                        } else {
                            jsonBuilder.append("\"type\":\"view\",");
                            jsonBuilder.append("\"name\":").append("\"").append(menu.getMenuName()).append("\",");
                            jsonBuilder.append("\"url\":").append("\"").append(menu.getMenuURL()).append("\"");
                        }
                        jsonBuilder.append("},");
                    }
                    if (childMenuList.size() > 0) {
                        jsonBuilder.setLength(jsonBuilder.length() - 1);
                    }
                    jsonBuilder.append("]");
                    jsonBuilder.append("},");

                }
            }
            jsonBuilder.setLength(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("]}");
        logger.debug("json : {}", jsonBuilder.toString());
        return jsonBuilder.toString();
    }
}
