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
import com.sales.entity.Coupon;
import com.sales.entity.CouponConfig;
import com.sales.entity.Reservation;
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
        act = ActionNames.insertCoupon,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"couponId", "couponName", "status", "couponConfigId", "availableCount", "takeConditionType", "couponDesc", "effectiveStartTime", "effectiveEndTime", "messageTitle", "messageURL", "messageDesc", "keyword"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增Coupon",
        validateParameters = {
    @Field(fieldName = "couponId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券配置id"),
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
public class InsertCouponService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertCouponService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String token = applicationContext.getToken();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("createTime", DateTimeUtils.currentDay());
        parameters.put("status", String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
        logger.debug("parameters={}", parameters);
        String messageTitle = parameters.get("messageTitle");
        String messageURL = parameters.get("messageURL");
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        List<Order> orderList = new ArrayList<Order>(1);
        Order couponConfigIdOrder = new Order("couponConfigId", OrderEnum.DESC);
        orderList.add(couponConfigIdOrder);
        EntityDao<CouponConfig> entityConfigDAO = applicationContext.getEntityDAO(EntityNames.couponConfig);
        List<CouponConfig> entityList = entityConfigDAO.inquireByCondition(conditionList, orderList);
        long couponConfigId = -1;
        if (entityList != null && !entityList.isEmpty()) {
            couponConfigId = entityList.get(0).getCouponConfigId();
        }
        EntityDao<Coupon> entityDAO = applicationContext.getEntityDAO(EntityNames.coupon);
        Coupon entity = entityDAO.insert(parameters);
        if (entity != null) {
            Map<String, String> responseMessageMap = new HashMap<String, String>(4, 1);
            responseMessageMap.put("companyId", String.valueOf(companyId));
            responseMessageMap.put("type", String.valueOf(SalesConstant.RESPONSE_TEXT_TYPE));
            responseMessageMap.put("responseContentType", String.valueOf(SalesConstant.COPOUN_TYPE));
            if (messageTitle != null) {
                responseMessageMap.put("responseContent", messageTitle);
            }
            if (messageURL != null) {
                responseMessageMap.put("responseImageURL", messageURL);
            }
            responseMessageMap.put("keyword", parameters.get("keyword"));
            responseMessageMap.put("cfgType", String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
            responseMessageMap.put("relatedEventId", String.valueOf(couponConfigId));
            responseMessageMap.put("relatedId", String.valueOf(entity.getCouponId()));
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
            if (responseMessageCfg != null) {
                ResponseMessageCache.getInstance().insertCache(responseMessageCfg);
                EntityDao<Resources> resourcesDAO = applicationContext.getEntityDAO(EntityNames.resources);
                Map<String, String> resourceMap = new HashMap<String, String>(4, 1);
                String couponURL = SalesConstant.COPOUN_URL + "?token=" + token + "&couponId=" + entity.getCouponId();
                resourceMap.put("resourcesURL", couponURL);
                resourceMap.put("resourcesName", entity.getCouponName());
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
