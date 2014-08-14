package com.sales.member;

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
import com.sales.entity.MemberCard;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireMemberCardByIdAndToken,
        importantParameters = {"memberCardId", "token"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"memberCardId","memberLogoURL","memberCardURL","memberCardName","memberCardDesc","effectiveStartTime","effectiveEndTime","readCount","clickCount","companyId","balance","consumeMoney","memberCardConfigId","status","keyword","memberCardPrivileges"},
        description = "查询MemberCard详细内容",
        validateParameters = {
    @Field(fieldName = "memberCardId", fieldType = FieldTypeEnum.BIG_INT, description = "会员卡id"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问token")
  })
public class InquireMemberCardByIdAndTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireMemberCardByIdAndTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();

        logger.debug("parameters={}", parameters);
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            PrimaryKey primaryKey = new PrimaryKey();
            String memberCardId = parameters.get("memberCardId");
            primaryKey.putKeyField("memberCardId", String.valueOf(memberCardId));
            primaryKey.putKeyField("companyId", tokens[1]);
            EntityDao<MemberCard> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCard);
            MemberCard entity = entityDAO.inqurieByKey(primaryKey);
            if (entity != null) {
                applicationContext.setEntityData(entity);
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
