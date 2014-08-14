package com.sales.user;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
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
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireCompanyInfo,
        requestEncrypt = CryptEnum.AES,
        importantParameters = {"session", "encryptType"},
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"companyId", "companyName", "password", "province", "city", "region", "street","linkMobile", "email","serviceStartTime","serviceEndTime"},
        description = "查询公司信息",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireCompanyInfoService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        EntityDao<Company> companyDAO = applicationContext.getEntityDAO(EntityNames.company);
//        List<Condition> conditionList = new ArrayList<Condition>(1);
//        Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
//        conditionList.add(companyCondition);
//        List<Company> companyList = companyDAO.inquireByCondition(conditionList);
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        Company company = companyDAO.inqurieByKey(primaryKey);
        Map<String, String> companyMap = company.toMap();
        applicationContext.setMapData(companyMap);
        applicationContext.success();

    }
}
