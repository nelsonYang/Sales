package com.sales.service;

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
import com.sales.entity.EntityNames;
import com.sales.entity.PayOrder;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updatePayOrder,
        importantParameters = {"session", "encryptType","payOrderId"},
        minorParameters = {"payOrderName","payOrderSuject","payOrderWay","payOrderMoney","createTime","companyId","payOrderStatus","thirdPartyPayOrder","payAccount"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改PayOrder",
        validateParameters = {
       	@Field(fieldName ="payOrderId", fieldType = FieldTypeEnum.BIG_INT, description ="支付订单id"),
	@Field(fieldName ="payOrderName", fieldType = FieldTypeEnum.CHAR36, description ="支付订单名称"),
	@Field(fieldName ="payOrderSuject", fieldType = FieldTypeEnum.CHAR36, description ="支付项目"),
	@Field(fieldName ="payOrderWay", fieldType = FieldTypeEnum.TYINT, description ="支付方式"),
	@Field(fieldName ="payOrderMoney", fieldType = FieldTypeEnum.BIG_INT, description ="支付金额"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="支付时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="payOrderStatus", fieldType = FieldTypeEnum.TYINT, description ="支付状态"),
	@Field(fieldName ="thirdPartyPayOrder", fieldType = FieldTypeEnum.CHAR64, description ="第三方的订单号"),
	@Field(fieldName ="payAccount", fieldType = FieldTypeEnum.CHAR36, description ="支付的帐号"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdatePayOrderService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdatePayOrderService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<PayOrder> entityDAO = applicationContext.getEntityDAO(EntityNames.payOrder);
        PayOrder entity = entityDAO.update(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
