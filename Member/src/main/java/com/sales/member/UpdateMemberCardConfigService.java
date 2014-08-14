package com.sales.member;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.config.ActionNames;
import com.sales.config.SalesConstant;
import com.sales.entity.EntityNames;
import com.sales.entity.MemberCardConfig;
import com.sales.entity.ResponseMessageCfg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateMemberCardConfig,
        importantParameters = {"session", "encryptType", "memberCardConfigId"},
        minorParameters = {"merchantName", "isExperienceOpen", "telephone", "memberCardName", "merchantLogo", "memberCardBackgroupURL", "merchantAddress", "integerationPerSign", "keyword", "matchType", "title", "messageImageURL", "memberCardDesc"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改MemberCardConfig",
        validateParameters = {
    @Field(fieldName = "memberCardConfigId", fieldType = FieldTypeEnum.INT, description = "会员卡配置id"),
    @Field(fieldName = "merchantName", fieldType = FieldTypeEnum.CHAR36, description = "商家名称"),
    @Field(fieldName = "isExperienceOpen", fieldType = FieldTypeEnum.TYINT, description = "是否开启积分1-开启 2-不开起"),
    @Field(fieldName = "telephone", fieldType = FieldTypeEnum.CHAR36, description = "联系方式"),
    @Field(fieldName = "memberCardName", fieldType = FieldTypeEnum.CHAR36, description = "会员卡名称"),
    @Field(fieldName = "merchantLogo", fieldType = FieldTypeEnum.CHAR64, description = "商家logo"),
    @Field(fieldName = "memberCardBackgroupURL", fieldType = FieldTypeEnum.CHAR128, description = "会员卡背景"),
    @Field(fieldName = "merchantAddress", fieldType = FieldTypeEnum.CHAR128, description = "商家地址"),
    @Field(fieldName = "integerationPerSign", fieldType = FieldTypeEnum.BIG_INT, description = "签到积分"),
    @Field(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字"),
    @Field(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配类型1-精确2-模糊"),
    @Field(fieldName = "title", fieldType = FieldTypeEnum.CHAR36, description = "标题"),
    @Field(fieldName = "messageImageURL", fieldType = FieldTypeEnum.CHAR128, description = "图片的url"),
    @Field(fieldName = "memberCardDesc", fieldType = FieldTypeEnum.CHAR128, description = "会员卡说名"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateMemberCardConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateMemberCardConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        long companyId = applicationContext.getUserId();
        parameters.put("companyId", String.valueOf(companyId));
        logger.debug("parameters={}", parameters);
        EntityDao<MemberCardConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCardConfig);
        MemberCardConfig entity = entityDAO.update(parameters);
        if (entity != null) {
            EntityDao<ResponseMessageCfg> responseMessageDAO = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
            List<Condition> conditionList = new ArrayList<Condition>(2);
            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
            conditionList.add(companyIdCondition);
            Condition responseContentTypeCondition = new Condition("responseContentType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.MEMBER_CARD_TYPE));
            conditionList.add(responseContentTypeCondition);
            Condition relateIdIdCondition = new Condition("relatedEventId", ConditionTypeEnum.EQUAL, String.valueOf(entity.getMemberCardConfigId()));
            conditionList.add(relateIdIdCondition);
            List<ResponseMessageCfg> responseMessageCfgList = responseMessageDAO.inquireByCondition(conditionList);
            if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
                ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
                Map<String, String> responseMessageMap = new HashMap<String, String>(2, 1);
                responseMessageMap.put("responseMessageCfgId", String.valueOf(responseMessageCfg.getResponseMessageCfgId()));
                responseMessageMap.put("keyword", parameters.get("keyword"));
                responseMessageCfg = responseMessageDAO.update(responseMessageMap);
                if (responseMessageCfg != null) {
                    applicationContext.success();
                } else {
                    throw new RollBackException("操作失败");
                }
            } else {
                applicationContext.success();
            }
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
