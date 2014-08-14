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
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.ReservationConfig;
import com.sales.entity.ResponseMessageCfg;
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
        act = ActionNames.insertReservationConfig,
        importantParameters = {"session", "encryptType",},
        minorParameters = {"reservationConfigId", "keyword", "matchType", "messageTitle", "messageImageURL"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增或修改预约基本配置",
        validateParameters = {
    @Field(fieldName = "reservationConfigId", fieldType = FieldTypeEnum.TYINT, description = "预约配置id"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配类型"),
    @Field(fieldName = "messageTitle", fieldType = FieldTypeEnum.CHAR36, description = "预约标题"),
    @Field(fieldName = "messageImageURL", fieldType = FieldTypeEnum.CHAR128, description = "预约图片"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertReservationConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertReservationConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        String reservationConfigId = parameters.get("reservationConfigId");
        String messageTitle = parameters.get("messageTitle");
        String messageURL = parameters.get("messageImageURL");
        EntityDao<ReservationConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.reservationConfig);
        if (reservationConfigId == null) {
            ReservationConfig entity = entityDAO.insert(parameters);
            if (entity != null) {
                Map<String, String> responseMessageMap = new HashMap<String, String>(4, 1);
                responseMessageMap.put("companyId", String.valueOf(companyId));
                responseMessageMap.put("type", String.valueOf(SalesConstant.RESPONSE_TEXT_TYPE));
                responseMessageMap.put("responseContentType", String.valueOf(SalesConstant.RESERVATION_TYPE));
                responseMessageMap.put("keyword", parameters.get("keyword"));
                if (messageTitle != null) {
                    responseMessageMap.put("responseContent", messageTitle);
                }
                if (messageURL != null) {
                    responseMessageMap.put("responseImageURL", messageURL);
                }
                responseMessageMap.put("cfgType", String.valueOf(SalesConstant.BASIC_CFG_TYPE));
                responseMessageMap.put("relatedEventId", String.valueOf(entity.getReservationConfigId()));
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
                if (responseMessageCfg != null) {
                     //  ResponseMessageCache.getInstance().insertCache(responseMessageCfg);
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            ReservationConfig entity = entityDAO.update(parameters);
            if (entity != null) {
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                List<Condition> conditionList = new ArrayList<Condition>(3);
                Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyIdCondition);
                Condition relateEventIdCondition = new Condition("relatedEventId", ConditionTypeEnum.EQUAL, String.valueOf(entity.getReservationConfigId()));
                conditionList.add(relateEventIdCondition);
                Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.RESERVATION_TYPE));
                conditionList.add(responseContentTypeCondition);
                Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.BASIC_CFG_TYPE));
                conditionList.add(cfgTypeCondition);
                List<Order> orderList = new ArrayList<Order>();
                Order responseMessageCfgOrder = new Order("responseMessageCfgId", OrderEnum.DESC);
                orderList.add(responseMessageCfgOrder);
                List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList, orderList);
                if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                    ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
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
                       //    ResponseMessageCache.getInstance().updateCache(responseMessageCfg);
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
