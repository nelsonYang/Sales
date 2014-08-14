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
import com.sales.entity.AwardResult;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteAwardResult,
        importantParameters = {"session", "encryptType","awardResultId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除AwardResult操作",
        validateParameters = {
    	@Field(fieldName ="awardResultId", fieldType = FieldTypeEnum.BIG_INT, description ="中奖结果主键id"),
	@Field(fieldName ="awardSN", fieldType = FieldTypeEnum.CHAR36, description ="中奖的序列号"),
	@Field(fieldName ="awardWeixinNO", fieldType = FieldTypeEnum.CHAR36, description ="中奖微信号"),
	@Field(fieldName ="awardMobileNO", fieldType = FieldTypeEnum.CHAR11, description ="中奖手机号"),
	@Field(fieldName ="awardTime", fieldType = FieldTypeEnum.DATETIME, description ="中奖的时间"),
	@Field(fieldName ="awardNum", fieldType = FieldTypeEnum.INT, description ="中奖的数量"),
	@Field(fieldName ="awardStatus", fieldType = FieldTypeEnum.TYINT, description ="中奖的处理状态"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description =""),
	@Field(fieldName ="operator", fieldType = FieldTypeEnum.BIG_INT, description ="操作人"),
	@Field(fieldName ="settleTime", fieldType = FieldTypeEnum.DATETIME, description ="处理时间"),
	@Field(fieldName ="awardId", fieldType = FieldTypeEnum.BIG_INT, description ="中奖所属的奖项"),
	@Field(fieldName ="eventId", fieldType = FieldTypeEnum.BIG_INT, description ="活动id"),
	@Field(fieldName ="weixinId", fieldType = FieldTypeEnum.CHAR36, description ="微信id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteAwardResultService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteAwardResultService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String awardResultId = parameters.get("awardResultId");
	primaryKey.putKeyField("awardResultId",String.valueOf(awardResultId));

        EntityDao<AwardResult> entityDAO = applicationContext.getEntityDAO(EntityNames.awardResult);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
