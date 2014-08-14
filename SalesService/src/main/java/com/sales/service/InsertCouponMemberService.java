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
import com.sales.entity.CouponMember;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertCouponMember,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"memberId","companyId","createTime","couponId","memberNo"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增CouponMember",
        validateParameters = {
       	@Field(fieldName ="couponMemberId", fieldType = FieldTypeEnum.BIG_INT, description ="优惠券会员id"),
	@Field(fieldName ="memberId", fieldType = FieldTypeEnum.BIG_INT, description ="会员id"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description =""),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="couponId", fieldType = FieldTypeEnum.BIG_INT, description ="优惠券id"),
	@Field(fieldName ="memberNo", fieldType = FieldTypeEnum.CHAR36, description ="手机号"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertCouponMemberService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertCouponMemberService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<CouponMember> entityDAO = applicationContext.getEntityDAO(EntityNames.couponMember);
        CouponMember entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
