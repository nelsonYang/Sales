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
import com.framework.utils.Base64Utils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteMenu;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeiSiteMenuListByToken,
        importantParameters = { "token"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"weiSiteMenuId", "weiSiteMenuName", "weiSiteMenuDesc", "weiSiteMenuImageURL", "weiSiteMenuLinkWebSite", "weiSiteMenuParentId", "companyId", "resourcesId", "weiSiteConfigId"},
        description = "查询WeiSiteMenu配置",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "token类型")
})
public class InquireWeiSiteMenuListByTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireWeiSiteMenuListService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        logger.debug("parameters={}", parameters);
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            EntityDao<WeiSiteMenu> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteMenu);
            List<Condition> conditionList = new ArrayList<Condition>(0);
            Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyCondition);
            List<WeiSiteMenu> entityList = entityDAO.inquireByCondition(conditionList);
            if (entityList != null) {
                applicationContext.setEntityList(entityList);
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }
    }
}
