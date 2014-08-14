package com.sales.setting.sitetemplate;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.framework.utils.ConverterUtils;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteTemplateCfg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireWeiSiteTemplateCfgService,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.MAP_DATA_LIST_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"type", "weiSiteTemplateName", "weiSiteTemplateURL"},
        description = "查询服务列表",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireSiteTemplateListService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        EntityDao<WeiSiteTemplateCfg> weiSiteTemplateCfgDAO = applicationContext.getEntityDAO(EntityNames.tariffPackagesCfg);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        List<WeiSiteTemplateCfg> weiSiteTemplateList = weiSiteTemplateCfgDAO.inquireByCondition(conditionList);
        List<Map<String, String>> resultMapList = ConverterUtils.toMapList(weiSiteTemplateList);
        Map<String, List<Map<String, String>>> listMap = new HashMap<String, List<Map<String, String>>>(2, 1);
        listMap.put("siteTemplateList", resultMapList);
        applicationContext.setListMapData(listMap);
        applicationContext.success();

    }
}
