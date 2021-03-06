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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@ServiceConfig(
        act = SalesActionName.inquireMenuTree,
        importantParameters = {"session", "encryptType"},
        requestEncrypt = CryptEnum.PLAIN,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.MAP_DATA_LIST_JSON,
        terminalType = TerminalTypeEnum.WEB,
        returnParameters = {"menuId", "menuName", "childMenus"},
        description = "查询菜单列表",
        validateParameters = {
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "session类型"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class InquireMenuTreeService implements Service {

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
        List<Map<String, String>> menuMapList = new ArrayList<Map<String, String>>(menuList.size());
        Map<String, String> menuMap;
        StringBuilder jsonBuilder;
        for (Menu menu : menuList) {
            jsonBuilder = new StringBuilder();
            menuMap = new HashMap<String, String>(4, 1);
            menuMap.put("menuId", String.valueOf(menu.getMenuId()));
            menuMap.put("menuName", menu.getMenuName());
            this.getChildMenuJson(menuDAO, menu.getMenuId(), companyId, jsonBuilder);
            menuMap.put("childMenus", jsonBuilder.toString());
            menuMapList.add(menuMap);
        }
        Map<String, List<Map<String, String>>> listMap = new HashMap<String, List<Map<String, String>>>(2, 1);
        listMap.put("menuList", menuMapList);
        applicationContext.setListMapData(listMap);
        applicationContext.success();

    }

    private void getChildMenuJson(EntityDao<Menu> menuDAO, long menuId, long companyId, StringBuilder jsonBuilder) {
        List<Condition> conditionList = new ArrayList<Condition>(2);
        Condition companyConditionn = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        Condition menuParentConditon = new Condition("menuParent", ConditionTypeEnum.EQUAL, String.valueOf(menuId));
        conditionList.add(companyConditionn);
        conditionList.add(menuParentConditon);
        List<Menu> menuList = menuDAO.inquireByCondition(conditionList);
        jsonBuilder.append("[");
        StringBuilder childJsonBuilder;
        for (Menu menu : menuList) {
            childJsonBuilder = new StringBuilder();
            this.getChildMenuJson(menuDAO, menu.getMenuId(), companyId, childJsonBuilder);
            jsonBuilder.append("{")
                    .append("\"menuId\":\"")
                    .append(menu.getMenuId())
                    .append("\",\"menuName\":\"")
                    .append(menu.getMenuName())
                    .append("\",\"childMenus\":")
                    .append(childJsonBuilder.toString())
                    .append("},");

        }
        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 1);
        }
        jsonBuilder.append("]");
    }
}
