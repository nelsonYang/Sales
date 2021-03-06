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
import com.sales.entity.MerchantConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteMerchantConfig,
        importantParameters = {"session", "encryptType","merchantConfigId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除MerchantConfig操作",
        validateParameters = {
    	@Field(fieldName ="merchantConfigId", fieldType = FieldTypeEnum.TYINT, description ="门店id"),
	@Field(fieldName ="merchantName", fieldType = FieldTypeEnum.CHAR36, description ="门店名称"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键子"),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配模式"),
	@Field(fieldName ="messageTitle", fieldType = FieldTypeEnum.CHAR36, description ="消息标题"),
	@Field(fieldName ="messageImageURL", fieldType = FieldTypeEnum.CHAR64, description ="消息图片"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteMerchantConfigService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteMerchantConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String merchantConfigId = parameters.get("merchantConfigId");
	primaryKey.putKeyField("merchantConfigId",String.valueOf(merchantConfigId));

        EntityDao<MerchantConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.merchantConfig);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
