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
import com.sales.entity.Reservation;
import com.sales.entity.ReservationConfig;
import com.sales.entity.Resources;
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
        act = ActionNames.insertReservation,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"keyword", "matchType", "reservationConfigId","messageTitle", "messageImageURL", "reservationAddress", "reservationTelephone", "reservationDesc"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增预约",
        validateParameters = {
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "reservationConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "预约"),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配度1-精确2-模糊"),
    @Field(fieldName = "messageTitle", fieldType = FieldTypeEnum.CHAR36, description = "消息标题"),
    @Field(fieldName = "messageImageURL", fieldType = FieldTypeEnum.CHAR128, description = "图片url"),
    @Field(fieldName = "reservationAddress", fieldType = FieldTypeEnum.CHAR36, description = "预约地址"),
    @Field(fieldName = "reservationTelephone", fieldType = FieldTypeEnum.CHAR36, description = "预约的电话"),
    @Field(fieldName = "reservationDesc", fieldType = FieldTypeEnum.CHAR128, description = "预约说明"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertReservationService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertReservationService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String token = applicationContext.getToken();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("status", String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
        logger.debug("parameters={}", parameters);
        String messageTitle = parameters.get("messageTitle");
        String messageURL = parameters.get("messageImageURL");
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        List<Order> orderList = new ArrayList<Order>(1);
        Order reservationConfigIdOrder = new Order("reservationConfigId", OrderEnum.DESC);
        orderList.add(reservationConfigIdOrder);
        EntityDao<ReservationConfig> entityConfigDAO = applicationContext.getEntityDAO(EntityNames.reservationConfig);
        List<ReservationConfig> entityList = entityConfigDAO.inquireByCondition(conditionList, orderList);
        long reservationConfigId = -1;
        String reservationName = null;
        if (entityList != null && !entityList.isEmpty()) {
            reservationConfigId = entityList.get(0).getReservationConfigId();
            reservationName = entityList.get(0).getMessageTitle();
        }
        EntityDao<Reservation> entityDAO = applicationContext.getEntityDAO(EntityNames.reservation);
        Reservation entity = entityDAO.insert(parameters);
        if (entity != null) {
            Map<String, String> responseMessageMap = new HashMap<String, String>(4, 1);
            responseMessageMap.put("companyId", String.valueOf(companyId));
            responseMessageMap.put("type", String.valueOf(SalesConstant.RESPONSE_TEXT_TYPE));
            responseMessageMap.put("responseContentType", String.valueOf(SalesConstant.RESERVATION_TYPE));
            responseMessageMap.put("keyword", parameters.get("keyword"));
            responseMessageMap.put("cfgType", String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
            if (messageTitle != null) {
                responseMessageMap.put("responseContent", messageTitle);
            }
            if (messageURL != null) {
                responseMessageMap.put("responseImageURL", messageURL);
            }
            responseMessageMap.put("relatedEventId", String.valueOf(reservationConfigId));
            responseMessageMap.put("relatedId", String.valueOf(entity.getReservationId()));
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
            if (responseMessageCfg != null) {
                ResponseMessageCache.getInstance().insertCache(responseMessageCfg);
                 EntityDao<Resources> resourcesDAO = applicationContext.getEntityDAO(EntityNames.resources);
                Map<String, String> resourceMap = new HashMap<String, String>(4, 1);
                String  reservationURL = SalesConstant.RESERVATION_URL + "?token=" + token + "&reservationId=" + entity.getReservationId();
                resourceMap.put("resourcesURL", reservationURL);
                resourceMap.put("resourcesName", reservationName == null ? entity.getReservationDesc() : reservationName);
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

    }
}
