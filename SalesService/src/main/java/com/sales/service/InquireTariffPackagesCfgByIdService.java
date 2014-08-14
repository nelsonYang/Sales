package com.sales.service;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.exception.RollBackException;
import com.framework.service.api.Service;
import com.frameworkLog.factory.LogFactory;
import com.sales.entity.EntityNames;
import com.sales.entity.TariffPackagesCfg;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.inquireTariffPackagesCfgById,
        importantParameters = {"session", "encryptType", "tariffPackagesCfgId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"tariffPackagesCfgId","type","money","num","tariffPackagesName"},
        description = "查询TariffPackagesCfg详细内容",
        validateParameters = {
          	@Field(fieldName ="tariffPackagesCfgId", fieldType = FieldTypeEnum.BIG_INT, description ="资费套餐id"),
	@Field(fieldName ="type", fieldType = FieldTypeEnum.TYINT, description ="资费套餐的类型"),
	@Field(fieldName ="money", fieldType = FieldTypeEnum.BIG_INT, description ="资费额"),
	@Field(fieldName ="num", fieldType = FieldTypeEnum.TYINT, description ="资费额对应的时间"),
	@Field(fieldName ="tariffPackagesName", fieldType = FieldTypeEnum.CHAR36, description ="资费套餐的名字"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireTariffPackagesCfgByIdService implements Service {
    
    private Logger logger = LogFactory.getInstance().getLogger(InquireTariffPackagesCfgByIdService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
       logger.debug("parameters={}", parameters); 
        PrimaryKey primaryKey = new PrimaryKey(); 
	String tariffPackagesCfgId = parameters.get("tariffPackagesCfgId");
	primaryKey.putKeyField("tariffPackagesCfgId",String.valueOf(tariffPackagesCfgId));

        EntityDao<TariffPackagesCfg> entityDAO = applicationContext.getEntityDAO(EntityNames.tariffPackagesCfg);
          TariffPackagesCfg entity = entityDAO.inqurieByKey(primaryKey);
        if (entity != null) {
            applicationContext.setEntityData(entity);
            applicationContext.success();
        } else {
             throw new RollBackException("操作失败");
        }


    }
}
