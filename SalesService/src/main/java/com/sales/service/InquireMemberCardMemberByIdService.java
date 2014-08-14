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
import com.sales.entity.MemberCardMember;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireMemberCardMemberById,
        importantParameters = {"session", "encryptType", "memberCardMemberId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"memberCardMemberId","companyId","memberCardId","memberId","memberNO","createTime"},
        description = "查询MemberCardMember详细内容",
        validateParameters = {
          	@Field(fieldName ="memberCardMemberId", fieldType = FieldTypeEnum.BIG_INT, description ="会员卡记录id"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="memberCardId", fieldType = FieldTypeEnum.BIG_INT, description ="会员卡id"),
	@Field(fieldName ="memberId", fieldType = FieldTypeEnum.BIG_INT, description ="会员id"),
	@Field(fieldName ="memberNO", fieldType = FieldTypeEnum.CHAR36, description ="会员号"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="会员领取的时间"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMemberCardMemberByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireMemberCardMemberByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String memberCardMemberId = parameters.get("memberCardMemberId");
	primaryKey.putKeyField("memberCardMemberId",String.valueOf(memberCardMemberId));

        EntityDao<MemberCardMember> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCardMember);
          MemberCardMember entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
