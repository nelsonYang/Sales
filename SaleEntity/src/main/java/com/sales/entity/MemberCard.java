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
    entityName = EntityNames.memberCard, 
    keyFields = {"memberCardId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class MemberCard extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("memberCardId", String.valueOf(this.memberCardId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "memberCardId", fieldType =FieldTypeEnum.BIG_INT , description = "会员卡id")
	private long memberCardId;
	public long getMemberCardId() {
	return memberCardId;
	}
 	 public void setMemberCardId(long memberCardId){
	 this.memberCardId = memberCardId;
	}
	@FieldConfig(fieldName = "memberLogoURL", fieldType =FieldTypeEnum.CHAR64 , description = "会员的logo图")
	private String memberLogoURL;
	public String getMemberLogoURL() {
	return memberLogoURL;
	}
 	 public void setMemberLogoURL(String memberLogoURL){
	 this.memberLogoURL = memberLogoURL;
	}
	@FieldConfig(fieldName = "memberCardURL", fieldType =FieldTypeEnum.CHAR64 , description = "会员卡url")
	private String memberCardURL;
	public String getMemberCardURL() {
	return memberCardURL;
	}
 	 public void setMemberCardURL(String memberCardURL){
	 this.memberCardURL = memberCardURL;
	}
	@FieldConfig(fieldName = "memberCardName", fieldType =FieldTypeEnum.CHAR36 , description = "会员开名称")
	private String memberCardName;
	public String getMemberCardName() {
	return memberCardName;
	}
 	 public void setMemberCardName(String memberCardName){
	 this.memberCardName = memberCardName;
	}
	@FieldConfig(fieldName = "memberCardDesc", fieldType =FieldTypeEnum.CHAR128 , description = "会员卡描述")
	private String memberCardDesc;
	public String getMemberCardDesc() {
	return memberCardDesc;
	}
 	 public void setMemberCardDesc(String memberCardDesc){
	 this.memberCardDesc = memberCardDesc;
	}
	@FieldConfig(fieldName = "effectiveStartTime", fieldType =FieldTypeEnum.DATETIME , description = "会员卡生效开始时间")
	private Date effectiveStartTime;
	public Date getEffectiveStartTime() {
	return effectiveStartTime;
	}
 	 public void setEffectiveStartTime(Date effectiveStartTime){
	 this.effectiveStartTime = effectiveStartTime;
	}
	@FieldConfig(fieldName = "effectiveEndTime", fieldType =FieldTypeEnum.DATETIME , description = "生效的结束时间")
	private Date effectiveEndTime;
	public Date getEffectiveEndTime() {
	return effectiveEndTime;
	}
 	 public void setEffectiveEndTime(Date effectiveEndTime){
	 this.effectiveEndTime = effectiveEndTime;
	}
	@FieldConfig(fieldName = "readCount", fieldType =FieldTypeEnum.INT , description = "被浏览的次数")
	private int readCount;
	public int getReadCount() {
	return readCount;
	}
 	 public void setReadCount(int readCount){
	 this.readCount = readCount;
	}
	@FieldConfig(fieldName = "clickCount", fieldType =FieldTypeEnum.INT , description = "被点击的次数")
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
	@FieldConfig(fieldName = "balance", fieldType =FieldTypeEnum.INT , description = "积分")
	private int balance;
	public int getBalance() {
	return balance;
	}
 	 public void setBalance(int balance){
	 this.balance = balance;
	}
	@FieldConfig(fieldName = "consumeMoney", fieldType =FieldTypeEnum.INT , description = "消费金额")
	private int consumeMoney;
	public int getConsumeMoney() {
	return consumeMoney;
	}
 	 public void setConsumeMoney(int consumeMoney){
	 this.consumeMoney = consumeMoney;
	}
	@FieldConfig(fieldName = "memberCardConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "会员卡的配置")
	private long memberCardConfigId;
	public long getMemberCardConfigId() {
	return memberCardConfigId;
	}
 	 public void setMemberCardConfigId(long memberCardConfigId){
	 this.memberCardConfigId = memberCardConfigId;
	}
	@FieldConfig(fieldName = "status", fieldType =FieldTypeEnum.TYINT , description = "会员卡状态1-有效0-无效")
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
	@FieldConfig(fieldName = "memberCardPrivileges", fieldType =FieldTypeEnum.CHAR128 , description = "会员卡特权")
	private String memberCardPrivileges;
	public String getMemberCardPrivileges() {
	return memberCardPrivileges;
	}
 	 public void setMemberCardPrivileges(String memberCardPrivileges){
	 this.memberCardPrivileges = memberCardPrivileges;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("memberCardId", String.valueOf(this.memberCardId));
	 entityMap.put("memberLogoURL", this.memberLogoURL);
	 entityMap.put("memberCardURL", this.memberCardURL);
	 entityMap.put("memberCardName", this.memberCardName);
	 entityMap.put("memberCardDesc", this.memberCardDesc);
	try{
	 entityMap.put("effectiveStartTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(effectiveStartTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("effectiveStartTime","");
	}
	try{
	 entityMap.put("effectiveEndTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(effectiveEndTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("effectiveEndTime","");
	}
	 entityMap.put("readCount", String.valueOf(this.readCount));
	 entityMap.put("clickCount", String.valueOf(this.clickCount));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("balance", String.valueOf(this.balance));
	 entityMap.put("consumeMoney", String.valueOf(this.consumeMoney));
	 entityMap.put("memberCardConfigId", String.valueOf(this.memberCardConfigId));
	 entityMap.put("status", String.valueOf(this.status));
	 entityMap.put("keyword", this.keyword);
	 entityMap.put("memberCardPrivileges", this.memberCardPrivileges);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.memberCardId=Long.parseLong(entityMap.get("memberCardId"));
	 this.memberLogoURL=entityMap.get("memberLogoURL");
	 this.memberCardURL=entityMap.get("memberCardURL");
	 this.memberCardName=entityMap.get("memberCardName");
	 this.memberCardDesc=entityMap.get("memberCardDesc");
	try{
	 this.effectiveStartTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("effectiveStartTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("effectiveStartTime","");
	}
	try{
	 this.effectiveEndTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("effectiveEndTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("effectiveEndTime","");
	}
	 this.readCount=Integer.parseInt(entityMap.get("readCount"));
	 this.clickCount=Integer.parseInt(entityMap.get("clickCount"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.balance=Integer.parseInt(entityMap.get("balance"));
	 this.consumeMoney=Integer.parseInt(entityMap.get("consumeMoney"));
	 this.memberCardConfigId=Long.parseLong(entityMap.get("memberCardConfigId"));
	 this.status=Byte.parseByte(entityMap.get("status"));
	 this.keyword=entityMap.get("keyword");
	 this.memberCardPrivileges=entityMap.get("memberCardPrivileges");

    }
}
