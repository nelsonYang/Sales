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
import com.sales.entity.PersonCard;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deletePersonCard,
        importantParameters = {"session", "encryptType","personCardId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除PersonCard操作",
        validateParameters = {
    	@Field(fieldName ="personCardId", fieldType = FieldTypeEnum.BIG_INT, description ="名片id"),
	@Field(fieldName ="personName", fieldType = FieldTypeEnum.CHAR11, description ="名片名字"),
	@Field(fieldName ="personMobile", fieldType = FieldTypeEnum.CHAR11, description ="名片号码"),
	@Field(fieldName ="personEmail", fieldType = FieldTypeEnum.CHAR36, description ="名片的邮箱"),
	@Field(fieldName ="personIconURL", fieldType = FieldTypeEnum.CHAR64, description ="名片的图片"),
	@Field(fieldName ="companyName", fieldType = FieldTypeEnum.CHAR36, description ="公司名字"),
	@Field(fieldName ="companyAddress", fieldType = FieldTypeEnum.CHAR64, description ="公司地址"),
	@Field(fieldName ="readCount", fieldType = FieldTypeEnum.INT, description ="被浏览次数"),
	@Field(fieldName ="clickCount", fieldType = FieldTypeEnum.INT, description ="被点击的次数"),
	@Field(fieldName ="weiboNo", fieldType = FieldTypeEnum.CHAR36, description ="微薄号"),
	@Field(fieldName ="qqNO", fieldType = FieldTypeEnum.CHAR11, description ="qq号"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="messageTitle", fieldType = FieldTypeEnum.CHAR36, description ="消息标题"),
	@Field(fieldName ="messageImageURL", fieldType = FieldTypeEnum.CHAR64, description ="消息图片"),
	@Field(fieldName ="personCardDesc", fieldType = FieldTypeEnum.CHAR64, description ="微名片描述"),
	@Field(fieldName ="personCardAddress", fieldType = FieldTypeEnum.CHAR64, description ="地址"),
	@Field(fieldName ="personLinkSite", fieldType = FieldTypeEnum.CHAR64, description ="链接地址"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeletePersonCardService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeletePersonCardService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String personCardId = parameters.get("personCardId");
	primaryKey.putKeyField("personCardId",String.valueOf(personCardId));

        EntityDao<PersonCard> entityDAO = applicationContext.getEntityDAO(EntityNames.personCard);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
