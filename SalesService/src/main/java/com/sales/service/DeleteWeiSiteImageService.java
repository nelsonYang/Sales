package com.sales.service;

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
import com.framework.exception.RollBackException;
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteImage;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteWeiSiteImage,
        importantParameters = {"session", "encryptType","weiSiteImageId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除WeiSiteImage操作",
        validateParameters = {
    	@Field(fieldName ="weiSiteImageId", fieldType = FieldTypeEnum.BIG_INT, description ="微站图片id"),
	@Field(fieldName ="weiSiteImageName", fieldType = FieldTypeEnum.CHAR36, description ="图片名称"),
	@Field(fieldName ="weiSiteImageDesc", fieldType = FieldTypeEnum.CHAR128, description ="图片描述"),
	@Field(fieldName ="weiSiteImageURL", fieldType = FieldTypeEnum.CHAR64, description ="图片的地址"),
	@Field(fieldName ="weiSiteImageType", fieldType = FieldTypeEnum.TYINT, description ="图片的类型"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="bindId", fieldType = FieldTypeEnum.BIG_INT, description ="绑定的id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteWeiSiteImageService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteWeiSiteImageService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String weiSiteImageId = parameters.get("weiSiteImageId");
	primaryKey.putKeyField("weiSiteImageId",String.valueOf(weiSiteImageId));

        EntityDao<WeiSiteImage> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteImage);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
