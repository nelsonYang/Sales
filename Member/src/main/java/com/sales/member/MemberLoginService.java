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
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.loginMember,
        importantParameters = {"userName", "password", "token"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"memberId"},
        description = "会员登录",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR64, description = "token"),
    @Field(fieldName = "userName", fieldType = FieldTypeEnum.CHAR24, description = "用户名"),
    @Field(fieldName = "password", fieldType = FieldTypeEnum.CHAR64, description = "密码"),
    @Field(fieldName = "sign", fieldType = FieldTypeEnum.CHAR1024, description = "签名验证"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class MemberLoginService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(MemberLoginService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            String companyName = parameters.get("userName");
            String password = parameters.get("password");
            logger.debug("companyName={}", companyName);
            logger.debug("password={}", password);
            EntityDao<Member> entityDAO = applicationContext.getEntityDAO(EntityNames.member);
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition userNameCondition = new Condition("mobile", ConditionTypeEnum.EQUAL, companyName);
            Condition passwordCondition = new Condition("password", ConditionTypeEnum.EQUAL, password);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyIdCondition);
            conditionList.add(userNameCondition);
            conditionList.add(passwordCondition);
            List<Member> memberList = entityDAO.inquireByCondition(conditionList);
            if (memberList != null && !memberList.isEmpty()) {
                Member member = memberList.get(0);
                Map<String, String> resultMap = new HashMap<String, String>(2, 1);
                resultMap.put("memberId", String.valueOf(member.getMemberId()));
                applicationContext.setMapData(resultMap);
                applicationContext.success();
            } else {
                applicationContext.fail();
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }
    }
}
