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
import com.sales.entity.WeiSiteStyleTemplate;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeiSiteStyleTemplateById,
        importantParameters = {"session", "encryptType", "weiSiteStyleTemplateId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weiSiteStyleTemplateId","weiSiteStyleTemplateCfgId","companyId","createTime","isValid"},
        description = "查询WeiSiteStyleTemplate详细内容",
        validateParameters = {
          	@Field(fieldName ="weiSiteStyleTemplateId", fieldType = FieldTypeEnum.BIG_INT, description ="微站的模板id"),
	@Field(fieldName ="weiSiteStyleTemplateCfgId", fieldType = FieldTypeEnum.BIG_INT, description ="所属的模板id"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司的id"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="isValid", fieldType = FieldTypeEnum.TYINT, description ="是否生效"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeiSiteStyleTemplateByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireWeiSiteStyleTemplateByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String weiSiteStyleTemplateId = parameters.get("weiSiteStyleTemplateId");
	primaryKey.putKeyField("weiSiteStyleTemplateId",String.valueOf(weiSiteStyleTemplateId));

        EntityDao<WeiSiteStyleTemplate> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteStyleTemplate);
          WeiSiteStyleTemplate entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
