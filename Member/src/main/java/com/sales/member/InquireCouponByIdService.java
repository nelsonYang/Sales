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
import com.sales.entity.Coupon;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireCouponById,
        importantParameters = {"session", "encryptType", "couponId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"couponId","companyId","couponConfigId","createTime","status","couponName","availableCount","takeConditionType","couponDesc","effectiveStartTime","effectiveEndTime","messageTitle","messageURL","messageDesc","keyword"},
        description = "查询Coupon详细内容",
        validateParameters = {
    @Field(fieldName = "couponId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireCouponByIdService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireCouponByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        PrimaryKey primaryKey = new PrimaryKey();
        String couponId = parameters.get("couponId");
        primaryKey.putKeyField("couponId", String.valueOf(couponId));
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        EntityDao<Coupon> entityDAO = applicationContext.getEntityDAO(EntityNames.coupon);
        Coupon entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }


    }
}
