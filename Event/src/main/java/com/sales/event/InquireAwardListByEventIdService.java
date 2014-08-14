package com.sales.event;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.framework.utils.ConverterUtils;
import com.framework.utils.JsonUtils;
import com.sales.config.ActionNames;
import com.sales.config.SalesErrorCode;
import com.sales.entity.Award;
import com.sales.entity.EntityNames;
import com.sales.entity.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireAwardsListByEventId,
        importantParameters = {"eventId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        requireLogin = LoginEnum.REQUIRE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"eventId", "eventName", "eventStartURL", "eventEndImageURL", "eventEffectiveStartTime", "eventEffectiveEndTime", "awards"},
        description = "查询活动和奖项列表",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "eventId", fieldType = FieldTypeEnum.BIG_INT, description = "菜单id"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireAwardListByEventIdService implements Service {
    
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String eventIdStr = parameters.get("eventId");
        EntityDao<Event> eventDAO = applicationContext.getEntityDAO(EntityNames.event);
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("eventId", eventIdStr);
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        Event event = eventDAO.inqurieByKey(primaryKey);
        if (event != null) {
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            Condition eventCondition = new Condition("eventId", ConditionTypeEnum.EQUAL, eventIdStr);
            conditionList.add(companyCondition);
            conditionList.add(eventCondition);
            EntityDao<Award> awardDao = applicationContext.getEntityDAO(EntityNames.award);
            List<Award> awardList = awardDao.inquireByCondition(conditionList);
            List<Map<String, String>> awardMapList = ConverterUtils.toMapList(awardList);
            String json = JsonUtils.mapListToJsonArray(awardMapList);
            Map<String, String> dataMap = event.toMap();
            dataMap.put("awards", json);
            applicationContext.setMapData(dataMap);
            applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.ENTITY_NOT_FOUND);
        }
     }
}
