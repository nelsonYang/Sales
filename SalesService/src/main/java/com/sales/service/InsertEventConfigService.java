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
import com.sales.entity.EventConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertEventConfig,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"keyword","matchType","messageTitle","messageImageURL","isDialOpen","companyId","type"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增EventConfig",
        validateParameters = {
       	@Field(fieldName ="eventConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="活动配置id"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键子"),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配类型"),
	@Field(fieldName ="messageTitle", fieldType = FieldTypeEnum.CHAR36, description ="消息标题"),
	@Field(fieldName ="messageImageURL", fieldType = FieldTypeEnum.CHAR64, description ="消息图片"),
	@Field(fieldName ="isDialOpen", fieldType = FieldTypeEnum.TYINT, description ="是否开启短信通知"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="活动类型1-瓜瓜卡，2-水果达人 3-欢乐转盘"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertEventConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertEventConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<EventConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.eventConfig);
        EventConfig entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
