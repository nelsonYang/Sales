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
import com.sales.entity.WeixinMessage;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeixinMessageById,
        importantParameters = {"session", "encryptType", "weixinMessageId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weixinMessageId","fromUser","toUser","type","createTime","content","imageURL","vedioURL","audioURL","companyId"},
        description = "查询WeixinMessage详细内容",
        validateParameters = {
          	@Field(fieldName ="weixinMessageId", fieldType = FieldTypeEnum.BIG_INT, description =""),
	@Field(fieldName ="fromUser", fieldType = FieldTypeEnum.CHAR36, description ="消息的来源"),
	@Field(fieldName ="toUser", fieldType = FieldTypeEnum.CHAR36, description ="消息的接收者"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="消息的类型"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description =""),
	@Field(fieldName ="content", fieldType = FieldTypeEnum.CHAR128, description ="消息的文本内容"),
	@Field(fieldName ="imageURL", fieldType = FieldTypeEnum.CHAR64, description ="图片的内容"),
	@Field(fieldName ="vedioURL", fieldType = FieldTypeEnum.CHAR64, description ="图片的内容"),
	@Field(fieldName ="audioURL", fieldType = FieldTypeEnum.CHAR64, description ="音频的内容"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeixinMessageByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireWeixinMessageByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String weixinMessageId = parameters.get("weixinMessageId");
	primaryKey.putKeyField("weixinMessageId",String.valueOf(weixinMessageId));

        EntityDao<WeixinMessage> entityDAO = applicationContext.getEntityDAO(EntityNames.weixinMessage);
          WeixinMessage entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
