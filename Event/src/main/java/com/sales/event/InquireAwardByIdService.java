package com.sales.event;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
import com.sales.entity.Award;
import com.sales.entity.EntityNames;
import com.sales.entity.Event;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireAwardById,
        importantParameters = {"session", "encryptType", "awardId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"awardId", "awardName", "eventName", "awardNum", "awardNumPerPerson", "awardDesc"},
        description = "查询奖项",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "awardId", fieldType = FieldTypeEnum.BIG_INT, description = "奖项id"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireAwardByIdService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String awardIdStr = parameters.get("awardId");
        EntityDao<Award> awardDao = applicationContext.getEntityDAO(EntityNames.award);
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("awardId", awardIdStr);
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        Award award = awardDao.inqurieByKey(primaryKey);
        if (award != null) {
            EntityDao<Event> eventDAO = applicationContext.getEntityDAO(EntityNames.event);
            primaryKey = new PrimaryKey();
            primaryKey.putKeyField("eventId", String.valueOf(award.getEventId()));
            primaryKey.putKeyField("companyId", String.valueOf(companyId));
            Event event = eventDAO.inqurieByKey(primaryKey);
            Map<String,String>dataMap = award.toMap();
            dataMap.put("eventName", event.getEventName());
            applicationContext.setMapData(dataMap);
            applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.ENTITY_NOT_FOUND);
        }


    }
}
