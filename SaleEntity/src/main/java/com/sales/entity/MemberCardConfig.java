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
    entityName = EntityNames.memberCardConfig, 
    keyFields = {"memberCardConfigId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class MemberCardConfig extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("memberCardConfigId", String.valueOf(this.memberCardConfigId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "memberCardConfigId", fieldType =FieldTypeEnum.INT , description = "会员卡配置id")
	private int memberCardConfigId;
	public int getMemberCardConfigId() {
	return memberCardConfigId;
	}
 	 public void setMemberCardConfigId(int memberCardConfigId){
	 this.memberCardConfigId = memberCardConfigId;
	}
	@FieldConfig(fieldName = "merchantName", fieldType =FieldTypeEnum.CHAR36 , description = "商家名称")
	private String merchantName;
	public String getMerchantName() {
	return merchantName;
	}
 	 public void setMerchantName(String merchantName){
	 this.merchantName = merchantName;
	}
	@FieldConfig(fieldName = "isExperienceOpen", fieldType =FieldTypeEnum.TYINT , description = "是否开启积分1-开启 2-不开起")
	private byte isExperienceOpen;
	public byte getIsExperienceOpen() {
	return isExperienceOpen;
	}
 	 public void setIsExperienceOpen(byte isExperienceOpen){
	 this.isExperienceOpen = isExperienceOpen;
	}
	@FieldConfig(fieldName = "telephone", fieldType =FieldTypeEnum.CHAR36 , description = "联系方式")
	private String telephone;
	public String getTelephone() {
	return telephone;
	}
 	 public void setTelephone(String telephone){
	 this.telephone = telephone;
	}
	@FieldConfig(fieldName = "memberCardName", fieldType =FieldTypeEnum.CHAR36 , description = "会员卡名称")
	private String memberCardName;
	public String getMemberCardName() {
	return memberCardName;
	}
 	 public void setMemberCardName(String memberCardName){
	 this.memberCardName = memberCardName;
	}
	@FieldConfig(fieldName = "merchantLogo", fieldType =FieldTypeEnum.CHAR64 , description = "商家logo")
	private String merchantLogo;
	public String getMerchantLogo() {
	return merchantLogo;
	}
 	 public void setMerchantLogo(String merchantLogo){
	 this.merchantLogo = merchantLogo;
	}
	@FieldConfig(fieldName = "memberCardBackgroupURL", fieldType =FieldTypeEnum.CHAR64 , description = "会员卡背景")
	private String memberCardBackgroupURL;
	public String getMemberCardBackgroupURL() {
	return memberCardBackgroupURL;
	}
 	 public void setMemberCardBackgroupURL(String memberCardBackgroupURL){
	 this.memberCardBackgroupURL = memberCardBackgroupURL;
	}
	@FieldConfig(fieldName = "merchantAddress", fieldType =FieldTypeEnum.CHAR128 , description = "商家地址")
	private String merchantAddress;
	public String getMerchantAddress() {
	return merchantAddress;
	}
 	 public void setMerchantAddress(String merchantAddress){
	 this.merchantAddress = merchantAddress;
	}
	@FieldConfig(fieldName = "integerationPerSign", fieldType =FieldTypeEnum.BIG_INT , description = "签到积分")
	private long integerationPerSign;
	public long getIntegerationPerSign() {
	return integerationPerSign;
	}
 	 public void setIntegerationPerSign(long integerationPerSign){
	 this.integerationPerSign = integerationPerSign;
	}
	@FieldConfig(fieldName = "keyword", fieldType =FieldTypeEnum.CHAR36 , description = "关键字")
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
	@FieldConfig(fieldName = "title", fieldType =FieldTypeEnum.CHAR36 , description = "标题")
	private String title;
	public String getTitle() {
	return title;
	}
 	 public void setTitle(String title){
	 this.title = title;
	}
	@FieldConfig(fieldName = "messageImageURL", fieldType =FieldTypeEnum.CHAR64 , description = "图片的url")
	private String messageImageURL;
	public String getMessageImageURL() {
	return messageImageURL;
	}
 	 public void setMessageImageURL(String messageImageURL){
	 this.messageImageURL = messageImageURL;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "memberCardDesc", fieldType =FieldTypeEnum.CHAR128 , description = "会员卡说名")
	private String memberCardDesc;
	public String getMemberCardDesc() {
	return memberCardDesc;
	}
 	 public void setMemberCardDesc(String memberCardDesc){
	 this.memberCardDesc = memberCardDesc;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("memberCardConfigId", String.valueOf(this.memberCardConfigId));
	 entityMap.put("merchantName", this.merchantName);
	 entityMap.put("isExperienceOpen", String.valueOf(this.isExperienceOpen));
	 entityMap.put("telephone", this.telephone);
	 entityMap.put("memberCardName", this.memberCardName);
	 entityMap.put("merchantLogo", this.merchantLogo);
	 entityMap.put("memberCardBackgroupURL", this.memberCardBackgroupURL);
	 entityMap.put("merchantAddress", this.merchantAddress);
	 entityMap.put("integerationPerSign", String.valueOf(this.integerationPerSign));
	 entityMap.put("keyword", this.keyword);
	 entityMap.put("matchType", String.valueOf(this.matchType));
	 entityMap.put("title", this.title);
	 entityMap.put("messageImageURL", this.messageImageURL);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("memberCardDesc", this.memberCardDesc);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.memberCardConfigId=Integer.parseInt(entityMap.get("memberCardConfigId"));
	 this.merchantName=entityMap.get("merchantName");
	 this.isExperienceOpen=Byte.parseByte(entityMap.get("isExperienceOpen"));
	 this.telephone=entityMap.get("telephone");
	 this.memberCardName=entityMap.get("memberCardName");
	 this.merchantLogo=entityMap.get("merchantLogo");
	 this.memberCardBackgroupURL=entityMap.get("memberCardBackgroupURL");
	 this.merchantAddress=entityMap.get("merchantAddress");
	 this.integerationPerSign=Long.parseLong(entityMap.get("integerationPerSign"));
	 this.keyword=entityMap.get("keyword");
	 this.matchType=Byte.parseByte(entityMap.get("matchType"));
	 this.title=entityMap.get("title");
	 this.messageImageURL=entityMap.get("messageImageURL");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.memberCardDesc=entityMap.get("memberCardDesc");

    }
}
