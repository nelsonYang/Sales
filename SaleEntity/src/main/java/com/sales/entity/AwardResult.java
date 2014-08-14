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
    entityName = EntityNames.awardResult, 
    keyFields = {"awardResultId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class AwardResult extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("awardResultId", String.valueOf(this.awardResultId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "awardResultId", fieldType =FieldTypeEnum.BIG_INT , description = "中奖结果主键id")
	private long awardResultId;
	public long getAwardResultId() {
	return awardResultId;
	}
 	 public void setAwardResultId(long awardResultId){
	 this.awardResultId = awardResultId;
	}
	@FieldConfig(fieldName = "awardSN", fieldType =FieldTypeEnum.CHAR36 , description = "中奖的序列号")
	private String awardSN;
	public String getAwardSN() {
	return awardSN;
	}
 	 public void setAwardSN(String awardSN){
	 this.awardSN = awardSN;
	}
	@FieldConfig(fieldName = "awardWeixinNO", fieldType =FieldTypeEnum.CHAR36 , description = "中奖微信号")
	private String awardWeixinNO;
	public String getAwardWeixinNO() {
	return awardWeixinNO;
	}
 	 public void setAwardWeixinNO(String awardWeixinNO){
	 this.awardWeixinNO = awardWeixinNO;
	}
	@FieldConfig(fieldName = "awardMobileNO", fieldType =FieldTypeEnum.CHAR11 , description = "中奖手机号")
	private String awardMobileNO;
	public String getAwardMobileNO() {
	return awardMobileNO;
	}
 	 public void setAwardMobileNO(String awardMobileNO){
	 this.awardMobileNO = awardMobileNO;
	}
	@FieldConfig(fieldName = "awardTime", fieldType =FieldTypeEnum.DATETIME , description = "中奖的时间")
	private Date awardTime;
	public Date getAwardTime() {
	return awardTime;
	}
 	 public void setAwardTime(Date awardTime){
	 this.awardTime = awardTime;
	}
	@FieldConfig(fieldName = "awardNum", fieldType =FieldTypeEnum.INT , description = "中奖的数量")
	private int awardNum;
	public int getAwardNum() {
	return awardNum;
	}
 	 public void setAwardNum(int awardNum){
	 this.awardNum = awardNum;
	}
	@FieldConfig(fieldName = "awardStatus", fieldType =FieldTypeEnum.TYINT , description = "中奖的处理状态")
	private byte awardStatus;
	public byte getAwardStatus() {
	return awardStatus;
	}
 	 public void setAwardStatus(byte awardStatus){
	 this.awardStatus = awardStatus;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "operator", fieldType =FieldTypeEnum.BIG_INT , description = "操作人")
	private long operator;
	public long getOperator() {
	return operator;
	}
 	 public void setOperator(long operator){
	 this.operator = operator;
	}
	@FieldConfig(fieldName = "settleTime", fieldType =FieldTypeEnum.DATETIME , description = "处理时间")
	private Date settleTime;
	public Date getSettleTime() {
	return settleTime;
	}
 	 public void setSettleTime(Date settleTime){
	 this.settleTime = settleTime;
	}
	@FieldConfig(fieldName = "awardId", fieldType =FieldTypeEnum.BIG_INT , description = "中奖所属的奖项")
	private long awardId;
	public long getAwardId() {
	return awardId;
	}
 	 public void setAwardId(long awardId){
	 this.awardId = awardId;
	}
	@FieldConfig(fieldName = "eventId", fieldType =FieldTypeEnum.BIG_INT , description = "活动id")
	private long eventId;
	public long getEventId() {
	return eventId;
	}
 	 public void setEventId(long eventId){
	 this.eventId = eventId;
	}
	@FieldConfig(fieldName = "weixinId", fieldType =FieldTypeEnum.CHAR36 , description = "微信id")
	private String weixinId;
	public String getWeixinId() {
	return weixinId;
	}
 	 public void setWeixinId(String weixinId){
	 this.weixinId = weixinId;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("awardResultId", String.valueOf(this.awardResultId));
	 entityMap.put("awardSN", this.awardSN);
	 entityMap.put("awardWeixinNO", this.awardWeixinNO);
	 entityMap.put("awardMobileNO", this.awardMobileNO);
	try{
	 entityMap.put("awardTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(awardTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("awardTime","");
	}
	 entityMap.put("awardNum", String.valueOf(this.awardNum));
	 entityMap.put("awardStatus", String.valueOf(this.awardStatus));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("operator", String.valueOf(this.operator));
	try{
	 entityMap.put("settleTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(settleTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("settleTime","");
	}
	 entityMap.put("awardId", String.valueOf(this.awardId));
	 entityMap.put("eventId", String.valueOf(this.eventId));
	 entityMap.put("weixinId", this.weixinId);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.awardResultId=Long.parseLong(entityMap.get("awardResultId"));
	 this.awardSN=entityMap.get("awardSN");
	 this.awardWeixinNO=entityMap.get("awardWeixinNO");
	 this.awardMobileNO=entityMap.get("awardMobileNO");
	try{
	 this.awardTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("awardTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("awardTime","");
	}
	 this.awardNum=Integer.parseInt(entityMap.get("awardNum"));
	 this.awardStatus=Byte.parseByte(entityMap.get("awardStatus"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.operator=Long.parseLong(entityMap.get("operator"));
	try{
	 this.settleTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("settleTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("settleTime","");
	}
	 this.awardId=Long.parseLong(entityMap.get("awardId"));
	 this.eventId=Long.parseLong(entityMap.get("eventId"));
	 this.weixinId=entityMap.get("weixinId");

    }
}
