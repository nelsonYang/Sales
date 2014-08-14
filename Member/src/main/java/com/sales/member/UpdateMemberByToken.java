package com.sales.member;

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
import com.framework.utils.Base64Utils;
import com.sales.config.ActionNames;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.Member;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateMemberByToken,
        importantParameters = {"token", "encryptType", "memberId"},
        minorParameters = {"realName", "weixinNo", "qqNO", "email", "mobile", "birthday", "memberNO", "address", "integration", "password"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        description = "修改Member",
        validateParameters = {
    @Field(fieldName = "memberId", fieldType = FieldTypeEnum.INT, description = "会员id"),
    @Field(fieldName = "realName", fieldType = FieldTypeEnum.CHAR36, description = "真实姓名"),
    @Field(fieldName = "weixinNo", fieldType = FieldTypeEnum.CHAR36, description = ""),
    @Field(fieldName = "qqNO", fieldType = FieldTypeEnum.CHAR36, description = "qq号"),
    @Field(fieldName = "email", fieldType = FieldTypeEnum.CHAR36, description = "邮箱"),
    @Field(fieldName = "mobile", fieldType = FieldTypeEnum.CHAR36, description = "手机"),
    @Field(fieldName = "password", fieldType = FieldTypeEnum.CHAR36, description = "密码"),
    @Field(fieldName = "birthday", fieldType = FieldTypeEnum.DATE, description = "生日"),
    @Field(fieldName = "memberNO", fieldType = FieldTypeEnum.CHAR36, description = "会员号"),
    @Field(fieldName = "address", fieldType = FieldTypeEnum.CHAR128, description = "会员地址"),
    @Field(fieldName = "integration", fieldType = FieldTypeEnum.BIG_INT, description = "积分"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "token"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateMemberByToken implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateMemberByToken.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            parameters.put("companyId", tokens[1]);
            logger.debug("parameters={}", parameters);
            EntityDao<Member> entityDAO = applicationContext.getEntityDAO(EntityNames.member);
            Member entity = entityDAO.update(parameters);
            if (entity != null) {
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
