package com.sales.service;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
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
        act = ActionNames.insertWeixinMessage,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"fromUser","toUser","type","createTime","content","imageURL","vedioURL","audioURL","companyId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增WeixinMessage",
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

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertWeixinMessageService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertWeixinMessageService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<WeixinMessage> entityDAO = applicationContext.getEntityDAO(EntityNames.weixinMessage);
        WeixinMessage entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
