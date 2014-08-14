package com.sales.service;

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
import com.framework.exception.RollBackException;
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteTemplateCfg;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateWeiSiteTemplateCfg,
        importantParameters = {"session", "encryptType","weiSiteTemplateCfgId"},
        minorParameters = {"weiSiteTemplateName","type","weiSiteTemplateURL","weiSiteTemplateHtml"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改WeiSiteTemplateCfg",
        validateParameters = {
       	@Field(fieldName ="weiSiteTemplateCfgId", fieldType = FieldTypeEnum.BIG_INT, description ="微网站模板id"),
	@Field(fieldName ="weiSiteTemplateName", fieldType = FieldTypeEnum.CHAR36, description ="微网站模板名称"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="微网站类型"),
	@Field(fieldName ="weiSiteTemplateURL", fieldType = FieldTypeEnum.CHAR64, description ="图片url"),
	@Field(fieldName ="weiSiteTemplateHtml", fieldType = FieldTypeEnum.CHAR1024, description ="模板的html"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateWeiSiteTemplateCfgService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateWeiSiteTemplateCfgService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<WeiSiteTemplateCfg> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteTemplateCfg);
        WeiSiteTemplateCfg entity = entityDAO.update(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
