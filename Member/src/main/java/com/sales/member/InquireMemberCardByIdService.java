package com.sales.member;

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
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.MemberCard;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireMemberCardById,
        importantParameters = {"session", "encryptType", "memberCardId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"memberCardId","memberLogoURL","memberCardURL","memberCardName","memberCardDesc","effectiveStartTime","effectiveEndTime","readCount","clickCount","companyId","balance","consumeMoney","memberCardConfigId","status","keyword","memberCardPrivileges"},
        description = "查询MemberCard详细内容",
        validateParameters = {
    @Field(fieldName = "memberCardId", fieldType = FieldTypeEnum.BIG_INT, description = "会员卡id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMemberCardByIdService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireMemberCardByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        PrimaryKey primaryKey = new PrimaryKey();
        String memberCardId = parameters.get("memberCardId");
        primaryKey.putKeyField("memberCardId", String.valueOf(memberCardId));
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        EntityDao<MemberCard> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCard);
        MemberCard entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }


    }
}
