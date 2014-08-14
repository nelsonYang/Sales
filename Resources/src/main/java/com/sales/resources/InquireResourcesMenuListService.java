package com.sales.resources;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.entity.EntityNames;
import com.sales.entity.Resources;
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
        act = ActionNames.inquireResourcesMenuList,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        returnParameters = {"systemResource", "businessResource"},
        description = "查询Resources配置",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireResourcesMenuListService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireResourcesMenuListService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        EntityDao<Resources> entityDAO = applicationContext.getEntityDAO(EntityNames.resources);
        List<Condition> conditionList = new ArrayList<Condition>(0);
        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        conditionList.add(companyIdCondition);
        List<Resources> entityList = entityDAO.inquireByCondition(conditionList);
        if (entityList != null) {
            Map<String, String> resultMap;
            List<Map<String, String>> systemMapList = new ArrayList<Map<String, String>>(0);
            List<Map<String, String>> bussinessMapList = new ArrayList<Map<String, String>>(0);
            Map<String, String> resourceMap = null;
            for (Resources resources : entityList) {
                if (resources.getResourcesType() == SalesConstant.SYSTEM_RESOURCE_TYPE || resources.getResourcesType() == SalesConstant.CUSTOM_RESOURCE_TYPE || resources.getResourcesType() == SalesConstant.WEISITE_RESOURCE_TYPE) {
                    resourceMap = new HashMap<String, String>(2);
                    resourceMap.put("resourcesName", resources.getResourcesName());
                    resourceMap.put("resourcesId", String.valueOf(resources.getResourcesId()));
                    bussinessMapList.add(resourceMap);
                } else if (resources.getResourcesType() == SalesConstant.SINGLE__TEXT || resources.getResourcesType() == SalesConstant.SINGLE_IMAGE_TEXT || resources.getResourcesType() == SalesConstant.MULTI_IMAGE_TEXT) {
                    resourceMap = new HashMap<String, String>(2);
                    resourceMap.put("resourcesName", resources.getResourcesName());
                    resourceMap.put("resourcesId", String.valueOf(resources.getResourcesId()));
                    systemMapList.add(resourceMap);
                }

            }
            resultMap = new HashMap<String, String>(4, 1);
            resultMap.put("systemResource", JsonUtils.mapListToJsonArray(systemMapList));
            resultMap.put("businessResource", JsonUtils.mapListToJsonArray(bussinessMapList));
            applicationContext.setMapData(resultMap);
            applicationContext.success();

        }
    }
}
