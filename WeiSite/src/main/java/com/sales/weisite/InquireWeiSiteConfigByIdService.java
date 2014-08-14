package com.sales.weisite;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeiSiteConfigById,
        importantParameters = {"session", "encryptType", "weiSiteConfigId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"weiSiteConfigId", "weiSiteName", "backgroupMusicURL", "keyword", "matchType", "telphone", "isDialOpen", "title", "backgroupImageURL"},
        description = "查询WeiSiteConfig详细内容",
        validateParameters = {
    @Field(fieldName = "weiSiteConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "微网站配置id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireWeiSiteConfigByIdService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireWeiSiteConfigByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        logger.debug("parameters={}", parameters);
        PrimaryKey primaryKey = new PrimaryKey();
        String weiSiteConfigId = parameters.get("weiSiteConfigId");
        primaryKey.putKeyField("weiSiteConfigId", String.valueOf(weiSiteConfigId));
        primaryKey.putKeyField("companyId", String.valueOf(companyId));
        EntityDao<WeiSiteConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteConfig);
        WeiSiteConfig entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }


    }
}
