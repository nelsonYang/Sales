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
import com.sales.entity.ResponseToolConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireResponseToolConfigById,
        importantParameters = {"session", "encryptType", "responseToolsConfigId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"responseToolsConfigId","responseKeyword","responseToolName","type","isClose","companyId"},
        description = "查询ResponseToolConfig详细内容",
        validateParameters = {
          	@Field(fieldName ="responseToolsConfigId", fieldType = FieldTypeEnum.INT, description ="配置id"),
	@Field(fieldName ="responseKeyword", fieldType = FieldTypeEnum.CHAR36, description ="配置关键子"),
	@Field(fieldName ="responseToolName", fieldType = FieldTypeEnum.CHAR36, description ="配置的关键名称"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="回复的类型"),
	@Field(fieldName ="isClose", fieldType = FieldTypeEnum.TYINT, description ="1-开启2关闭"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.CHAR36, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireResponseToolConfigByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireResponseToolConfigByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String responseToolsConfigId = parameters.get("responseToolsConfigId");
	primaryKey.putKeyField("responseToolsConfigId",String.valueOf(responseToolsConfigId));

        EntityDao<ResponseToolConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.responseToolConfig);
          ResponseToolConfig entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
