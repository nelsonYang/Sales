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
import com.sales.entity.WeiSiteArticle;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.updateWeiSiteArticle,
        importantParameters = {"session", "encryptType", "weiSiteArticleId"},
        minorParameters = {"weiSiteImageURL", "weiSitelinkWebSite", "weiSiteTitle", "weiSiteContent", "bindId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改微网站文章",
        validateParameters = {
    @Field(fieldName = "weiSiteArticleId", fieldType = FieldTypeEnum.BIG_INT, description = "文章Fid"),
    @Field(fieldName = "weiSiteTitle", fieldType = FieldTypeEnum.CHAR64, description = "文章的标题"),
    @Field(fieldName = "weiSiteContent", fieldType = FieldTypeEnum.CHAR512, description = "文章的内容"),
    @Field(fieldName = "bindId", fieldType = FieldTypeEnum.BIG_INT, description = "文章的所属的id"),
    @Field(fieldName = "weiSiteImageURL", fieldType = FieldTypeEnum.CHAR128, description = "文章的图片地址"),
    @Field(fieldName = "weiSitelinkWebSite", fieldType = FieldTypeEnum.CHAR128, description = "文章的外链"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateWeiSiteArticalService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        EntityDao<WeiSiteArticle> weiSiteArticleDAO = applicationContext.getEntityDAO(EntityNames.weiSiteArticle);
        parameters.put("companyId", String.valueOf(companyId));
        WeiSiteArticle weiSiteArticle = weiSiteArticleDAO.update(parameters);
        if (weiSiteArticle != null) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }
    }
}
