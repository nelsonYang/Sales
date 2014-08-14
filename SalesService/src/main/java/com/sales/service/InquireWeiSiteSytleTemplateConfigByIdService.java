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
import com.sales.entity.WeiSiteSytleTemplateConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeiSiteSytleTemplateConfigById,
        importantParameters = {"session", "encryptType", "weiSiteSytleTemplateId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weiSiteSytleTemplateId","weiSiteSytleTemplateName","weiSiteSytleTemplateURL","type","createTime"},
        description = "查询WeiSiteSytleTemplateConfig详细内容",
        validateParameters = {
          	@Field(fieldName ="weiSiteSytleTemplateId", fieldType = FieldTypeEnum.BIG_INT, description ="微网站风格配置id"),
	@Field(fieldName ="weiSiteSytleTemplateName", fieldType = FieldTypeEnum.CHAR36, description ="模板名称"),
	@Field(fieldName ="weiSiteSytleTemplateURL", fieldType = FieldTypeEnum.CHAR64, description ="风格配置的url"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="模板风格的类型"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建的时间"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeiSiteSytleTemplateConfigByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireWeiSiteSytleTemplateConfigByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String weiSiteSytleTemplateId = parameters.get("weiSiteSytleTemplateId");
	primaryKey.putKeyField("weiSiteSytleTemplateId",String.valueOf(weiSiteSytleTemplateId));

        EntityDao<WeiSiteSytleTemplateConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteSytleTemplateConfig);
          WeiSiteSytleTemplateConfig entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
