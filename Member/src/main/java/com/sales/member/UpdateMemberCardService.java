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
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.Coupon;
import com.sales.entity.EntityNames;
import com.sales.entity.MemberCard;
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
        act = ActionNames.updateMemberCard,
        importantParameters = {"session", "encryptType", "memberCardId"},
        minorParameters = {"memberLogoURL", "memberCardURL", "memberCardName", "memberCardDesc", "effectiveStartTime", "effectiveEndTime", "readCount", "clickCount", "balance", "memberCardConfigId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改MemberCard",
        validateParameters = {
    @Field(fieldName = "memberCardId", fieldType = FieldTypeEnum.BIG_INT, description = "会员卡id"),
     @Field(fieldName = "memberLogoURL", fieldType = FieldTypeEnum.CHAR128, description = "会员的logo图"),
    @Field(fieldName = "memberCardURL", fieldType = FieldTypeEnum.CHAR128, description = "会员卡url"),
    @Field(fieldName = "memberCardName", fieldType = FieldTypeEnum.CHAR36, description = "会员开名称"),
    @Field(fieldName = "memberCardDesc", fieldType = FieldTypeEnum.CHAR128, description = "会员卡描述"),
    @Field(fieldName = "effectiveStartTime", fieldType = FieldTypeEnum.DATETIME, description = "会员卡生效开始时间"),
    @Field(fieldName = "effectiveEndTime", fieldType = FieldTypeEnum.DATETIME, description = "生效的结束时间"),
    @Field(fieldName = "readCount", fieldType = FieldTypeEnum.INT, description = "被浏览的次数"),
    @Field(fieldName = "clickCount", fieldType = FieldTypeEnum.INT, description = "被点击的次数"),
    @Field(fieldName = "balance", fieldType = FieldTypeEnum.INT, description = "积分"),
    @Field(fieldName = "consumeMoney", fieldType = FieldTypeEnum.INT, description = "消费金额"),
    @Field(fieldName = "memberCardConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "会员卡的配置"),
    @Field(fieldName = "status", fieldType = FieldTypeEnum.TYINT, description = "会员卡状态1-有效0-无效"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "memberCardPrivileges", fieldType = FieldTypeEnum.CHAR128, description = "会员卡特权"),    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateMemberCardService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateMemberCardService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        EntityDao<MemberCard> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCard);
        String keyword = parameters.get("keyword");
        String matchType = parameters.get("matchType");
        String messageTitle = parameters.get("memberCardName");
        String messageImageURL = parameters.get("memberCardURL");
        MemberCard entity = entityDAO.update(parameters);
        if (entity != null) {
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            List<Condition> conditionList = new ArrayList<Condition>(3);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            conditionList.add(companyIdCondition);
            Condition relateEventIdCondition = new Condition("relatedId", ConditionTypeEnum.EQUAL, String.valueOf(entity.getMemberCardId()));
            conditionList.add(relateEventIdCondition);
            Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.MEMBER_CARD_TYPE));
            conditionList.add(responseContentTypeCondition);
            Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
            conditionList.add(cfgTypeCondition);
            List<Order> orderList = new ArrayList<Order>();
            Order responseMessageCfgOrder = new Order("responseMessageCfgId", OrderEnum.DESC);
            orderList.add(responseMessageCfgOrder);
            List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList, orderList);
            if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                String responseKeyword = responseMessageCfg.getKeyword();
                Map<String, String> responseMessageMap = new HashMap<String, String>(2, 1);
                responseMessageMap.put("responseMessageCfgId", String.valueOf(responseMessageCfg.getResponseMessageCfgId()));
                if (keyword != null && !keyword.isEmpty()) {
                    responseMessageMap.put("keyword", keyword);
                }
                if (matchType != null && !matchType.isEmpty()) {
                    responseMessageMap.put("matchType", matchType);
                }
                if (messageTitle != null && !messageTitle.isEmpty()) {
                    responseMessageMap.put("responseContent", messageTitle);
                }
                if (messageImageURL != null && !messageImageURL.isEmpty()) {
                    responseMessageMap.put("responseImageURL", messageImageURL);
                }
                responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                if (responseMessageCfg != null) {
                    ResponseMessageCache.getInstance().updateCache(responseMessageCfg,responseKeyword);
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
