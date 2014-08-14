package com.sales.resources;

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
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.datacache.ResponseMessageCache;
import com.sales.entity.EntityNames;
import com.sales.entity.ResponseMessageCfg;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertResponseMessageCfg,
        importantParameters = {"session", "encryptType", "type"},
        minorParameters = {"responseMessageCfgId", "responseContent", "responseImageURL", "responseAudio", "keyword", "relatedURL", "isClose", "responseContentType", "relatedId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增ResponseMessageCfg",
        validateParameters = {
    @Field(fieldName = "responseMessageCfgId", fieldType = FieldTypeEnum.BIG_INT, description = "回复信息配置表"),
    @Field(fieldName = "responseContent", fieldType = FieldTypeEnum.CHAR128, description = "回复内容"),
    @Field(fieldName = "responseImageURL", fieldType = FieldTypeEnum.CHAR128, description = "回复图片"),
    @Field(fieldName = "responseAudio", fieldType = FieldTypeEnum.CHAR64, description = "回复音频"),
    @Field(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "回复的类型0-文本1-音频2-music3-vedio4-news5-image6-location7-link8-event_event9-event_scan10-event_subscribe11-event_unsubscribe,12-首次关注13-关键字"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "relatedURL", fieldType = FieldTypeEnum.CHAR64, description = "相关的url"),
    @Field(fieldName = "isClose", fieldType = FieldTypeEnum.TYINT, description = "1-开启 2-关闭"),
    @Field(fieldName = "responseContentType", fieldType = FieldTypeEnum.TYINT, description = "回复类型"),
    @Field(fieldName = "relatedId", fieldType = FieldTypeEnum.BIG_INT, description = "关联id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertResponseMessageCfgService implements Service {
    private ResponseMessageCache responseMessageCache = ResponseMessageCache.getInstance();
    private Logger logger = LogFactory.getInstance().getLogger(InsertResponseMessageCfgService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        parameters.put("cfgType", String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
        parameters.put("relatedEventId", "-1");
        logger.debug("parameters={}", parameters);
        String typeStr = parameters.get("type");
        String keyword = parameters.get("keyword");
        String responseMessageCfgId = parameters.get("responseMessageCfgId");
        int type = Integer.parseInt(typeStr);
        EntityDao<ResponseMessageCfg> entityDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        if (responseMessageCfgId == null || responseMessageCfgId.isEmpty()) {
            ResponseMessageCfg entity = entityDAO.insert(parameters);
            if (entity != null) {
                if (SalesConstant.RESPONSE_EVENT_SUBSCRIBE_TYPE == type) {
                        responseMessageCache.insertCache(entity);
                }
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        } else {
            ResponseMessageCfg entity = entityDAO.update(parameters);
            if (entity != null) {
                if (SalesConstant.RESPONSE_EVENT_SUBSCRIBE_TYPE == type) {
                    if(keyword == null || keyword.isEmpty()){
                        keyword = entity.getKeyword();
                    }
                    responseMessageCache.updateCache(entity, keyword);
                }
                applicationContext.success();
            } else {
                throw new RollBackException("操作失败");
            }
        }

    }
}
