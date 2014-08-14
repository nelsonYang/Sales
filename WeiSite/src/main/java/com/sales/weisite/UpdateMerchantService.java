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
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.MemberCard;
import com.sales.entity.Merchant;
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
        act = ActionNames.updateMerchant,
        importantParameters = {"session", "encryptType", "merchantId"},
        minorParameters = {"merchantName", "lag", "lat", "matchType", "keyword", "backgroupImageURL", "merchantConfigId", "telphone", "address"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改Merchant",
        validateParameters = {
    @Field(fieldName = "merchantId", fieldType = FieldTypeEnum.BIG_INT, description = "商家id"),
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
public class UpdateMerchantService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateMerchantService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        EntityDao<Merchant> entityDAO = applicationContext.getEntityDAO(EntityNames.merchant);
        String keyword = parameters.get("keyword");
        String matchType = parameters.get("matchType");
        String messageTitle = parameters.get("merchantName");
        String messageImageURL = parameters.get("backgroupImageURL");
        Merchant entity = entityDAO.update(parameters);
        if (entity != null) {
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            List<Condition> conditionList = new ArrayList<Condition>(3);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            conditionList.add(companyIdCondition);
            Condition relateEventIdCondition = new Condition("relatedId", ConditionTypeEnum.EQUAL, String.valueOf(entity.getMerchantId()));
            conditionList.add(relateEventIdCondition);
            Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.MERCHATE_TYPE));
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
