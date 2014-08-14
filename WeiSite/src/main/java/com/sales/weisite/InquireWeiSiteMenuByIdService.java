package com.sales.weisite;

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
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteMenu;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireWeiSiteMenuById,
        importantParameters = {"session", "encryptType", "weiSiteMenuId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weiSiteMenuId", "weiSiteMenuName", "weiSiteMenuParentId", "weiSiteMenuImageURL", "weiSiteMenuLinkWebSite","weiSiteMenuDesc"},
        description = "查询菜单",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "weiSiteMenuId", fieldType = FieldTypeEnum.BIG_INT, description = "文章id"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeiSiteMenuByIdService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String weiSiteArticleId = parameters.get("weiSiteMenuId");
        EntityDao<WeiSiteMenu> weiSiteMenuDAO = applicationContext.getEntityDAO(EntityNames.weiSiteMenu);
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("weiSiteMenuId", weiSiteArticleId);
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        WeiSiteMenu menu = weiSiteMenuDAO.inqurieByKey(primaryKey);
        if (menu != null) {
            applicationContext.setEntityData(menu);
            applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.ENTITY_NOT_FOUND);
        }


    }
}
