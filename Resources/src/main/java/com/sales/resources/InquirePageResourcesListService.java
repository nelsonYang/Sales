package com.sales.resources;

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
import com.framework.utils.JsonUtils;
import com.framework.utils.SetUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.Resources;
import com.sales.entity.ResourcesImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquirePageResourcesList,
        importantParameters = {"session", "pageCount", "pageNo", "encryptType"},
        minorParameters = {"resourcesType"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_LIST_JSON_PAGE,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"resourcesId", "resourcesType", "createTime", "content"},
        description = "分页查询Resources",
        validateParameters = {
    @Field(fieldName = "pageCount", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "pageNo", fieldType = FieldTypeEnum.INT, description = "分页参数"),
    @Field(fieldName = "resourcesType", fieldType = FieldTypeEnum.TYINT, description = "资源类型1-文字2-单图文 3-多图文4-自定义url5-系统url6微官网url"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquirePageResourcesListService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquirePageResourcesListService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        String pageCount = parameters.get("pageCount");
        String pageNo = parameters.get("pageNo");
        String imageType = parameters.get("resourcesType");
        EntityDao<Resources> entityDAO = applicationContext.getEntityDAO(EntityNames.resources);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        if (imageType != null && !imageType.isEmpty()) {
            Condition imageTypeCondition = new Condition("resourcesType", ConditionTypeEnum.EQUAL, imageType);
            conditionList.add(imageTypeCondition);
        }
        PageModel enityPageMode = entityDAO.inquirePageByCondition(conditionList, Integer.parseInt(pageNo), Integer.parseInt(pageCount));
        List<Resources> entityList = enityPageMode.getDataList();
        if (entityList != null) {
            if (!entityList.isEmpty()) {
                Set<Long> resourcesImageIdSet = new HashSet<Long>();
                List<Map<String, String>> resultMapList = new ArrayList<Map<String, String>>();
                Map<String, String> resourceMap;
                for (Resources resource : entityList) {
                    resourcesImageIdSet.add(resource.getResourcesId());
                    resourceMap = resource.toMap();
                    resourceMap.put("content", "[]");
                    resultMapList.add(resourceMap);
                }
                conditionList = new ArrayList<Condition>(2);
                companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
                conditionList.add(companyIdCondition);
                Condition resourcesIdCondition = new Condition("resourcesId", ConditionTypeEnum.IN, SetUtils.LongSet2Str(resourcesImageIdSet));
                conditionList.add(resourcesIdCondition);
                EntityDao<ResourcesImage> resourcesImageDAO = applicationContext.getEntityDAO(EntityNames.resourcesImage);
                List<ResourcesImage> resourcesImageList = resourcesImageDAO.inquireByCondition(conditionList);
                List<Map<String, String>> resourcesMapList;
                String json;
                for (Resources resource : entityList) {
                    resourcesMapList = new ArrayList<Map<String, String>>(resourcesImageList.size());
                    for (ResourcesImage resourceImage : resourcesImageList) {
                        if (resource.getResourcesId() == resourceImage.getResourcesId()) {
                            resourcesMapList.add(resourceImage.toMap());
                        }
                    }
                    json = JsonUtils.mapListToJsonArray(resourcesMapList);
                    for (Map<String, String> resultmap : resultMapList) {
                        if (Long.parseLong(resultmap.get("resourcesId")) == resource.getResourcesId()) {
                            resultmap.put("content", json);
                            break;
                        }
                    }
                }



                Map<String, List<Map<String, String>>> resultMap = new HashMap<String, List<Map<String, String>>>();
                resultMap.put("resourcesList", resultMapList);
                applicationContext.setListMapData(resultMap);
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
