package com.sales.weiapplication;

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
import com.framework.utils.Base64Utils;
import com.framework.utils.DateTimeUtils;
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
        act = ActionNames.insertWeiBarByToken,
        importantParameters = {"token"},
        minorParameters = {"weiBarConfigId", "weiBarTitle", "weiBarContent", "memberId", "parentId", "memberNo", "memberName"},
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        requestEncrypt = CryptEnum.SIGN,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        description = "新增WeiBar",
        validateParameters = {
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR64, description = "token"),
    @Field(fieldName = "weiBarConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "微吧配置id"),
    @Field(fieldName = "weiBarTitle", fieldType = FieldTypeEnum.CHAR36, description = "微吧标题"),
    @Field(fieldName = "weiBarContent", fieldType = FieldTypeEnum.CHAR128, description = "微吧内容"),
    @Field(fieldName = "memberId", fieldType = FieldTypeEnum.BIG_INT, description = "会员id"),
    @Field(fieldName = "parentId", fieldType = FieldTypeEnum.BIG_INT, description = "文章的id"),
    @Field(fieldName = "memberNo", fieldType = FieldTypeEnum.CHAR36, description = "会员no"),
    @Field(fieldName = "memberName", fieldType = FieldTypeEnum.CHAR36, description = "会员名字")
})
public class InsertWeiBarByTokenService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertWeiBarByTokenService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            parameters.put("companyId", tokens[1]);
            parameters.put("createTime", DateTimeUtils.currentDay());
            logger.debug("parameters={}", parameters);
            EntityDao<WeiBar> entityDAO = applicationContext.getEntityDAO(EntityNames.weiBar);
            WeiBar entity = entityDAO.insert(parameters);
            if (entity != null) {
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }
    }
}
