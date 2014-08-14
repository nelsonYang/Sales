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
import com.sales.entity.CouponConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteCouponConfig,
        importantParameters = {"session", "encryptType","couponConfigId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除CouponConfig操作",
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

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteCouponConfigService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteCouponConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String couponConfigId = parameters.get("couponConfigId");
	primaryKey.putKeyField("couponConfigId",String.valueOf(couponConfigId));

        EntityDao<CouponConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.couponConfig);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
