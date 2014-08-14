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
import com.sales.entity.Event;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireEventList,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"eventId","eventName","eventDesc","eventStartURL","eventEndImageURL","eventEffectiveStartTime","eventEffectiveEndTime","type","readCount","clickCount","companyId","status","keyword","matchType","isRemindOpen","eventNum","eventCurrentNum","eventConfigId"},
        description = "查询Event配置",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireEventListService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireEventListService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        logger.debug("parameters={}", parameters);
        EntityDao<Event> entityDAO = applicationContext.getEntityDAO(EntityNames.event);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        List<Event> entityList = entityDAO.inquireByCondition(conditionList);
        if (entityList != null) {
            applicationContext.setEntityList(entityList);
            applicationContext.success();
        } else {
          throw new RollBackException("操作失败");
        }

    }
}
