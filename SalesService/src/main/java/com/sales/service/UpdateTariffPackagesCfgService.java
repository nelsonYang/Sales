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
import com.frameworkLog.factory.LogFactory;
import com.framework.service.api.Service;
import com.sales.entity.EntityNames;
import com.sales.entity.TariffPackagesCfg;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.updateTariffPackagesCfg,
        importantParameters = {"session", "encryptType","tariffPackagesCfgId"},
        minorParameters = {"type","money","num","tariffPackagesName"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "修改TariffPackagesCfg",
        validateParameters = {
       	@Field(fieldName ="tariffPackagesCfgId", fieldType = FieldTypeEnum.BIG_INT, description ="资费套餐id"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="资费套餐的类型"),
	@Field(fieldName ="money", fieldType = FieldTypeEnum.BIG_INT, description ="资费额"),
	@Field(fieldName ="num", fieldType = FieldTypeEnum.TYINT, description ="资费额对应的时间"),
	@Field(fieldName ="tariffPackagesName", fieldType = FieldTypeEnum.CHAR36, description ="资费套餐的名字"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class UpdateTariffPackagesCfgService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(UpdateTariffPackagesCfgService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<TariffPackagesCfg> entityDAO = applicationContext.getEntityDAO(EntityNames.tariffPackagesCfg);
        TariffPackagesCfg entity = entityDAO.update(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
