package com.sales.setting.responsemessage;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.ResponseMessageCfg;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireResponseMessageById,
        importantParameters = {"session", "encryptType", "responseMessageCfgId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"responseMessageCfgId", "responseContent", "responseImageURL", "responseAudio", "keyword"},
        description = "查询回应消息详细",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "responseMessageCfgId", fieldType = FieldTypeEnum.BIG_INT, description = "回应消息id"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireResponseMessageByIdService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String responseMessageCfgId = parameters.get("responseMessageCfgId");
        EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("responseMessageCfgId", responseMessageCfgId);
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        ResponseMessageCfg responseMessageCfg = responseMessageDAO.inqurieByKey(primaryKey);
        if (responseMessageCfg != null) {
            Map<String, String> resultMap = responseMessageCfg.toMap();
            if (resultMap.get("responseContent") == null) {
                resultMap.put("responseContent", "");
            }
            if (resultMap.get("responseImageURL") == null) {
                resultMap.put("responseImageURL", "");
            }
            if (resultMap.get("responseAudio") == null) {
                resultMap.put("responseAudio", "");
            }
            applicationContext.setMapData(resultMap);
            applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.ENTITY_NOT_FOUND);
        }


    }
}
