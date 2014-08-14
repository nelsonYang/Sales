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
    entityName = EntityNames.weiSiteMenu, 
    keyFields = {"weiSiteMenuId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class WeiSiteMenu extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("weiSiteMenuId", String.valueOf(this.weiSiteMenuId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "weiSiteMenuId", fieldType =FieldTypeEnum.BIG_INT , description = "微站栏目id")
	private long weiSiteMenuId;
	public long getWeiSiteMenuId() {
	return weiSiteMenuId;
	}
 	 public void setWeiSiteMenuId(long weiSiteMenuId){
	 this.weiSiteMenuId = weiSiteMenuId;
	}
	@FieldConfig(fieldName = "weiSiteMenuName", fieldType =FieldTypeEnum.CHAR36 , description = "栏目的名称")
	private String weiSiteMenuName;
	public String getWeiSiteMenuName() {
	return weiSiteMenuName;
	}
 	 public void setWeiSiteMenuName(String weiSiteMenuName){
	 this.weiSiteMenuName = weiSiteMenuName;
	}
	@FieldConfig(fieldName = "weiSiteMenuDesc", fieldType =FieldTypeEnum.CHAR128 , description = "栏目的描述")
	private String weiSiteMenuDesc;
	public String getWeiSiteMenuDesc() {
	return weiSiteMenuDesc;
	}
 	 public void setWeiSiteMenuDesc(String weiSiteMenuDesc){
	 this.weiSiteMenuDesc = weiSiteMenuDesc;
	}
	@FieldConfig(fieldName = "weiSiteMenuImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "栏目的图片背景")
	private String weiSiteMenuImageURL;
	public String getWeiSiteMenuImageURL() {
	return weiSiteMenuImageURL;
	}
 	 public void setWeiSiteMenuImageURL(String weiSiteMenuImageURL){
	 this.weiSiteMenuImageURL = weiSiteMenuImageURL;
	}
	@FieldConfig(fieldName = "weiSiteMenuLinkWebSite", fieldType =FieldTypeEnum.CHAR64 , description = "栏目的链接地址")
	private String weiSiteMenuLinkWebSite;
	public String getWeiSiteMenuLinkWebSite() {
	return weiSiteMenuLinkWebSite;
	}
 	 public void setWeiSiteMenuLinkWebSite(String weiSiteMenuLinkWebSite){
	 this.weiSiteMenuLinkWebSite = weiSiteMenuLinkWebSite;
	}
	@FieldConfig(fieldName = "weiSiteMenuParentId", fieldType =FieldTypeEnum.BIG_INT , description = "栏目的父级id")
	private long weiSiteMenuParentId;
	public long getWeiSiteMenuParentId() {
	return weiSiteMenuParentId;
	}
 	 public void setWeiSiteMenuParentId(long weiSiteMenuParentId){
	 this.weiSiteMenuParentId = weiSiteMenuParentId;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "resourcesId", fieldType =FieldTypeEnum.BIG_INT , description = "资源id")
	private long resourcesId;
	public long getResourcesId() {
	return resourcesId;
	}
 	 public void setResourcesId(long resourcesId){
	 this.resourcesId = resourcesId;
	}
	@FieldConfig(fieldName = "weiSiteConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "微网站的配置id")
	private long weiSiteConfigId;
	public long getWeiSiteConfigId() {
	return weiSiteConfigId;
	}
 	 public void setWeiSiteConfigId(long weiSiteConfigId){
	 this.weiSiteConfigId = weiSiteConfigId;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("weiSiteMenuId", String.valueOf(this.weiSiteMenuId));
	 entityMap.put("weiSiteMenuName", this.weiSiteMenuName);
	 entityMap.put("weiSiteMenuDesc", this.weiSiteMenuDesc);
	 entityMap.put("weiSiteMenuImageURL", this.weiSiteMenuImageURL);
	 entityMap.put("weiSiteMenuLinkWebSite", this.weiSiteMenuLinkWebSite);
	 entityMap.put("weiSiteMenuParentId", String.valueOf(this.weiSiteMenuParentId));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("resourcesId", String.valueOf(this.resourcesId));
	 entityMap.put("weiSiteConfigId", String.valueOf(this.weiSiteConfigId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.weiSiteMenuId=Long.parseLong(entityMap.get("weiSiteMenuId"));
	 this.weiSiteMenuName=entityMap.get("weiSiteMenuName");
	 this.weiSiteMenuDesc=entityMap.get("weiSiteMenuDesc");
	 this.weiSiteMenuImageURL=entityMap.get("weiSiteMenuImageURL");
	 this.weiSiteMenuLinkWebSite=entityMap.get("weiSiteMenuLinkWebSite");
	 this.weiSiteMenuParentId=Long.parseLong(entityMap.get("weiSiteMenuParentId"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.resourcesId=Long.parseLong(entityMap.get("resourcesId"));
	 this.weiSiteConfigId=Long.parseLong(entityMap.get("weiSiteConfigId"));

    }
}
