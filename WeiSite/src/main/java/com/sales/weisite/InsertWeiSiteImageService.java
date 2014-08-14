package com.sales.weisite;

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
        act = SalesActionName.insertWeiSiteImage,
        importantParameters = {"session", "encryptType", "weiSiteImageName", "weiSiteImageURL", "bindId", "weiSiteImageType"},
        minorParameters = {"weiSiteImageDesc"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增微网站图片",
        validateParameters = {
    @Field(fieldName = "weiSiteImageName", fieldType = FieldTypeEnum.CHAR64, description = "图片的名称"),
    @Field(fieldName = "weiSiteImageDesc", fieldType = FieldTypeEnum.CHAR512, description = "图片的描述"),
    @Field(fieldName = "bindId", fieldType = FieldTypeEnum.BIG_INT, description = "图片的所属的id"),
    @Field(fieldName = "weiSiteImageType", fieldType = FieldTypeEnum.TYINT, description = "图片类型"),
    @Field(fieldName = "weiSiteImageURL", fieldType = FieldTypeEnum.CHAR128, description = "图片地址"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertWeiSiteImageService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        EntityDao<WeiSiteImage> weiSiteImageDAO = applicationContext.getEntityDAO(EntityNames.weiSiteImage);
        parameters.put("companyId", String.valueOf(companyId));
        WeiSiteImage weiSiteImage = weiSiteImageDAO.insert(parameters);
        if (weiSiteImage != null) {
            applicationContext.success();
        } else {
            applicationContext.fail();
        }
    }
}
