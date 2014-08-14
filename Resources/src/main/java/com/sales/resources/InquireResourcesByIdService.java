package com.sales.resources;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.JsonUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.Resources;
import com.sales.entity.ResourcesImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireResourcesById,
        importantParameters = {"session", "encryptType", "resourcesId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"resourcesId", "resourcesType", "contents"},
        description = "查询Resources详细内容",
        validateParameters = {
    @Field(fieldName = "resourcesId", fieldType = FieldTypeEnum.BIG_INT, description = "资源id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireResourcesByIdService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireResourcesByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        PrimaryKey primaryKey = new PrimaryKey();
        String resourcesId = parameters.get("resourcesId");
        primaryKey.putKeyField("resourcesId", String.valueOf(resourcesId));
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        EntityDao<Resources> entityDAO = applicationContext.getEntityDAO(EntityNames.resources);
        Resources entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            conditionList.add(companyIdCondition);
            Condition resourcesIdCondition = new Condition("resourcesId", ConditionTypeEnum.EQUAL, resourcesId);
            conditionList.add(resourcesIdCondition);
            EntityDao<ResourcesImage> resourcesImageDAO = applicationContext.getEntityDAO(EntityNames.resourcesImage);
            List<ResourcesImage> resourcesImageList = resourcesImageDAO.inquireByCondition(conditionList);
            List<Map<String, String>> resourcesMapList = new ArrayList<Map<String, String>>(resourcesImageList.size());
            Map<String, String> resourcesMap;
            for (ResourcesImage resourcesImage : resourcesImageList) {
                resourcesMap = resourcesImage.toMap();
                resourcesMapList.add(resourcesMap);
            }
            String json = JsonUtils.mapListToJsonArray(resourcesMapList);
            Map<String, String> resultMap = entity.toMap();
            resultMap.put("content", json);
            applicationContext.setMapData(resultMap);
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }


    }
}
