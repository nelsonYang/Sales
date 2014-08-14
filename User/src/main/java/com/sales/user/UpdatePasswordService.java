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
import com.sales.config.SalesErrorCode;
import com.sales.entity.Company;
import com.sales.entity.EntityNames;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.updatePassword,
        requestEncrypt = CryptEnum.AES,
        importantParameters = {"session", "encryptType","oldPassword", "password"},
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"companyName", "password", "province", "city", "region", "street","linkMobile", "email", "qqNO"},
        description = "修改密码",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "oldPassword", fieldType = FieldTypeEnum.CHAR64, description = "原始密码"),
    @Field(fieldName = "password", fieldType = FieldTypeEnum.CHAR64, description = "密码"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdatePasswordService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> dataMap = applicationContext.getSimpleMapParameters();
        String oldPassword = dataMap.get("oldPassword");
        EntityDao<Company> companyDAO = applicationContext.getEntityDAO(EntityNames.company);
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        Company company = companyDAO.inqurieByKey(primaryKey);
        if (company.getPassword().equals(oldPassword)) {
            dataMap.put("companyId", String.valueOf(companyId));
            company = companyDAO.update(dataMap);
            Map<String, String> companyMap = company.toMap();
            applicationContext.setMapData(companyMap);
            applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.OLD_PASSWORD_ERROR);
        }
    }
}
