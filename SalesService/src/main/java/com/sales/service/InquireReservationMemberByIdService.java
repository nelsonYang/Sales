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
import com.sales.entity.ReservationMember;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireReservationMemberById,
        importantParameters = {"session", "encryptType", "reservationMemberId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"reservationMemberId","reservationMemberName","reservationMemberTelephone","reservationMemberDateTime","reservationMemberAddress","reservationMemberDesc","createTime","companyId"},
        description = "查询ReservationMember详细内容",
        validateParameters = {
          	@Field(fieldName ="reservationMemberId", fieldType = FieldTypeEnum.BIG_INT, description ="预约会员id"),
	@Field(fieldName ="reservationMemberName", fieldType = FieldTypeEnum.CHAR36, description ="预约人的姓名"),
	@Field(fieldName ="reservationMemberTelephone", fieldType = FieldTypeEnum.CHAR36, description ="预约人的手机"),
	@Field(fieldName ="reservationMemberDateTime", fieldType = FieldTypeEnum.DATETIME, description ="预约的时间"),
	@Field(fieldName ="reservationMemberAddress", fieldType = FieldTypeEnum.CHAR64, description ="预约的地址"),
	@Field(fieldName ="reservationMemberDesc", fieldType = FieldTypeEnum.CHAR64, description ="备注"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireReservationMemberByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireReservationMemberByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String reservationMemberId = parameters.get("reservationMemberId");
	primaryKey.putKeyField("reservationMemberId",String.valueOf(reservationMemberId));

        EntityDao<ReservationMember> entityDAO = applicationContext.getEntityDAO(EntityNames.reservationMember);
          ReservationMember entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
