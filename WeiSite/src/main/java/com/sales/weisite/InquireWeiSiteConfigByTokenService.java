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
import com.sales.entity.WeiSiteConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeiSiteConfigByToken,
        importantParameters = {"token", "encryptType"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"weiSiteConfigId", "weiSiteName", "backgroupMusicURL", "keyword", "matchType", "telphone", "isDialOpen", "title", "backgroupImageURL"},
        description = "查询WeiSiteConfig配置",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeiSiteConfigByTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireWeiSiteConfigByTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            logger.debug("parameters={}", parameters);
            EntityDao<WeiSiteConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteConfig);
            List<Condition> conditionList = new ArrayList<Condition>(0);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyIdCondition);
            List<WeiSiteConfig> entityList = entityDAO.inquireByCondition(conditionList);
            if (entityList != null) {
                if (!entityList.isEmpty()) {
                    applicationContext.setEntityData(entityList.get(0));
                    applicationContext.success();
                } else {
                    applicationContext.fail();
                }
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
             applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
