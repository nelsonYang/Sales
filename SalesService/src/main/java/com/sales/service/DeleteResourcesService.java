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
import com.sales.entity.Resources;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteResources,
        importantParameters = {"session", "encryptType","resourcesId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Resources操作",
        validateParameters = {
    	@Field(fieldName ="resourcesId", fieldType = FieldTypeEnum.BIG_INT, description ="资源id"),
	@Field(fieldName ="resourcesType", fieldType = FieldTypeEnum.TYINT, description ="资源类型1-文字2-单图文 3-多图文4-自定义url5-系统url6微官网url"),
	@Field(fieldName ="resourcesName", fieldType = FieldTypeEnum.CHAR36, description ="资源名称"),
	@Field(fieldName ="resourcesURL", fieldType = FieldTypeEnum.CHAR36, description ="资源URL"),
	@Field(fieldName ="resourceContent", fieldType = FieldTypeEnum.CHAR128, description ="资源内容"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteResourcesService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteResourcesService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String resourcesId = parameters.get("resourcesId");
	primaryKey.putKeyField("resourcesId",String.valueOf(resourcesId));

        EntityDao<Resources> entityDAO = applicationContext.getEntityDAO(EntityNames.resources);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
