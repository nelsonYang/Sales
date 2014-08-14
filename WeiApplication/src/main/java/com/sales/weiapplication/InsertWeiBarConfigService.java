package com.sales.weiapplication;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.enumeration.OrderEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.DateTimeUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.Resources;
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
        act = ActionNames.insertWeiBarConfig,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"weiBarConfigId", "keyword", "matchType", "messageTitle", "messageImageURL"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增修改微吧配置",
        validateParameters = {
    @Field(fieldName = "weiBarConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "微吧配置id"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配度1-精确2-模糊"),
    @Field(fieldName = "messageTitle", fieldType = FieldTypeEnum.CHAR36, description = "消息标题"),
    @Field(fieldName = "messageImageURL", fieldType = FieldTypeEnum.CHAR64, description = "图片url"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertWeiBarConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertWeiBarConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String token = applicationContext.getToken();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        String weiBarConfigId = parameters.get("weiBarConfigId");
        String messageTitle = parameters.get("messageTitle");
        String messageURL = parameters.get("messageImageURL");
        EntityDao<WeiBarConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiBarConfig);
        if (weiBarConfigId == null || weiBarConfigId.isEmpty()) {
            WeiBarConfig entity = entityDAO.insert(parameters);
            if (entity != null) {
                Map<String, String> responseMessageMap = new HashMap<String, String>(4, 1);
                responseMessageMap.put("companyId", String.valueOf(companyId));
                responseMessageMap.put("type", String.valueOf(SalesConstant.RESPONSE_TEXT_TYPE));
                responseMessageMap.put("responseContentType", String.valueOf(SalesConstant.WEIBAR_TYPE));
                responseMessageMap.put("keyword", parameters.get("keyword"));
                if (messageTitle != null) {
                    responseMessageMap.put("responseContent", messageTitle);
                }
                if (messageURL != null) {
                    responseMessageMap.put("responseImageURL", messageURL);
                }
                responseMessageMap.put("relatedEventId", String.valueOf(entity.getWeiBarConfigId()));
                responseMessageMap.put("cfgType", String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
                if (responseMessageCfg != null) {
                    ResponseMessageCache.getInstance().insertCache(responseMessageCfg);
                    EntityDao<Resources> resourcesDAO = applicationContext.getEntityDAO(EntityNames.resources);
                    Map<String, String> resourceMap = new HashMap<String, String>(4, 1);
                    String reservationURL = SalesConstant.WEIBAR_URL + "?token=" + token + "&weiBarConfigId=" + entity.getWeiBarConfigId();
                    resourceMap.put("resourcesURL", reservationURL);
                    resourceMap.put("resourcesName",entity.getMessageTitle());
                    resourceMap.put("resourcesType", String.valueOf(SalesConstant.SYSTEM_RESOURCE_TYPE));
                    resourceMap.put("companyId", String.valueOf(companyId));
                    resourceMap.put("createTime", DateTimeUtils.currentDay());
                    resourcesDAO.insert(resourceMap);
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }

            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            WeiBarConfig entity = entityDAO.update(parameters);
            if (entity != null) {
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                List<Condition> conditionList = new ArrayList<Condition>(2);
                Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyIdCondition);
                Condition relatedEventIdCondition = new Condition("relatedEventId", ConditionTypeEnum.EQUAL, String.valueOf(entity.getWeiBarConfigId()));
                conditionList.add(relatedEventIdCondition);
                Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.WEIBAR_TYPE));
                conditionList.add(responseContentTypeCondition);
                Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
                conditionList.add(cfgTypeCondition);
                List<Order> orderList = new ArrayList<Order>();
                Order responseMessageCfgOrder = new Order("responseMessageCfgId", OrderEnum.DESC);
                orderList.add(responseMessageCfgOrder);
                List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList, orderList);
                if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                    ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                    String responseKeyword = responseMessageCfg.getKeyword();
                    Map<String, String> responseMessageMap = new HashMap<String, String>(2, 1);
                    responseMessageMap.put("responseMessageCfgId", String.valueOf(responseMessageCfg.getResponseMessageCfgId()));
                    responseMessageMap.put("keyword", parameters.get("keyword"));
                    if (messageTitle != null) {
                        responseMessageMap.put("responseContent", messageTitle);
                    }
                    if (messageURL != null) {
                        responseMessageMap.put("responseImageURL", messageURL);
                    }
                    responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                    if (responseMessageCfg != null) {
                        ResponseMessageCache.getInstance().updateCache(responseMessageCfg, responseKeyword);
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
}
