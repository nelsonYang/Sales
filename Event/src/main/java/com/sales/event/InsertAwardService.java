package com.sales.event;

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
import com.sales.config.SalesActionName;
import com.sales.entity.Award;
import com.sales.entity.EntityNames;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.insertAward,
        importantParameters = {"session", "encryptType", "awardName", "eventId", "awardNum", "awardNumPerPerson"},
        minorParameters = {"awardDesc"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.BATCH_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "添加奖项设置",
        validateParameters = {
    @Field(fieldName = "awardName", fieldType = FieldTypeEnum.CHAR24, description = "奖项名称"),
    @Field(fieldName = "awardDesc", fieldType = FieldTypeEnum.CHAR512, description = "奖项描述"),
    @Field(fieldName = "eventId", fieldType = FieldTypeEnum.BIG_INT, description = "归属的活动id"),
    @Field(fieldName = "awardNum", fieldType = FieldTypeEnum.TYINT, description = "奖项的数量"),
    @Field(fieldName = "awardNumPerPerson", fieldType = FieldTypeEnum.TYINT, description = "每个人领取奖项的数量"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertAwardService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String[]> parameters = applicationContext.getBatchMapParameters();
        long companyId = applicationContext.getUserId();
        //"awardName", "eventId", "awardNum", "awardNumPerPerson"
        String[] awardNames = parameters.get("awardName");
        String[] eventIds = parameters.get("eventId");
        String[] awardNums = parameters.get("awardNum");
        String[] awardNumPerPersons = parameters.get("awardNumPerPerson");
        String[] awardDescs = parameters.get("awardDesc");
        Map<String, String> awardMap;
        boolean isSuccess = true;
        for (int index = 0; index < eventIds.length; index++) {
            awardMap = new HashMap<String, String>(8, 1);
            awardMap.put("companyId", String.valueOf(companyId));
            awardMap.put("awardNum", awardNums[index]);
            awardMap.put("awardCurrentNum", "0");
            awardMap.put("awardName", awardNames[index]);
            awardMap.put("awardNumPerPerson", awardNumPerPersons[index]);
            awardMap.put("eventId", eventIds[index]);
            if (awardDescs != null && awardDescs.length > 0) {
                awardMap.put("awardDesc", awardDescs[index]);
            }
            EntityDao<Award> awardDao = applicationContext.getEntityDAO(EntityNames.award);
            Award award = awardDao.insert(awardMap);
            if (award == null) {
                isSuccess = false;

            }
        }
        if (isSuccess) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }

    }
}
