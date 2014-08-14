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
import com.sales.entity.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.registerMember,
        importantParameters = {"token"},
        minorParameters = {"realName", "weixinNo", "qqNO", "email", "mobile", "birthday", "memberNO", "address", "integration", "password"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        description = "新增Member",
        validateParameters = {
    @Field(fieldName = "realName", fieldType = FieldTypeEnum.CHAR36, description = "真实姓名"),
    @Field(fieldName = "weixinNo", fieldType = FieldTypeEnum.CHAR36, description = ""),
    @Field(fieldName = "qqNO", fieldType = FieldTypeEnum.CHAR36, description = "qq号"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问token"),
    @Field(fieldName = "email", fieldType = FieldTypeEnum.CHAR36, description = "邮箱"),
    @Field(fieldName = "mobile", fieldType = FieldTypeEnum.CHAR36, description = "手机"),
    @Field(fieldName = "birthday", fieldType = FieldTypeEnum.DATE, description = "生日"),
    @Field(fieldName = "memberNO", fieldType = FieldTypeEnum.CHAR36, description = "会员号"),
    @Field(fieldName = "address", fieldType = FieldTypeEnum.CHAR128, description = "会员地址"),
    @Field(fieldName = "integration", fieldType = FieldTypeEnum.BIG_INT, description = "积分"),
    @Field(fieldName = "password", fieldType = FieldTypeEnum.CHAR64, description = "密码"),})
public class MemberRegisterService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(MemberRegisterService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            parameters.put("source", "2");
            parameters.put("companyId", tokens[1]);
            parameters.put("createTime", DateTimeUtils.currentDay());
            logger.debug("parameters={}", parameters);

            EntityDao<Member> entityDAO = applicationContext.getEntityDAO(EntityNames.member);
            String mobile = parameters.get("mobile");
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition userNameCondition = new Condition("mobile", ConditionTypeEnum.EQUAL, mobile);
            conditionList.add(userNameCondition);
            List<Member> memberList = entityDAO.inquireByCondition(conditionList);
            if (memberList == null || memberList.isEmpty()) {
                Member entity = entityDAO.insert(parameters);
                if (entity != null) {
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                applicationContext.fail(SalesErrorCode.MOBILE_EXIST);
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
