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
import com.framework.service.api.Service;
import com.framework.spi.entity.SessionEntity;
import com.framework.utils.Base64Utils;
import com.frameworkLog.factory.LogFactory;
import com.sales.entity.Company;
import com.sales.entity.EntityNames;
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.login,
        importantParameters = {"userName", "password"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"session", "key"},
        description = "注册",
        validateParameters = {
    @Field(fieldName = "userName", fieldType = FieldTypeEnum.CHAR24, description = "用户名"),
    @Field(fieldName = "password", fieldType = FieldTypeEnum.CHAR64, description = "密码"),
    @Field(fieldName = "sign", fieldType = FieldTypeEnum.CHAR1024, description = "签名验证"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class LoginService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(LoginService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String companyName = parameters.get("userName");
        String password = parameters.get("password");
        logger.debug("companyName={}", companyName);
        logger.debug("password={}", password);
        EntityDao<Company> companyDAO = applicationContext.getEntityDAO(EntityNames.company);
        //获取公司id
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition userNameCondition = new Condition("companyName", ConditionTypeEnum.EQUAL, companyName);
        Condition passwordCondition = new Condition("password", ConditionTypeEnum.EQUAL, password);
        conditionList.add(userNameCondition);
        conditionList.add(passwordCondition);
        List<Company> companyList = companyDAO.inquireByCondition(conditionList);
        if (companyList != null && !companyList.isEmpty()) {
            try {
                Company company = companyList.get(0);
                HttpSession httpSession = applicationContext.getHttpSession();
               // String token = httpSession.getId();
                String companyToken = company.getCompanyToken();
                StringBuilder content = new StringBuilder(60);
                content.append("{\"userName\":\"").append(company.getCompanyName())
                        .append("\",\"token\":\"").append(companyToken).append("\",\"userId\":\"").append(company.getCompanyId())
                        .append("\",\"orgUserId\":\"").append("-1").append("\",\"orgId\":\"").append("-1").append("\",\"orgType\":\"").append("-1")
                        .append("\",\"regionId\":\"").append(company.getRegion())
                        .append("\",\"childOrgIds\":\"").append("-1")
                        .append("\"}");
                logger.debug("content :{}", content.toString());
                String base64Content = Base64Utils.encode(content.toString().getBytes("UTF-8"));
                if (base64Content != null) {
                    //注册
                    base64Content = base64Content.replace("\n", "");
                    base64Content = base64Content.replace("\r", "");

                    SessionEntity companySessionEntity = applicationContext.getWebSessionManager().getSessionEntityByKey(String.valueOf(company.getCompanyId()));
                    String key = applicationContext.getKey(String.valueOf(company.getCompanyId()));
                    boolean isSuccess = true;
                    String session = "";
                    String realKey = "";
                    if (companySessionEntity == null) {
                        SessionEntity sessionEntity = new SessionEntity();
                        sessionEntity.setToken(companyToken);
                        sessionEntity.setSession(base64Content);
                        sessionEntity.setTouchTime(System.currentTimeMillis());
                        sessionEntity.setSessionId(httpSession.getId());
                        boolean isSessionSuccess = applicationContext.getWebSessionManager().putKey(String.valueOf(company.getCompanyId()), sessionEntity);
                        if (!isSessionSuccess) {
                            isSuccess = false;
                        } else {
                            session = sessionEntity.getSession();
                        }
                    } else {
                        session = companySessionEntity.getSession();
                    }
                    if (isSuccess) {
                        if (key == null) {
                            realKey = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
                            boolean isKeySuccess = applicationContext.putKey(String.valueOf(company.getCompanyId()), realKey);
                            if (!isKeySuccess) {
                                isSuccess = false;
                            }
                        } else {
                            realKey = key;
                        }
                    }
                    if (isSuccess) {
                        Map<String, String> resultMap = new HashMap<String, String>(2, 1);
                        resultMap.put("session", session);
                        resultMap.put("key", realKey);
                        applicationContext.setMapData(resultMap);
                        applicationContext.success();
                    } else {
                        applicationContext.fail();
                    }
                } else {
                    applicationContext.fail();
                }
            } catch (Exception ex) {
                logger.error("ex={}", ex);
            }
        } else {
            applicationContext.fail(SalesErrorCode.USER_OR_PASSWORD_ERROR);
        }

    }
}
