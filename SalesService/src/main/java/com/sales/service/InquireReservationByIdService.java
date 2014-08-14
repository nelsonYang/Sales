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
import com.sales.entity.Reservation;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireReservationById,
        importantParameters = {"session", "encryptType", "reservationId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"reservationId","reservationConfigId","reservationAddress","reservationTelephone","reservationDesc","companyId","status"},
        description = "查询Reservation详细内容",
        validateParameters = {
          	@Field(fieldName ="reservationId", fieldType = FieldTypeEnum.BIG_INT, description ="预约"),
	@Field(fieldName ="reservationConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="预约配置id"),
	@Field(fieldName ="reservationAddress", fieldType = FieldTypeEnum.CHAR36, description ="预约地址"),
	@Field(fieldName ="reservationTelephone", fieldType = FieldTypeEnum.CHAR36, description ="预约的电话"),
	@Field(fieldName ="reservationDesc", fieldType = FieldTypeEnum.CHAR128, description ="预约说明"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="status", fieldType = FieldTypeEnum.TYINT, description ="活动 0-禁用 1-启用"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireReservationByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireReservationByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String reservationId = parameters.get("reservationId");
	primaryKey.putKeyField("reservationId",String.valueOf(reservationId));

        EntityDao<Reservation> entityDAO = applicationContext.getEntityDAO(EntityNames.reservation);
          Reservation entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
