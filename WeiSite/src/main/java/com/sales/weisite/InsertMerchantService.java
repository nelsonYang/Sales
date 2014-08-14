package com.sales.weisite;

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
import com.sales.entity.MemberCardConfig;
import com.sales.entity.Merchant;
import com.sales.entity.MerchantConfig;
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
        act = ActionNames.insertMerchant,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"merchantName", "lag", "lat", "matchType", "keyword", "backgroupImageURL", "merchantConfigId", "telphone", "address"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增Merchant",
        validateParameters = {
    @Field(fieldName = "merchantName", fieldType = FieldTypeEnum.CHAR36, description = "门店名字"),
    @Field(fieldName = "lag", fieldType = FieldTypeEnum.DOUBLE, description = "精度"),
    @Field(fieldName = "lat", fieldType = FieldTypeEnum.DOUBLE, description = "维度"),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配模式1-精确2-模糊"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "backgroupImageURL", fieldType = FieldTypeEnum.CHAR128, description = "背景图片"),
    @Field(fieldName = "merchantConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "配置id"),
    @Field(fieldName = "telphone", fieldType = FieldTypeEnum.CHAR36, description = "联系电话"),
    @Field(fieldName = "address", fieldType = FieldTypeEnum.CHAR128, description = "地址"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertMerchantService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertMerchantService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String token = applicationContext.getToken();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("createTime", DateTimeUtils.currentDay());
        parameters.put("status", String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
        logger.debug("parameters={}", parameters);
        String messageTitle = parameters.get("merchantName");
        String messageImageURL = parameters.get("backgroupImageURL");
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        List<Order> orderList = new ArrayList<Order>(1);
        Order memberCardConfigIdOrder = new Order("merchantConfigId", OrderEnum.DESC);
        orderList.add(memberCardConfigIdOrder);
        EntityDao<MerchantConfig> entityConfigDAO = applicationContext.getEntityDAO(EntityNames.merchantConfig);
        List<MerchantConfig> entityList = entityConfigDAO.inquireByCondition(conditionList, orderList);
        long memberCardConfigId = -1;
        if (entityList != null && !entityList.isEmpty()) {
            memberCardConfigId = entityList.get(0).getMerchantConfigId();
        }
        EntityDao<Merchant> entityDAO = applicationContext.getEntityDAO(EntityNames.merchant);
        Merchant entity = entityDAO.insert(parameters);
        if (entity != null) {
            Map<String, String> responseMessageMap = new HashMap<String, String>(4, 1);
            responseMessageMap.put("companyId", String.valueOf(companyId));
            responseMessageMap.put("type", String.valueOf(SalesConstant.RESPONSE_TEXT_TYPE));
            responseMessageMap.put("responseContentType", String.valueOf(SalesConstant.MERCHATE_TYPE));
            responseMessageMap.put("cfgType", String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
            if (messageImageURL != null) {
                responseMessageMap.put("responseImageURL", messageImageURL);
            }
            if (messageTitle != null) {
                responseMessageMap.put("responseContent", messageTitle);
            }
            responseMessageMap.put("keyword", parameters.get("keyword"));
            responseMessageMap.put("relatedEventId", String.valueOf(memberCardConfigId));
            responseMessageMap.put("relatedId", String.valueOf(entity.getMerchantId()));
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            ResponseMessageCfg responseMessageCfg = responseMessageDAO.insert(responseMessageMap);
            if (responseMessageCfg != null) {
                ResponseMessageCache.getInstance().insertCache(responseMessageCfg);
                EntityDao<Resources> resourcesDAO = applicationContext.getEntityDAO(EntityNames.resources);
                Map<String, String> resourceMap = new HashMap<String, String>(4, 1);
                String merchantURL = SalesConstant.MERCHANT_URL + "?token=" + token + "&merchantId=" + entity.getMerchantId();
                resourceMap.put("resourcesURL", merchantURL);
                resourceMap.put("resourcesName", entity.getMerchantName());
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
