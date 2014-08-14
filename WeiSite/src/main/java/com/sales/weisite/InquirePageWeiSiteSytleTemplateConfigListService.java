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
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteStyleTemplate;
import com.sales.entity.WeiSiteSytleTemplateConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquirePageWeiSiteSytleTemplateConfigList,
        importantParameters = {"session", "pageCount", "pageNo", "encryptType"},
        minorParameters = {"type"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"weiSiteSytleTemplateId", "weiSiteSytleTemplateName", "weiSiteSytleTemplateURL", "weiSiteSytleTemplateImageURL", "type", "createTime", "checked"},
        description = "分页查询WeiSiteSytleTemplateConfig",
        validateParameters = {
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "模板风格的类型"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePageWeiSiteSytleTemplateConfigListService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquirePageWeiSiteSytleTemplateConfigListService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        logger.debug("parameters={}", parameters);
        String pageCount = parameters.get("pageCount");
        String pageNo = parameters.get("pageNo");
        String type = parameters.get("type");
        EntityDao<WeiSiteSytleTemplateConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteSytleTemplateConfig);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        if (type != null && !type.isEmpty()) {
            Condition typeCondition = new Condition("type", ConditionTypeEnum.EQUAL, type);
            conditionList.add(typeCondition);
        }
        PageModel enityPageMode = entityDAO.inquirePageByCondition(conditionList, Integer.parseInt(pageNo), Integer.parseInt(pageCount));
        List<WeiSiteSytleTemplateConfig> entityList = enityPageMode.getDataList();
        if (entityList != null) {
            if (!entityList.isEmpty()) {
                conditionList = new ArrayList<Condition>(1);
                Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyCondition);
                EntityDao<WeiSiteStyleTemplate> weiSiteTemplateDAO = applicationContext.getEntityDAO(EntityNames.weiSiteStyleTemplate);
                List<WeiSiteStyleTemplate> weiSiteStyleTemplateList = weiSiteTemplateDAO.inquireByCondition(conditionList);
                List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>(entityList.size());
                Map<String, String> weiSiteStyleTemplateMap;
                for (WeiSiteSytleTemplateConfig weiSiteSyteTemplateConfig : entityList) {
                    weiSiteStyleTemplateMap = weiSiteSyteTemplateConfig.toMap();
                    weiSiteStyleTemplateMap.put("checked", "0");
                    if (!weiSiteStyleTemplateList.isEmpty()) {
                        for (WeiSiteStyleTemplate weiSiteStyleTemplate : weiSiteStyleTemplateList) {
                            if (weiSiteStyleTemplate.getWeiSiteStyleTemplateCfgId() == weiSiteSyteTemplateConfig.getWeiSiteSytleTemplateId()) {
                                weiSiteStyleTemplateMap.put("checked", "1");
                                break;
                            }
                        }
                    }
                    resultMapList.add(weiSiteStyleTemplateMap);
                }
                Map<String, List<Map<String, String>>> listMap = new HashMap<String, List<Map<String, String>>>(2, 1);
                listMap.put("dataList", resultMapList);
                applicationContext.setListMapData(listMap);
                applicationContext.setCount(enityPageMode.getTotalCount());
                applicationContext.setTotalPage(enityPageMode.getTotalPage());
                applicationContext.success();
            } else {
                applicationContext.noData();
            }
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
