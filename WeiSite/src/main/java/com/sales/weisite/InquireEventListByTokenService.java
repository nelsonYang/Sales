package com.sales.weisite;

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
import com.framework.utils.Base64Utils;
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
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
        act = SalesActionName.inquireEventListByTokenAndType,
        importantParameters = {"token", "weixinId", "type", "pageNo", "pageCount"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"eventId", "eventName", "eventEffectiveStartTime", "eventEffectiveEndTime", "readCount", "clickCount", "keyword", "status"},
        description = "查询活动列表",
        validateParameters = {
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "页数"),
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "每页显示的数量"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问者的token类型"),
    @Field(fieldName = "weixinId", fieldType = FieldTypeEnum.CHAR1024, description = "访问者微信号"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "类型")
})
public class InquireEventListByTokenService implements Service {
    
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String weixinId = parameters.get("weixinId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            int pageNo = Integer.parseInt(parameters.get("pageNo"));
            int pageCount = Integer.parseInt(parameters.get("pageCount"));
            List<Condition> conditionList = new ArrayList<Condition>(1);
            Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyCondition);
            EntityDao<Event> eventDao = applicationContext.getEntityDAO(EntityNames.event);
            PageModel pageModel = eventDao.inquirePageByCondition(conditionList, pageNo, pageCount);
            List<Event> eventList = pageModel.getDataList();
            applicationContext.setTotalPage(pageModel.getTotalPage());
            applicationContext.setCount(pageModel.getTotalCount());
            applicationContext.setEntityList(eventList);
            applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }
    }
}
