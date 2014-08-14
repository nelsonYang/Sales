package com.sales.user;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.WeixinToken;
import com.sales.weixin.api.WeixinAPI;
import com.sales.weixinservice.servlet.WeixinReceiveServlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.bindServiceAccount,
        requestEncrypt = CryptEnum.AES,
        importantParameters = {"appId", "appSecret", "session", "encryptType"},
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        description = "绑定服务号",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "appId", fieldType = FieldTypeEnum.CHAR128, description = "应用id"),
    @Field(fieldName = "appSecret", fieldType = FieldTypeEnum.CHAR128, description = "应用密钥"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class BindServiceAccountService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> dataMap = applicationContext.getSimpleMapParameters();
        String appId = dataMap.get("appId");
        String appSecret = dataMap.get("appSecret");
        dataMap.put("companyId", String.valueOf(companyId));
        EntityDao<WeixinToken> weixinTokenDAO = applicationContext.getEntityDAO(EntityNames.weixinToken);
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondtion = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondtion);
        synchronized (this) {
            List<WeixinToken> weixinTokenList = weixinTokenDAO.inquireByCondition(conditionList);
            if (weixinTokenList == null || weixinTokenList.isEmpty()) {
                Map<String, String> parameters = new HashMap<String, String>(2, 1);
                parameters.put("appid", appId);
                parameters.put("secret", appSecret);
                WeixinAPI weixinAPI = WeixinReceiveServlet.getWeiXinAPI();
                Map<String, String> resultMap = weixinAPI.getAccessToken(parameters);
                String accessToken = resultMap.get("access_token");
                if (accessToken != null && !accessToken.isEmpty()) {
                    dataMap.put("accessToken", accessToken);
                    weixinTokenDAO.insert(dataMap);
                    applicationContext.success();
                } else {
                    applicationContext.fail();
                }
            } else {
                WeixinToken weixinToken = weixinTokenList.get(0);
                dataMap.put("tokenId", String.valueOf(weixinToken.getTokenId()));
                Map<String, String> parameters = new HashMap<String, String>(2, 1);
                parameters.put("appid", appId);
                parameters.put("secret", appSecret);
                WeixinAPI weixinAPI = WeixinReceiveServlet.getWeiXinAPI();
                Map<String, String> resultMap = weixinAPI.getAccessToken(parameters);
                String accessToken = resultMap.get("access_token");
                if (accessToken != null && !accessToken.isEmpty()) {
                    dataMap.put("accessToken", accessToken);
                    weixinTokenDAO.update(dataMap);
                    applicationContext.success();
                } else {
                    applicationContext.fail();
                }
            }
        }
    }
}
