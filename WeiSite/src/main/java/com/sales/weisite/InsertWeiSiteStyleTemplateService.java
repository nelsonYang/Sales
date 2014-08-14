package com.sales.weisite;

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
import com.framework.utils.DateTimeUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteStyleTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertWeiSiteStyleTemplate,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"weiSiteStyleTemplateCfgId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增WeiSiteStyleTemplate",
        validateParameters = {
    @Field(fieldName = "weiSiteStyleTemplateCfgId", fieldType = FieldTypeEnum.BIG_INT, description = "所属的模板id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertWeiSiteStyleTemplateService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertWeiSiteStyleTemplateService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("createTime", DateTimeUtils.currentDay());
        logger.debug("parameters={}", parameters);
        EntityDao<WeiSiteStyleTemplate> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteStyleTemplate);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyCondition);
        List<WeiSiteStyleTemplate> weiSiteStyleTemplateList = entityDAO.inquireByCondition(conditionList);
        if (weiSiteStyleTemplateList == null || weiSiteStyleTemplateList.isEmpty()) {
            WeiSiteStyleTemplate entity = entityDAO.insert(parameters);
            if (entity != null) {
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            parameters.put("weiSiteStyleTemplateId", String.valueOf(weiSiteStyleTemplateList.get(0).getWeiSiteStyleTemplateId()));
            WeiSiteStyleTemplate entity = entityDAO.update(parameters);
            if (entity != null) {
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        }

    }
}
