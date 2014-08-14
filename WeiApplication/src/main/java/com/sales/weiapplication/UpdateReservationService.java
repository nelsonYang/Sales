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
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.Reservation;
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
        act = ActionNames.updateReservation,
        importantParameters = {"session", "encryptType", "reservationId"},
        minorParameters = {"keyword", "matchType", "reservationConfigId", "messageTitle", "messageImageURL", "reservationAddress", "reservationTelephone", "reservationDesc"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改预约",
        validateParameters = {
    @Field(fieldName = "reservationConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "预约"),
    @Field(fieldName = "reservationId", fieldType = FieldTypeEnum.BIG_INT, description = "预约"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配度1-精确2-模糊"),
    @Field(fieldName = "messageTitle", fieldType = FieldTypeEnum.CHAR36, description = "消息标题"),
    @Field(fieldName = "messageImageURL", fieldType = FieldTypeEnum.CHAR128, description = "图片url"),
    @Field(fieldName = "reservationAddress", fieldType = FieldTypeEnum.CHAR36, description = "预约地址"),
    @Field(fieldName = "reservationTelephone", fieldType = FieldTypeEnum.CHAR36, description = "预约的电话"),
    @Field(fieldName = "reservationDesc", fieldType = FieldTypeEnum.CHAR128, description = "预约说明"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateReservationService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateReservationService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        String reservationId = parameters.get("reservationId");
        String keyword = parameters.get("keyword");
        String matchType = parameters.get("matchType");
        String messageTitle = parameters.get("messageTitle");
        String messageImageURL = parameters.get("messageImageURL");

        EntityDao<Reservation> entityDAO = applicationContext.getEntityDAO(EntityNames.reservation);
        Reservation entity = entityDAO.update(parameters);
        if (entity != null) {
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            List<Condition> conditionList = new ArrayList<Condition>(3);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            conditionList.add(companyIdCondition);
            Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.RESERVATION_TYPE));
            conditionList.add(responseContentTypeCondition);
            Condition relateEventIdCondition = new Condition("relatedId", ConditionTypeEnum.EQUAL, reservationId);
            conditionList.add(relateEventIdCondition);
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
                if (keyword != null && !keyword.isEmpty()) {
                    responseMessageMap.put("keyword", keyword);
                }
                if (matchType != null && !matchType.isEmpty()) {
                    responseMessageMap.put("matchType", matchType);
                }
                if (messageTitle != null && !messageTitle.isEmpty()) {
                    responseMessageMap.put("messageTitle", messageTitle);
                }
                if (messageImageURL != null && !messageImageURL.isEmpty()) {
                    responseMessageMap.put("messageImageURL", messageImageURL);
                }
                responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                if (responseMessageCfg != null) {
                    ResponseMessageCache.getInstance().updateCache(responseMessageCfg, responseKeyword);
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                throw new RollBackException("操作失败");
            }
        }
    }
}
