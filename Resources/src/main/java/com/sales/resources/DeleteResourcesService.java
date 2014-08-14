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
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
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
        act = ActionNames.deleteResources,
        importantParameters = {"session", "encryptType", "resourcesId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Resources操作",
        validateParameters = {
    @Field(fieldName = "resourcesId", fieldType = FieldTypeEnum.BIG_INT, description = "资源id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteResourcesService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(DeleteResourcesService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        PrimaryKey primaryKey = new PrimaryKey();
        String resourcesId = parameters.get("resourcesId");
        primaryKey.putKeyField("resourcesId", String.valueOf(resourcesId));
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        EntityDao<Resources> entityDAO = applicationContext.getEntityDAO(EntityNames.resources);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            EntityDao<ResourcesImage> resourceImagesDAO = applicationContext.getEntityDAO(EntityNames.resourcesImage);
            String sql = "delete from resourcesImage where companyId= ? and resourcesId = ?";
            String companyIdStr = String.valueOf(companyId);
            String resourceIdStr = String.valueOf(resourcesId);
            String[] values = {companyIdStr, resourceIdStr};
            boolean isDeleted = resourceImagesDAO.executeUpdateBySql(new String[]{"resouresImage"}, sql, values, parameters);
            if (isDeleted) {
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            throw new RollBackException("操作失败");
        }
    }
}
