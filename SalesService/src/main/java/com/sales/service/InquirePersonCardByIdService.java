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
import com.sales.entity.PersonCard;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquirePersonCardById,
        importantParameters = {"session", "encryptType", "personCardId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"personCardId","personName","personMobile","personEmail","personIconURL","companyName","companyAddress","readCount","clickCount","weiboNo","qqNO","companyId","createTime","messageTitle","messageImageURL","personCardDesc","personCardAddress","personLinkSite"},
        description = "查询PersonCard详细内容",
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

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePersonCardByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquirePersonCardByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String personCardId = parameters.get("personCardId");
	primaryKey.putKeyField("personCardId",String.valueOf(personCardId));

        EntityDao<PersonCard> entityDAO = applicationContext.getEntityDAO(EntityNames.personCard);
          PersonCard entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
