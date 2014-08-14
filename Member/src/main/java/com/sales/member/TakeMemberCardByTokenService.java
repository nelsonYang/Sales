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
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.MemberCardMember;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 设计一张表
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.takeMemberCardByToken,
        importantParameters = {"token", "memberId", "memberCardId"},
        minorParameters = {"memberNO"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        requireTranscation = true,
        description = "领取会员卡",
        validateParameters = {
    @Field(fieldName = "memberCardId", fieldType = FieldTypeEnum.BIG_INT, description = "会员号id"),
    @Field(fieldName = "memberId", fieldType = FieldTypeEnum.BIG_INT, description = "会员id"),
    @Field(fieldName = "memberNO", fieldType = FieldTypeEnum.CHAR36, description = "会员号"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "token")
})
public class TakeMemberCardByTokenService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String memberCardId = parameters.get("memberCardId");
        String memberId = parameters.get("memberId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            parameters.put("companyId", tokens[1]);
            parameters.put("createTime", DateTimeUtils.currentDay());

            EntityDao<MemberCardMember> memberCardMemberDAO = applicationContext.getEntityDAO(EntityNames.memberCardMember);
            List<Condition> conditionList = new ArrayList<Condition>(3);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyIdCondition);
            Condition memberCardIdCondition = new Condition("memberCardId", ConditionTypeEnum.EQUAL, memberCardId);
            conditionList.add(memberCardIdCondition);
            Condition memberIdCondition = new Condition("memberId", ConditionTypeEnum.EQUAL, memberId);
            conditionList.add(memberIdCondition);
            List<MemberCardMember> memberCardMemberList = memberCardMemberDAO.inquireByCondition(conditionList);
            if (memberCardMemberList != null) {
                if (memberCardMemberList.size() <= 0) {
                    MemberCardMember memberCardMember = memberCardMemberDAO.insert(parameters);
                    if (memberCardMember != null) {
                        applicationContext.success();
                    } else {
                        throw new RollBackException("领取会员卡失败", SalesErrorCode.ALWARY_TAKE_MEMBER_CARD);
                    }
                } else {
                    applicationContext.fail(SalesErrorCode.ALWARY_TAKE_MEMBER_CARD);
                }
            } else {
                applicationContext.fail(SalesErrorCode.ENTITY_NOT_FOUND);
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
