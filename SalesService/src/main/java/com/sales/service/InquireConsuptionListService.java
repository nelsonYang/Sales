package com.sales.service;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
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
import com.sales.entity.Consuption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireConsuptionList,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"consuptionId","couponId","couponName","availableCount","consumeMoney","operatorName","createTime"},
        description = "查询Consuption配置",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireConsuptionListService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireConsuptionListService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        logger.debug("parameters={}", parameters);
        EntityDao<Consuption> entityDAO = applicationContext.getEntityDAO(EntityNames.consuption);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        List<Consuption> entityList = entityDAO.inquireByCondition(conditionList);
        if (entityList != null) {
            applicationContext.setEntityList(entityList);
            applicationContext.success();
        } else {
          throw new RollBackException("操作失败");
        }

    }
}