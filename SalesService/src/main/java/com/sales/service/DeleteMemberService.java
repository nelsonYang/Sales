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
import com.sales.entity.Member;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteMember,
        importantParameters = {"session", "encryptType","memberId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Member操作",
        validateParameters = {
    	@Field(fieldName ="memberId", fieldType = FieldTypeEnum.INT, description ="会员id"),
	@Field(fieldName ="realName", fieldType = FieldTypeEnum.CHAR36, description ="真实姓名"),
	@Field(fieldName ="weixinNo", fieldType = FieldTypeEnum.CHAR36, description =""),
	@Field(fieldName ="qqNO", fieldType = FieldTypeEnum.CHAR36, description ="qq号"),
	@Field(fieldName ="email", fieldType = FieldTypeEnum.CHAR36, description ="邮箱"),
	@Field(fieldName ="mobile", fieldType = FieldTypeEnum.CHAR36, description ="手机"),
	@Field(fieldName ="birthday", fieldType = FieldTypeEnum.DATE, description ="生日"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="memberNO", fieldType = FieldTypeEnum.CHAR36, description ="会员号"),
	@Field(fieldName ="address", fieldType = FieldTypeEnum.CHAR128, description ="会员地址"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="integration", fieldType = FieldTypeEnum.BIG_INT, description ="积分"),
	@Field(fieldName ="source", fieldType = FieldTypeEnum.TYINT, description ="来源1-注册2-微信"),
	@Field(fieldName ="password", fieldType = FieldTypeEnum.CHAR36, description ="密码"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteMemberService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteMemberService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String memberId = parameters.get("memberId");
	primaryKey.putKeyField("memberId",String.valueOf(memberId));

        EntityDao<Member> entityDAO = applicationContext.getEntityDAO(EntityNames.member);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
