package com.sales.user;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.framework.utils.DateTimeUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.entity.Company;
import com.sales.entity.EntityNames;
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.register,
        importantParameters = {"companyName", "password", "province", "city", "region", "street", "linkMobile", "email"},
        minorParameters = {"qqNO"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        description = "注册",
        validateParameters = {
    @Field(fieldName = "companyName", fieldType = FieldTypeEnum.CHAR24, description = "用户名"),
    @Field(fieldName = "password", fieldType = FieldTypeEnum.CHAR64, description = "密码"),
    @Field(fieldName = "province", fieldType = FieldTypeEnum.INT, description = "省"),
    @Field(fieldName = "city", fieldType = FieldTypeEnum.INT, description = "市"),
    @Field(fieldName = "region", fieldType = FieldTypeEnum.INT, description = "区"),
    @Field(fieldName = "street", fieldType = FieldTypeEnum.CHAR36, description = "街道"),
    @Field(fieldName = "linkMobile", fieldType = FieldTypeEnum.CHAR24, description = "联系电话"),
    @Field(fieldName = "email", fieldType = FieldTypeEnum.CHAR24, description = "邮箱"),
    @Field(fieldName = "qqNO", fieldType = FieldTypeEnum.CHAR24, description = "qq号"),
    @Field(fieldName = "sign", fieldType = FieldTypeEnum.CHAR1024, description = "签名验证"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class RegisterService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(RegisterService.class);

    public void execute() {

        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        EntityDao<Company> companyDAO = applicationContext.getEntityDAO(EntityNames.company);
        String companyName = parameters.get("companyName");
        List<Condition> conditionList = new ArrayList<Condition>(1);
        Condition userNameCondition = new Condition("companyName", ConditionTypeEnum.EQUAL, companyName);
        conditionList.add(userNameCondition);
        List<Company> companyList = companyDAO.inquireByCondition(conditionList);
        if (companyList == null || companyList.isEmpty()) {
            try {
                //获取公司id
                String companyId = companyDAO.seqValue("seq_company");
                parameters.put("companyId", companyId);
                parameters.put("createTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
                String token = "sales-" + companyId;
                String companyToken = Base64Utils.encode(token.getBytes("utf-8"));
                parameters.put("companyToken", companyToken);
                logger.debug("parameters={}", parameters);
                Company company = companyDAO.insert(parameters);
                if (company != null) {
                    applicationContext.success();
                } else {
                    applicationContext.fail();
                }
            } catch (UnsupportedEncodingException ex) {
                logger.error("注册失败", ex);
                applicationContext.fail();
            }
        } else {
            applicationContext.fail(SalesErrorCode.USER_EXISTS);
        }

    }
}
