package com.sales.user;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.WeixinToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.bindWeixinInterface,
        requestEncrypt = CryptEnum.AES,
        importantParameters = {"weixinNO", "weixinDevAPI","validateToken","session", "encryptType"},
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        description = "微信接口配置",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "weixinNO", fieldType = FieldTypeEnum.CHAR128, description = "微信号"),
    @Field(fieldName = "weixinDevAPI", fieldType = FieldTypeEnum.CHAR128, description = "微信api"),
     @Field(fieldName = "validateToken", fieldType = FieldTypeEnum.CHAR128, description = "微信验证token"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class BindWeixinInterfaceService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> dataMap = applicationContext.getSimpleMapParameters();
        dataMap.put("companyId", String.valueOf(companyId));
        EntityDao<WeixinToken> weixinTokenDAO = applicationContext.getEntityDAO(EntityNames.weixinToken);
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyIdCondtion = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondtion);
        synchronized (this) {
            List<WeixinToken> weixinTokenList = weixinTokenDAO.inquireByCondition(conditionList);
            if (weixinTokenList == null || weixinTokenList.isEmpty()) {
                weixinTokenDAO.insert(dataMap);
                applicationContext.success();
            } else {
                WeixinToken weixinToken = weixinTokenList.get(0);
                dataMap.put("tokenId", String.valueOf(weixinToken.getTokenId()));
                weixinTokenDAO.update(dataMap);
                applicationContext.success();
            }
        }
    }
}
