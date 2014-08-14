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
    entityName = EntityNames.weixinToken, 
    keyFields = {"tokenId"},
    useCache = false
)
public class WeixinToken extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("tokenId", String.valueOf(this.tokenId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "tokenId", fieldType =FieldTypeEnum.INT , description = "token的id")
	private int tokenId;
	public int getTokenId() {
	return tokenId;
	}
 	 public void setTokenId(int tokenId){
	 this.tokenId = tokenId;
	}
	@FieldConfig(fieldName = "appId", fieldType =FieldTypeEnum.CHAR24 , description = "微信appid")
	private String appId;
	public String getAppId() {
	return appId;
	}
 	 public void setAppId(String appId){
	 this.appId = appId;
	}
	@FieldConfig(fieldName = "appSecret", fieldType =FieldTypeEnum.CHAR64 , description = "微信secret")
	private String appSecret;
	public String getAppSecret() {
	return appSecret;
	}
 	 public void setAppSecret(String appSecret){
	 this.appSecret = appSecret;
	}
	@FieldConfig(fieldName = "accessToken", fieldType =FieldTypeEnum.CHAR64 , description = "微信token")
	private String accessToken;
	public String getAccessToken() {
	return accessToken;
	}
 	 public void setAccessToken(String accessToken){
	 this.accessToken = accessToken;
	}
	@FieldConfig(fieldName = "expireTime", fieldType =FieldTypeEnum.DATETIME , description = "token的过期时间")
	private Date expireTime;
	public Date getExpireTime() {
	return expireTime;
	}
 	 public void setExpireTime(Date expireTime){
	 this.expireTime = expireTime;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "weixinNO", fieldType =FieldTypeEnum.CHAR36 , description = "微信号")
	private String weixinNO;
	public String getWeixinNO() {
	return weixinNO;
	}
 	 public void setWeixinNO(String weixinNO){
	 this.weixinNO = weixinNO;
	}
	@FieldConfig(fieldName = "weixinEmail", fieldType =FieldTypeEnum.CHAR36 , description = "微信邮箱")
	private String weixinEmail;
	public String getWeixinEmail() {
	return weixinEmail;
	}
 	 public void setWeixinEmail(String weixinEmail){
	 this.weixinEmail = weixinEmail;
	}
	@FieldConfig(fieldName = "weixinDevAPI", fieldType =FieldTypeEnum.CHAR64 , description = "开发者api")
	private String weixinDevAPI;
	public String getWeixinDevAPI() {
	return weixinDevAPI;
	}
 	 public void setWeixinDevAPI(String weixinDevAPI){
	 this.weixinDevAPI = weixinDevAPI;
	}
	@FieldConfig(fieldName = "validateToken", fieldType =FieldTypeEnum.CHAR64 , description = "微信的接口token")
	private String validateToken;
	public String getValidateToken() {
	return validateToken;
	}
 	 public void setValidateToken(String validateToken){
	 this.validateToken = validateToken;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("tokenId", String.valueOf(this.tokenId));
	 entityMap.put("appId", this.appId);
	 entityMap.put("appSecret", this.appSecret);
	 entityMap.put("accessToken", this.accessToken);
	try{
	 entityMap.put("expireTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expireTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("expireTime","");
	}
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("weixinNO", this.weixinNO);
	 entityMap.put("weixinEmail", this.weixinEmail);
	 entityMap.put("weixinDevAPI", this.weixinDevAPI);
	 entityMap.put("validateToken", this.validateToken);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.tokenId=Integer.parseInt(entityMap.get("tokenId"));
	 this.appId=entityMap.get("appId");
	 this.appSecret=entityMap.get("appSecret");
	 this.accessToken=entityMap.get("accessToken");

	try{
	 this.expireTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("expireTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("expireTime","");
	}
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.weixinNO=entityMap.get("weixinNO");
	 this.weixinEmail=entityMap.get("weixinEmail");
	 this.weixinDevAPI=entityMap.get("weixinDevAPI");
	 this.validateToken=entityMap.get("validateToken");

    }
}
