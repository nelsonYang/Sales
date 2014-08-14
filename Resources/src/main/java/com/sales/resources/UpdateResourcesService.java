package com.sales.resources;

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
import com.framework.exception.RollBackException;
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.framework.utils.DateTimeUtils;
import com.sales.config.ActionNames;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.Resources;
import com.sales.entity.ResourcesImage;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateResources,
        importantParameters = {"session", "encryptType", "resourcesId"},
        minorParameters = {"resourcesName", "resourcesURL", "resourceContent", "imageURL"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.BATCH_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改Resources",
        validateParameters = {
    @Field(fieldName = "resourcesId", fieldType = FieldTypeEnum.BIG_INT, description = "资源id"),
    @Field(fieldName = "resourcesName", fieldType = FieldTypeEnum.CHAR36, description = "资源名称"),
    @Field(fieldName = "resourcesURL", fieldType = FieldTypeEnum.CHAR128, description = "链接地址"),
    @Field(fieldName = "imageURL", fieldType = FieldTypeEnum.CHAR256, description = "图片地址"),
    @Field(fieldName = "resourceContent", fieldType = FieldTypeEnum.CHAR128, description = "资源内容"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateResourcesService implements Service {

    private ResponseMessageCache responseMessageCache = ResponseMessageCache.getInstance();
    private Logger logger = LogFactory.getInstance().getLogger(UpdateResourcesService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String[]> parameters = applicationContext.getBatchMapParameters();
        long companyId = applicationContext.getUserId();
        String[] resourcesId = parameters.get("resourcesId");
        String[] resourcesName = parameters.get("resourcesName");
        String[] resourcesURL = parameters.get("resourcesURL");
        String[] imageURL = parameters.get("imageURL");
        String[] resourceContent = parameters.get("resourceContent");
        logger.debug("parameters={}", parameters);
        EntityDao<Resources> resourcesDAO = applicationContext.getEntityDAO(EntityNames.resources);
        Map<String, String> resourceMap = new HashMap<String, String>(4, 1);
        resourceMap.put("resourcesId", resourcesId[0]);
        resourceMap.put("resourcesName", resourcesName[0]);
        resourceMap.put("resourcesURL", imageURL[0]);
        Resources resources = resourcesDAO.update(resourceMap);
        if (resources != null) {
            EntityDao<ResourcesImage> resourceImageDAO = applicationContext.getEntityDAO(EntityNames.resourcesImage);
            String sql = "delete from resourcesImage where resourcesId = ?";
            boolean isSuccess = resourceImageDAO.executeUpdateBySql(new String[]{"resourcesImage"}, sql, new String[]{resourcesId[0]}, new HashMap<String, String>(2, 1));
            if (isSuccess) {
                Map<String, String> resourcesImageMap;
                if (resourceContent != null && resourceContent.length > 0 && imageURL != null && imageURL.length > 0) {
                    for (int index = 0; index < resourcesId.length; index++) {
                        resourcesImageMap = new HashMap<String, String>(8, 1);
                        resourcesImageMap.put("companyId", String.valueOf(companyId));
                        resourcesImageMap.put("createTime", DateTimeUtils.currentDay());
                        resourcesImageMap.put("resourcesTitle", resourcesName[index]);
                        resourcesImageMap.put("resourcesId", String.valueOf(resources.getResourcesId()));
                        resourcesImageMap.put("linkSite", resourcesURL[index]);
                        resourcesImageMap.put("resoucesImageURL", imageURL[index]);
                        resourcesImageMap.put("resoucesContent", resourceContent[index]);
                        resourceImageDAO.insert(resourcesImageMap);
                    }
                }
                responseMessageCache.updateCache(resources);
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            throw new RollBackException("操作失败");
        }
    }
}
