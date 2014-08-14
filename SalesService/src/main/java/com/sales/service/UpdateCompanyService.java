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
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.entity.EntityNames;
import com.sales.entity.Company;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateCompany,
        importantParameters = {"session", "encryptType","companyId"},
        minorParameters = {"companyName","password","linkMobile","qqNO","email","province","city","region","serviceStartTime","serviceEndTime","logoURL","companySite","companyTel","createTime","companyDimisionCode","weixinNO","street","companyToken"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改Company",
        validateParameters = {
       	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="companyName", fieldType = FieldTypeEnum.CHAR24, description ="公司名城"),
	@Field(fieldName ="password", fieldType = FieldTypeEnum.CHAR36, description ="密码"),
	@Field(fieldName ="linkMobile", fieldType = FieldTypeEnum.CHAR11, description ="联系人号码"),
	@Field(fieldName ="qqNO", fieldType = FieldTypeEnum.CHAR11, description ="QQ号"),
	@Field(fieldName ="email", fieldType = FieldTypeEnum.CHAR24, description ="邮箱"),
	@Field(fieldName ="province", fieldType = FieldTypeEnum.INT, description ="省"),
	@Field(fieldName ="city", fieldType = FieldTypeEnum.INT, description ="市"),
	@Field(fieldName ="region", fieldType = FieldTypeEnum.INT, description ="区"),
	@Field(fieldName ="serviceStartTime", fieldType = FieldTypeEnum.DATETIME, description ="服务开始时间"),
	@Field(fieldName ="serviceEndTime", fieldType = FieldTypeEnum.DATETIME, description ="服务结束时间"),
	@Field(fieldName ="logoURL", fieldType = FieldTypeEnum.CHAR64, description ="公司logo"),
	@Field(fieldName ="companySite", fieldType = FieldTypeEnum.CHAR64, description ="公司网站"),
	@Field(fieldName ="companyTel", fieldType = FieldTypeEnum.CHAR24, description ="公司电话"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="companyDimisionCode", fieldType = FieldTypeEnum.CHAR64, description ="公司二维码"),
	@Field(fieldName ="weixinNO", fieldType = FieldTypeEnum.CHAR36, description ="公司微信好"),
	@Field(fieldName ="street", fieldType = FieldTypeEnum.CHAR36, description ="街道"),
	@Field(fieldName ="companyToken", fieldType = FieldTypeEnum.CHAR64, description ="公司访问的token"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateCompanyService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateCompanyService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<Company> entityDAO = applicationContext.getEntityDAO(EntityNames.company);
        Company entity = entityDAO.update(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
