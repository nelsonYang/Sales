package com.sales.weisite;

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
import com.sales.entity.WeiSiteMenu;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.updateWeiSiteMenu,
        importantParameters = {"session", "encryptType", "weiSiteMenuId"},
        minorParameters = {"weiSiteMenuName", "weiSiteMenuParentId", "weiSiteMenuImageURL", "weiSiteMenuLinkWebSite","weiSiteMenuDesc"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "创建微网站菜单",
        validateParameters = {
    @Field(fieldName = "weiSiteMenuId", fieldType = FieldTypeEnum.BIG_INT, description = "菜单id"),
    @Field(fieldName = "weiSiteMenuName", fieldType = FieldTypeEnum.CHAR24, description = "菜单名称"),
    @Field(fieldName = "weiSiteMenuParentId", fieldType = FieldTypeEnum.BIG_INT, description = "父id"),
    @Field(fieldName = "weiSiteMenuDesc", fieldType = FieldTypeEnum.CHAR128, description = "menu的描述"),
    @Field(fieldName = "weiSiteMenuImageURL", fieldType = FieldTypeEnum.CHAR128, description = "meun的图片地址"),
    @Field(fieldName = "weiSiteMenuLinkWebSite", fieldType = FieldTypeEnum.CHAR128, description = "meun的外链"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateWeiSiteMenuService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        EntityDao<WeiSiteMenu> weiSiteMenuDAO = applicationContext.getEntityDAO(EntityNames.weiSiteMenu);
        parameters.put("companyId", String.valueOf(companyId));
        WeiSiteMenu menu = weiSiteMenuDAO.update(parameters);
        if (menu != null) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }
    }
}
