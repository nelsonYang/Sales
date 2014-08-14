package com.sales.weiapplication;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.framework.utils.DateTimeUtils;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.PersonCard;
import java.sql.Timestamp;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.insertPersonCard,
        importantParameters = {"session", "encryptType", "personName", "personMobile", "personEmail", "qqNO", "weiboNo"},
        minorParameters = {"personIconURL", "companyName", "companyAddress"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增名片",
        validateParameters = {
    @Field(fieldName = "personName", fieldType = FieldTypeEnum.CHAR24, description = "名片名字"),
    @Field(fieldName = "personMobile", fieldType = FieldTypeEnum.CHAR512, description = "名片手机"),
    @Field(fieldName = "personEmail", fieldType = FieldTypeEnum.CHAR128, description = "名片邮箱"),
    @Field(fieldName = "qqNO", fieldType = FieldTypeEnum.CHAR24, description = "名片qq号"),
    @Field(fieldName = "weiboNo", fieldType = FieldTypeEnum.CHAR24, description = "名片微信号"),
    @Field(fieldName = "personIconURL", fieldType = FieldTypeEnum.CHAR128, description = "图片地址"),
    @Field(fieldName = "companyName", fieldType = FieldTypeEnum.CHAR128, description = "公司名称"),
    @Field(fieldName = "companyAddress", fieldType = FieldTypeEnum.CHAR128, description = "公司地址"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertPersonCardService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("createTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
        EntityDao<PersonCard> personCardDAO = applicationContext.getEntityDAO(EntityNames.personCard);
        PersonCard personCard = personCardDAO.insert(parameters);
        if (personCard != null) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }

    }
}
