package com.sales.service;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.entity.EntityNames;
import com.sales.entity.Event;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteEvent,
        importantParameters = {"session", "encryptType","eventId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Event操作",
        validateParameters = {
    	@Field(fieldName ="eventId", fieldType = FieldTypeEnum.INT, description ="活动id"),
	@Field(fieldName ="eventName", fieldType = FieldTypeEnum.CHAR36, description ="活动名称"),
	@Field(fieldName ="eventDesc", fieldType = FieldTypeEnum.CHAR128, description ="活动描述"),
	@Field(fieldName ="eventStartURL", fieldType = FieldTypeEnum.CHAR64, description ="活动开始图片"),
	@Field(fieldName ="eventEndImageURL", fieldType = FieldTypeEnum.CHAR64, description ="活动结束图片"),
	@Field(fieldName ="eventEffectiveStartTime", fieldType = FieldTypeEnum.DATETIME, description ="活动生效的开始时间"),
	@Field(fieldName ="eventEffectiveEndTime", fieldType = FieldTypeEnum.DATETIME, description ="活动生效的结束时间"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="活动类型 1-瓜瓜卡 2-水果达人 3-欢乐打转盘"),
	@Field(fieldName ="readCount", fieldType = FieldTypeEnum.INT, description ="活动被浏览次数"),
	@Field(fieldName ="clickCount", fieldType = FieldTypeEnum.INT, description ="活动被点击的次数"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="status", fieldType = FieldTypeEnum.TYINT, description ="状态"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键字"),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配类型"),
	@Field(fieldName ="isRemindOpen", fieldType = FieldTypeEnum.TYINT, description ="是否开启手机通知"),
	@Field(fieldName ="eventNum", fieldType = FieldTypeEnum.BIG_INT, description ="活动的数量"),
	@Field(fieldName ="eventCurrentNum", fieldType = FieldTypeEnum.BIG_INT, description ="活动当前剩下的数量"),
	@Field(fieldName ="eventConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="活动配置id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteEventService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteEventService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String eventId = parameters.get("eventId");
	primaryKey.putKeyField("eventId",String.valueOf(eventId));

        EntityDao<Event> entityDAO = applicationContext.getEntityDAO(EntityNames.event);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
