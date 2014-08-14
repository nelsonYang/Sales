package com.sales.service;

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
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.entity.EntityNames;
import com.sales.entity.Menu;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertMenu,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"menuName","menuParent","menuType","menuURL","menuKey","companyId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增Menu",
        validateParameters = {
       	@Field(fieldName ="menuId", fieldType = FieldTypeEnum.BIG_INT, description ="菜单id"),
	@Field(fieldName ="menuName", fieldType = FieldTypeEnum.CHAR24, description ="菜单名称"),
	@Field(fieldName ="menuParent", fieldType = FieldTypeEnum.BIG_INT, description ="父级菜单"),
	@Field(fieldName ="menuType", fieldType = FieldTypeEnum.TYINT, description ="菜单类型"),
	@Field(fieldName ="menuURL", fieldType = FieldTypeEnum.CHAR64, description ="菜单的URL"),
	@Field(fieldName ="menuKey", fieldType = FieldTypeEnum.CHAR64, description ="菜单唯一标识"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertMenuService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertMenuService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<Menu> entityDAO = applicationContext.getEntityDAO(EntityNames.menu);
        Menu entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
