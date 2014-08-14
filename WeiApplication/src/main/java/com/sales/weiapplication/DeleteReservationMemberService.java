package com.sales.weiapplication;

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
        act = ActionNames.deleteReservationMember,
        importantParameters = {"session", "encryptType", "reservationMemberId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除ReservationMember操作",
        validateParameters = {
    @Field(fieldName = "reservationMemberId", fieldType = FieldTypeEnum.BIG_INT, description = "预约会员id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteReservationMemberService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(DeleteReservationMemberService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        PrimaryKey primaryKey = new PrimaryKey();
        String reservationMemberId = parameters.get("reservationMemberId");
        primaryKey.putKeyField("reservationMemberId", String.valueOf(reservationMemberId));
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        EntityDao<ReservationMember> entityDAO = applicationContext.getEntityDAO(EntityNames.reservationMember);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }
    }
}
