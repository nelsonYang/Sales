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
import com.sales.config.SalesActionName;
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
        act = SalesActionName.inquireEventList,
        importantParameters = {"session", "encryptType", "pageNo", "pageCount"},
        minorParameters = {"type"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"eventId", "eventName", "eventEffectiveStartTime", "eventEffectiveEndTime", "readCount", "clickCount", "keyword", "status","eventDesc"},
        description = "查询活动列表",
        validateParameters = {
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "页数"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "活动类型"),
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "每页显示的数量"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireEventListService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        int pageNo = Integer.parseInt(parameters.get("pageNo"));
        int pageCount = Integer.parseInt(parameters.get("pageCount"));
        String type = parameters.get("type");
        List<Condition> conditionList = new ArrayList<Condition>(1);
        Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyCondition);
        if (type != null && !type.isEmpty()) {
            Condition typeCondition = new Condition("type", ConditionTypeEnum.EQUAL, type);
            conditionList.add(typeCondition);
        }
        EntityDao<Event> eventDao = applicationContext.getEntityDAO(EntityNames.event);
        PageModel pageModel = eventDao.inquirePageByCondition(conditionList, pageNo, pageCount);
        List<Event> eventList = pageModel.getDataList();
        applicationContext.setTotalPage(pageModel.getTotalPage());
        applicationContext.setCount(pageModel.getTotalCount());
        applicationContext.setEntityList(eventList);
        applicationContext.success();
    }
}
