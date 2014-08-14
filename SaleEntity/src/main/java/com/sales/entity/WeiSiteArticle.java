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
    entityName = EntityNames.weiSiteArticle, 
    keyFields = {"weiSiteArticleId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class WeiSiteArticle extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("weiSiteArticleId", String.valueOf(this.weiSiteArticleId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "weiSiteArticleId", fieldType =FieldTypeEnum.BIG_INT , description = "微站文章id")
	private long weiSiteArticleId;
	public long getWeiSiteArticleId() {
	return weiSiteArticleId;
	}
 	 public void setWeiSiteArticleId(long weiSiteArticleId){
	 this.weiSiteArticleId = weiSiteArticleId;
	}
	@FieldConfig(fieldName = "weiSiteTitle", fieldType =FieldTypeEnum.CHAR36 , description = "标题")
	private String weiSiteTitle;
	public String getWeiSiteTitle() {
	return weiSiteTitle;
	}
 	 public void setWeiSiteTitle(String weiSiteTitle){
	 this.weiSiteTitle = weiSiteTitle;
	}
	@FieldConfig(fieldName = "weiSiteContent", fieldType =FieldTypeEnum.CHAR128 , description = "微站的内容")
	private String weiSiteContent;
	public String getWeiSiteContent() {
	return weiSiteContent;
	}
 	 public void setWeiSiteContent(String weiSiteContent){
	 this.weiSiteContent = weiSiteContent;
	}
	@FieldConfig(fieldName = "weiSiteImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "图片的url")
	private String weiSiteImageURL;
	public String getWeiSiteImageURL() {
	return weiSiteImageURL;
	}
 	 public void setWeiSiteImageURL(String weiSiteImageURL){
	 this.weiSiteImageURL = weiSiteImageURL;
	}
	@FieldConfig(fieldName = "weiSitelinkWebSite", fieldType =FieldTypeEnum.CHAR64 , description = "微站外联地址")
	private String weiSitelinkWebSite;
	public String getWeiSitelinkWebSite() {
	return weiSitelinkWebSite;
	}
 	 public void setWeiSitelinkWebSite(String weiSitelinkWebSite){
	 this.weiSitelinkWebSite = weiSitelinkWebSite;
	}
	@FieldConfig(fieldName = "isShow", fieldType =FieldTypeEnum.TYINT , description = "是否显示")
	private byte isShow;
	public byte getIsShow() {
	return isShow;
	}
 	 public void setIsShow(byte isShow){
	 this.isShow = isShow;
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
	@FieldConfig(fieldName = "bindId", fieldType =FieldTypeEnum.BIG_INT , description = "归属的id")
	private long bindId;
	public long getBindId() {
	return bindId;
	}
 	 public void setBindId(long bindId){
	 this.bindId = bindId;
	}
	@FieldConfig(fieldName = "type", fieldType =FieldTypeEnum.TYINT , description = "文章的类型")
	private byte type;
	public byte getType() {
	return type;
	}
 	 public void setType(byte type){
	 this.type = type;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("weiSiteArticleId", String.valueOf(this.weiSiteArticleId));
	 entityMap.put("weiSiteTitle", this.weiSiteTitle);
	 entityMap.put("weiSiteContent", this.weiSiteContent);
	 entityMap.put("weiSiteImageURL", this.weiSiteImageURL);
	 entityMap.put("weiSitelinkWebSite", this.weiSitelinkWebSite);
	 entityMap.put("isShow", String.valueOf(this.isShow));
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("bindId", String.valueOf(this.bindId));
	 entityMap.put("type", String.valueOf(this.type));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.weiSiteArticleId=Long.parseLong(entityMap.get("weiSiteArticleId"));
	 this.weiSiteTitle=entityMap.get("weiSiteTitle");
	 this.weiSiteContent=entityMap.get("weiSiteContent");
	 this.weiSiteImageURL=entityMap.get("weiSiteImageURL");
	 this.weiSitelinkWebSite=entityMap.get("weiSitelinkWebSite");
	 this.isShow=Byte.parseByte(entityMap.get("isShow"));
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.bindId=Long.parseLong(entityMap.get("bindId"));
	 this.type=Byte.parseByte(entityMap.get("type"));

    }
}
