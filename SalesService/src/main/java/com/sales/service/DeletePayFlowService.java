package com.sales.service;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
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
import com.sales.entity.PayFlow;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deletePayFlow,
        importantParameters = {"session", "encryptType","payFlowId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除PayFlow操作",
        validateParameters = {
    	@Field(fieldName ="payFlowId", fieldType = FieldTypeEnum.BIG_INT, description ="支付流水id"),
	@Field(fieldName ="fromAccount", fieldType = FieldTypeEnum.CHAR36, description ="转出帐号"),
	@Field(fieldName ="toAccount", fieldType = FieldTypeEnum.CHAR36, description ="转入帐号"),
	@Field(fieldName ="money", fieldType = FieldTypeEnum.BIG_INT, description ="金额"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建的时间"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeletePayFlowService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeletePayFlowService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String payFlowId = parameters.get("payFlowId");
	primaryKey.putKeyField("payFlowId",String.valueOf(payFlowId));

        EntityDao<PayFlow> entityDAO = applicationContext.getEntityDAO(EntityNames.payFlow);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
