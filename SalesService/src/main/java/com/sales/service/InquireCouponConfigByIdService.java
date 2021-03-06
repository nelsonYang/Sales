package com.sales.service;

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
        returnParameters = {"couponConfigId","keyword","matchType","messageTitle","messageImageURL","createTime","companyId","couponNum","couponCurrentNum","effectiveStartTime","effectiveEndTime","couponName","couponDesc"},
        description = "查询CouponConfig详细内容",
        validateParameters = {
          	@Field(fieldName ="couponConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="优惠券配置id"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键字"),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配类型"),
	@Field(fieldName ="messageTitle", fieldType = FieldTypeEnum.CHAR36, description ="消息标题"),
	@Field(fieldName ="messageImageURL", fieldType = FieldTypeEnum.CHAR64, description ="图片地址"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="穿见时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="couponNum", fieldType = FieldTypeEnum.BIG_INT, description ="优惠券设置的数量"),
	@Field(fieldName ="couponCurrentNum", fieldType = FieldTypeEnum.BIG_INT, description ="优惠券剩下的数量"),
	@Field(fieldName ="effectiveStartTime", fieldType = FieldTypeEnum.DATETIME, description ="生效开始时间"),
	@Field(fieldName ="effectiveEndTime", fieldType = FieldTypeEnum.DATETIME, description ="生效的结束时间"),
	@Field(fieldName ="couponName", fieldType = FieldTypeEnum.CHAR36, description ="优惠券名称"),
	@Field(fieldName ="couponDesc", fieldType = FieldTypeEnum.CHAR128, description ="优惠券的说明"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireCouponConfigByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireCouponConfigByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String couponConfigId = parameters.get("couponConfigId");
	primaryKey.putKeyField("couponConfigId",String.valueOf(couponConfigId));

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
