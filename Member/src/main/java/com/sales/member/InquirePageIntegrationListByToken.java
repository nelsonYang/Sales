package com.sales.member;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PageModel;
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
import com.sales.entity.IntegrationFlow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireIntegrationFlowListByToken,
        importantParameters = {"memberId", "pageCount", "pageNo", "encryptType", "token"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"integrationFlowId", "memberId", "companyId", "createTime", "integrationAmount", "type"},
        description = "分页查询CouponMember",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问token"),
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "memberId", fieldType = FieldTypeEnum.BIG_INT, description = "会员id"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePageIntegrationListByToken implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquirePageIntegrationListByToken.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();

        logger.debug("parameters={}", parameters);
        String pageCount = parameters.get("pageCount");
        String pageNo = parameters.get("pageNo");
        String token = parameters.get("token");
        String memberId = parameters.get("memberId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            EntityDao<IntegrationFlow> entityDAO = applicationContext.getEntityDAO(EntityNames.integrationFlow);
            List<Condition> conditionList = new ArrayList<Condition>(0);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyIdCondition);
            Condition memberIdCondition = new Condition("memberId", ConditionTypeEnum.EQUAL, String.valueOf(memberId));
            conditionList.add(memberIdCondition);
            PageModel enityPageMode = entityDAO.inquirePageByCondition(conditionList, Integer.parseInt(pageNo), Integer.parseInt(pageCount));
            List<IntegrationFlow> entityList = enityPageMode.getDataList();
            if (entityList != null) {
                if (!entityList.isEmpty()) {
                    applicationContext.setEntityList(entityList);
                    applicationContext.setCount(enityPageMode.getTotalCount());
                    applicationContext.setTotalPage(enityPageMode.getTotalPage());
                    applicationContext.success();
                } else {
                    applicationContext.noData();
                }
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
