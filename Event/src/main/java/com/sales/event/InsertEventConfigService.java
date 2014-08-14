package com.sales.event;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.enumeration.OrderEnum;
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
import com.sales.config.SalesConstant;
import com.sales.entity.EntityNames;
import com.sales.entity.EventConfig;
import com.sales.entity.ResponseMessageCfg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertEventConfig,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"eventConfigId", "keyword", "matchType", "messageTitle", "messageImageURL", "isDialOpen", "type"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增EventConfig",
        validateParameters = {
    @Field(fieldName = "eventConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "基本配置id"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键子"),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配类型"),
    @Field(fieldName = "messageTitle", fieldType = FieldTypeEnum.CHAR36, description = "消息标题"),
    @Field(fieldName = "messageImageURL", fieldType = FieldTypeEnum.CHAR128, description = "消息图片"),
    @Field(fieldName = "isDialOpen", fieldType = FieldTypeEnum.TYINT, description = "是否开启短信通知"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "活动类型1-瓜瓜2-水果大人3-欢乐转盘"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertEventConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertEventConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        String messageTitle = parameters.get("messageTitle");
        String messageURL = parameters.get("messageImageURL");
        String eventConfigId = parameters.get("eventConfigId");
        EntityDao<EventConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.eventConfig);
        if (eventConfigId == null || eventConfigId.isEmpty()) {
            EventConfig entity = entityDAO.insert(parameters);
            if (entity != null) {
                Map<String, String> responseMessageMap = new HashMap<String, String>(4, 1);
                responseMessageMap.put("companyId", String.valueOf(companyId));
                responseMessageMap.put("type", String.valueOf(SalesConstant.RESPONSE_TEXT_TYPE));
                responseMessageMap.put("responseContentType", String.valueOf(SalesConstant.EVENT_TYPE));
                responseMessageMap.put("keyword", parameters.get("keyword"));
                if (messageTitle != null) {
                    responseMessageMap.put("responseContent", messageTitle);
                }
                if (messageURL != null) {
                    responseMessageMap.put("responseImageURL", messageURL);
                }
                responseMessageMap.put("cfgType", String.valueOf(SalesConstant.BASIC_CFG_TYPE));
                responseMessageMap.put("relatedEventId", String.valueOf(entity.getEventConfigId()));
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
                if (responseMessageCfg != null) {
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            EventConfig entity = entityDAO.update(parameters);
            if (entity != null) {
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                List<Condition> conditionList = new ArrayList<Condition>(2);
                Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyIdCondition);
                Condition relateIdIdCondition = new Condition("relatedEventId", ConditionTypeEnum.EQUAL, String.valueOf(entity.getEventConfigId()));
                conditionList.add(relateIdIdCondition);
                Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.MEMBER_CARD_TYPE));
                conditionList.add(responseContentTypeCondition);
                Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.BASIC_CFG_TYPE));
                conditionList.add(cfgTypeCondition);
                List<Order> orderList = new ArrayList<Order>();
                Order responseMessageCfgOrder = new Order("responseMessageCfgId", OrderEnum.DESC);
                orderList.add(responseMessageCfgOrder);
                List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList,orderList);
                if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                    ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                    Map<String, String> responseMessageMap = new HashMap<String, String>(2, 1);
                    responseMessageMap.put("responseMessageCfgId", String.valueOf(responseMessageCfg.getResponseMessageCfgId()));
                    responseMessageMap.put("keyword", parameters.get("keyword"));
                    if (messageTitle != null) {
                        responseMessageMap.put("responseContent", messageTitle);
                    }
                    if (messageURL != null) {
                        responseMessageMap.put("responseImageURL", messageURL);
                    }
                    responseMessageMap.put("cfgType", String.valueOf(SalesConstant.BASIC_CFG_TYPE));
                    responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                    if (responseMessageCfg != null) {
                        applicationContext.success();
                    } else {
                        throw new RollBackException("操作失败");
                    }
                } else {
                    applicationContext.success();
                }
            } else {
                throw new RollBackException("操作失败");
            }
        }
    }
}
