package com.sales.upload;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.ActionNames;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Administrator
 */
@ServiceConfig(
        act = ActionNames.inquireHttpSessionId,
        importantParameters = {"session", "encryptType"},
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        returnParameters = {"sessionId"},
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        responseEncrypt = CryptEnum.AES,
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT)
})
public class InquireHttpSessionIdService implements Service {

    @Override
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        HttpSession httpSession = applicationContext.getHttpSession();
        String sessionId = httpSession.getId();
        Map<String, String> sessionMap = new HashMap<String, String>(2, 1);
        sessionMap.put("sessionId", sessionId);
        applicationContext.setMapData(sessionMap);
        applicationContext.success();

    }
}
