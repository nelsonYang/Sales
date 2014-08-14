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
import com.sales.entity.Consuption;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteConsuption,
        importantParameters = {"session", "encryptType","consuptionId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Consuption操作",
        validateParameters = {
    	@Field(fieldName ="consuptionId", fieldType = FieldTypeEnum.BIG_INT, description ="消费记录id"),
	@Field(fieldName ="couponId", fieldType = FieldTypeEnum.BIG_INT, description ="优惠券id"),
	@Field(fieldName ="couponName", fieldType = FieldTypeEnum.CHAR36, description ="优惠券名称"),
	@Field(fieldName ="availableCount", fieldType = FieldTypeEnum.TYINT, description ="可用次数-1表示不限"),
	@Field(fieldName ="consumeMoney", fieldType = FieldTypeEnum.DOUBLE, description ="消费金额"),
	@Field(fieldName ="operatorName", fieldType = FieldTypeEnum.CHAR11, description ="操作员"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="记录时间"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteConsuptionService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteConsuptionService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String consuptionId = parameters.get("consuptionId");
	primaryKey.putKeyField("consuptionId",String.valueOf(consuptionId));

        EntityDao<Consuption> entityDAO = applicationContext.getEntityDAO(EntityNames.consuption);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
