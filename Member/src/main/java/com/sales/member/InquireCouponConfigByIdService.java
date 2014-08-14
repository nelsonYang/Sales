package com.sales.member;

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
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.CouponConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireCouponConfigById,
        importantParameters = {"session", "encryptType", "couponConfigId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"couponConfigId", "keyword", "matchType", "messageTitle", "messageImageURL", "createTime", "companyId", "couponNum", "couponCurrentNum", "effectiveStartTime", "effectiveEndTime", "couponName", "couponDesc"},
        description = "查询CouponConfig详细内容",
        validateParameters = {
    @Field(fieldName = "couponConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券配置id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireCouponConfigByIdService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireCouponConfigByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        PrimaryKey primaryKey = new PrimaryKey();
        String couponConfigId = parameters.get("couponConfigId");
        primaryKey.putKeyField("couponConfigId", String.valueOf(couponConfigId));
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        EntityDao<CouponConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.couponConfig);
        CouponConfig entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }


    }
}
