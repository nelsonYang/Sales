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
import com.sales.entity.WeixinToken;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertWeixinToken,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"appId","appSecret","accessToken","expireTime","companyId","weixinNO","weixinEmail","weixinDevAPI","validateToken"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增WeixinToken",
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

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertWeixinTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertWeixinTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<WeixinToken> entityDAO = applicationContext.getEntityDAO(EntityNames.weixinToken);
        WeixinToken entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
