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
import com.framework.utils.DateTimeUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.MemberCard;
import com.sales.entity.MemberCardConfig;
import com.sales.entity.Resources;
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
        act = ActionNames.insertMemberCard,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"memberLogoURL", "memberCardURL", "memberCardName", "memberCardDesc", "effectiveStartTime", "effectiveEndTime", "readCount", "clickCount", "balance", "consumeMoney", "status", "keyword", "memberCardConfigId", "memberCardPrivileges"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增MemberCard",
        validateParameters = {
    @Field(fieldName = "memberLogoURL", fieldType = FieldTypeEnum.CHAR64, description = "会员的logo图"),
    @Field(fieldName = "memberCardURL", fieldType = FieldTypeEnum.CHAR64, description = "会员卡url"),
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
    @Field(fieldName = "memberCardPrivileges", fieldType = FieldTypeEnum.CHAR128, description = "会员卡特权"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertMemberCardService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertMemberCardService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String token = applicationContext.getToken();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("status", String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
        String messageTitle = parameters.get("memberCardName");
        String messageURL = parameters.get("memberCardURL");
        logger.debug("parameters={}", parameters);
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        List<Order> orderList = new ArrayList<Order>(1);
        Order memberCardConfigIdOrder = new Order("memberCardConfigId", OrderEnum.DESC);
        orderList.add(memberCardConfigIdOrder);
        EntityDao<MemberCardConfig> entityConfigDAO = applicationContext.getEntityDAO(EntityNames.memberCardConfig);
        List<MemberCardConfig> entityList = entityConfigDAO.inquireByCondition(conditionList, orderList);
        long memberCardConfigId = -1;
        if (entityList != null && !entityList.isEmpty()) {
            memberCardConfigId = entityList.get(0).getMemberCardConfigId();
        }
        EntityDao<MemberCard> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCard);
        MemberCard entity = entityDAO.insert(parameters);
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
            responseMessageMap.put("cfgType", String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
            responseMessageMap.put("relatedEventId", String.valueOf(memberCardConfigId));
            responseMessageMap.put("relatedId", String.valueOf(entity.getMemberCardId()));
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
            if (responseMessageCfg != null) {
                ResponseMessageCache.getInstance().insertCache(responseMessageCfg);
                EntityDao<Resources> resourcesDAO = applicationContext.getEntityDAO(EntityNames.resources);
                Map<String, String> resourceMap = new HashMap<String, String>(4, 1);
                String memberCardURL = SalesConstant.MEMBER_CARD_URL + "?token=" + token + "&memberCardId=" + entity.getMemberCardId();
                resourceMap.put("resourcesURL", memberCardURL);
                resourceMap.put("resourcesName", entity.getMemberCardName());
                resourceMap.put("resourcesType", String.valueOf(SalesConstant.SYSTEM_RESOURCE_TYPE));
                resourceMap.put("companyId", String.valueOf(companyId));
                resourceMap.put("createTime", DateTimeUtils.currentDay());
                resourcesDAO.insert(resourceMap);
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }

        } else {
            throw new RollBackException("操作失败");
        }

    }
}
