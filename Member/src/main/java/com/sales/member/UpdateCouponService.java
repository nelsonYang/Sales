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
import com.sales.entity.EntityNames;
import com.sales.entity.Coupon;
import com.sales.entity.Reservation;
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
        act = ActionNames.updateCoupon,
        importantParameters = {"session", "encryptType", "couponId"},
        minorParameters = {"couponName", "status", "couponConfigId", "availableCount", "takeConditionType", "couponDesc", "effectiveStartTime", "effectiveEndTime", "messageTitle", "messageURL", "messageDesc", "keyword"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改Coupon",
        validateParameters = {
    @Field(fieldName = "couponId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券id"),
    @Field(fieldName = "memberId", fieldType = FieldTypeEnum.BIG_INT, description = "会员id"),
    @Field(fieldName = "couponConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券配置id"),
    @Field(fieldName = "status", fieldType = FieldTypeEnum.TYINT, description = "活动0-禁止1-启用"),
    @Field(fieldName = "couponName", fieldType = FieldTypeEnum.CHAR36, description = "优惠券名称"),
    @Field(fieldName = "availableCount", fieldType = FieldTypeEnum.TYINT, description = "可用数量"),
    @Field(fieldName = "takeConditionType", fieldType = FieldTypeEnum.TYINT, description = "领取条件-1不限1会员"),
    @Field(fieldName = "couponDesc", fieldType = FieldTypeEnum.CHAR128, description = "优惠券说明"),
    @Field(fieldName = "effectiveStartTime", fieldType = FieldTypeEnum.DATETIME, description = "有效开始时间"),
    @Field(fieldName = "effectiveEndTime", fieldType = FieldTypeEnum.DATETIME, description = "有效结束时间"),
    @Field(fieldName = "messageTitle", fieldType = FieldTypeEnum.CHAR36, description = "消息标题"),
    @Field(fieldName = "messageURL", fieldType = FieldTypeEnum.CHAR128, description = "消息图片地址"),
    @Field(fieldName = "messageDesc", fieldType = FieldTypeEnum.CHAR128, description = "消息描述"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateCouponService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateCouponService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        String couponId = parameters.get("couponId");
        String keyword = parameters.get("keyword");
        String matchType = parameters.get("matchType");
        String messageTitle = parameters.get("messageTitle");
        String messageImageURL = parameters.get("messageURL");
        EntityDao<Coupon> entityDAO = applicationContext.getEntityDAO(EntityNames.coupon);
        Coupon entity = entityDAO.update(parameters);
        if (entity != null) {
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            List<Condition> conditionList = new ArrayList<Condition>(3);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            conditionList.add(companyIdCondition);
            Condition relateEventIdCondition = new Condition("relatedId", ConditionTypeEnum.EQUAL, couponId);
            conditionList.add(relateEventIdCondition);
            Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.COPOUN_TYPE));
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
