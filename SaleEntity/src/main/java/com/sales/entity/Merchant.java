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
    entityName = EntityNames.merchant, 
    keyFields = {"merchantId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class Merchant extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("merchantId", String.valueOf(this.merchantId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "merchantId", fieldType =FieldTypeEnum.BIG_INT , description = "商家id")
	private long merchantId;
	public long getMerchantId() {
	return merchantId;
	}
 	 public void setMerchantId(long merchantId){
	 this.merchantId = merchantId;
	}
	@FieldConfig(fieldName = "merchantName", fieldType =FieldTypeEnum.CHAR36 , description = "门店名字")
	private String merchantName;
	public String getMerchantName() {
	return merchantName;
	}
 	 public void setMerchantName(String merchantName){
	 this.merchantName = merchantName;
	}
	@FieldConfig(fieldName = "lag", fieldType =FieldTypeEnum.DOUBLE , description = "精度")
	private double lag;
	public double getLag() {
	return lag;
	}
 	 public void setLag(double lag){
	 this.lag = lag;
	}
	@FieldConfig(fieldName = "lat", fieldType =FieldTypeEnum.DOUBLE , description = "维度")
	private double lat;
	public double getLat() {
	return lat;
	}
 	 public void setLat(double lat){
	 this.lat = lat;
	}
	@FieldConfig(fieldName = "matchType", fieldType =FieldTypeEnum.TYINT , description = "匹配模式1-精确2-模糊")
	private byte matchType;
	public byte getMatchType() {
	return matchType;
	}
 	 public void setMatchType(byte matchType){
	 this.matchType = matchType;
	}
	@FieldConfig(fieldName = "keyword", fieldType =FieldTypeEnum.CHAR36 , description = "关键字")
	private String keyword;
	public String getKeyword() {
	return keyword;
	}
 	 public void setKeyword(String keyword){
	 this.keyword = keyword;
	}
	@FieldConfig(fieldName = "backgroupImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "背景图片")
	private String backgroupImageURL;
	public String getBackgroupImageURL() {
	return backgroupImageURL;
	}
 	 public void setBackgroupImageURL(String backgroupImageURL){
	 this.backgroupImageURL = backgroupImageURL;
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
	@FieldConfig(fieldName = "telphone", fieldType =FieldTypeEnum.CHAR36 , description = "联系电话")
	private String telphone;
	public String getTelphone() {
	return telphone;
	}
 	 public void setTelphone(String telphone){
	 this.telphone = telphone;
	}
	@FieldConfig(fieldName = "address", fieldType =FieldTypeEnum.CHAR128 , description = "地址")
	private String address;
	public String getAddress() {
	return address;
	}
 	 public void setAddress(String address){
	 this.address = address;
	}
	@FieldConfig(fieldName = "merchantConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "门店配置信息")
	private long merchantConfigId;
	public long getMerchantConfigId() {
	return merchantConfigId;
	}
 	 public void setMerchantConfigId(long merchantConfigId){
	 this.merchantConfigId = merchantConfigId;
	}
	@FieldConfig(fieldName = "status", fieldType =FieldTypeEnum.TYINT , description = "状态1-有效0-无效")
	private byte status;
	public byte getStatus() {
	return status;
	}
 	 public void setStatus(byte status){
	 this.status = status;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("merchantId", String.valueOf(this.merchantId));
	 entityMap.put("merchantName", this.merchantName);
	 entityMap.put("lag", String.valueOf(this.lag));
	 entityMap.put("lat", String.valueOf(this.lat));
	 entityMap.put("matchType", String.valueOf(this.matchType));
	 entityMap.put("keyword", this.keyword);
	 entityMap.put("backgroupImageURL", this.backgroupImageURL);
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("telphone", this.telphone);
	 entityMap.put("address", this.address);
	 entityMap.put("merchantConfigId", String.valueOf(this.merchantConfigId));
	 entityMap.put("status", String.valueOf(this.status));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.merchantId=Long.parseLong(entityMap.get("merchantId"));
	 this.merchantName=entityMap.get("merchantName");
	 this.lag=Double.parseDouble(entityMap.get("lag"));
	 this.lat=Double.parseDouble(entityMap.get("lat"));
	 this.matchType=Byte.parseByte(entityMap.get("matchType"));
	 this.keyword=entityMap.get("keyword");
	 this.backgroupImageURL=entityMap.get("backgroupImageURL");
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.telphone=entityMap.get("telphone");
	 this.address=entityMap.get("address");
	 this.merchantConfigId=Long.parseLong(entityMap.get("merchantConfigId"));
	 this.status=Byte.parseByte(entityMap.get("status"));

    }
}
