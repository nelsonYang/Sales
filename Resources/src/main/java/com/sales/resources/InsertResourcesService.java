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
import com.framework.service.api.Service;
import com.framework.utils.DateTimeUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
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
        act = ActionNames.insertResources,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"resourcesType", "resourcesName", "resourcesURL", "resourceContent", "imageURL"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.BATCH_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增单和多图文",
        validateParameters = {
    @Field(fieldName = "resourcesType", fieldType = FieldTypeEnum.TYINT, description = "资源类型1-文字2-单图文 3-多图文4-自定义url5-系统url6微官网url"),
    @Field(fieldName = "resourcesName", fieldType = FieldTypeEnum.CHAR36, description = "资源名称"),
    @Field(fieldName = "resourcesURL", fieldType = FieldTypeEnum.CHAR256, description = "链接url"),
    @Field(fieldName = "imageURL", fieldType = FieldTypeEnum.CHAR256, description = "图片url"),
    @Field(fieldName = "resourceContent", fieldType = FieldTypeEnum.CHAR128, description = "资源内容"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertResourcesService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertResourcesService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String[]> parameters = applicationContext.getBatchMapParameters();
        long companyId = applicationContext.getUserId();
        String[] resourcesType = parameters.get("resourcesType");
        String[] resourcesName = parameters.get("resourcesName");
        String[] resourcesURL = parameters.get("resourcesURL");
        String[] imageURL = parameters.get("imageURL");
        String[] resourceContent = parameters.get("resourceContent");
        Map<String, String> resourceMap = new HashMap<String, String>();
        resourceMap.put("companyId", String.valueOf(companyId));
        resourceMap.put("createTime", DateTimeUtils.currentDay());
        resourceMap.put("resourcesType", resourcesType[0]);
        resourceMap.put("resourcesName", resourcesName[0]);
        resourceMap.put("resourcesURL", imageURL[0]);
        logger.debug("parameters={}", parameters);
        EntityDao<Resources> entityDAO = applicationContext.getEntityDAO(EntityNames.resources);
        Resources entity = entityDAO.insert(resourceMap);
        if (entity != null) {
            EntityDao<ResourcesImage> resourceImageDAO = applicationContext.getEntityDAO(EntityNames.resourcesImage);
            Map<String, String> resourcesImageMap;
            if (imageURL != null && imageURL.length > 0) {
                for (int index = 0; index < resourcesName.length; index++) {
                    resourcesImageMap = new HashMap<String, String>(8, 1);
                    resourcesImageMap.put("companyId", String.valueOf(companyId));
                    resourcesImageMap.put("createTime", DateTimeUtils.currentDay());
                    resourcesImageMap.put("resourcesTitle", resourcesName[index]);
                    resourcesImageMap.put("resourcesId", String.valueOf(entity.getResourcesId()));
                    resourcesImageMap.put("linkSite", resourcesURL[index]);
                    resourcesImageMap.put("resoucesImageURL", imageURL[index]);
                    resourcesImageMap.put("resoucesContent", resourceContent[index]);
                    resourceImageDAO.insert(resourcesImageMap);
                }
            }
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
