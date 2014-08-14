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
import com.sales.entity.Reservation;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteReservation,
        importantParameters = {"session", "encryptType","reservationId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Reservation操作",
        validateParameters = {
    	@Field(fieldName ="reservationId", fieldType = FieldTypeEnum.BIG_INT, description ="预约"),
	@Field(fieldName ="reservationConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="预约配置id"),
	@Field(fieldName ="reservationAddress", fieldType = FieldTypeEnum.CHAR36, description ="预约地址"),
	@Field(fieldName ="reservationTelephone", fieldType = FieldTypeEnum.CHAR36, description ="预约的电话"),
	@Field(fieldName ="reservationDesc", fieldType = FieldTypeEnum.CHAR128, description ="预约说明"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="status", fieldType = FieldTypeEnum.TYINT, description ="活动 0-禁用 1-启用"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteReservationService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteReservationService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String reservationId = parameters.get("reservationId");
	primaryKey.putKeyField("reservationId",String.valueOf(reservationId));

        EntityDao<Reservation> entityDAO = applicationContext.getEntityDAO(EntityNames.reservation);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
