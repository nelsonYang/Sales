package com.sales.resources;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireFirstCarCfg,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"type"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"responseMessageCfgId", "responseContent", "responseImageURL", "responseAudio", "type", "companyId", "relatedEventId", "keyword", "relatedURL", "isClose", "responseContentType", "relatedId"},
        description = "查询ResponseMessageCfg配置",
        validateParameters = {
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "回复的类型0-文本1-音频2-music3-vedio4-news5-image6-location7-link8-event_event9-event_scan10-event_subscribe11-event_unsubscribe,12-首次关注13-关键字"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireFirstCareCfgService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireFirstCareCfgService.class);
    
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String type = parameters.get("type");
        logger.debug("parameters={}", parameters);
        EntityDao<ResponseMessageCfg> entityDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        if (type != null && !type.isEmpty()) {
            Condition typeCondition = new Condition("type", ConditionTypeEnum.EQUAL, type);
            conditionList.add(typeCondition);
        }
        List<ResponseMessageCfg> entityList = entityDAO.inquireByCondition(conditionList);
        if (entityList != null) {
            if (!entityList.isEmpty()) {
                applicationContext.setEntityData(entityList.get(0));
                applicationContext.success();
            } else {
                applicationContext.noData();
            }
        } else {
            throw new RollBackException("操作失败");
        }
        
    }
}
