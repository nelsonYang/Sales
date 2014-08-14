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
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.MemberCardLevel;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateMemberCardLevel,
        importantParameters = {"session", "encryptType", "memberCardLevelId"},
        minorParameters = {"memberCardLevelMin", "memberCardLevelMax", "memberCardLevel", "memberCardBackgroupURL"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改MemberCardLevel",
        validateParameters = {
    @Field(fieldName = "memberCardLevelId", fieldType = FieldTypeEnum.BIG_INT, description = "会员卡等级id"),
    @Field(fieldName = "memberCardLevelMin", fieldType = FieldTypeEnum.BIG_INT, description = "会员卡等级最小值"),
    @Field(fieldName = "memberCardLevelMax", fieldType = FieldTypeEnum.BIG_INT, description = "会员卡的最大值"),
    @Field(fieldName = "memberCardLevel", fieldType = FieldTypeEnum.BIG_INT, description = ""),
    @Field(fieldName = "memberCardBackgroupURL", fieldType = FieldTypeEnum.CHAR128, description = "会员卡的背景图"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateMemberCardLevelService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateMemberCardLevelService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        EntityDao<MemberCardLevel> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCardLevel);
        MemberCardLevel entity = entityDAO.update(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
