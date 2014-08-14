package com.sales.service;

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
import com.framework.exception.RollBackException;
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.entity.EntityNames;
import com.sales.entity.Coupon;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteCoupon,
        importantParameters = {"session", "encryptType","couponId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Coupon操作",
        validateParameters = {
    	@Field(fieldName ="couponId", fieldType = FieldTypeEnum.BIG_INT, description ="优惠券id"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="couponConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="优惠券配置id"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="status", fieldType = FieldTypeEnum.TYINT, description ="活动0-禁止1-启用"),
	@Field(fieldName ="couponName", fieldType = FieldTypeEnum.CHAR36, description ="优惠券名称"),
	@Field(fieldName ="availableCount", fieldType = FieldTypeEnum.TYINT, description ="不限"),
	@Field(fieldName ="takeConditionType", fieldType = FieldTypeEnum.TYINT, description ="领取条件-1不限1会员"),
	@Field(fieldName ="couponDesc", fieldType = FieldTypeEnum.CHAR128, description ="优惠券说明"),
	@Field(fieldName ="effectiveStartTime", fieldType = FieldTypeEnum.DATETIME, description ="有效开始时间"),
	@Field(fieldName ="effectiveEndTime", fieldType = FieldTypeEnum.DATETIME, description ="有效结束时间"),
	@Field(fieldName ="messageTitle", fieldType = FieldTypeEnum.CHAR36, description ="消息标题"),
	@Field(fieldName ="messageURL", fieldType = FieldTypeEnum.CHAR64, description ="消息图片地址"),
	@Field(fieldName ="messageDesc", fieldType = FieldTypeEnum.CHAR128, description ="消息描述"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键字"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteCouponService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteCouponService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String couponId = parameters.get("couponId");
	primaryKey.putKeyField("couponId",String.valueOf(couponId));

        EntityDao<Coupon> entityDAO = applicationContext.getEntityDAO(EntityNames.coupon);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
