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
    entityName = EntityNames.resources, 
    keyFields = {"resourcesId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class Resources extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("resourcesId", String.valueOf(this.resourcesId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "resourcesId", fieldType =FieldTypeEnum.BIG_INT , description = "资源id")
	private long resourcesId;
	public long getResourcesId() {
	return resourcesId;
	}
 	 public void setResourcesId(long resourcesId){
	 this.resourcesId = resourcesId;
	}
	@FieldConfig(fieldName = "resourcesType", fieldType =FieldTypeEnum.TYINT , description = "资源类型1-文字2-单图文 3-多图文4-自定义url5-系统url6微官网url")
	private byte resourcesType;
	public byte getResourcesType() {
	return resourcesType;
	}
 	 public void setResourcesType(byte resourcesType){
	 this.resourcesType = resourcesType;
	}
	@FieldConfig(fieldName = "resourcesName", fieldType =FieldTypeEnum.CHAR36 , description = "资源名称")
	private String resourcesName;
	public String getResourcesName() {
	return resourcesName;
	}
 	 public void setResourcesName(String resourcesName){
	 this.resourcesName = resourcesName;
	}
	@FieldConfig(fieldName = "resourcesURL", fieldType =FieldTypeEnum.CHAR36 , description = "资源URL")
	private String resourcesURL;
	public String getResourcesURL() {
	return resourcesURL;
	}
 	 public void setResourcesURL(String resourcesURL){
	 this.resourcesURL = resourcesURL;
	}
	@FieldConfig(fieldName = "resourceContent", fieldType =FieldTypeEnum.CHAR128 , description = "资源内容")
	private String resourceContent;
	public String getResourceContent() {
	return resourceContent;
	}
 	 public void setResourceContent(String resourceContent){
	 this.resourceContent = resourceContent;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "创建时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
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
	 entityMap.put("resourcesId", String.valueOf(this.resourcesId));
	 entityMap.put("resourcesType", String.valueOf(this.resourcesType));
	 entityMap.put("resourcesName", this.resourcesName);
	 entityMap.put("resourcesURL", this.resourcesURL);
	 entityMap.put("resourceContent", this.resourceContent);
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.resourcesId=Long.parseLong(entityMap.get("resourcesId"));
	 this.resourcesType=Byte.parseByte(entityMap.get("resourcesType"));
	 this.resourcesName=entityMap.get("resourcesName");
	 this.resourcesURL=entityMap.get("resourcesURL");
	 this.resourceContent=entityMap.get("resourceContent");
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
