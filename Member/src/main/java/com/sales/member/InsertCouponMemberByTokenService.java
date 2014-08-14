package com.sales.member;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.framework.utils.DateTimeUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.CouponMember;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertCouponMemberByToken,
        importantParameters = {"token"},
        minorParameters = {"memberId", "couponId", "memberNo"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        description = "领取优惠券",
        validateParameters = {
    @Field(fieldName = "couponMemberId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券会员id"),
    @Field(fieldName = "memberId", fieldType = FieldTypeEnum.BIG_INT, description = "会员id"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问token"),
    @Field(fieldName = "couponId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券id"),
    @Field(fieldName = "memberNo", fieldType = FieldTypeEnum.CHAR36, description = "手机号")
})
public class InsertCouponMemberByTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertCouponMemberByTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        logger.debug("parameters={}", parameters);
        String token = parameters.get("token");
        String memberId = parameters.get("memberId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            parameters.put("companyId", tokens[1]);
            parameters.put("createTime", DateTimeUtils.currentDay());
            logger.debug("parameters={}", parameters);
            EntityDao<CouponMember> entityDAO = applicationContext.getEntityDAO(EntityNames.couponMember);
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyIdCondition);
            Condition memberIdCondition = new Condition("memberId", ConditionTypeEnum.EQUAL, memberId);
            conditionList.add(memberIdCondition);
            List<CouponMember> couponMemberList = entityDAO.inquireByCondition(conditionList);
            if (couponMemberList == null || couponMemberList.isEmpty()) {
                CouponMember entity = entityDAO.insert(parameters);
                if (entity != null) {
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                applicationContext.fail(SalesErrorCode.ALWARY_TAKE_MEMBER_CARD);
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
