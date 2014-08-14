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
    entityName = EntityNames.merchantConfig, 
    keyFields = {"merchantConfigId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class MerchantConfig extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("merchantConfigId", String.valueOf(this.merchantConfigId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "merchantConfigId", fieldType =FieldTypeEnum.TYINT , description = "门店id")
	private byte merchantConfigId;
	public byte getMerchantConfigId() {
	return merchantConfigId;
	}
 	 public void setMerchantConfigId(byte merchantConfigId){
	 this.merchantConfigId = merchantConfigId;
	}
	@FieldConfig(fieldName = "merchantName", fieldType =FieldTypeEnum.CHAR36 , description = "门店名称")
	private String merchantName;
	public String getMerchantName() {
	return merchantName;
	}
 	 public void setMerchantName(String merchantName){
	 this.merchantName = merchantName;
	}
	@FieldConfig(fieldName = "keyword", fieldType =FieldTypeEnum.CHAR36 , description = "关键子")
	private String keyword;
	public String getKeyword() {
	return keyword;
	}
 	 public void setKeyword(String keyword){
	 this.keyword = keyword;
	}
	@FieldConfig(fieldName = "matchType", fieldType =FieldTypeEnum.TYINT , description = "匹配模式")
	private byte matchType;
	public byte getMatchType() {
	return matchType;
	}
 	 public void setMatchType(byte matchType){
	 this.matchType = matchType;
	}
	@FieldConfig(fieldName = "messageTitle", fieldType =FieldTypeEnum.CHAR36 , description = "消息标题")
	private String messageTitle;
	public String getMessageTitle() {
	return messageTitle;
	}
 	 public void setMessageTitle(String messageTitle){
	 this.messageTitle = messageTitle;
	}
	@FieldConfig(fieldName = "messageImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "消息图片")
	private String messageImageURL;
	public String getMessageImageURL() {
	return messageImageURL;
	}
 	 public void setMessageImageURL(String messageImageURL){
	 this.messageImageURL = messageImageURL;
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
	 entityMap.put("merchantConfigId", String.valueOf(this.merchantConfigId));
	 entityMap.put("merchantName", this.merchantName);
	 entityMap.put("keyword", this.keyword);
	 entityMap.put("matchType", String.valueOf(this.matchType));
	 entityMap.put("messageTitle", this.messageTitle);
	 entityMap.put("messageImageURL", this.messageImageURL);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.merchantConfigId=Byte.parseByte(entityMap.get("merchantConfigId"));
	 this.merchantName=entityMap.get("merchantName");
	 this.keyword=entityMap.get("keyword");
	 this.matchType=Byte.parseByte(entityMap.get("matchType"));
	 this.messageTitle=entityMap.get("messageTitle");
	 this.messageImageURL=entityMap.get("messageImageURL");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
