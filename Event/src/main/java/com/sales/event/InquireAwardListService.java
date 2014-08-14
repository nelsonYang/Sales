package com.sales.event;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PageModel;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.framework.utils.SetUtils;
import com.sales.config.SalesActionName;
import com.sales.entity.Award;
import com.sales.entity.EntityNames;
import com.sales.entity.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireAwardList,
        importantParameters = {"session", "encryptType", "pageNo", "pageCount"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"awardId", "awardName", "eventName", "awardNum", "awardNumPerPerson", "awardDesc"},
        description = "查询奖项设置列表",
        validateParameters = {
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "页数"),
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "每页显示的数量"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireAwardListService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        int pageNo = Integer.parseInt(parameters.get("pageNo"));
        int pageCount = Integer.parseInt(parameters.get("pageCount"));
        List<Condition> conditionList = new ArrayList<Condition>(1);
        Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyCondition);
        EntityDao<Award> awardDao = applicationContext.getEntityDAO(EntityNames.award);
        PageModel pageModel = awardDao.inquirePageByCondition(conditionList, pageNo, pageCount);
        List<Award> awardList = pageModel.getDataList();
        if (awardList != null && !awardList.isEmpty()) {
            Set<Long> eventIdSet = new HashSet<Long>(awardList.size());
            for (Award award : awardList) {
                eventIdSet.add(award.getEventId());
            }
            conditionList = new ArrayList<Condition>(2);
            conditionList.add(companyCondition);
            Condition eventCondition = new Condition("eventId", ConditionTypeEnum.IN, SetUtils.LongSet2Str(eventIdSet));
            conditionList.add(eventCondition);
            EntityDao<Event> eventDao = applicationContext.getEntityDAO(EntityNames.event);
            List<Event> eventList = eventDao.inquireByCondition(conditionList);
            List<Map<String, String>> awardMapList = new ArrayList<Map<String, String>>(awardList.size());
            Map<String, String> awardMap;
            for (Award award : awardList) {
                awardMap = award.toMap();
                awardMap.put("eventName", "");
                awardMapList.add(awardMap);
                for (Event event : eventList) {
                    if (event.getEventId() == award.getEventId()) {
                        awardMap.put("eventName", event.getEventName());
                        break;
                    }
                }
            }
            Map<String, List<Map<String, String>>> listMap = new HashMap<String, List<Map<String, String>>>(2, 1);
            listMap.put("awardList", awardMapList);
            applicationContext.setTotalPage(pageModel.getTotalPage());
            applicationContext.setCount(pageModel.getTotalCount());
            applicationContext.setListMapData(listMap);
            applicationContext.success();
        } else {
            applicationContext.noData();
        }
    }
}
