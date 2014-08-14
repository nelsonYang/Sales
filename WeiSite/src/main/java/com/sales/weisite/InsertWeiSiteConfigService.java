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
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
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
        act = ActionNames.insertWeiSiteConfig,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"weiSiteConfigId", "weiSiteName", "backgroupMusicURL", "keyword", "matchType", "telphone", "isDialOpen", "title", "backgroupImageURL"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增WeiSiteConfig",
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
public class InsertWeiSiteConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertWeiSiteConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        String messageTitle = parameters.get("title");
        String messageImageURL = parameters.get("backgroupImageURL");
        String weiSiteConfigId = parameters.get("weiSiteConfigId");
        EntityDao<WeiSiteConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteConfig);
        if (weiSiteConfigId != null && !weiSiteConfigId.isEmpty()) {
            WeiSiteConfig entity = entityDAO.update(parameters);
            if (entity != null) {
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                List<Condition> conditionList = new ArrayList<Condition>(2);
                Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyIdCondition);
                Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.WEI_SITE_TYPE));
                conditionList.add(responseContentTypeCondition);
                Condition relateIdIdCondition = new Condition("relatedEventId", ConditionTypeEnum.EQUAL, String.valueOf(entity.getWeiSiteConfigId()));
                conditionList.add(relateIdIdCondition);
                Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
                conditionList.add(cfgTypeCondition);
                List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList);
                if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                    ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                    String responseKeyword = responseMessageCfg.getKeyword();
                    Map<String, String> responseMessageMap = new HashMap<String, String>(2, 1);
                    responseMessageMap.put("responseMessageCfgId", String.valueOf(responseMessageCfg.getResponseMessageCfgId()));
                    responseMessageMap.put("keyword", parameters.get("keyword"));
                    if (messageImageURL != null) {
                        responseMessageMap.put("responseImageURL", messageImageURL);
                    }
                    if (messageTitle != null) {
                        responseMessageMap.put("responseContent", messageTitle);
                    }
                    responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                    if (responseMessageCfg != null) {
                        ResponseMessageCache.getInstance().updateCache(responseMessageCfg,responseKeyword);
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

        } else {
            WeiSiteConfig entity = entityDAO.insert(parameters);
            if (entity != null) {
                Map<String, String> responseMessageMap = new HashMap<String, String>(4, 1);
                responseMessageMap.put("companyId", String.valueOf(companyId));
                responseMessageMap.put("type", String.valueOf(SalesConstant.RESPONSE_TEXT_TYPE));
                responseMessageMap.put("responseContentType", String.valueOf(SalesConstant.WEI_SITE_TYPE));
                responseMessageMap.put("cfgType", String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
                responseMessageMap.put("keyword", parameters.get("keyword"));
                if (messageImageURL != null) {
                    responseMessageMap.put("responseImageURL", messageImageURL);
                }
                responseMessageMap.put("relatedEventId", String.valueOf(entity.getWeiSiteConfigId()));
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
                if (responseMessageCfg != null) {
                    ResponseMessageCache.getInstance().insertCache(responseMessageCfg);
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                throw new RollBackException("操作失败");
            }
        }


    }
}
