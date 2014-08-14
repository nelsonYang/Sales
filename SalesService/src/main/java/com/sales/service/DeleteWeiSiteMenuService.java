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
import com.sales.entity.WeiSiteMenu;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteWeiSiteMenu,
        importantParameters = {"session", "encryptType","weiSiteMenuId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除WeiSiteMenu操作",
        validateParameters = {
    	@Field(fieldName ="weiSiteMenuId", fieldType = FieldTypeEnum.BIG_INT, description ="微站栏目id"),
	@Field(fieldName ="weiSiteMenuName", fieldType = FieldTypeEnum.CHAR36, description ="栏目的名称"),
	@Field(fieldName ="weiSiteMenuDesc", fieldType = FieldTypeEnum.CHAR128, description ="栏目的描述"),
	@Field(fieldName ="weiSiteMenuImageURL", fieldType = FieldTypeEnum.CHAR64, description ="栏目的图片背景"),
	@Field(fieldName ="weiSiteMenuLinkWebSite", fieldType = FieldTypeEnum.CHAR64, description ="栏目的链接地址"),
	@Field(fieldName ="weiSiteMenuParentId", fieldType = FieldTypeEnum.BIG_INT, description ="栏目的父级id"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="resourcesId", fieldType = FieldTypeEnum.BIG_INT, description ="资源id"),
	@Field(fieldName ="weiSiteConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="微网站的配置id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteWeiSiteMenuService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteWeiSiteMenuService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String weiSiteMenuId = parameters.get("weiSiteMenuId");
	primaryKey.putKeyField("weiSiteMenuId",String.valueOf(weiSiteMenuId));

        EntityDao<WeiSiteMenu> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteMenu);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
