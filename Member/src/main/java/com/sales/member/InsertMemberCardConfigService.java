package com.sales.member;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.condition.Order;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.enumeration.OrderEnum;
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
import com.sales.entity.MemberCardConfig;
import com.sales.entity.ResponseMessageCfg;
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
        act = ActionNames.insertMemberCardConfig,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"memberCardConfigId", "merchantName", "isExperienceOpen", "telephone", "memberCardName", "merchantLogo", "memberCardBackgroupURL", "merchantAddress", "integerationPerSign", "keyword", "matchType", "title", "messageImageURL", "memberCardDesc"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增MemberCardConfig",
        validateParameters = {
    @Field(fieldName = "memberCardConfigId", fieldType = FieldTypeEnum.INT, description = "会员卡配置id"),
    @Field(fieldName = "merchantName", fieldType = FieldTypeEnum.CHAR36, description = "商家名称"),
    @Field(fieldName = "isExperienceOpen", fieldType = FieldTypeEnum.TYINT, description = "是否开启积分1-开启 2-不开起"),
    @Field(fieldName = "telephone", fieldType = FieldTypeEnum.CHAR36, description = "联系方式"),
    @Field(fieldName = "memberCardName", fieldType = FieldTypeEnum.CHAR36, description = "会员卡名称"),
    @Field(fieldName = "merchantLogo", fieldType = FieldTypeEnum.CHAR64, description = "商家logo"),
    @Field(fieldName = "memberCardBackgroupURL", fieldType = FieldTypeEnum.CHAR128, description = "会员卡背景"),
    @Field(fieldName = "merchantAddress", fieldType = FieldTypeEnum.CHAR128, description = "商家地址"),
    @Field(fieldName = "integerationPerSign", fieldType = FieldTypeEnum.BIG_INT, description = "签到积分"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配类型1-精确2-模糊"),
    @Field(fieldName = "title", fieldType = FieldTypeEnum.CHAR36, description = "标题"),
    @Field(fieldName = "messageImageURL", fieldType = FieldTypeEnum.CHAR128, description = "图片的url"),
    @Field(fieldName = "memberCardDesc", fieldType = FieldTypeEnum.CHAR128, description = "会员卡说名"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertMemberCardConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertMemberCardConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        String memberCardConfigId = parameters.get("memberCardConfigId");
        String messageTitle = parameters.get("merchantName");
        String messageURL = parameters.get("messageImageURL");
        EntityDao<MemberCardConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCardConfig);
        if (memberCardConfigId == null || memberCardConfigId.isEmpty()) {
            MemberCardConfig entity = entityDAO.insert(parameters);
            if (entity != null) {
                Map<String, String> responseMessageMap = new HashMap<String, String>(4, 1);
                responseMessageMap.put("companyId", String.valueOf(companyId));
                responseMessageMap.put("type", String.valueOf(SalesConstant.RESPONSE_TEXT_TYPE));
                responseMessageMap.put("responseContentType", String.valueOf(SalesConstant.MEMBER_CARD_TYPE));
                responseMessageMap.put("keyword", parameters.get("keyword"));
                if (messageTitle != null) {
                    responseMessageMap.put("responseContent", messageTitle);
                }
                if (messageURL != null) {
                    responseMessageMap.put("responseImageURL", messageURL);
                }
                responseMessageMap.put("cfgType", String.valueOf(SalesConstant.BASIC_CFG_TYPE));
                responseMessageMap.put("relatedEventId", String.valueOf(entity.getMemberCardConfigId()));
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
                if (responseMessageCfg != null) {
                    // ResponseMessageCache.getInstance().insertCache(responseMessageCfg);
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            MemberCardConfig entity = entityDAO.update(parameters);
            if (entity != null) {
                EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
                List<Condition> conditionList = new ArrayList<Condition>(3);
                Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyIdCondition);
                Condition relateEventIdCondition = new Condition("relatedEventId", ConditionTypeEnum.EQUAL, memberCardConfigId);
                conditionList.add(relateEventIdCondition);
                Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.MEMBER_CARD_TYPE));
                conditionList.add(responseContentTypeCondition);
                Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.BASIC_CFG_TYPE));
                conditionList.add(cfgTypeCondition);
                List<Order> orderList = new ArrayList<Order>();
                Order responseMessageCfgOrder = new Order("responseMessageCfgId", OrderEnum.DESC);
                orderList.add(responseMessageCfgOrder);
                List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList, orderList);
                if (!responseMessageCfgList.isEmpty()) {
                    ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                    Map<String, String> responseMessageMap = new HashMap<String, String>(2, 1);
                    responseMessageMap.put("responseMessageCfgId", String.valueOf(responseMessageCfg.getResponseMessageCfgId()));
                    responseMessageMap.put("keyword", parameters.get("keyword"));
                    if (messageTitle != null) {
                        responseMessageMap.put("responseContent", messageTitle);
                    }
                    if (messageURL != null) {
                        responseMessageMap.put("responseImageURL", messageURL);
                    }
                    responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                    if (responseMessageCfg != null) {
                        //ResponseMessageCache.getInstance().updateCache(responseMessageCfg);
                        applicationContext.success();
                    } else {
                        throw new RollBackException("操作失败");
                    }
                }
            } else {
                throw new RollBackException("操作失败");
            }
        }
    }
}
