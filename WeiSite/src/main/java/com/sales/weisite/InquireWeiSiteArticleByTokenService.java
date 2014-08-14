package com.sales.weisite;

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
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
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
        act = SalesActionName.inquireWeiSiteArticleByToken,
        importantParameters = {"token", "weixinId", "weiSiteArticleId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"weiSiteArticleId","weiSiteImageURL", "weiSitelinkWebSite","weiSiteTitle", "weiSiteContent", "bindId"},
        description = "查询文章",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问者的token"),
    @Field(fieldName = "weiSiteArticleId", fieldType = FieldTypeEnum.BIG_INT, description = "菜单id"),
    @Field(fieldName = "weixinId", fieldType = FieldTypeEnum.CHAR1024, description = "访问者微信号")
})
public class InquireWeiSiteArticleByTokenService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String weiSiteArticleId = parameters.get("weiSiteArticleId");
        String weixinId = parameters.get("weixinId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {

            EntityDao<WeiSiteArticle> weiSiteArticleDAO = applicationContext.getEntityDAO(EntityNames.weiSiteArticle);
            PrimaryKey primaryKey = new PrimaryKey();
            primaryKey.putKeyField("weiSiteArticleId", weiSiteArticleId);
            primaryKey.putKeyField("companyId", tokens[1]);
            WeiSiteArticle weiSiteArticle = weiSiteArticleDAO.inqurieByKey(primaryKey);
            if (weiSiteArticle != null) {
                applicationContext.setEntityData(weiSiteArticle);
                applicationContext.success();
            } else {
                applicationContext.fail(SalesErrorCode.ENTITY_NOT_FOUND);
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
