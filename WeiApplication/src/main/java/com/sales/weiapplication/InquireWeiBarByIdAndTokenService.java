package com.sales.weiapplication;

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
import com.sales.entity.WeiBar;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireWeiBarByIdAndToken,
        importantParameters = {"weiBarId", "token"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        returnParameters = {"weiBarId", "weiBarConfigId", "weiBarTitle", "weiBarContent", "memberId", "parentId", "memberNo", "memberName", "createTime", "companyId"},
        description = "查询WeiBar详细内容",
        validateParameters = {
    @Field(fieldName = "weiBarId", fieldType = FieldTypeEnum.BIG_INT, description = "微吧id"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR1024, description = "访问token")
 })
public class InquireWeiBarByIdAndTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InquireWeiBarByIdAndTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            logger.debug("parameters={}", parameters);
            PrimaryKey primaryKey = new PrimaryKey();
            String weiBarId = parameters.get("weiBarId");
            primaryKey.putKeyField("weiBarId", String.valueOf(weiBarId));
            primaryKey.putKeyField("companyId", tokens[1]);
            EntityDao<WeiBar> entityDAO = applicationContext.getEntityDAO(EntityNames.weiBar);
            WeiBar entity = entityDAO.inqurieByKey(primaryKey);
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
