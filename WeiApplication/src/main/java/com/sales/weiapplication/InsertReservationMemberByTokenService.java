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
import com.framework.utils.Base64Utils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.ReservationMember;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertReservationMemberByToken,
        importantParameters = {"token", "memberId","reservationId"},
        minorParameters = {"reservationMemberName", "reservationMemberTelephone", "reservationMemberDateTime", "reservationMemberAddress", "reservationMemberDesc"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        description = "新增ReservationMember",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR64, description = "token"),
    @Field(fieldName = "memberId", fieldType = FieldTypeEnum.BIG_INT, description = "会员id"),
    @Field(fieldName = "reservationId", fieldType = FieldTypeEnum.BIG_INT, description = "预约id"),
    @Field(fieldName = "reservationMemberName", fieldType = FieldTypeEnum.CHAR36, description = "预约人的姓名"),
    @Field(fieldName = "reservationMemberTelephone", fieldType = FieldTypeEnum.CHAR36, description = "预约人的手机"),
    @Field(fieldName = "reservationMemberDateTime", fieldType = FieldTypeEnum.DATETIME, description = "预约的时间"),
    @Field(fieldName = "reservationMemberAddress", fieldType = FieldTypeEnum.CHAR64, description = "预约的地址"),
    @Field(fieldName = "reservationMemberDesc", fieldType = FieldTypeEnum.CHAR64, description = "备注")
})
public class InsertReservationMemberByTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertReservationMemberService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        logger.debug("parameters={}", parameters);
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            parameters.put("companyId", tokens[1]);
            EntityDao<ReservationMember> entityDAO = applicationContext.getEntityDAO(EntityNames.reservationMember);
            ReservationMember entity = entityDAO.insert(parameters);
            if (entity != null) {
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
