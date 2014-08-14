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
import com.sales.entity.WeiSiteTemplateCfg;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeiSiteTemplateCfgById,
        importantParameters = {"session", "encryptType", "weiSiteTemplateCfgId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weiSiteTemplateCfgId","weiSiteTemplateName","type","weiSiteTemplateURL","weiSiteTemplateHtml"},
        description = "查询WeiSiteTemplateCfg详细内容",
        validateParameters = {
          	@Field(fieldName ="weiSiteTemplateCfgId", fieldType = FieldTypeEnum.BIG_INT, description ="微网站模板id"),
	@Field(fieldName ="weiSiteTemplateName", fieldType = FieldTypeEnum.CHAR36, description ="微网站模板名称"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="微网站类型"),
	@Field(fieldName ="weiSiteTemplateURL", fieldType = FieldTypeEnum.CHAR64, description ="图片url"),
	@Field(fieldName ="weiSiteTemplateHtml", fieldType = FieldTypeEnum.CHAR1024, description ="模板的html"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeiSiteTemplateCfgByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireWeiSiteTemplateCfgByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String weiSiteTemplateCfgId = parameters.get("weiSiteTemplateCfgId");
	primaryKey.putKeyField("weiSiteTemplateCfgId",String.valueOf(weiSiteTemplateCfgId));

        EntityDao<WeiSiteTemplateCfg> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteTemplateCfg);
          WeiSiteTemplateCfg entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
