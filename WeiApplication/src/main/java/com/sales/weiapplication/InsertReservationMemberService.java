package com.sales.weiapplication;

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
import com.framework.utils.DateTimeUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.ReservationMember;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertReservationMember,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"reservationMemberName", "reservationMemberTelephone", "reservationMemberDateTime", "reservationMemberAddress", "reservationMemberDesc"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增ReservationMember",
        validateParameters = {
    @Field(fieldName = "reservationMemberName", fieldType = FieldTypeEnum.CHAR36, description = "预约人的姓名"),
    @Field(fieldName = "reservationMemberTelephone", fieldType = FieldTypeEnum.CHAR36, description = "预约人的手机"),
    @Field(fieldName = "reservationMemberDateTime", fieldType = FieldTypeEnum.DATETIME, description = "预约的时间"),
    @Field(fieldName = "reservationMemberAddress", fieldType = FieldTypeEnum.CHAR64, description = "预约的地址"),
    @Field(fieldName = "reservationMemberDesc", fieldType = FieldTypeEnum.CHAR64, description = "备注"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertReservationMemberService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertReservationMemberService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("createTime", DateTimeUtils.currentDay());
        logger.debug("parameters={}", parameters);
        EntityDao<ReservationMember> entityDAO = applicationContext.getEntityDAO(EntityNames.reservationMember);
        ReservationMember entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
