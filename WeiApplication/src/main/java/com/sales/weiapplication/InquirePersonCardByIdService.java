package com.sales.weiapplication;

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
import com.sales.entity.EntityNames;
import com.sales.entity.PersonCard;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquirePersonCardById,
        importantParameters = {"session", "encryptType", "personCardId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"personCardId", "personName", "personMobile", "personEmail", "qqNO", "weiboNo", "personIconURL", "companyName", "companyAddress"},
        description = "查询名片详细",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "personCardId", fieldType = FieldTypeEnum.BIG_INT, description = "名片id"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePersonCardByIdService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String personCardId = parameters.get("personCardId");
        EntityDao<PersonCard> personCardDAO = applicationContext.getEntityDAO(EntityNames.personCard);
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("personCardId", personCardId);
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        PersonCard personCard = personCardDAO.inqurieByKey(primaryKey);
        if (personCard != null) {
            applicationContext.setEntityData(personCard);
            applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.ENTITY_NOT_FOUND);
        }


    }
}
