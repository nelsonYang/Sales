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
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.framework.utils.DateTimeUtils;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.IntegrationFlow;
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
        act = ActionNames.memberSign,
        importantParameters = {"token", "encryptType", "memberId"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        description = "会员签到",
        validateParameters = {
    @Field(fieldName = "memberId", fieldType = FieldTypeEnum.INT, description = "会员id"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "token"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class MemberSignByToken implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(MemberSignByToken.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String memberId = parameters.get("memberId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            parameters.put("companyId", tokens[1]);
            logger.debug("parameters={}", parameters);
            EntityDao<IntegrationFlow> integrationFlowDAO = applicationContext.getEntityDAO(EntityNames.integrationFlow);
            List<Condition> conditionList = new ArrayList<Condition>(1);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyIdCondition);
            Condition dateCondition = new Condition("date_format(createTime,'%Y-%m-%d')", ConditionTypeEnum.EQUAL, DateTimeUtils.currentYYYYMMDay());
            conditionList.add(dateCondition);
            List<IntegrationFlow> integrationFlowList = integrationFlowDAO.inquireByCondition(conditionList);
            if (integrationFlowList != null && integrationFlowList.isEmpty()) {
                EntityDao<Member> entityDAO = applicationContext.getEntityDAO(EntityNames.member);
                String sql = "update member set integration = integration + " + SalesConstant.SIGN_INTEGRATION + " where companyId =" + tokens[1] + " and memberId=" + memberId;
                boolean isSuccess = entityDAO.executeUpdateBySql(new String[]{"member"}, sql, new String[]{}, parameters);
                if (isSuccess) {
                    Map<String, String> integrationFlowMap = new HashMap<String, String>(8, 1);
                    integrationFlowMap.put("memberId", memberId);
                    integrationFlowMap.put("companyId", tokens[1]);
                    integrationFlowMap.put("type", "1");
                    integrationFlowMap.put("integrationAmount", String.valueOf(SalesConstant.SIGN_INTEGRATION));
                    integrationFlowMap.put("createTime", DateTimeUtils.currentDay());
                    IntegrationFlow integrationFlow = integrationFlowDAO.insert(integrationFlowMap);
                    if (integrationFlow != null) {
                        applicationContext.success();
                    } else {
                        throw new RollBackException("操作失败");
                    }
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                applicationContext.fail(SalesErrorCode.ALREADY_SIGN);
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
