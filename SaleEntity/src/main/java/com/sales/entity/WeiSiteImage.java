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
    entityName = EntityNames.weiSiteImage, 
    keyFields = {"weiSiteImageId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class WeiSiteImage extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("weiSiteImageId", String.valueOf(this.weiSiteImageId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "weiSiteImageId", fieldType =FieldTypeEnum.BIG_INT , description = "微站图片id")
	private long weiSiteImageId;
	public long getWeiSiteImageId() {
	return weiSiteImageId;
	}
 	 public void setWeiSiteImageId(long weiSiteImageId){
	 this.weiSiteImageId = weiSiteImageId;
	}
	@FieldConfig(fieldName = "weiSiteImageName", fieldType =FieldTypeEnum.CHAR36 , description = "图片名称")
	private String weiSiteImageName;
	public String getWeiSiteImageName() {
	return weiSiteImageName;
	}
 	 public void setWeiSiteImageName(String weiSiteImageName){
	 this.weiSiteImageName = weiSiteImageName;
	}
	@FieldConfig(fieldName = "weiSiteImageDesc", fieldType =FieldTypeEnum.CHAR128 , description = "图片描述")
	private String weiSiteImageDesc;
	public String getWeiSiteImageDesc() {
	return weiSiteImageDesc;
	}
 	 public void setWeiSiteImageDesc(String weiSiteImageDesc){
	 this.weiSiteImageDesc = weiSiteImageDesc;
	}
	@FieldConfig(fieldName = "weiSiteImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "图片的地址")
	private String weiSiteImageURL;
	public String getWeiSiteImageURL() {
	return weiSiteImageURL;
	}
 	 public void setWeiSiteImageURL(String weiSiteImageURL){
	 this.weiSiteImageURL = weiSiteImageURL;
	}
	@FieldConfig(fieldName = "weiSiteImageType", fieldType =FieldTypeEnum.TYINT , description = "图片的类型")
	private byte weiSiteImageType;
	public byte getWeiSiteImageType() {
	return weiSiteImageType;
	}
 	 public void setWeiSiteImageType(byte weiSiteImageType){
	 this.weiSiteImageType = weiSiteImageType;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "bindId", fieldType =FieldTypeEnum.BIG_INT , description = "绑定的id")
	private long bindId;
	public long getBindId() {
	return bindId;
	}
 	 public void setBindId(long bindId){
	 this.bindId = bindId;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("weiSiteImageId", String.valueOf(this.weiSiteImageId));
	 entityMap.put("weiSiteImageName", this.weiSiteImageName);
	 entityMap.put("weiSiteImageDesc", this.weiSiteImageDesc);
	 entityMap.put("weiSiteImageURL", this.weiSiteImageURL);
	 entityMap.put("weiSiteImageType", String.valueOf(this.weiSiteImageType));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("bindId", String.valueOf(this.bindId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.weiSiteImageId=Long.parseLong(entityMap.get("weiSiteImageId"));
	 this.weiSiteImageName=entityMap.get("weiSiteImageName");
	 this.weiSiteImageDesc=entityMap.get("weiSiteImageDesc");
	 this.weiSiteImageURL=entityMap.get("weiSiteImageURL");
	 this.weiSiteImageType=Byte.parseByte(entityMap.get("weiSiteImageType"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.bindId=Long.parseLong(entityMap.get("bindId"));

    }
}
