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
import com.sales.entity.ResourcesImage;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertResourcesImage,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"resoucesId", "resoucesImageURL", "resoucesContent", "imageType"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增ResourcesImage",
        validateParameters = {
    @Field(fieldName = "resoucesId", fieldType = FieldTypeEnum.BIG_INT, description = "资源id"),
    @Field(fieldName = "resoucesImageURL", fieldType = FieldTypeEnum.CHAR64, description = "资源图片的url"),
    @Field(fieldName = "resoucesContent", fieldType = FieldTypeEnum.CHAR512, description = "资源内容"),
    @Field(fieldName = "imageType", fieldType = FieldTypeEnum.TYINT, description = "图片类型"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertResourcesImageService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertResourcesImageService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("createTime", DateTimeUtils.currentDay());
        logger.debug("parameters={}", parameters);
        EntityDao<ResourcesImage> entityDAO = applicationContext.getEntityDAO(EntityNames.resourcesImage);
        ResourcesImage entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
