package com.sales.service;

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
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.entity.EntityNames;
import com.sales.entity.EventFlow;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireEventFlowById,
        importantParameters = {"session", "encryptType", "eventFlowId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"eventFlowId","weixinId","createTime","eventId","companyId"},
        description = "查询EventFlow详细内容",
        validateParameters = {
          	@Field(fieldName ="eventFlowId", fieldType = FieldTypeEnum.BIG_INT, description =""),
	@Field(fieldName ="weixinId", fieldType = FieldTypeEnum.CHAR36, description ="微信id"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="eventId", fieldType = FieldTypeEnum.BIG_INT, description ="参加的活动"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireEventFlowByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireEventFlowByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String eventFlowId = parameters.get("eventFlowId");
	primaryKey.putKeyField("eventFlowId",String.valueOf(eventFlowId));

        EntityDao<EventFlow> entityDAO = applicationContext.getEntityDAO(EntityNames.eventFlow);
          EventFlow entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
