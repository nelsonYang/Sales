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
    entityName = EntityNames.weiBarConfig, 
    keyFields = {"weiBarConfigId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class WeiBarConfig extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("weiBarConfigId", String.valueOf(this.weiBarConfigId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "weiBarConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "微吧配置id")
	private long weiBarConfigId;
	public long getWeiBarConfigId() {
	return weiBarConfigId;
	}
 	 public void setWeiBarConfigId(long weiBarConfigId){
	 this.weiBarConfigId = weiBarConfigId;
	}
	@FieldConfig(fieldName = "keyword", fieldType =FieldTypeEnum.CHAR36 , description = "关键字")
	private String keyword;
	public String getKeyword() {
	return keyword;
	}
 	 public void setKeyword(String keyword){
	 this.keyword = keyword;
	}
	@FieldConfig(fieldName = "matchType", fieldType =FieldTypeEnum.TYINT , description = "匹配度1-精确2-模糊")
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
	@FieldConfig(fieldName = "messageImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "图片url")
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
	 entityMap.put("weiBarConfigId", String.valueOf(this.weiBarConfigId));
	 entityMap.put("keyword", this.keyword);
	 entityMap.put("matchType", String.valueOf(this.matchType));
	 entityMap.put("messageTitle", this.messageTitle);
	 entityMap.put("messageImageURL", this.messageImageURL);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.weiBarConfigId=Long.parseLong(entityMap.get("weiBarConfigId"));
	 this.keyword=entityMap.get("keyword");
	 this.matchType=Byte.parseByte(entityMap.get("matchType"));
	 this.messageTitle=entityMap.get("messageTitle");
	 this.messageImageURL=entityMap.get("messageImageURL");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
