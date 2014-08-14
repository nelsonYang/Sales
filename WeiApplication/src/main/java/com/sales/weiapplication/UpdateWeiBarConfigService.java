package com.sales.weiapplication;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
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
import com.sales.config.SalesConstant;
import com.sales.entity.EntityNames;
import com.sales.entity.ResponseMessageCfg;
import com.sales.entity.WeiBarConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateWeiBarConfig,
        importantParameters = {"session", "encryptType", "weiBarConfigId"},
        minorParameters = {"keyword", "matchType", "messageTitle", "messageImageURL"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改WeiBarConfig",
        validateParameters = {
    @Field(fieldName = "weiBarConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "微吧配置id"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配度1-精确2-模糊"),
    @Field(fieldName = "messageTitle", fieldType = FieldTypeEnum.CHAR36, description = "消息标题"),
    @Field(fieldName = "messageImageURL", fieldType = FieldTypeEnum.CHAR128, description = "图片url"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateWeiBarConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateWeiBarConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        EntityDao<WeiBarConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiBarConfig);
        WeiBarConfig entity = entityDAO.update(parameters);
        if (entity != null) {
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            conditionList.add(companyIdCondition);
            Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.WEIBAR_TYPE));
            conditionList.add(responseContentTypeCondition);
            Condition relateIdIdCondition = new Condition("relatedEventId", ConditionTypeEnum.EQUAL, String.valueOf(entity.getWeiBarConfigId()));
            conditionList.add(relateIdIdCondition);
            List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList);
            if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                Map<String, String> responseMessageMap = new HashMap<String, String>(2, 1);
                responseMessageMap.put("responseMessageCfgId", String.valueOf(responseMessageCfg.getResponseMessageCfgId()));
                responseMessageMap.put("keyword", parameters.get("keyword"));
                responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                if (responseMessageCfg != null) {
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                applicationContext.success();
            }
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
