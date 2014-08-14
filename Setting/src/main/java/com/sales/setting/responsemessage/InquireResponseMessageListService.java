package com.sales.setting.responsemessage;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.ResponseMessageCfg;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireResponseMessageList,
        importantParameters = {"session", "type", "encryptType"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"responseMessageCfgId", "responseContent", "responseImageURL", "responseAudio", "keyword"},
        description = "查询回应消息列表",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "回应消息类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireResponseMessageListService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String type = parameters.get("type");
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyCondition);
        Condition typeCondition = new Condition("type", ConditionTypeEnum.EQUAL, type);
        conditionList.add(typeCondition);
        EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        List<ResponseMessageCfg>  responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList);
        applicationContext.setEntityList(responseMessageCfgList);
        applicationContext.success();

    }
}
