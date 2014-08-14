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
import com.sales.entity.WeixinToken;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteWeixinToken,
        importantParameters = {"session", "encryptType","tokenId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除WeixinToken操作",
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

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteWeixinTokenService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteWeixinTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String tokenId = parameters.get("tokenId");
	primaryKey.putKeyField("tokenId",String.valueOf(tokenId));

        EntityDao<WeixinToken> entityDAO = applicationContext.getEntityDAO(EntityNames.weixinToken);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
