package com.sales.service;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PageModel;
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
import com.sales.entity.EventFlow;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquirePageEventFlowList,
        importantParameters = {"session", "pageCount", "pageNo", "encryptType"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"eventFlowId","weixinId","createTime","eventId","companyId"},
        description = "分页查询EventFlow",
        validateParameters = {
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePageEventFlowListService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquirePageEventFlowListService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        logger.debug("parameters={}", parameters);
        String pageCount = parameters.get("pageCount");
        String pageNo = parameters.get("pageNo");
        EntityDao<EventFlow> entityDAO = applicationContext.getEntityDAO(EntityNames.eventFlow);
        List<Condition> conditionList = new ArrayList<Condition>(0);
       
        PageModel enityPageMode = entityDAO.inquirePageByCondition(conditionList, Integer.parseInt(pageNo), Integer.parseInt(pageCount));
        List<EventFlow> entityList = enityPageMode.getDataList();
        if (entityList != null) {
            if (!entityList.isEmpty()) {
                applicationContext.setEntityList(entityList);
                applicationContext.setCount(enityPageMode.getTotalCount());
                applicationContext.setTotalPage(applicationContext.getTotalPage());
                applicationContext.success();
            } else {
                applicationContext.noData();
            }
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
