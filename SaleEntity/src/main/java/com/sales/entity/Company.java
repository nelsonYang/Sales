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
    entityName = EntityNames.company, 
    keyFields = {"companyId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class Company extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("companyId", String.valueOf(this.companyId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "companyName", fieldType =FieldTypeEnum.CHAR24 , description = "公司名城")
	private String companyName;
	public String getCompanyName() {
	return companyName;
	}
 	 public void setCompanyName(String companyName){
	 this.companyName = companyName;
	}
	@FieldConfig(fieldName = "password", fieldType =FieldTypeEnum.CHAR36 , description = "密码")
	private String password;
	public String getPassword() {
	return password;
	}
 	 public void setPassword(String password){
	 this.password = password;
	}
	@FieldConfig(fieldName = "linkMobile", fieldType =FieldTypeEnum.CHAR11 , description = "联系人号码")
	private String linkMobile;
	public String getLinkMobile() {
	return linkMobile;
	}
 	 public void setLinkMobile(String linkMobile){
	 this.linkMobile = linkMobile;
	}
	@FieldConfig(fieldName = "qqNO", fieldType =FieldTypeEnum.CHAR11 , description = "QQ号")
	private String qqNO;
	public String getQqNO() {
	return qqNO;
	}
 	 public void setQqNO(String qqNO){
	 this.qqNO = qqNO;
	}
	@FieldConfig(fieldName = "email", fieldType =FieldTypeEnum.CHAR24 , description = "邮箱")
	private String email;
	public String getEmail() {
	return email;
	}
 	 public void setEmail(String email){
	 this.email = email;
	}
	@FieldConfig(fieldName = "province", fieldType =FieldTypeEnum.INT , description = "省")
	private int province;
	public int getProvince() {
	return province;
	}
 	 public void setProvince(int province){
	 this.province = province;
	}
	@FieldConfig(fieldName = "city", fieldType =FieldTypeEnum.INT , description = "市")
	private int city;
	public int getCity() {
	return city;
	}
 	 public void setCity(int city){
	 this.city = city;
	}
	@FieldConfig(fieldName = "region", fieldType =FieldTypeEnum.INT , description = "区")
	private int region;
	public int getRegion() {
	return region;
	}
 	 public void setRegion(int region){
	 this.region = region;
	}
	@FieldConfig(fieldName = "serviceStartTime", fieldType =FieldTypeEnum.DATETIME , description = "服务开始时间")
	private Date serviceStartTime;
	public Date getServiceStartTime() {
	return serviceStartTime;
	}
 	 public void setServiceStartTime(Date serviceStartTime){
	 this.serviceStartTime = serviceStartTime;
	}
	@FieldConfig(fieldName = "serviceEndTime", fieldType =FieldTypeEnum.DATETIME , description = "服务结束时间")
	private Date serviceEndTime;
	public Date getServiceEndTime() {
	return serviceEndTime;
	}
 	 public void setServiceEndTime(Date serviceEndTime){
	 this.serviceEndTime = serviceEndTime;
	}
	@FieldConfig(fieldName = "logoURL", fieldType =FieldTypeEnum.CHAR64 , description = "公司logo")
	private String logoURL;
	public String getLogoURL() {
	return logoURL;
	}
 	 public void setLogoURL(String logoURL){
	 this.logoURL = logoURL;
	}
	@FieldConfig(fieldName = "companySite", fieldType =FieldTypeEnum.CHAR64 , description = "公司网站")
	private String companySite;
	public String getCompanySite() {
	return companySite;
	}
 	 public void setCompanySite(String companySite){
	 this.companySite = companySite;
	}
	@FieldConfig(fieldName = "companyTel", fieldType =FieldTypeEnum.CHAR24 , description = "公司电话")
	private String companyTel;
	public String getCompanyTel() {
	return companyTel;
	}
 	 public void setCompanyTel(String companyTel){
	 this.companyTel = companyTel;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "创建时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
	}
	@FieldConfig(fieldName = "companyDimisionCode", fieldType =FieldTypeEnum.CHAR64 , description = "公司二维码")
	private String companyDimisionCode;
	public String getCompanyDimisionCode() {
	return companyDimisionCode;
	}
 	 public void setCompanyDimisionCode(String companyDimisionCode){
	 this.companyDimisionCode = companyDimisionCode;
	}
	@FieldConfig(fieldName = "weixinNO", fieldType =FieldTypeEnum.CHAR36 , description = "公司微信好")
	private String weixinNO;
	public String getWeixinNO() {
	return weixinNO;
	}
 	 public void setWeixinNO(String weixinNO){
	 this.weixinNO = weixinNO;
	}
	@FieldConfig(fieldName = "street", fieldType =FieldTypeEnum.CHAR36 , description = "街道")
	private String street;
	public String getStreet() {
	return street;
	}
 	 public void setStreet(String street){
	 this.street = street;
	}
	@FieldConfig(fieldName = "companyToken", fieldType =FieldTypeEnum.CHAR64 , description = "公司访问的token")
	private String companyToken;
	public String getCompanyToken() {
	return companyToken;
	}
 	 public void setCompanyToken(String companyToken){
	 this.companyToken = companyToken;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("companyName", this.companyName);
	 entityMap.put("password", this.password);
	 entityMap.put("linkMobile", this.linkMobile);
	 entityMap.put("qqNO", this.qqNO);
	 entityMap.put("email", this.email);
	 entityMap.put("province", String.valueOf(this.province));
	 entityMap.put("city", String.valueOf(this.city));
	 entityMap.put("region", String.valueOf(this.region));
	try{
	 entityMap.put("serviceStartTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(serviceStartTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("serviceStartTime","");
	}
	try{
	 entityMap.put("serviceEndTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(serviceEndTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("serviceEndTime","");
	}
	 entityMap.put("logoURL", this.logoURL);
	 entityMap.put("companySite", this.companySite);
	 entityMap.put("companyTel", this.companyTel);
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("companyDimisionCode", this.companyDimisionCode);
	 entityMap.put("weixinNO", this.weixinNO);
	 entityMap.put("street", this.street);
	 entityMap.put("companyToken", this.companyToken);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.companyName=entityMap.get("companyName");
	 this.password=entityMap.get("password");
	 this.linkMobile=entityMap.get("linkMobile");
	 this.qqNO=entityMap.get("qqNO");
	 this.email=entityMap.get("email");
	 this.province=Integer.parseInt(entityMap.get("province"));
	 this.city=Integer.parseInt(entityMap.get("city"));
	 this.region=Integer.parseInt(entityMap.get("region"));
	try{
	 this.serviceStartTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("serviceStartTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("serviceStartTime","");
	}
	try{
	 this.serviceEndTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("serviceEndTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("serviceEndTime","");
	}
	 this.logoURL=entityMap.get("logoURL");
	 this.companySite=entityMap.get("companySite");
	 this.companyTel=entityMap.get("companyTel");
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.companyDimisionCode=entityMap.get("companyDimisionCode");
	 this.weixinNO=entityMap.get("weixinNO");
	 this.street=entityMap.get("street");
	 this.companyToken=entityMap.get("companyToken");

    }
}
