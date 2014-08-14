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
import com.sales.config.SalesActionName;
import com.sales.config.SalesConstant;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.Event;
import com.sales.entity.ResponseMessageCfg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.updateEvent,
        importantParameters = {"session", "encryptType", "eventId", "eventName"},
        minorParameters = {"eventDesc", "eventStartURL", "eventEndImageURL", "eventEffectiveStartTime", "eventEffectiveEndTime", "type", "keyword", "eventConfigId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改活动",
        validateParameters = {
    @Field(fieldName = "eventId", fieldType = FieldTypeEnum.BIG_INT, description = "活动id"),
    @Field(fieldName = "eventName", fieldType = FieldTypeEnum.CHAR24, description = "活动名称"),
    @Field(fieldName = "eventDesc", fieldType = FieldTypeEnum.CHAR512, description = "活动描述"),
    @Field(fieldName = "eventStartURL", fieldType = FieldTypeEnum.CHAR128, description = "活动的url"),
    @Field(fieldName = "eventEndImageURL", fieldType = FieldTypeEnum.CHAR128, description = "活动结束的图片"),
    @Field(fieldName = "eventEffectiveStartTime", fieldType = FieldTypeEnum.DATETIME, description = "活动的开始时间"),
    @Field(fieldName = "eventEffectiveEndTime", fieldType = FieldTypeEnum.DATETIME, description = "活动的结束时间"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "活动的类型"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR24, description = "关键字"),
    @Field(fieldName = "eventConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "活动配置id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateEventService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String messageTitle = parameters.get("eventName");
        String messageURL = parameters.get("eventStartURL");
        String keyword = parameters.get("keyword");
        parameters.put("companyId", String.valueOf(companyId));
        EntityDao<Event> eventDAO = applicationContext.getEntityDAO(EntityNames.event);
        Event event = eventDAO.update(parameters);
        if (event != null) {
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            List<Condition> conditionList = new ArrayList<Condition>(3);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            conditionList.add(companyIdCondition);
            Condition relateEventIdCondition = new Condition("relatedId", ConditionTypeEnum.EQUAL, String.valueOf(event.getEventId()));
            conditionList.add(relateEventIdCondition);
            Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.EVENT_TYPE));
            conditionList.add(responseContentTypeCondition);
            Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
            conditionList.add(cfgTypeCondition);
            List<Order> orderList = new ArrayList<Order>();
            Order responseMessageCfgOrder = new Order("responseMessageCfgId", OrderEnum.DESC);
            orderList.add(responseMessageCfgOrder);
            List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList, orderList);
            if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                String responseKeyword = responseMessageCfg.getKeyword();
                Map<String, String> responseMessageMap = new HashMap<String, String>(2, 1);
                responseMessageMap.put("responseMessageCfgId", String.valueOf(responseMessageCfg.getResponseMessageCfgId()));
                if (keyword != null && !keyword.isEmpty()) {
                    responseMessageMap.put("keyword", keyword);
                }
                if (messageTitle != null && !messageTitle.isEmpty()) {
                    responseMessageMap.put("responseContent", messageTitle);
                }
                if (messageURL != null && !messageURL.isEmpty()) {
                    responseMessageMap.put("responseImageURL", messageURL);
                }
                responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                if (responseMessageCfg != null) {
                    ResponseMessageCache.getInstance().updateCache(responseMessageCfg,responseKeyword);
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                throw new RollBackException("操作失败");
            }

        }
    }
}
