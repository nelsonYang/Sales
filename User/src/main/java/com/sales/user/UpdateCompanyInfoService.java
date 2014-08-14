package com.sales.user;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
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
        act = SalesActionName.updateCompanyInfo,
        requestEncrypt = CryptEnum.AES,
        minorParameters = {"session", "encryptType","companyName", "password", "province", "city", "region", "street", "linkMobile", "email","qqNO"},
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"companyName", "password", "province", "city", "region","street", "linkMobile", "email","qqNO"},
        description = "修改公司信息",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "companyName", fieldType = FieldTypeEnum.CHAR24, description = "用户名"),
    @Field(fieldName = "password", fieldType = FieldTypeEnum.CHAR64, description = "密码"),
    @Field(fieldName = "province", fieldType = FieldTypeEnum.INT, description = "省"),
    @Field(fieldName = "city", fieldType = FieldTypeEnum.INT, description = "市"),
    @Field(fieldName = "region", fieldType = FieldTypeEnum.INT, description = "区"),
    @Field(fieldName = "street", fieldType = FieldTypeEnum.CHAR8, description = "街道"),
    @Field(fieldName = "linkMobile", fieldType = FieldTypeEnum.CHAR24, description = "联系电话"),
    @Field(fieldName = "email", fieldType = FieldTypeEnum.CHAR24, description = "邮箱"),
    @Field(fieldName = "qqNO", fieldType = FieldTypeEnum.CHAR24, description = "qq号"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateCompanyInfoService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> dataMap = applicationContext.getSimpleMapParameters();
        EntityDao<Company> companyDAO = applicationContext.getEntityDAO(EntityNames.company);
        dataMap.put("companyId", String.valueOf(companyId));
        Company company = companyDAO.update(dataMap);
        Map<String, String> companyMap = company.toMap();
        applicationContext.setMapData(companyMap);
        applicationContext.success();

    }
}
