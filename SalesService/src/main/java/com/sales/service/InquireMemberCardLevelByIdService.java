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
import com.sales.entity.MemberCardLevel;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireMemberCardLevelById,
        importantParameters = {"session", "encryptType", "memberCardLevelId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"memberCardLevelId","memberCardLevelMin","memberCardLevelMax","memberCardLevel","memberCardBackgroupURL","companyId"},
        description = "查询MemberCardLevel详细内容",
        validateParameters = {
          	@Field(fieldName ="memberCardLevelId", fieldType = FieldTypeEnum.BIG_INT, description ="会员卡等级id"),
	@Field(fieldName ="memberCardLevelMin", fieldType = FieldTypeEnum.BIG_INT, description ="会员卡等级最小值"),
	@Field(fieldName ="memberCardLevelMax", fieldType = FieldTypeEnum.BIG_INT, description ="会员卡的最大值"),
	@Field(fieldName ="memberCardLevel", fieldType = FieldTypeEnum.BIG_INT, description =""),
	@Field(fieldName ="memberCardBackgroupURL", fieldType = FieldTypeEnum.CHAR128, description ="会员卡的背景图"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMemberCardLevelByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireMemberCardLevelByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String memberCardLevelId = parameters.get("memberCardLevelId");
	primaryKey.putKeyField("memberCardLevelId",String.valueOf(memberCardLevelId));

        EntityDao<MemberCardLevel> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCardLevel);
          MemberCardLevel entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
