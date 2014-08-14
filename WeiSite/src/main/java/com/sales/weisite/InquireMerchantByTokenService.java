package com.sales.weisite;

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
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.Merchant;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireMerchantByToken,
        importantParameters = {"token", "merchantId"},
        minorParameters = {"weixinId"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        requireLogin = LoginEnum.NOT_REQUIRE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"merchantId", "merchantName", "lag", "lat", "matchType", "keyword", "backgroupImageURL", "createTime", "companyId", "telphone", "address"},
        description = "查询门店详情",
        validateParameters = {
    @Field(fieldName = "merchantId", fieldType = FieldTypeEnum.BIG_INT, description = "门店id"),
    @Field(fieldName = "weixinId", fieldType = FieldTypeEnum.CHAR128, description = "微信id"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR128, description = "token"),
   @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMerchantByTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireMerchantByTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String weixinId = parameters.get("weixinId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            String merchantId = parameters.get("merchantId");
            PrimaryKey primaryKey = new PrimaryKey();
            primaryKey.putKeyField("merchantId", merchantId);
            primaryKey.putKeyField("companyId", tokens[1]);
            EntityDao<Merchant> entityDAO = applicationContext.getEntityDAO(EntityNames.merchant);
            Merchant merchant = entityDAO.inqurieByKey(primaryKey);
            if (merchant != null) {
                applicationContext.setEntityData(merchant);
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
