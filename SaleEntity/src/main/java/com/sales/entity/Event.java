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
    entityName = EntityNames.event, 
    keyFields = {"eventId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class Event extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("eventId", String.valueOf(this.eventId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "eventId", fieldType =FieldTypeEnum.INT , description = "活动id")
	private int eventId;
	public int getEventId() {
	return eventId;
	}
 	 public void setEventId(int eventId){
	 this.eventId = eventId;
	}
	@FieldConfig(fieldName = "eventName", fieldType =FieldTypeEnum.CHAR36 , description = "活动名称")
	private String eventName;
	public String getEventName() {
	return eventName;
	}
 	 public void setEventName(String eventName){
	 this.eventName = eventName;
	}
	@FieldConfig(fieldName = "eventDesc", fieldType =FieldTypeEnum.CHAR128 , description = "活动描述")
	private String eventDesc;
	public String getEventDesc() {
	return eventDesc;
	}
 	 public void setEventDesc(String eventDesc){
	 this.eventDesc = eventDesc;
	}
	@FieldConfig(fieldName = "eventStartURL", fieldType =FieldTypeEnum.CHAR64 , description = "活动开始图片")
	private String eventStartURL;
	public String getEventStartURL() {
	return eventStartURL;
	}
 	 public void setEventStartURL(String eventStartURL){
	 this.eventStartURL = eventStartURL;
	}
	@FieldConfig(fieldName = "eventEndImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "活动结束图片")
	private String eventEndImageURL;
	public String getEventEndImageURL() {
	return eventEndImageURL;
	}
 	 public void setEventEndImageURL(String eventEndImageURL){
	 this.eventEndImageURL = eventEndImageURL;
	}
	@FieldConfig(fieldName = "eventEffectiveStartTime", fieldType =FieldTypeEnum.DATETIME , description = "活动生效的开始时间")
	private Date eventEffectiveStartTime;
	public Date getEventEffectiveStartTime() {
	return eventEffectiveStartTime;
	}
 	 public void setEventEffectiveStartTime(Date eventEffectiveStartTime){
	 this.eventEffectiveStartTime = eventEffectiveStartTime;
	}
	@FieldConfig(fieldName = "eventEffectiveEndTime", fieldType =FieldTypeEnum.DATETIME , description = "活动生效的结束时间")
	private Date eventEffectiveEndTime;
	public Date getEventEffectiveEndTime() {
	return eventEffectiveEndTime;
	}
 	 public void setEventEffectiveEndTime(Date eventEffectiveEndTime){
	 this.eventEffectiveEndTime = eventEffectiveEndTime;
	}
	@FieldConfig(fieldName = "type", fieldType =FieldTypeEnum.TYINT , description = "活动类型 1-瓜瓜卡 2-水果达人 3-欢乐打转盘")
	private byte type;
	public byte getType() {
	return type;
	}
 	 public void setType(byte type){
	 this.type = type;
	}
	@FieldConfig(fieldName = "readCount", fieldType =FieldTypeEnum.INT , description = "活动被浏览次数")
	private int readCount;
	public int getReadCount() {
	return readCount;
	}
 	 public void setReadCount(int readCount){
	 this.readCount = readCount;
	}
	@FieldConfig(fieldName = "clickCount", fieldType =FieldTypeEnum.INT , description = "活动被点击的次数")
	private int clickCount;
	public int getClickCount() {
	return clickCount;
	}
 	 public void setClickCount(int clickCount){
	 this.clickCount = clickCount;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "status", fieldType =FieldTypeEnum.TYINT , description = "状态")
	private byte status;
	public byte getStatus() {
	return status;
	}
 	 public void setStatus(byte status){
	 this.status = status;
	}
	@FieldConfig(fieldName = "keyword", fieldType =FieldTypeEnum.CHAR36 , description = "关键字")
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
	@FieldConfig(fieldName = "isRemindOpen", fieldType =FieldTypeEnum.TYINT , description = "是否开启手机通知")
	private byte isRemindOpen;
	public byte getIsRemindOpen() {
	return isRemindOpen;
	}
 	 public void setIsRemindOpen(byte isRemindOpen){
	 this.isRemindOpen = isRemindOpen;
	}
	@FieldConfig(fieldName = "eventNum", fieldType =FieldTypeEnum.BIG_INT , description = "活动的数量")
	private long eventNum;
	public long getEventNum() {
	return eventNum;
	}
 	 public void setEventNum(long eventNum){
	 this.eventNum = eventNum;
	}
	@FieldConfig(fieldName = "eventCurrentNum", fieldType =FieldTypeEnum.BIG_INT , description = "活动当前剩下的数量")
	private long eventCurrentNum;
	public long getEventCurrentNum() {
	return eventCurrentNum;
	}
 	 public void setEventCurrentNum(long eventCurrentNum){
	 this.eventCurrentNum = eventCurrentNum;
	}
	@FieldConfig(fieldName = "eventConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "活动配置id")
	private long eventConfigId;
	public long getEventConfigId() {
	return eventConfigId;
	}
 	 public void setEventConfigId(long eventConfigId){
	 this.eventConfigId = eventConfigId;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("eventId", String.valueOf(this.eventId));
	 entityMap.put("eventName", this.eventName);
	 entityMap.put("eventDesc", this.eventDesc);
	 entityMap.put("eventStartURL", this.eventStartURL);
	 entityMap.put("eventEndImageURL", this.eventEndImageURL);
	try{
	 entityMap.put("eventEffectiveStartTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(eventEffectiveStartTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("eventEffectiveStartTime","");
	}
	try{
	 entityMap.put("eventEffectiveEndTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(eventEffectiveEndTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("eventEffectiveEndTime","");
	}
	 entityMap.put("type", String.valueOf(this.type));
	 entityMap.put("readCount", String.valueOf(this.readCount));
	 entityMap.put("clickCount", String.valueOf(this.clickCount));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("status", String.valueOf(this.status));
	 entityMap.put("keyword", this.keyword);
	 entityMap.put("matchType", String.valueOf(this.matchType));
	 entityMap.put("isRemindOpen", String.valueOf(this.isRemindOpen));
	 entityMap.put("eventNum", String.valueOf(this.eventNum));
	 entityMap.put("eventCurrentNum", String.valueOf(this.eventCurrentNum));
	 entityMap.put("eventConfigId", String.valueOf(this.eventConfigId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.eventId=Integer.parseInt(entityMap.get("eventId"));
	 this.eventName=entityMap.get("eventName");
	 this.eventDesc=entityMap.get("eventDesc");
	 this.eventStartURL=entityMap.get("eventStartURL");
	 this.eventEndImageURL=entityMap.get("eventEndImageURL");
	try{
	 this.eventEffectiveStartTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("eventEffectiveStartTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("eventEffectiveStartTime","");
	}
	try{
	 this.eventEffectiveEndTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("eventEffectiveEndTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("eventEffectiveEndTime","");
	}
	 this.type=Byte.parseByte(entityMap.get("type"));
	 this.readCount=Integer.parseInt(entityMap.get("readCount"));
	 this.clickCount=Integer.parseInt(entityMap.get("clickCount"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.status=Byte.parseByte(entityMap.get("status"));
	 this.keyword=entityMap.get("keyword");
	 this.matchType=Byte.parseByte(entityMap.get("matchType"));
	 this.isRemindOpen=Byte.parseByte(entityMap.get("isRemindOpen"));
	 this.eventNum=Long.parseLong(entityMap.get("eventNum"));
	 this.eventCurrentNum=Long.parseLong(entityMap.get("eventCurrentNum"));
	 this.eventConfigId=Long.parseLong(entityMap.get("eventConfigId"));

    }
}
