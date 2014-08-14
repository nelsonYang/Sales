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
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.framework.utils.Base64Utils;
import com.framework.utils.DateTimeUtils;
import com.sales.config.SalesActionName;
import com.sales.config.SalesConstant;
import com.sales.config.SalesErrorCode;
import com.sales.entity.EntityNames;
import com.sales.entity.WeixinMessage;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.insertWeixinMessageByToken,
        importantParameters = {"session", "encryptType", "token"},
        minorParameters={"weixinId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.NOT_REQUIRE,
        description = "新增微信操作日志",
        validateParameters = {
    @Field(fieldName = "weixinId", fieldType = FieldTypeEnum.CHAR512, description = "威信id"),
    @Field(fieldName = "token", fieldType = FieldTypeEnum.CHAR512, description = "token"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertWeixinMessageByTokenService implements Service {

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String token = parameters.get("token");
        String weixinId = parameters.get("weixinId");
        String companyToken = Base64Utils.decodeToString(token);
        String[] tokens = companyToken.split("-");
        if (tokens != null && tokens.length == 2) {
            EntityDao<WeixinMessage> awardDao = applicationContext.getEntityDAO(EntityNames.weixinMessage);
            Map<String, String> weixinMessageMap = new HashMap<String, String>();
            weixinMessageMap.put("companyId", tokens[1]);
            weixinMessageMap.put("type", String.valueOf(SalesConstant.VISIT_WEI_SITE_TYPE));
            weixinMessageMap.put("createTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
            weixinMessageMap.put("fromUser", "");
            weixinMessageMap.put("toUser", weixinId == null ? "" : weixinId);
            weixinMessageMap.put("content", "订阅");
            WeixinMessage weixinMessage = awardDao.insert(weixinMessageMap);
            if (weixinMessage != null) {
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            applicationContext.fail(SalesErrorCode.INVALID_TOKEN);
        }
    }
}
