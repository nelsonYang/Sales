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
import com.sales.entity.ResponseMessageCfg;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteResponseMessageCfg,
        importantParameters = {"session", "encryptType","responseMessageCfgId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除ResponseMessageCfg操作",
        validateParameters = {
    	@Field(fieldName ="responseMessageCfgId", fieldType = FieldTypeEnum.BIG_INT, description ="回复信息配置表"),
	@Field(fieldName ="responseContent", fieldType = FieldTypeEnum.CHAR128, description ="回复内容"),
	@Field(fieldName ="responseImageURL", fieldType = FieldTypeEnum.CHAR64, description ="回复图片"),
	@Field(fieldName ="responseAudio", fieldType = FieldTypeEnum.CHAR64, description ="回复音频"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="回复的类型0-文本1-音频2-music3-vedio4-news5-image6-location7-link8-event_event9-event_scan10-event_subscribe11-event_unsubscribe"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="relatedEventId", fieldType = FieldTypeEnum.BIG_INT, description ="关联的活动id"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键字"),
	@Field(fieldName ="relatedURL", fieldType = FieldTypeEnum.CHAR64, description ="相关的url"),
	@Field(fieldName ="isClose", fieldType = FieldTypeEnum.TYINT, description ="1-开启 2-关闭"),
	@Field(fieldName ="responseContentType", fieldType = FieldTypeEnum.TYINT, description ="回复类型1-文本2-图文 3-活动 4-预订 5-微吧6-商家7-会员卡8-微站9-优惠券"),
	@Field(fieldName ="relatedId", fieldType = FieldTypeEnum.BIG_INT, description ="关联id"),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配模式1-精确0-模糊"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteResponseMessageCfgService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteResponseMessageCfgService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String responseMessageCfgId = parameters.get("responseMessageCfgId");
	primaryKey.putKeyField("responseMessageCfgId",String.valueOf(responseMessageCfgId));

        EntityDao<ResponseMessageCfg> entityDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
