package com.sales.member;

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
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.CouponMember;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireCouponMemberById,
        importantParameters = {"session", "encryptType", "couponMemberId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"couponMemberId", "memberId", "companyId", "createTime", "couponId", "memberNo"},
        description = "查询CouponMember详细内容",
        validateParameters = {
    @Field(fieldName = "couponMemberId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券会员id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireCouponMemberByIdService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireCouponMemberByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        PrimaryKey primaryKey = new PrimaryKey();
        String couponMemberId = parameters.get("couponMemberId");
        primaryKey.putKeyField("couponMemberId", String.valueOf(couponMemberId));
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        EntityDao<CouponMember> entityDAO = applicationContext.getEntityDAO(EntityNames.couponMember);
        CouponMember entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }


    }
}
