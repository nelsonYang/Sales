package com.sales.weisite;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PageModel;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireWeiSiteArticleListByToken,
        importantParameters = {"token", "weixinId", "pageNo", "pageCount"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        requireLogin = LoginEnum.NOT_REQUIRE,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weiSiteArticleId", "weiSiteImageURL", "weiSitelinkWebSite", "weiSiteTitle", "weiSiteContent", "bindId"},
        description = "查询文章列表",
        validateParameters = {
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "页数"),
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "每页显示的数量"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问的token"),
    @Field(fieldName = "weixinId", fieldType = FieldTypeEnum.CHAR1024, description = "访问者的微信号")
})
public class InquireWeiSiteArticleListByTokenService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String weixinId = parameters.get("weixinId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            int pageNo = Integer.parseInt(parameters.get("pageNo"));
            int pageCount = Integer.parseInt(parameters.get("pageCount"));
            List<Condition> conditionList = new ArrayList<Condition>(1);
            Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, tokens[1]);
            conditionList.add(companyCondition);
            EntityDao<WeiSiteArticle> weiSiteArticleDAO = applicationContext.getEntityDAO(EntityNames.weiSiteArticle);
            PageModel pageModel = weiSiteArticleDAO.inquirePageByCondition(conditionList, pageNo, pageCount);
            List<WeiSiteArticle> weiSiteArticleList = pageModel.getDataList();
            applicationContext.setTotalPage(pageModel.getTotalPage());
            applicationContext.setCount(pageModel.getTotalCount());
            applicationContext.setEntityList(weiSiteArticleList);
            applicationContext.success();
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }
    }
}
