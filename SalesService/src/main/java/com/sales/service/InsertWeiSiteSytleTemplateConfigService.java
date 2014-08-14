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
        act = ActionNames.insertWeiSiteSytleTemplateConfig,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"weiSiteSytleTemplateName","weiSiteSytleTemplateURL","type","createTime"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增WeiSiteSytleTemplateConfig",
        validateParameters = {
       	@Field(fieldName ="weiSiteSytleTemplateId", fieldType = FieldTypeEnum.BIG_INT, description ="微网站风格配置id"),
	@Field(fieldName ="weiSiteSytleTemplateName", fieldType = FieldTypeEnum.CHAR36, description ="模板名称"),
	@Field(fieldName ="weiSiteSytleTemplateURL", fieldType = FieldTypeEnum.CHAR64, description ="风格配置的url"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="模板风格的类型"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建的时间"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertWeiSiteSytleTemplateConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertWeiSiteSytleTemplateConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<WeiSiteSytleTemplateConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteSytleTemplateConfig);
        WeiSiteSytleTemplateConfig entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
