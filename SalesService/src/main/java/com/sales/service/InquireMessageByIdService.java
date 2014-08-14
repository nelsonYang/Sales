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
import com.sales.entity.Message;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireMessageById,
        importantParameters = {"session", "encryptType", "messageId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"messageId","imageURL","content","openID","type","isSend","createTime","companyId"},
        description = "查询Message详细内容",
        validateParameters = {
          	@Field(fieldName ="messageId", fieldType = FieldTypeEnum.BIG_INT, description ="消息id"),
	@Field(fieldName ="imageURL", fieldType = FieldTypeEnum.CHAR64, description ="消息图片的url"),
	@Field(fieldName ="content", fieldType = FieldTypeEnum.CHAR128, description ="消息内容"),
	@Field(fieldName ="openID", fieldType = FieldTypeEnum.CHAR64, description ="发送对象"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="消息类别"),
	@Field(fieldName ="isSend", fieldType = FieldTypeEnum.TYINT, description ="是否已经发送"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.CHAR36, description ="消息创建的时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description =""),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMessageByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireMessageByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String messageId = parameters.get("messageId");
	primaryKey.putKeyField("messageId",String.valueOf(messageId));

        EntityDao<Message> entityDAO = applicationContext.getEntityDAO(EntityNames.message);
          Message entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
