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
import com.sales.entity.WeixinToken;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeixinTokenById,
        importantParameters = {"session", "encryptType", "tokenId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"tokenId","appId","appSecret","accessToken","expireTime","companyId","weixinNO","weixinEmail","weixinDevAPI","validateToken"},
        description = "查询WeixinToken详细内容",
        validateParameters = {
          	@Field(fieldName ="tokenId", fieldType = FieldTypeEnum.INT, description ="token的id"),
	@Field(fieldName ="appId", fieldType = FieldTypeEnum.CHAR24, description ="微信appid"),
	@Field(fieldName ="appSecret", fieldType = FieldTypeEnum.CHAR64, description ="微信secret"),
	@Field(fieldName ="accessToken", fieldType = FieldTypeEnum.CHAR64, description ="微信token"),
	@Field(fieldName ="expireTime", fieldType = FieldTypeEnum.DATETIME, description ="token的过期时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="weixinNO", fieldType = FieldTypeEnum.CHAR36, description ="微信号"),
	@Field(fieldName ="weixinEmail", fieldType = FieldTypeEnum.CHAR36, description ="微信邮箱"),
	@Field(fieldName ="weixinDevAPI", fieldType = FieldTypeEnum.CHAR64, description ="开发者api"),
	@Field(fieldName ="validateToken", fieldType = FieldTypeEnum.CHAR64, description ="微信的接口token"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeixinTokenByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireWeixinTokenByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String tokenId = parameters.get("tokenId");
	primaryKey.putKeyField("tokenId",String.valueOf(tokenId));

        EntityDao<WeixinToken> entityDAO = applicationContext.getEntityDAO(EntityNames.weixinToken);
          WeixinToken entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
