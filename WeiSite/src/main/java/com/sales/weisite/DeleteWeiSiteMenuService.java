package com.sales.weisite;

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
import com.sales.entity.WeiSiteMenu;
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
        act = SalesActionName.deleteWeiSiteMenu,
        importantParameters = {"session", "encryptType", "weiSiteMenuId"},
        requestEncrypt = CryptEnum.AES,
        parametersWrapperType = ParameterWrapperTypeEnum.SIMPLE_MAP,
        responseEncrypt = CryptEnum.AES,
        responseType = ResponseTypeEnum.NO_DATA_JSON,
        terminalType = TerminalTypeEnum.WEB,
        requireLogin = LoginEnum.REQUIRE,
        description = "删除菜单",
        validateParameters = {
    @Field(fieldName = "weiSiteMenuId", fieldType = FieldTypeEnum.BIG_INT, description = "菜单id"),
    @Field(fieldName = "session", fieldType = FieldTypeEnum.CHAR1024, description = "sesion信息"),
    @Field(fieldName = "encryptType", fieldType = FieldTypeEnum.TYINT, description = "加密类型")
})
public class DeleteWeiSiteMenuService implements Service {
    
    public void execute() {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        long companyId = applicationContext.getUserId();
        Map<String, String> parameters = applicationContext.getSimpleMapParameters();
        String weiSiteMenuId = parameters.get("weiSiteMenuId");
        EntityDao<WeiSiteMenu> weiSiteMenuDAO = applicationContext.getEntityDAO(EntityNames.weiSiteMenu);
         //递归查找id集合
        Set<Long> meunIdSet = new HashSet<Long>(10);
        this.getChildMenuId(weiSiteMenuDAO, Long.parseLong(weiSiteMenuId), companyId, meunIdSet);
        PrimaryKey primaryKey;
        for (long menuId : meunIdSet) {
            primaryKey = new PrimaryKey();
            primaryKey.putKeyField("weiSiteMenuId", String.valueOf(menuId));
            primaryKey.putKeyField("companyId", String.valueOf(companyId));
            weiSiteMenuDAO.delete(primaryKey);
        }
        applicationContext.success();
    }
    
    private void getChildMenuId( EntityDao<WeiSiteMenu> weiSiteMenuDAO, long menuId, long companyId, Set<Long> allMenuIdSet) {
        allMenuIdSet.add(menuId);
        Condition parentMenuIdConditon = new Condition("weiSiteMenuParentId", ConditionTypeEnum.EQUAL, String.valueOf(menuId));
        Condition companyIdConditon = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
        List<Condition> conditionList = new ArrayList<Condition>(2);
        conditionList.add(parentMenuIdConditon);
        conditionList.add(companyIdConditon);
        List<PrimaryKey> primaryKeyList = weiSiteMenuDAO.inquireKeyByCondition(conditionList);
        String menuIdStr;
        for (PrimaryKey primaryKey : primaryKeyList) {
            menuIdStr = primaryKey.getKeyField("weiSiteMenuId");
            this.getChildMenuId(weiSiteMenuDAO, Long.parseLong(menuIdStr), companyId, allMenuIdSet);
        }
    }
}
