package com.sales.service;

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
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.entity.EntityNames;
import com.sales.entity.MerchantConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireMerchantConfigById,
        importantParameters = {"session", "encryptType", "merchantConfigId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"merchantConfigId","merchantName","keyword","matchType","messageTitle","messageImageURL","companyId"},
        description = "查询MerchantConfig详细内容",
        validateParameters = {
          	@Field(fieldName ="merchantConfigId", fieldType = FieldTypeEnum.TYINT, description ="门店id"),
	@Field(fieldName ="merchantName", fieldType = FieldTypeEnum.CHAR36, description ="门店名称"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键子"),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配模式"),
	@Field(fieldName ="messageTitle", fieldType = FieldTypeEnum.CHAR36, description ="消息标题"),
	@Field(fieldName ="messageImageURL", fieldType = FieldTypeEnum.CHAR64, description ="消息图片"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMerchantConfigByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireMerchantConfigByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String merchantConfigId = parameters.get("merchantConfigId");
	primaryKey.putKeyField("merchantConfigId",String.valueOf(merchantConfigId));

        EntityDao<MerchantConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.merchantConfig);
          MerchantConfig entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
