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
    entityName = EntityNames.weiSiteConfig, 
    keyFields = {"weiSiteConfigId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class WeiSiteConfig extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("weiSiteConfigId", String.valueOf(this.weiSiteConfigId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "weiSiteConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "微网站配置id")
	private long weiSiteConfigId;
	public long getWeiSiteConfigId() {
	return weiSiteConfigId;
	}
 	 public void setWeiSiteConfigId(long weiSiteConfigId){
	 this.weiSiteConfigId = weiSiteConfigId;
	}
	@FieldConfig(fieldName = "weiSiteName", fieldType =FieldTypeEnum.CHAR36 , description = "微网站名称")
	private String weiSiteName;
	public String getWeiSiteName() {
	return weiSiteName;
	}
 	 public void setWeiSiteName(String weiSiteName){
	 this.weiSiteName = weiSiteName;
	}
	@FieldConfig(fieldName = "backgroupMusicURL", fieldType =FieldTypeEnum.CHAR64 , description = "背景音乐的url")
	private String backgroupMusicURL;
	public String getBackgroupMusicURL() {
	return backgroupMusicURL;
	}
 	 public void setBackgroupMusicURL(String backgroupMusicURL){
	 this.backgroupMusicURL = backgroupMusicURL;
	}
	@FieldConfig(fieldName = "keyword", fieldType =FieldTypeEnum.CHAR36 , description = "")
	private String keyword;
	public String getKeyword() {
	return keyword;
	}
 	 public void setKeyword(String keyword){
	 this.keyword = keyword;
	}
	@FieldConfig(fieldName = "matchType", fieldType =FieldTypeEnum.TYINT , description = "匹配类型1-精确2-模糊")
	private byte matchType;
	public byte getMatchType() {
	return matchType;
	}
 	 public void setMatchType(byte matchType){
	 this.matchType = matchType;
	}
	@FieldConfig(fieldName = "telphone", fieldType =FieldTypeEnum.CHAR11 , description = "手机号码")
	private String telphone;
	public String getTelphone() {
	return telphone;
	}
 	 public void setTelphone(String telphone){
	 this.telphone = telphone;
	}
	@FieldConfig(fieldName = "isDialOpen", fieldType =FieldTypeEnum.TYINT , description = "开启一键拨号")
	private byte isDialOpen;
	public byte getIsDialOpen() {
	return isDialOpen;
	}
 	 public void setIsDialOpen(byte isDialOpen){
	 this.isDialOpen = isDialOpen;
	}
	@FieldConfig(fieldName = "title", fieldType =FieldTypeEnum.CHAR36 , description = "官网名称")
	private String title;
	public String getTitle() {
	return title;
	}
 	 public void setTitle(String title){
	 this.title = title;
	}
	@FieldConfig(fieldName = "backgroupImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "背景图片")
	private String backgroupImageURL;
	public String getBackgroupImageURL() {
	return backgroupImageURL;
	}
 	 public void setBackgroupImageURL(String backgroupImageURL){
	 this.backgroupImageURL = backgroupImageURL;
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
	 entityMap.put("weiSiteConfigId", String.valueOf(this.weiSiteConfigId));
	 entityMap.put("weiSiteName", this.weiSiteName);
	 entityMap.put("backgroupMusicURL", this.backgroupMusicURL);
	 entityMap.put("keyword", this.keyword);
	 entityMap.put("matchType", String.valueOf(this.matchType));
	 entityMap.put("telphone", this.telphone);
	 entityMap.put("isDialOpen", String.valueOf(this.isDialOpen));
	 entityMap.put("title", this.title);
	 entityMap.put("backgroupImageURL", this.backgroupImageURL);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.weiSiteConfigId=Long.parseLong(entityMap.get("weiSiteConfigId"));
	 this.weiSiteName=entityMap.get("weiSiteName");
	 this.backgroupMusicURL=entityMap.get("backgroupMusicURL");
	 this.keyword=entityMap.get("keyword");
	 this.matchType=Byte.parseByte(entityMap.get("matchType"));
	 this.telphone=entityMap.get("telphone");
	 this.isDialOpen=Byte.parseByte(entityMap.get("isDialOpen"));
	 this.title=entityMap.get("title");
	 this.backgroupImageURL=entityMap.get("backgroupImageURL");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
