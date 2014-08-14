package com.sales.weiapplication;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.enumeration.OrderEnum;
import com.framework.entity.pojo.PageModel;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.SetUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.entity.EntityNames;
import com.sales.entity.Reservation;
import com.sales.entity.ResponseMessageCfg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquirePageReservationList,
        importantParameters = {"session", "pageCount", "pageNo", "encryptType"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"reservationId", "keyword", "matchType", "messageTitle", "messageImageURL", "reservationConfigId", "reservationAddress", "reservationTelephone", "reservationDesc", "companyId"},
        description = "分页查询Reservation",
        validateParameters = {
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePageReservationListService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquirePageReservationListService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        String pageCount = parameters.get("pageCount");
        String pageNo = parameters.get("pageNo");
        EntityDao<Reservation> entityDAO = applicationContext.getEntityDAO(EntityNames.reservation);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        PageModel enityPageMode = entityDAO.inquirePageByCondition(conditionList, Integer.parseInt(pageNo), Integer.parseInt(pageCount));
        List<Reservation> entityList = enityPageMode.getDataList();
        if (entityList != null) {
            if (!entityList.isEmpty()) {
                Set<Long> reservationIdSet = new HashSet<Long>(entityList.size());
                for (Reservation reservation : entityList) {
                    reservationIdSet.add(reservation.getReservationId());
                }
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                conditionList = new ArrayList<Condition>(3);
                companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyIdCondition);
                Condition relateEventIdCondition = new Condition("relatedId", ConditionTypeEnum.IN, SetUtils.LongSet2Str(reservationIdSet));
                conditionList.add(relateEventIdCondition);
                Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.RESERVATION_TYPE));
                conditionList.add(responseContentTypeCondition);
                Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
                conditionList.add(cfgTypeCondition);
                List<Order> orderList = new ArrayList<Order>();
                Order responseMessageCfgOrder = new Order("responseMessageCfgId", OrderEnum.DESC);
                orderList.add(responseMessageCfgOrder);
                List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList, orderList);
                List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
                Map<String, String> reservationMap;
                Map<String, String> responseMessageMap;
                for (Reservation reservation : entityList) {
                    reservationMap = reservation.toMap();
                    reservationMap.put("keyword", "");
                    reservationMap.put("matchType", "");
                    reservationMap.put("messageTitle", "");
                    reservationMap.put("messageImageURL", "");
                    for (ResponseMessageCfg responseMessageCfg : responseMessageCfgList) {
                        if (reservation.getReservationId() == responseMessageCfg.getRelatedId()) {
                            responseMessageMap = responseMessageCfg.toMap();
                            reservationMap.put("keyword", responseMessageMap.get("keyword"));
                            reservationMap.put("messageTitle", responseMessageMap.get("responseContent"));
                            reservationMap.put("messageImageURL", responseMessageMap.get("responseImageURL"));
                            break;
                        }
                    }
                    resultMapList.add(reservationMap);
                }
                Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>(2, 1);
                resultMap.put("reservationList", resultMapList);
                applicationContext.setListMapData(resultMap);
                applicationContext.setCount(enityPageMode.getTotalCount());
                applicationContext.setTotalPage(enityPageMode.getTotalPage());
                applicationContext.success();
            } else {
                applicationContext.noData();
            }
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
