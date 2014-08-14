package com.sales.resources;

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
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.ResponseMessageCfg;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateResponseMessageCfg,
        importantParameters = {"session", "encryptType", "responseMessageCfgId"},
        minorParameters = {"responseContent", "responseImageURL", "responseAudio", "type", "relatedEventId", "keyword", "relatedURL", "isClose", "responseContentType", "relatedId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改ResponseMessageCfg",
        validateParameters = {
    @Field(fieldName = "responseMessageCfgId", fieldType = FieldTypeEnum.BIG_INT, description = "回复信息配置表"),
    @Field(fieldName = "responseContent", fieldType = FieldTypeEnum.CHAR128, description = "回复内容"),
    @Field(fieldName = "responseImageURL", fieldType = FieldTypeEnum.CHAR64, description = "回复图片"),
    @Field(fieldName = "responseAudio", fieldType = FieldTypeEnum.CHAR64, description = "回复音频"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "回复的类型"),
    @Field(fieldName = "relatedEventId", fieldType = FieldTypeEnum.BIG_INT, description = "关联的活动id"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "relatedURL", fieldType = FieldTypeEnum.CHAR64, description = "相关的url"),
    @Field(fieldName = "isClose", fieldType = FieldTypeEnum.TYINT, description = "1-开启 2-关闭"),
    @Field(fieldName = "responseContentType", fieldType = FieldTypeEnum.TYINT, description = "回复类型"),
    @Field(fieldName = "relatedId", fieldType = FieldTypeEnum.BIG_INT, description = "关联id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateResponseMessageCfgService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateResponseMessageCfgService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        EntityDao<ResponseMessageCfg> entityDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        ResponseMessageCfg entity = entityDAO.update(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
