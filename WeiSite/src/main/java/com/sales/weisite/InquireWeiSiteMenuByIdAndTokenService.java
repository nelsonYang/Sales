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
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.ActionNames;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.WeiSiteMenu;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeiSiteMenuByIdAndToken,
        importantParameters = {"weiSiteMenuId", "token"},
        requestEncrypt = CryptEnum.SIGN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"weiSiteMenuId", "weiSiteMenuName", "weiSiteMenuDesc", "weiSiteMenuImageURL", "weiSiteMenuLinkWebSite", "weiSiteMenuParentId", "resourcesId", "weiSiteConfigId"},
        description = "查询WeiSiteMenu详细内容",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "token"),
    @Field(fieldName = "weiSiteMenuId", fieldType = FieldTypeEnum.BIG_INT, description = "微站栏目id")
    })
public class InquireWeiSiteMenuByIdAndTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireWeiSiteMenuByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        logger.debug("parameters={}", parameters);
        String token = parameters.get("token");
        String weixinId = parameters.get("weixinId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            PrimaryKey primaryKey = new PrimaryKey();
            String weiSiteMenuId = parameters.get("weiSiteMenuId");
            primaryKey.putKeyField("weiSiteMenuId", String.valueOf(weiSiteMenuId));
            primaryKey.putKeyField("companyId", tokens[1]);
            EntityDao<WeiSiteMenu> entityDAO = applicationContext.getEntityDAO(EntityNames.weiSiteMenu);
            WeiSiteMenu entity = entityDAO.inqurieByKey(primaryKey);
            if (entity != null) {
                applicationContext.setEntityData(entity);
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }

    }
}
