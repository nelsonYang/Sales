package com.sales.service;

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
        act = ActionNames.insertMerchant,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"merchantName","lag","lat","matchType","keyword","backgroupImageURL","createTime","companyId","telphone","address","merchantConfigId","status"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增Merchant",
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

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertMerchantService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertMerchantService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<Merchant> entityDAO = applicationContext.getEntityDAO(EntityNames.merchant);
        Merchant entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
