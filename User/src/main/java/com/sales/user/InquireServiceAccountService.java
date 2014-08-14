package com.sales.user;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.Company;
import com.sales.entity.EntityNames;
import com.sales.entity.WeixinToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireServiceAccount,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"appId", "appSecret", "accessToken", "expireTime", "weixinNO", "weixinEmail", "weixinDevAPI"},
        description = "查询服务号的信息",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireServiceAccountService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        EntityDao<WeixinToken> weixinTokenDAO = applicationContext.getEntityDAO(EntityNames.weixinToken);
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondtion = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondtion);
        List<WeixinToken> weixinTokenList = weixinTokenDAO.inquireByCondition(conditionList);
        if (weixinTokenList != null && !weixinTokenList.isEmpty()) {
            WeixinToken weixinToken = weixinTokenList.get(0);
            Map<String, String> weixinTokenMap = weixinToken.toMap();
            weixinTokenMap.put("accessToken", "");
            EntityDao<Company> companyDAO = applicationContext.getEntityDAO(EntityNames.company);
            PrimaryKey primaryKey = new PrimaryKey();
            primaryKey.putKeyField("companyId", String.valueOf(companyId));
            Company company = companyDAO.inqurieByKey(primaryKey);
            weixinTokenMap.put("accessToken", company.getCompanyToken());
            applicationContext.setMapData(weixinTokenMap);
            applicationContext.success();
        } else {
            Map<String, String> weixinTokenMap = new HashMap<String, String>(8,1);
            EntityDao<Company> companyDAO = applicationContext.getEntityDAO(EntityNames.company);
            PrimaryKey primaryKey = new PrimaryKey();
            weixinTokenMap.put("appId", "");
            weixinTokenMap.put("appSecret", "");
            weixinTokenMap.put("expireTime", "");
            weixinTokenMap.put("weixinNO", "");
            weixinTokenMap.put("weixinEmail", "");
            weixinTokenMap.put("weixinDevAPI", "");
            primaryKey.putKeyField("companyId", String.valueOf(companyId));
            Company company = companyDAO.inqurieByKey(primaryKey);
            weixinTokenMap.put("accessToken", company.getCompanyToken());
            applicationContext.setMapData(weixinTokenMap);
            applicationContext.success();
        }

    }
}
