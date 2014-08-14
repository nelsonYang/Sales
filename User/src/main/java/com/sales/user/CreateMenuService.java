package com.sales.user;

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
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.Menu;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.createMenu,
        importantParameters = {"session", "encryptType", "menuName", "menuParent", "menuType", "menuURL", "menuKey"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "创建菜单",
        validateParameters = {
    @Field(fieldName = "menuName", fieldType = FieldTypeEnum.CHAR24, description = "菜单名称"),
    @Field(fieldName = "menuParent", fieldType = FieldTypeEnum.BIG_INT, description = "父id"),
    @Field(fieldName = "menuType", fieldType = FieldTypeEnum.TYINT, description = "menu的类型"),
    @Field(fieldName = "menuURL", fieldType = FieldTypeEnum.CHAR64, description = "meun的外链"),
    @Field(fieldName = "menuKey", fieldType = FieldTypeEnum.CHAR36, description = "meun唯一标识"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class CreateMenuService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        EntityDao<Menu> menuDAO = applicationContext.getEntityDAO(EntityNames.menu);
        parameters.put("companyId", String.valueOf(companyId));
        Menu menu = menuDAO.insert(parameters);
        if (menu != null) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }
    }
}
