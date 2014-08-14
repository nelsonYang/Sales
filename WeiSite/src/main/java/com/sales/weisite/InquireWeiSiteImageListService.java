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
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireWeiSiteImageList,
        importantParameters = {"session", "encryptType", "pageNo", "pageCount"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weiSiteImageId", "weiSiteImageName", "weiSiteImageURL", "bindId", "weiSiteImageDesc"},
        description = "查询微站图片列表",
        validateParameters = {
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "页数"),
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "每页显示的数量"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeiSiteImageListService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        int pageNo = Integer.parseInt(parameters.get("pageNo"));
        int pageCount = Integer.parseInt(parameters.get("pageCount"));
        List<Condition> conditionList = new ArrayList<Condition>(1);
        Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyCondition);
        EntityDao<WeiSiteImage> weiSiteImageDAO = applicationContext.getEntityDAO(EntityNames.weiSiteImage);
        PageModel pageModel = weiSiteImageDAO.inquirePageByCondition(conditionList, pageNo, pageCount);
        List<WeiSiteImage> weiSiteImageList = pageModel.getDataList();
        applicationContext.setTotalPage(pageModel.getTotalPage());
        applicationContext.setCount(pageModel.getTotalCount());
        applicationContext.setEntityList(weiSiteImageList);
        applicationContext.success();
    }
}
