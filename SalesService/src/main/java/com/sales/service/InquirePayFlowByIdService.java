package com.sales.service;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.entity.EntityNames;
import com.sales.entity.PayFlow;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquirePayFlowById,
        importantParameters = {"session", "encryptType", "payFlowId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"payFlowId","fromAccount","toAccount","money","createTime"},
        description = "查询PayFlow详细内容",
        validateParameters = {
          	@Field(fieldName ="payFlowId", fieldType = FieldTypeEnum.BIG_INT, description ="支付流水id"),
	@Field(fieldName ="fromAccount", fieldType = FieldTypeEnum.CHAR36, description ="转出帐号"),
	@Field(fieldName ="toAccount", fieldType = FieldTypeEnum.CHAR36, description ="转入帐号"),
	@Field(fieldName ="money", fieldType = FieldTypeEnum.BIG_INT, description ="金额"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建的时间"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePayFlowByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquirePayFlowByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String payFlowId = parameters.get("payFlowId");
	primaryKey.putKeyField("payFlowId",String.valueOf(payFlowId));

        EntityDao<PayFlow> entityDAO = applicationContext.getEntityDAO(EntityNames.payFlow);
          PayFlow entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
