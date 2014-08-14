package com.sales.setting.responsemessage;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.ResponseMessageCfg;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.updateResponseMessage,
        importantParameters = {"session", "encryptType", "responseMessageCfgId", "type"},
        minorParameters = {"responseContent", "responseImageURL", "responseAudio", "keyword"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改回应消息",
        validateParameters = {
    @Field(fieldName = "responseMessageCfgId", fieldType = FieldTypeEnum.BIG_INT, description = "回应id"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR24, description = "关键字"),
    @Field(fieldName = "responseContent", fieldType = FieldTypeEnum.CHAR512, description = "回应的文本内容"),
    @Field(fieldName = "responseImageURL", fieldType = FieldTypeEnum.CHAR128, description = "回应的图片地址"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "回应类型"),
    @Field(fieldName = "responseAudio", fieldType = FieldTypeEnum.CHAR128, description = "回应的音频地址"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateResponseMessageService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        ResponseMessageCfg responseMessageCfg = responseMessageDAO.update(parameters);
        if (responseMessageCfg != null) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }

    }
}
