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
import com.sales.entity.WeiBar;
import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = ActionNames.insertWeiBar,
        importantParameters = {"session", "encryptType"},
        minorParameters = {"weiBarConfigId","weiBarTitle","weiBarContent","memberId","parentId","memberNo","memberName","createTime","companyId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "新增WeiBar",
        validateParameters = {
       	@Field(fieldName ="weiBarId", fieldType = FieldTypeEnum.BIG_INT, description ="微吧id"),
	@Field(fieldName ="weiBarConfigId", fieldType = FieldTypeEnum.BIG_INT, description ="微吧配置id"),
	@Field(fieldName ="weiBarTitle", fieldType = FieldTypeEnum.CHAR36, description ="微吧标题"),
	@Field(fieldName ="weiBarContent", fieldType = FieldTypeEnum.CHAR128, description ="微吧内容"),
	@Field(fieldName ="memberId", fieldType = FieldTypeEnum.BIG_INT, description ="会员id"),
	@Field(fieldName ="parentId", fieldType = FieldTypeEnum.BIG_INT, description ="文章的id"),
	@Field(fieldName ="memberNo", fieldType = FieldTypeEnum.CHAR36, description ="会员no"),
	@Field(fieldName ="memberName", fieldType = FieldTypeEnum.CHAR36, description ="会员名字"),
	@Field(fieldName ="createTime", fieldType = FieldTypeEnum.DATETIME, description ="发布时间"),
	@Field(fieldName ="companyId", fieldType = FieldTypeEnum.BIG_INT, description ="公司id"),

    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InsertWeiBarService implements Service {

    private Logger logger = LogFactory.getInstance().getLogger(InsertWeiBarService.class);

    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
         logger.debug("parameters={}", parameters);
        EntityDao<WeiBar> entityDAO = applicationContext.getEntityDAO(EntityNames.weiBar);
        WeiBar entity = entityDAO.insert(parameters);
        if (entity != null) {
            applicationContext.success();
        } else {
            throw new RollBackException("操作失败");
        }

    }
}
