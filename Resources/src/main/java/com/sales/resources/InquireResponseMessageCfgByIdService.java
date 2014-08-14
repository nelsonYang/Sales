package com.sales.resources;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.ResponseMessageCfg;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireResponseMessageCfgById,
        importantParameters = {"session", "encryptType", "responseMessageCfgId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"responseMessageCfgId", "responseContent", "responseImageURL", "responseAudio", "type", "companyId", "relatedEventId", "keyword", "relatedURL", "isClose", "responseContentType", "relatedId"},
        description = "查询ResponseMessageCfg配置",
        validateParameters = {
    @Field(fieldName = "responseMessageCfgId", fieldType = FieldTypeEnum.BIG_INT, description = "id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireResponseMessageCfgByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireResponseMessageCfgByIdService.class);
    
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String responseMessageCfgId = parameters.get("responseMessageCfgId");
        logger.debug("parameters={}", parameters);
        EntityDao<ResponseMessageCfg> entityDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        PrimaryKey pk = new PrimaryKey();
        pk.putKeyField("responseMessageCfgId", responseMessageCfgId);
        pk.putKeyField("companyId", String.valueOf(companyId));
       ResponseMessageCfg responseMessageCfg = entityDAO.inqurieByKey(pk);
        if (responseMessageCfg != null) {
            applicationContext.setEntityData(responseMessageCfg);
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }
        
    }
}
