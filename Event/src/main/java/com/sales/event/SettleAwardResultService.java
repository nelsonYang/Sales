package com.sales.event;

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
import com.framework.utils.DateTimeUtils;
import com.sales.config.SalesActionName;
import com.sales.config.SalesConstant;
import com.sales.entity.AwardResult;
import com.sales.entity.EntityNames;
import java.sql.Timestamp;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.settleAwardResult,
        importantParameters = {"session", "encryptType", "awardResultId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        description = "处理奖项的发放",
        validateParameters = {
    @Field(fieldName = "awardResultId", fieldType = FieldTypeEnum.BIG_INT, description = "奖项结果id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class SettleAwardResultService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("awardStatus", String.valueOf(SalesConstant.PROCESSED_AWARD_STATUS));
        parameters.put("settleTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
        EntityDao<AwardResult> awardResultDao = applicationContext.getEntityDAO(EntityNames.awardResult);
        AwardResult awardResult = awardResultDao.update(parameters);
        if (awardResult != null) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }
    }
}
