package com.sales.weiapplication;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.enumeration.OrderEnum;
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
import com.sales.entity.WeiBar;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquirePageWeiBarListByToken,
        importantParameters = {"token", "pageCount", "pageNo", "encryptType"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"weiBarId", "weiBarConfigId", "weiBarTitle", "weiBarContent", "memberId", "parentId", "memberNo", "memberName", "createTime", "companyId"},
        description = "分页查询WeiBar",
        validateParameters = {
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePageWeiBarListByTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquirePageWeiBarListByTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();

        logger.debug("parameters={}", parameters);
        String pageCount = parameters.get("pageCount");
        String pageNo = parameters.get("pageNo");
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            EntityDao<WeiBar> entityDAO = applicationContext.getEntityDAO(EntityNames.weiBar);
            List<Condition> conditionList = new ArrayList<Condition>(0);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(tokens[1]));
            conditionList.add(companyIdCondition);
            List<Order> orderList = new ArrayList<Order>(1);
            Order createTimeOrder = new Order("createTime", OrderEnum.DESC);
            orderList.add(createTimeOrder);
            PageModel enityPageMode = entityDAO.inquirePageByCondition(conditionList, Integer.parseInt(pageNo), Integer.parseInt(pageCount), orderList);
            List<WeiBar> entityList = enityPageMode.getDataList();
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
