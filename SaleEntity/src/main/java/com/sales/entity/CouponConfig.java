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
    entityName = EntityNames.couponConfig, 
    keyFields = {"couponConfigId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class CouponConfig extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("couponConfigId", String.valueOf(this.couponConfigId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "couponConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "优惠券配置id")
	private long couponConfigId;
	public long getCouponConfigId() {
	return couponConfigId;
	}
 	 public void setCouponConfigId(long couponConfigId){
	 this.couponConfigId = couponConfigId;
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
	@FieldConfig(fieldName = "messageTitle", fieldType =FieldTypeEnum.CHAR36 , description = "消息标题")
	private String messageTitle;
	public String getMessageTitle() {
	return messageTitle;
	}
 	 public void setMessageTitle(String messageTitle){
	 this.messageTitle = messageTitle;
	}
	@FieldConfig(fieldName = "messageImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "图片地址")
	private String messageImageURL;
	public String getMessageImageURL() {
	return messageImageURL;
	}
 	 public void setMessageImageURL(String messageImageURL){
	 this.messageImageURL = messageImageURL;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "穿见时间")
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
	@FieldConfig(fieldName = "couponNum", fieldType =FieldTypeEnum.BIG_INT , description = "优惠券设置的数量")
	private long couponNum;
	public long getCouponNum() {
	return couponNum;
	}
 	 public void setCouponNum(long couponNum){
	 this.couponNum = couponNum;
	}
	@FieldConfig(fieldName = "couponCurrentNum", fieldType =FieldTypeEnum.BIG_INT , description = "优惠券剩下的数量")
	private long couponCurrentNum;
	public long getCouponCurrentNum() {
	return couponCurrentNum;
	}
 	 public void setCouponCurrentNum(long couponCurrentNum){
	 this.couponCurrentNum = couponCurrentNum;
	}
	@FieldConfig(fieldName = "effectiveStartTime", fieldType =FieldTypeEnum.DATETIME , description = "生效开始时间")
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
	@FieldConfig(fieldName = "couponName", fieldType =FieldTypeEnum.CHAR36 , description = "优惠券名称")
	private String couponName;
	public String getCouponName() {
	return couponName;
	}
 	 public void setCouponName(String couponName){
	 this.couponName = couponName;
	}
	@FieldConfig(fieldName = "couponDesc", fieldType =FieldTypeEnum.CHAR128 , description = "优惠券的说明")
	private String couponDesc;
	public String getCouponDesc() {
	return couponDesc;
	}
 	 public void setCouponDesc(String couponDesc){
	 this.couponDesc = couponDesc;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("couponConfigId", String.valueOf(this.couponConfigId));
	 entityMap.put("keyword", this.keyword);
	 entityMap.put("matchType", String.valueOf(this.matchType));
	 entityMap.put("messageTitle", this.messageTitle);
	 entityMap.put("messageImageURL", this.messageImageURL);
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("couponNum", String.valueOf(this.couponNum));
	 entityMap.put("couponCurrentNum", String.valueOf(this.couponCurrentNum));
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
	 entityMap.put("couponName", this.couponName);
	 entityMap.put("couponDesc", this.couponDesc);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.couponConfigId=Long.parseLong(entityMap.get("couponConfigId"));
	 this.keyword=entityMap.get("keyword");
	 this.matchType=Byte.parseByte(entityMap.get("matchType"));
	 this.messageTitle=entityMap.get("messageTitle");
	 this.messageImageURL=entityMap.get("messageImageURL");
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.couponNum=Long.parseLong(entityMap.get("couponNum"));
	 this.couponCurrentNum=Long.parseLong(entityMap.get("couponCurrentNum"));
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
	 this.couponName=entityMap.get("couponName");
	 this.couponDesc=entityMap.get("couponDesc");

    }
}
