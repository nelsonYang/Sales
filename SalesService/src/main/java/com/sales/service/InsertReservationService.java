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
        act = ActionNames.insertReservation,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"reservationConfigId","reservationAddress","reservationTelephone","reservationDesc","companyId","status"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增Reservation",
        validateParameters = {
       	@Field(fieldName ="reservationId", fieldType = FieldTypeEnum.BIG_INT, description ="预约"),
	@Field(fieldName ="reservationConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="预约配置id"),
	@Field(fieldName ="reservationAddress", fieldType = FieldTypeEnum.CHAR36, description ="预约地址"),
	@Field(fieldName ="reservationTelephone", fieldType = FieldTypeEnum.CHAR36, description ="预约的电话"),
	@Field(fieldName ="reservationDesc", fieldType = FieldTypeEnum.CHAR128, description ="预约说明"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="status", fieldType = FieldTypeEnum.TYINT, description ="活动 0-禁用 1-启用"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertReservationService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertReservationService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<Reservation> entityDAO = applicationContext.getEntityDAO(EntityNames.reservation);
        Reservation entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
