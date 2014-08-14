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
import com.sales.entity.ResourcesImage;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireResourcesImageById,
        importantParameters = {"session", "encryptType", "resourcesImageId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"resourcesImageId","resoucesId","resoucesImageURL","createTime","imageType","companyId","resourcesContent","linkSite","resourcesTitle"},
        description = "查询ResourcesImage详细内容",
        validateParameters = {
          	@Field(fieldName ="resourcesImageId", fieldType = FieldTypeEnum.BIG_INT, description ="资源图片id"),
	@Field(fieldName ="resoucesId", fieldType = FieldTypeEnum.BIG_INT, description ="资源id"),
	@Field(fieldName ="resoucesImageURL", fieldType = FieldTypeEnum.CHAR64, description ="资源图片的url"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="imageType", fieldType = FieldTypeEnum.TYINT, description ="图片类型"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="resourcesContent", fieldType = FieldTypeEnum.CHAR128, description ="标题的描述内容"),
	@Field(fieldName ="linkSite", fieldType = FieldTypeEnum.CHAR64, description ="链接地址"),
	@Field(fieldName ="resourcesTitle", fieldType = FieldTypeEnum.CHAR36, description ="资源的标题"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireResourcesImageByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireResourcesImageByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String resourcesImageId = parameters.get("resourcesImageId");
	primaryKey.putKeyField("resourcesImageId",String.valueOf(resourcesImageId));

        EntityDao<ResourcesImage> entityDAO = applicationContext.getEntityDAO(EntityNames.resourcesImage);
          ResourcesImage entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
