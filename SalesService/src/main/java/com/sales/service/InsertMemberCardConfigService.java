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
import com.sales.entity.MemberCardConfig;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertMemberCardConfig,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"merchantName","isExperienceOpen","telephone","memberCardName","merchantLogo","memberCardBackgroupURL","merchantAddress","integerationPerSign","keyword","matchType","title","messageImageURL","companyId","memberCardDesc"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增MemberCardConfig",
        validateParameters = {
       	@Field(fieldName ="memberCardConfigId", fieldType = FieldTypeEnum.INT, description ="会员卡配置id"),
	@Field(fieldName ="merchantName", fieldType = FieldTypeEnum.CHAR36, description ="商家名称"),
	@Field(fieldName ="isExperienceOpen", fieldType = FieldTypeEnum.TYINT, description ="是否开启积分1-开启 2-不开起"),
	@Field(fieldName ="telephone", fieldType = FieldTypeEnum.CHAR36, description ="联系方式"),
	@Field(fieldName ="memberCardName", fieldType = FieldTypeEnum.CHAR36, description ="会员卡名称"),
	@Field(fieldName ="merchantLogo", fieldType = FieldTypeEnum.CHAR64, description ="商家logo"),
	@Field(fieldName ="memberCardBackgroupURL", fieldType = FieldTypeEnum.CHAR64, description ="会员卡背景"),
	@Field(fieldName ="merchantAddress", fieldType = FieldTypeEnum.CHAR128, description ="商家地址"),
	@Field(fieldName ="integerationPerSign", fieldType = FieldTypeEnum.BIG_INT, description ="签到积分"),
	@Field(fieldName ="keyword", fieldType = FieldTypeEnum.CHAR36, description ="关键字"),
	@Field(fieldName ="matchType", fieldType = FieldTypeEnum.TYINT, description ="匹配类型1-精确2-模糊"),
	@Field(fieldName ="title", fieldType = FieldTypeEnum.CHAR36, description ="标题"),
	@Field(fieldName ="messageImageURL", fieldType = FieldTypeEnum.CHAR64, description ="图片的url"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="memberCardDesc", fieldType = FieldTypeEnum.CHAR128, description ="会员卡说名"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertMemberCardConfigService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertMemberCardConfigService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<MemberCardConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.memberCardConfig);
        MemberCardConfig entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
