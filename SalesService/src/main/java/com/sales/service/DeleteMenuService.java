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
import com.sales.entity.Menu;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteMenu,
        importantParameters = {"session", "encryptType","menuId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Menu操作",
        validateParameters = {
    	@Field(fieldName ="menuId", fieldType = FieldTypeEnum.BIG_INT, description ="菜单id"),
	@Field(fieldName ="menuName", fieldType = FieldTypeEnum.CHAR24, description ="菜单名称"),
	@Field(fieldName ="menuParent", fieldType = FieldTypeEnum.BIG_INT, description ="父级菜单"),
	@Field(fieldName ="menuType", fieldType = FieldTypeEnum.TYINT, description ="菜单类型"),
	@Field(fieldName ="menuURL", fieldType = FieldTypeEnum.CHAR64, description ="菜单的URL"),
	@Field(fieldName ="menuKey", fieldType = FieldTypeEnum.CHAR64, description ="菜单唯一标识"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteMenuService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteMenuService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String menuId = parameters.get("menuId");
	primaryKey.putKeyField("menuId",String.valueOf(menuId));

        EntityDao<Menu> entityDAO = applicationContext.getEntityDAO(EntityNames.menu);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
