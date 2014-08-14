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
import com.sales.entity.EntityNames;
import com.sales.entity.ReservationConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireReservationConfigList,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"reservationConfigId", "keyword", "matchType", "messageTitle", "messageImageURL", "companyId"},
        description = "查询预约配置",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireReservationConfigListService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireReservationConfigListService.class);
    
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        EntityDao<ReservationConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.reservationConfig);
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        List<Order> orderList = new ArrayList<Order>(1);
        Order reservationConfigIdOrder = new Order("reservationConfigId",OrderEnum.DESC);
        orderList.add(reservationConfigIdOrder);
        List<ReservationConfig> entityList = entityDAO.inquireByCondition(conditionList,orderList);
        if (entityList != null) {
            if (!entityList.isEmpty()) {
                applicationContext.setEntityData(entityList.get(0));
                applicationContext.success();
            }else{
                applicationContext.noData();
            }
        } else {
            throw new RollBackException("操作失败");
        }
        
    }
}
