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
    entityName = EntityNames.eventConfig, 
    keyFields = {"eventConfigId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class EventConfig extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("eventConfigId", String.valueOf(this.eventConfigId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "eventConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "活动配置id")
	private long eventConfigId;
	public long getEventConfigId() {
	return eventConfigId;
	}
 	 public void setEventConfigId(long eventConfigId){
	 this.eventConfigId = eventConfigId;
	}
	@FieldConfig(fieldName = "keyword", fieldType =FieldTypeEnum.CHAR36 , description = "关键子")
	private String keyword;
	public String getKeyword() {
	return keyword;
	}
 	 public void setKeyword(String keyword){
	 this.keyword = keyword;
	}
	@FieldConfig(fieldName = "matchType", fieldType =FieldTypeEnum.TYINT , description = "匹配类型")
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
	@FieldConfig(fieldName = "isDialOpen", fieldType =FieldTypeEnum.TYINT , description = "是否开启短信通知")
	private byte isDialOpen;
	public byte getIsDialOpen() {
	return isDialOpen;
	}
 	 public void setIsDialOpen(byte isDialOpen){
	 this.isDialOpen = isDialOpen;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "type", fieldType =FieldTypeEnum.TYINT , description = "活动类型1-瓜瓜卡，2-水果达人 3-欢乐转盘")
	private byte type;
	public byte getType() {
	return type;
	}
 	 public void setType(byte type){
	 this.type = type;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("eventConfigId", String.valueOf(this.eventConfigId));
	 entityMap.put("keyword", this.keyword);
	 entityMap.put("matchType", String.valueOf(this.matchType));
	 entityMap.put("messageTitle", this.messageTitle);
	 entityMap.put("messageImageURL", this.messageImageURL);
	 entityMap.put("isDialOpen", String.valueOf(this.isDialOpen));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("type", String.valueOf(this.type));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.eventConfigId=Long.parseLong(entityMap.get("eventConfigId"));
	 this.keyword=entityMap.get("keyword");
	 this.matchType=Byte.parseByte(entityMap.get("matchType"));
	 this.messageTitle=entityMap.get("messageTitle");
	 this.messageImageURL=entityMap.get("messageImageURL");
	 this.isDialOpen=Byte.parseByte(entityMap.get("isDialOpen"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.type=Byte.parseByte(entityMap.get("type"));

    }
}
