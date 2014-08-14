package com.sales.weiapplication;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.PersonCard;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.deletePersonCard,
        importantParameters = {"session", "encryptType", "personCardId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除名片",
        validateParameters = {
    @Field(fieldName = "personCardId", fieldType = FieldTypeEnum.BIG_INT, description = "名片id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeletePersonCardService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String personCardId = parameters.get("personCardId");
        EntityDao<PersonCard> personCardDAO = applicationContext.getEntityDAO(EntityNames.personCard);
        PrimaryKey personCardPK = new PrimaryKey();
        personCardPK.putKeyField("companyId", String.valueOf(companyId));
        personCardPK.putKeyField("personCardId", personCardId);
        boolean isSuccess = personCardDAO.delete(personCardPK);
        if (isSuccess) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }

    }
}
