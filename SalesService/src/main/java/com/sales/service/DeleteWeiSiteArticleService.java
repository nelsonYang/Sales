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
import com.sales.entity.WeiSiteArticle;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteWeiSiteArticle,
        importantParameters = {"session", "encryptType","weiSiteArticleId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除WeiSiteArticle操作",
        validateParameters = {
    	@Field(fieldName ="weiSiteArticleId", fieldType = FieldTypeEnum.BIG_INT, description ="微站文章id"),
	@Field(fieldName ="weiSiteTitle", fieldType = FieldTypeEnum.CHAR36, description ="标题"),
	@Field(fieldName ="weiSiteContent", fieldType = FieldTypeEnum.CHAR128, description ="微站的内容"),
	@Field(fieldName ="weiSiteImageURL", fieldType = FieldTypeEnum.CHAR64, description ="图片的url"),
	@Field(fieldName ="weiSitelinkWebSite", fieldType = FieldTypeEnum.CHAR64, description ="微站外联地址"),
	@Field(fieldName ="isShow", fieldType = FieldTypeEnum.TYINT, description ="是否显示"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="创建时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="bindId", fieldType = FieldTypeEnum.BIG_INT, description ="归属的id"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="文章的类型"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteWeiSiteArticleService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteWeiSiteArticleService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String weiSiteArticleId = parameters.get("weiSiteArticleId");
	primaryKey.putKeyField("weiSiteArticleId",String.valueOf(weiSiteArticleId));

        EntityDao<WeiSiteArticle> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteArticle);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
