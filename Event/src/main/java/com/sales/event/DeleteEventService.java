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
import com.sales.config.SalesActionName;
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
        act = SalesActionName.deleteEvent,
        importantParameters = {"session", "encryptType", "eventId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireTranscation = true,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除活动",
        validateParameters = {
    @Field(fieldName = "eventId", fieldType = FieldTypeEnum.BIG_INT, description = "活动id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteEventService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String eventId = parameters.get("eventId");
        EntityDao<Event> eventDAO = applicationContext.getEntityDAO(EntityNames.event);
        PrimaryKey eventPK = new PrimaryKey();
        eventPK.putKeyField("companyId", String.valueOf(companyId));
        eventPK.putKeyField("eventId", eventId);
        boolean isSuccess = eventDAO.delete(eventPK);
        if (isSuccess) {
            EntityDao<Award> awardDao = applicationContext.getEntityDAO(EntityNames.award);
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            Condition eventIdCondition = new Condition("eventId", ConditionTypeEnum.EQUAL, eventId);
            conditionList.add(eventIdCondition);
            conditionList.add(companyCondition);
            List<Award> awardList = awardDao.inquireByCondition(conditionList);
            PrimaryKey awardPK;
            for (Award award : awardList) {
                awardPK = new PrimaryKey();
                awardPK.putKeyField("companyId", String.valueOf(companyId));
                awardPK.putKeyField("awardId", String.valueOf(award.getAwardId()));
                awardDao.delete(awardPK);
            }
            applicationContext.success();
        } else {
            applicationContext.fail();
        }

    }
}
