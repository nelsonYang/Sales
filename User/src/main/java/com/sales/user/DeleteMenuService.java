package com.sales.user;

import com.framework.annotation.Field;
import com.framework.annotation.ServiceConfig;
import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.enumeration.CryptEnum;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.enumeration.LoginEnum;
import com.framework.enumeration.ParameterWrapperTypeEnum;
import com.framework.enumeration.ResponseTypeEnum;
import com.framework.enumeration.TerminalTypeEnum;
import com.framework.service.api.Service;
import com.sales.config.SalesActionName;
import com.sales.entity.EntityNames;
import com.sales.entity.Menu;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.deleteMenu,
        importantParameters = {"session", "encryptType", "menuId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.PLAIN,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除菜单",
        validateParameters = {
    @Field(fieldName = "menuId", fieldType = FieldTypeEnum.BIG_INT, description = "菜单id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteMenuService implements Service {
    
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String menuIdStr = parameters.get("menuId");
        EntityDao<Menu> menuDAO = applicationContext.getEntityDAO(EntityNames.menu);
        //递归查找id集合
        Set<Long> meunIdSet = new HashSet<Long>(10);
        this.getChildMenuId(menuDAO, Long.parseLong(menuIdStr), companyId, meunIdSet);
        PrimaryKey primaryKey;
        for (long menuId : meunIdSet) {
            primaryKey = new PrimaryKey();
            primaryKey.putKeyField("menuId", String.valueOf(menuId));
            primaryKey.putKeyField("companyId", String.valueOf(companyId));
            menuDAO.delete(primaryKey);
        }
        applicationContext.success();
    }
    
    private void getChildMenuId(EntityDao<Menu> menuDAO, long menuId, long companyId, Set<Long> allMenuIdSet) {
        allMenuIdSet.add(menuId);
        Condition parentMenuIdConditon = new Condition("menuParent", ConditionTypeEnum.EQUAL, String.valueOf(menuId));
        Condition companyIdConditon = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        List<Condition> conditionList = new ArrayList<Condition>(2);
        conditionList.add(parentMenuIdConditon);
        conditionList.add(companyIdConditon);
        List<PrimaryKey> primaryKeyList = menuDAO.inquireKeyByCondition(conditionList);
        String menuIdStr;
        for (PrimaryKey primaryKey : primaryKeyList) {
            menuIdStr = primaryKey.getKeyField("menuId");
            this.getChildMenuId(menuDAO, Long.parseLong(menuIdStr), companyId, allMenuIdSet);
        }
    }
}
