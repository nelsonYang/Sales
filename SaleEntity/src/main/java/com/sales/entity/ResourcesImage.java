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
    entityName = EntityNames.resourcesImage, 
    keyFields = {"resourcesImageId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class ResourcesImage extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("resourcesImageId", String.valueOf(this.resourcesImageId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "resourcesImageId", fieldType =FieldTypeEnum.BIG_INT , description = "资源图片id")
	private long resourcesImageId;
	public long getResourcesImageId() {
	return resourcesImageId;
	}
 	 public void setResourcesImageId(long resourcesImageId){
	 this.resourcesImageId = resourcesImageId;
	}
	@FieldConfig(fieldName = "resourcesId", fieldType =FieldTypeEnum.BIG_INT , description = "资源id")
	private long resourcesId;
	public long getResourcesId() {
	return resourcesId;
	}
 	 public void setResourcesId(long resoucesId){
	 this.resourcesId = resoucesId;
	}
	@FieldConfig(fieldName = "resoucesImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "资源图片的url")
	private String resoucesImageURL;
	public String getResoucesImageURL() {
	return resoucesImageURL;
	}
 	 public void setResoucesImageURL(String resoucesImageURL){
	 this.resoucesImageURL = resoucesImageURL;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "创建时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
	}
	@FieldConfig(fieldName = "imageType", fieldType =FieldTypeEnum.TYINT , description = "图片类型")
	private byte imageType;
	public byte getImageType() {
	return imageType;
	}
 	 public void setImageType(byte imageType){
	 this.imageType = imageType;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "resourcesContent", fieldType =FieldTypeEnum.CHAR128 , description = "标题的描述内容")
	private String resourcesContent;
	public String getResourcesContent() {
	return resourcesContent;
	}
 	 public void setResourcesContent(String resourcesContent){
	 this.resourcesContent = resourcesContent;
	}
	@FieldConfig(fieldName = "linkSite", fieldType =FieldTypeEnum.CHAR64 , description = "链接地址")
	private String linkSite;
	public String getLinkSite() {
	return linkSite;
	}
 	 public void setLinkSite(String linkSite){
	 this.linkSite = linkSite;
	}
	@FieldConfig(fieldName = "resourcesTitle", fieldType =FieldTypeEnum.CHAR36 , description = "资源的标题")
	private String resourcesTitle;
	public String getResourcesTitle() {
	return resourcesTitle;
	}
 	 public void setResourcesTitle(String resourcesTitle){
	 this.resourcesTitle = resourcesTitle;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("resourcesImageId", String.valueOf(this.resourcesImageId));
	 entityMap.put("resourcesId", String.valueOf(this.resourcesId));
	 entityMap.put("resoucesImageURL", this.resoucesImageURL);
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("imageType", String.valueOf(this.imageType));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("resourcesContent", this.resourcesContent);
	 entityMap.put("linkSite", this.linkSite);
	 entityMap.put("resourcesTitle", this.resourcesTitle);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.resourcesImageId=Long.parseLong(entityMap.get("resourcesImageId"));
	 this.resourcesId=Long.parseLong(entityMap.get("resourcesId"));
	 this.resoucesImageURL=entityMap.get("resoucesImageURL");

	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.imageType=Byte.parseByte(entityMap.get("imageType"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.resourcesContent=entityMap.get("resourcesContent");
	 this.linkSite=entityMap.get("linkSite");
	 this.resourcesTitle=entityMap.get("resourcesTitle");

    }
}
