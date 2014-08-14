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
import com.sales.entity.WeiSiteArticle;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireWeiSiteArticleById,
        importantParameters = {"session", "encryptType", "weiSiteArticleId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weiSiteArticleId","weiSiteImageURL", "weiSitelinkWebSite","weiSiteTitle", "weiSiteContent", "bindId"},
        description = "查询文章",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "weiSiteMenuId", fieldType = FieldTypeEnum.BIG_INT, description = "菜单id"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeiSiteArticleByIdService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String weiSiteMenuId = parameters.get("weiSiteMenuId");
        EntityDao<WeiSiteArticle> weiSiteArticleDAO = applicationContext.getEntityDAO(EntityNames.weiSiteArticle);
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("weiSiteMenuId", weiSiteMenuId);
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        WeiSiteArticle weiSiteArticle = weiSiteArticleDAO.inqurieByKey(primaryKey);
        if (weiSiteArticle != null) {
            applicationContext.setEntityData(weiSiteArticle);
            applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.ENTITY_NOT_FOUND);
        }


    }
}
