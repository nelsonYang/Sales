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
import com.sales.entity.Award;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.deleteAward,
        importantParameters = {"session", "encryptType","awardId"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除Award操作",
        validateParameters = {
    	@Field(fieldName ="awardId", fieldType = FieldTypeEnum.BIG_INT, description ="奖项id"),
	@Field(fieldName ="awardName", fieldType = FieldTypeEnum.CHAR36, description ="奖项的名字"),
	@Field(fieldName ="eventId", fieldType = FieldTypeEnum.BIG_INT, description ="归属的活动id"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),
	@Field(fieldName ="awardDesc", fieldType = FieldTypeEnum.CHAR64, description ="奖项的描述"),
	@Field(fieldName ="awardNum", fieldType = FieldTypeEnum.INT, description ="奖项的数量"),
	@Field(fieldName ="awardNumPerPerson", fieldType = FieldTypeEnum.CHAR36, description ="奖项每人的数量"),
	@Field(fieldName ="awardCurrentNum", fieldType = FieldTypeEnum.INT, description ="剩余的数量"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteAwardService implements Service {
   
    private Logger logger = LogFactory.getInstance().getLogger(DeleteAwardService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        PrimaryKey primaryKey = new PrimaryKey(); 
	String awardId = parameters.get("awardId");
	primaryKey.putKeyField("awardId",String.valueOf(awardId));

        EntityDao<Award> entityDAO = applicationContext.getEntityDAO(EntityNames.award);
        boolean isDelete = entityDAO.delete(primaryKey);
        if (isDelete) {
            applicationContext.success();
        } else {
           throw new RollBackException("操作失败");
        }
    }
}
