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
import com.sales.entity.Merchant;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireMerchantById,
        importantParameters = {"session", "encryptType", "merchantId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"merchantId","merchantName","lag","lat","matchType","keyword","backgroupImageURL","createTime","companyId","telphone","address","merchantConfigId","status"},
        description = "查询Merchant详细内容",
        validateParameters = {
          	@Field(fieldName ="merchantId", fieldType = FieldTypeEnum.BIG_INT, description ="商家id"),
	@Field(fieldName ="merchantName", fieldType = FieldTypeEnum.CHAR36, description ="门店名字"),
	@Field(fieldName ="lag", fieldType = FieldTypeEnum.DOUBLE, description ="精度"),
	@Field(fieldName ="lat", fieldType = FieldTypeEnum.DOUBLE, description ="维度"),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配模式1-精确2-模糊"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键字"),
	@Field(fieldName ="backgroupImageURL", fieldType = FieldTypeEnum.CHAR64, description ="背景图片"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="telphone", fieldType = FieldTypeEnum.CHAR36, description ="联系电话"),
	@Field(fieldName ="address", fieldType = FieldTypeEnum.CHAR128, description ="地址"),
	@Field(fieldName ="merchantConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="门店配置信息"),
	@Field(fieldName ="status", fieldType = FieldTypeEnum.TYINT, description ="状态1-有效0-无效"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMerchantByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireMerchantByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String merchantId = parameters.get("merchantId");
	primaryKey.putKeyField("merchantId",String.valueOf(merchantId));

        EntityDao<Merchant> entityDAO = applicationContext.getEntityDAO(EntityNames.merchant);
          Merchant entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
