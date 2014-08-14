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
import com.sales.entity.Message;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteMessage,
        importantParameters = {"session", "encryptType","messageId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Message操作",
        validateParameters = {
    	@Field(fieldName ="messageId", fieldType = FieldTypeEnum.BIG_INT, description ="消息id"),
	@Field(fieldName ="imageURL", fieldType = FieldTypeEnum.CHAR64, description ="消息图片的url"),
	@Field(fieldName ="content", fieldType = FieldTypeEnum.CHAR128, description ="消息内容"),
	@Field(fieldName ="openID", fieldType = FieldTypeEnum.CHAR64, description ="发送对象"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="消息类别"),
	@Field(fieldName ="isSend", fieldType = FieldTypeEnum.TYINT, description ="是否已经发送"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.CHAR36, description ="消息创建的时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description =""),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteMessageService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteMessageService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String messageId = parameters.get("messageId");
	primaryKey.putKeyField("messageId",String.valueOf(messageId));

        EntityDao<Message> entityDAO = applicationContext.getEntityDAO(EntityNames.message);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
