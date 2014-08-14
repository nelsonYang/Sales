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
import com.sales.entity.Consuption;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertConsuption,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"couponId","couponName","availableCount","consumeMoney","operatorName","createTime"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增Consuption",
        validateParameters = {
       	@Field(fieldName ="consuptionId", fieldType = FieldTypeEnum.BIG_INT, description ="消费记录id"),
	@Field(fieldName ="couponId", fieldType = FieldTypeEnum.BIG_INT, description ="优惠券id"),
	@Field(fieldName ="couponName", fieldType = FieldTypeEnum.CHAR36, description ="优惠券名称"),
	@Field(fieldName ="availableCount", fieldType = FieldTypeEnum.TYINT, description ="可用次数-1表示不限"),
	@Field(fieldName ="consumeMoney", fieldType = FieldTypeEnum.DOUBLE, description ="消费金额"),
	@Field(fieldName ="operatorName", fieldType = FieldTypeEnum.CHAR11, description ="操作员"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="记录时间"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertConsuptionService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertConsuptionService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<Consuption> entityDAO = applicationContext.getEntityDAO(EntityNames.consuption);
        Consuption entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
