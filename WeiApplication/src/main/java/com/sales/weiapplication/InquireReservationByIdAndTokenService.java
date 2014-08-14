package com.sales.weiapplication;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.enumeration.OrderEnum;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.Reservation;
import com.sales.entity.ResponseMessageCfg;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireReservationByIdAndToken,
        importantParameters = {"reservationId", "token"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"reservationId", "keyword", "matchType", "messageTitle", "messageImageURL", "reservationConfigId", "reservationAddress", "reservationTelephone", "reservationDesc", "companyId"},
        description = "查询Reservation详细内容",
        validateParameters = {
    @Field(fieldName = "reservationId", fieldType = FieldTypeEnum.BIG_INT, description = "预约"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问token")
})
public class InquireReservationByIdAndTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireReservationByIdAndTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            logger.debug("parameters={}", parameters);
            PrimaryKey primaryKey = new PrimaryKey();
            String reservationId = parameters.get("reservationId");
            primaryKey.putKeyField("reservationId", String.valueOf(reservationId));
            primaryKey.putKeyField("companyId", tokens[1]);
            EntityDao<Reservation> entityDAO = applicationContext.getEntityDAO(EntityNames.reservation);
            Reservation entity = entityDAO.inqurieByKey(primaryKey);
            if (entity != null) {
                Map<String, String> reservationMap = entity.toMap();
                reservationMap.put("keyword", "");
                reservationMap.put("matchType", "");
                reservationMap.put("messageTitle", "");
                reservationMap.put("messageImageURL", "");
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                List<Condition> conditionList = new ArrayList<Condition>(3);
                Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
                conditionList.add(companyIdCondition);
                Condition relateEventIdCondition = new Condition("relatedId", ConditionTypeEnum.EQUAL, reservationId);
                conditionList.add(relateEventIdCondition);
                Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.RESERVATION_TYPE));
                conditionList.add(responseContentTypeCondition);
                Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
                conditionList.add(cfgTypeCondition);
                List<Order> orderList = new ArrayList<Order>();
                Order responseMessageCfgOrder = new Order("responseMessageCfgId", OrderEnum.DESC);
                orderList.add(responseMessageCfgOrder);
                List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList, orderList);
                if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                    ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                    reservationMap.putAll(responseMessageCfg.toMap());
                }
                applicationContext.setMapData(reservationMap);
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }
    }
}
