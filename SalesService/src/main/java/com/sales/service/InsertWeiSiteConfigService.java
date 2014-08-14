package com.sales.service;

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
import com.frameworkLog.factory.LogFactory;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertWeiSiteConfig,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"weiSiteName","backgroupMusicURL","keyword","matchType","telphone","isDialOpen","title","backgroupImageURL","companyId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增WeiSiteConfig",
        validateParameters = {
       	@Field(fieldName ="weiSiteConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="微网站配置id"),
	@Field(fieldName ="weiSiteName", fieldType = FieldTypeEnum.CHAR36, description ="微网站名称"),
	@Field(fieldName ="backgroupMusicURL", fieldType = FieldTypeEnum.CHAR64, description ="背景音乐的url"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description =""),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配类型1-精确2-模糊"),
	@Field(fieldName ="telphone", fieldType = FieldTypeEnum.CHAR11, description ="手机号码"),
	@Field(fieldName ="isDialOpen", fieldType = FieldTypeEnum.TYINT, description ="开启一键拨号"),
	@Field(fieldName ="title", fieldType = FieldTypeEnum.CHAR36, description ="官网名称"),
	@Field(fieldName ="backgroupImageURL", fieldType = FieldTypeEnum.CHAR64, description ="背景图片"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertWeiSiteConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertWeiSiteConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<WeiSiteConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteConfig);
        WeiSiteConfig entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
