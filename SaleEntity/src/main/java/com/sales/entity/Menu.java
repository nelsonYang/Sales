package com.sales.entity;

import com.framework.entity.pojo.Entity;
import com.framework.entity.annotation.EntityConfig;
import com.framework.entity.annotation.FieldConfig;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.entity.pojo.PrimaryKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
@EntityConfig(
    entityName = EntityNames.menu, 
    keyFields = {"menuId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class Menu extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("menuId", String.valueOf(this.menuId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "menuId", fieldType =FieldTypeEnum.BIG_INT , description = "菜单id")
	private long menuId;
	public long getMenuId() {
	return menuId;
	}
 	 public void setMenuId(long menuId){
	 this.menuId = menuId;
	}
	@FieldConfig(fieldName = "menuName", fieldType =FieldTypeEnum.CHAR24 , description = "菜单名称")
	private String menuName;
	public String getMenuName() {
	return menuName;
	}
 	 public void setMenuName(String menuName){
	 this.menuName = menuName;
	}
	@FieldConfig(fieldName = "menuParent", fieldType =FieldTypeEnum.BIG_INT , description = "父级菜单")
	private long menuParent;
	public long getMenuParent() {
	return menuParent;
	}
 	 public void setMenuParent(long menuParent){
	 this.menuParent = menuParent;
	}
	@FieldConfig(fieldName = "menuType", fieldType =FieldTypeEnum.TYINT , description = "菜单类型")
	private byte menuType;
	public byte getMenuType() {
	return menuType;
	}
 	 public void setMenuType(byte menuType){
	 this.menuType = menuType;
	}
	@FieldConfig(fieldName = "menuURL", fieldType =FieldTypeEnum.CHAR64 , description = "菜单的URL")
	private String menuURL;
	public String getMenuURL() {
	return menuURL;
	}
 	 public void setMenuURL(String menuURL){
	 this.menuURL = menuURL;
	}
	@FieldConfig(fieldName = "menuKey", fieldType =FieldTypeEnum.CHAR64 , description = "菜单唯一标识")
	private String menuKey;
	public String getMenuKey() {
	return menuKey;
	}
 	 public void setMenuKey(String menuKey){
	 this.menuKey = menuKey;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("menuId", String.valueOf(this.menuId));
	 entityMap.put("menuName", this.menuName);
	 entityMap.put("menuParent", String.valueOf(this.menuParent));
	 entityMap.put("menuType", String.valueOf(this.menuType));
	 entityMap.put("menuURL", this.menuURL);
	 entityMap.put("menuKey", this.menuKey);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.menuId=Long.parseLong(entityMap.get("menuId"));
	 this.menuName=entityMap.get("menuName");
	 this.menuParent=Long.parseLong(entityMap.get("menuParent"));
	 this.menuType=Byte.parseByte(entityMap.get("menuType"));
	 this.menuURL=entityMap.get("menuURL");
	 this.menuKey=entityMap.get("menuKey");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
