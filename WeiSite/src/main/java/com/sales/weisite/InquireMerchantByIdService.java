package com.sales.weisite;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PageModel;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.Merchant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireMerchantById,
        importantParameters = {"session", "merchantId", "encryptType"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"merchantId","merchantName","lag","lat","matchType","keyword","backgroupImageURL","createTime","companyId","telphone","address"},
        description = "查询门店详情",
        validateParameters = {
    @Field(fieldName = "merchantId", fieldType = FieldTypeEnum.BIG_INT, description = "门店id"),
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMerchantByIdService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireMerchantByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        String merchantId = parameters.get("merchantId");
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("merchantId", merchantId);
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        EntityDao<Merchant> entityDAO = applicationContext.getEntityDAO(EntityNames.merchant);
        Merchant merchant = entityDAO.inqurieByKey(primaryKey);
        if (merchant != null) {
            applicationContext.setEntityData(merchant);
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
