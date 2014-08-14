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
import com.framework.utils.DateTimeUtils;
import com.sales.config.SalesActionName;
import com.sales.config.SalesConstant;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.Event;
import com.sales.entity.EventConfig;
import com.sales.entity.Resources;
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
        act = SalesActionName.insertEvent,
        importantParameters = {"session", "encryptType", "eventName", "eventDesc", "eventEffectiveStartTime", "eventEffectiveEndTime", "type", "keyword", "eventConfigId"},
        minorParameters = {"eventStartURL", "eventEndImageURL"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        returnParameters = {"eventId"},
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增活动",
        validateParameters = {
    @Field(fieldName = "eventName", fieldType = FieldTypeEnum.CHAR24, description = "活动名称"),
    @Field(fieldName = "eventDesc", fieldType = FieldTypeEnum.CHAR512, description = "活动描述"),
    @Field(fieldName = "eventStartURL", fieldType = FieldTypeEnum.CHAR128, description = "活动的url"),
    @Field(fieldName = "eventEndImageURL", fieldType = FieldTypeEnum.CHAR128, description = "活动结束的图片"),
    @Field(fieldName = "eventEffectiveStartTime", fieldType = FieldTypeEnum.DATETIME, description = "活动的开始时间"),
    @Field(fieldName = "eventEffectiveEndTime", fieldType = FieldTypeEnum.DATETIME, description = "活动的结束时间"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "活动的类型"),
    @Field(fieldName = "eventConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "活动配置id"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR24, description = "关键字"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertEventService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String token = applicationContext.getToken();
        String messageTitle = parameters.get("eventName");
        String messageURL = parameters.get("eventStartURL");

        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("status", String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
        EntityDao<Event> eventDAO = applicationContext.getEntityDAO(EntityNames.event);
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        List<Order> orderList = new ArrayList<Order>(1);
        Order memberCardConfigIdOrder = new Order("eventConfigId", OrderEnum.DESC);
        orderList.add(memberCardConfigIdOrder);
        EntityDao<EventConfig> entityConfigDAO = applicationContext.getEntityDAO(EntityNames.eventConfig);
        List<EventConfig> entityList = entityConfigDAO.inquireByCondition(conditionList, orderList);
        long memberCardConfigId = -1;
        if (entityList != null && !entityList.isEmpty()) {
            memberCardConfigId = entityList.get(0).getEventConfigId();
        }
        Event event = eventDAO.insert(parameters);
        if (event != null) {
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
            responseMessageMap.put("cfgType", String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
            responseMessageMap.put("relatedEventId", String.valueOf(memberCardConfigId));
            responseMessageMap.put("relatedId", String.valueOf(event.getEventId()));
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
            if (responseMessageCfg != null) {
                ResponseMessageCache.getInstance().insertCache(responseMessageCfg);
                //添加活动的系统url
                byte eventType = event.getType();
                EntityDao<Resources> entityDAO = applicationContext.getEntityDAO(EntityNames.resources);
                Map<String, String> resourceMap = new HashMap<String, String>(4, 1);
                String eventURL = "";
                if (eventType == SalesConstant.DA_ZHUAN_PAN_TYPE) {
                    eventURL = SalesConstant.DA_ZHUAN_PAN_EVENT_URL + "?token=" + token + "&eventId=" + event.getEventId();
                } else if (eventType == SalesConstant.GUA_GUA_KA_TYPE) {
                    eventURL = SalesConstant.GUA_GUA_KA_EVENT_URL + "?token=" + token + "&eventId=" + event.getEventId();
                } else {
                    eventURL = SalesConstant.SHUI_GUO_DA_REN_EVENT_URL + "?token=" + token + "&eventId=" + event.getEventId();
                }
                resourceMap.put("resourcesURL", eventURL);
                resourceMap.put("resourcesName", event.getEventName());
                resourceMap.put("resourcesType", String.valueOf(SalesConstant.SYSTEM_RESOURCE_TYPE));
                resourceMap.put("companyId", String.valueOf(companyId));
                resourceMap.put("createTime", DateTimeUtils.currentDay());
                entityDAO.insert(resourceMap);
                Map<String, String> resultMap = new HashMap<String, String>(2, 1);
                resultMap.put("eventId", String.valueOf(event.getEventId()));
                applicationContext.setMapData(resultMap);
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }

        } else {
            throw new RollBackException("操作失败");
        }

    }
}
