package com.sales.event;

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
import com.sales.entity.Award;
import com.sales.entity.EntityNames;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.deleteAward,
        importantParameters = {"session", "encryptType", "awardId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除奖项",
        validateParameters = {
    @Field(fieldName = "awardId", fieldType = FieldTypeEnum.BIG_INT, description = "奖项id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteAwardService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        String awardId = parameters.get("awardId");
        EntityDao<Award> awardDao = applicationContext.getEntityDAO(EntityNames.award);
        PrimaryKey awardPK = new PrimaryKey();
        awardPK.putKeyField("companyId", String.valueOf(companyId));
        awardPK.putKeyField("awardId", awardId);
        boolean isSuccess = awardDao.delete(awardPK);
        if (isSuccess) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }

    }
}
