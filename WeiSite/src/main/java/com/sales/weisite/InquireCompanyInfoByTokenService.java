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
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
import com.sales.entity.Company;
import com.sales.entity.EntityNames;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireCompanyInfoByToken,
        importantParameters = {"token"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        requireLogin = LoginEnum.NOT_REQUIRE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"companyName", "password", "province", "city", "region", "street", "linkMobile", "email", "logoURL"},
        description = "查询公司信息",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "token")
})
public class InquireCompanyInfoByTokenService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String weixinId = parameters.get("weixinId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyCondition);
            Condition companyTokenCondition = new Condition("companyToken", ConditionTypeEnum.EQUAL, token);
            conditionList.add(companyTokenCondition);
            EntityDao<Company> companyDAO = applicationContext.getEntityDAO(EntityNames.company);
            List<Company> companyList = companyDAO.inquireByCondition(conditionList);
            if (companyList != null && !companyList.isEmpty()) {
               applicationContext.setEntityData(companyList.get(0));
               applicationContext.success();
            } else {
                applicationContext.fail(SalesErrorCode.USER_NOT_EXISTS);
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }
    }
}
