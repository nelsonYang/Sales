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
import com.sales.entity.WeiBarConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeiBarConfigById,
        importantParameters = {"session", "encryptType", "weiBarConfigId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weiBarConfigId","keyword","matchType","messageTitle","messageImageURL","companyId"},
        description = "查询WeiBarConfig详细内容",
        validateParameters = {
          	@Field(fieldName ="weiBarConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="微吧配置id"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键字"),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配度1-精确2-模糊"),
	@Field(fieldName ="messageTitle", fieldType = FieldTypeEnum.CHAR36, description ="消息标题"),
	@Field(fieldName ="messageImageURL", fieldType = FieldTypeEnum.CHAR64, description ="图片url"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeiBarConfigByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireWeiBarConfigByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String weiBarConfigId = parameters.get("weiBarConfigId");
	primaryKey.putKeyField("weiBarConfigId",String.valueOf(weiBarConfigId));

        EntityDao<WeiBarConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiBarConfig);
          WeiBarConfig entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
