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
    entityName = EntityNames.personCard, 
    keyFields = {"personCardId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class PersonCard extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("personCardId", String.valueOf(this.personCardId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "personCardId", fieldType =FieldTypeEnum.BIG_INT , description = "名片id")
	private long personCardId;
	public long getPersonCardId() {
	return personCardId;
	}
 	 public void setPersonCardId(long personCardId){
	 this.personCardId = personCardId;
	}
	@FieldConfig(fieldName = "personName", fieldType =FieldTypeEnum.CHAR11 , description = "名片名字")
	private String personName;
	public String getPersonName() {
	return personName;
	}
 	 public void setPersonName(String personName){
	 this.personName = personName;
	}
	@FieldConfig(fieldName = "personMobile", fieldType =FieldTypeEnum.CHAR11 , description = "名片号码")
	private String personMobile;
	public String getPersonMobile() {
	return personMobile;
	}
 	 public void setPersonMobile(String personMobile){
	 this.personMobile = personMobile;
	}
	@FieldConfig(fieldName = "personEmail", fieldType =FieldTypeEnum.CHAR36 , description = "名片的邮箱")
	private String personEmail;
	public String getPersonEmail() {
	return personEmail;
	}
 	 public void setPersonEmail(String personEmail){
	 this.personEmail = personEmail;
	}
	@FieldConfig(fieldName = "personIconURL", fieldType =FieldTypeEnum.CHAR64 , description = "名片的图片")
	private String personIconURL;
	public String getPersonIconURL() {
	return personIconURL;
	}
 	 public void setPersonIconURL(String personIconURL){
	 this.personIconURL = personIconURL;
	}
	@FieldConfig(fieldName = "companyName", fieldType =FieldTypeEnum.CHAR36 , description = "公司名字")
	private String companyName;
	public String getCompanyName() {
	return companyName;
	}
 	 public void setCompanyName(String companyName){
	 this.companyName = companyName;
	}
	@FieldConfig(fieldName = "companyAddress", fieldType =FieldTypeEnum.CHAR64 , description = "公司地址")
	private String companyAddress;
	public String getCompanyAddress() {
	return companyAddress;
	}
 	 public void setCompanyAddress(String companyAddress){
	 this.companyAddress = companyAddress;
	}
	@FieldConfig(fieldName = "readCount", fieldType =FieldTypeEnum.INT , description = "被浏览次数")
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
	@FieldConfig(fieldName = "weiboNo", fieldType =FieldTypeEnum.CHAR36 , description = "微薄号")
	private String weiboNo;
	public String getWeiboNo() {
	return weiboNo;
	}
 	 public void setWeiboNo(String weiboNo){
	 this.weiboNo = weiboNo;
	}
	@FieldConfig(fieldName = "qqNO", fieldType =FieldTypeEnum.CHAR11 , description = "qq号")
	private String qqNO;
	public String getQqNO() {
	return qqNO;
	}
 	 public void setQqNO(String qqNO){
	 this.qqNO = qqNO;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "创建时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
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
	@FieldConfig(fieldName = "personCardDesc", fieldType =FieldTypeEnum.CHAR64 , description = "微名片描述")
	private String personCardDesc;
	public String getPersonCardDesc() {
	return personCardDesc;
	}
 	 public void setPersonCardDesc(String personCardDesc){
	 this.personCardDesc = personCardDesc;
	}
	@FieldConfig(fieldName = "personCardAddress", fieldType =FieldTypeEnum.CHAR64 , description = "地址")
	private String personCardAddress;
	public String getPersonCardAddress() {
	return personCardAddress;
	}
 	 public void setPersonCardAddress(String personCardAddress){
	 this.personCardAddress = personCardAddress;
	}
	@FieldConfig(fieldName = "personLinkSite", fieldType =FieldTypeEnum.CHAR64 , description = "链接地址")
	private String personLinkSite;
	public String getPersonLinkSite() {
	return personLinkSite;
	}
 	 public void setPersonLinkSite(String personLinkSite){
	 this.personLinkSite = personLinkSite;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("personCardId", String.valueOf(this.personCardId));
	 entityMap.put("personName", this.personName);
	 entityMap.put("personMobile", this.personMobile);
	 entityMap.put("personEmail", this.personEmail);
	 entityMap.put("personIconURL", this.personIconURL);
	 entityMap.put("companyName", this.companyName);
	 entityMap.put("companyAddress", this.companyAddress);
	 entityMap.put("readCount", String.valueOf(this.readCount));
	 entityMap.put("clickCount", String.valueOf(this.clickCount));
	 entityMap.put("weiboNo", this.weiboNo);
	 entityMap.put("qqNO", this.qqNO);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("messageTitle", this.messageTitle);
	 entityMap.put("messageImageURL", this.messageImageURL);
	 entityMap.put("personCardDesc", this.personCardDesc);
	 entityMap.put("personCardAddress", this.personCardAddress);
	 entityMap.put("personLinkSite", this.personLinkSite);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.personCardId=Long.parseLong(entityMap.get("personCardId"));
	 this.personName=entityMap.get("personName");
	 this.personMobile=entityMap.get("personMobile");
	 this.personEmail=entityMap.get("personEmail");
	 this.personIconURL=entityMap.get("personIconURL");
	 this.companyName=entityMap.get("companyName");
	 this.companyAddress=entityMap.get("companyAddress");
	 this.readCount=Integer.parseInt(entityMap.get("readCount"));
	 this.clickCount=Integer.parseInt(entityMap.get("clickCount"));
	 this.weiboNo=entityMap.get("weiboNo");
	 this.qqNO=entityMap.get("qqNO");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.messageTitle=entityMap.get("messageTitle");
	 this.messageImageURL=entityMap.get("messageImageURL");
	 this.personCardDesc=entityMap.get("personCardDesc");
	 this.personCardAddress=entityMap.get("personCardAddress");
	 this.personLinkSite=entityMap.get("personLinkSite");

    }
}
