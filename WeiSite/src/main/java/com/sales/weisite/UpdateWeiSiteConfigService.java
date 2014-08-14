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
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.config.ActionNames;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.ResponseMessageCfg;
import com.sales.entity.WeiSiteConfig;
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
        act = ActionNames.updateWeiSiteConfig,
        importantParameters = {"session", "encryptType", "weiSiteConfigId"},
        minorParameters = {"weiSiteName", "backgroupMusicURL", "keyword", "matchType", "telphone", "isDialOpen", "title", "backgroupImageURL"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改WeiSiteConfig",
        validateParameters = {
    @Field(fieldName = "weiSiteConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "微网站配置id"),
    @Field(fieldName = "weiSiteName", fieldType = FieldTypeEnum.CHAR36, description = "微网站名称"),
    @Field(fieldName = "backgroupMusicURL", fieldType = FieldTypeEnum.CHAR128, description = "背景音乐的url"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = ""),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配类型1-精确2-模糊"),
    @Field(fieldName = "telphone", fieldType = FieldTypeEnum.CHAR36, description = "手机号码"),
    @Field(fieldName = "isDialOpen", fieldType = FieldTypeEnum.TYINT, description = "开启一键拨号"),
    @Field(fieldName = "title", fieldType = FieldTypeEnum.CHAR36, description = "官网名称"),
    @Field(fieldName = "backgroupImageURL", fieldType = FieldTypeEnum.CHAR128, description = "背景图片"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateWeiSiteConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateWeiSiteConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        EntityDao<WeiSiteConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteConfig);
        WeiSiteConfig entity = entityDAO.update(parameters);
        if (entity != null) {
        EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            conditionList.add(companyIdCondition);
            Condition relateIdIdCondition = new Condition("relatedEventId", ConditionTypeEnum.EQUAL, String.valueOf(entity.getWeiSiteConfigId()));
            conditionList.add(relateIdIdCondition);
            List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList);
            if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                Map<String, String> responseMessageMap = new HashMap<String, String>(2, 1);
                responseMessageMap.put("responseMessageCfgId", String.valueOf(responseMessageCfg.getResponseMessageCfgId()));
                responseMessageMap.put("keyword", parameters.get("keyword"));
                responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                if (responseMessageCfg != null) {
                    //ResponseMessageCache.getInstance().updateCache(responseMessageCfg);
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                applicationContext.success();
            }
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
