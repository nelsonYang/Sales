package com.sales.user;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.Menu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireMenuList,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.ENTITY_LIST_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"menuId", "menuName", "menuParent", "menuType", "menuURL", "menuKey"},
        description = "查询菜单列表",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMenuListService implements Service {
    
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        EntityDao<Menu> menuDAO = applicationContext.getEntityDAO(EntityNames.menu);
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyConditionn = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        Condition menuParentConditon = new Condition("menuParent", ConditionTypeEnum.EQUAL, "-1");
        conditionList.add(companyConditionn);
        conditionList.add(menuParentConditon);
        List<Menu> menuList = menuDAO.inquireByCondition(conditionList);
        if (menuList != null && !menuList.isEmpty()) {
            applicationContext.setEntityList(menuList);
            applicationContext.success();
        } else {
            applicationContext.noData();
        }
        
        
    }
}
