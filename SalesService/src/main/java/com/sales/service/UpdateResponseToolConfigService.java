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
import com.sales.entity.ResponseToolConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateResponseToolConfig,
        importantParameters = {"session", "encryptType","responseToolsConfigId"},
        minorParameters = {"responseKeyword","responseToolName","type","isClose","companyId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改ResponseToolConfig",
        validateParameters = {
       	@Field(fieldName ="responseToolsConfigId", fieldType = FieldTypeEnum.INT, description ="配置id"),
	@Field(fieldName ="responseKeyword", fieldType = FieldTypeEnum.CHAR36, description ="配置关键子"),
	@Field(fieldName ="responseToolName", fieldType = FieldTypeEnum.CHAR36, description ="配置的关键名称"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="回复的类型"),
	@Field(fieldName ="isClose", fieldType = FieldTypeEnum.TYINT, description ="1-开启2关闭"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.CHAR36, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateResponseToolConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateResponseToolConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<ResponseToolConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.responseToolConfig);
        ResponseToolConfig entity = entityDAO.update(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
