package com.sales.weisite;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteImage;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.deleteWeiSiteImage,
        importantParameters = {"session", "encryptType", "weiSiteImageId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除微网站图片",
        validateParameters = {
    @Field(fieldName = "weiSiteImageId", fieldType = FieldTypeEnum.BIG_INT, description = "图片id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteWeiSiteImageService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        EntityDao<WeiSiteImage> weiSiteImageDAO = applicationContext.getEntityDAO(EntityNames.weiSiteImage);
        String weiSiteImageId = parameters.get("weiSiteImageId");
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("weiSiteImageId", String.valueOf(weiSiteImageId));
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        boolean isDelete = weiSiteImageDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }
    }
}
