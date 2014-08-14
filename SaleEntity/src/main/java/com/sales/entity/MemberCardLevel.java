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
    entityName = EntityNames.memberCardLevel, 
    keyFields = {"memberCardLevelId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class MemberCardLevel extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("memberCardLevelId", String.valueOf(this.memberCardLevelId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "memberCardLevelId", fieldType =FieldTypeEnum.BIG_INT , description = "会员卡等级id")
	private long memberCardLevelId;
	public long getMemberCardLevelId() {
	return memberCardLevelId;
	}
 	 public void setMemberCardLevelId(long memberCardLevelId){
	 this.memberCardLevelId = memberCardLevelId;
	}
	@FieldConfig(fieldName = "memberCardLevelMin", fieldType =FieldTypeEnum.BIG_INT , description = "会员卡等级最小值")
	private long memberCardLevelMin;
	public long getMemberCardLevelMin() {
	return memberCardLevelMin;
	}
 	 public void setMemberCardLevelMin(long memberCardLevelMin){
	 this.memberCardLevelMin = memberCardLevelMin;
	}
	@FieldConfig(fieldName = "memberCardLevelMax", fieldType =FieldTypeEnum.BIG_INT , description = "会员卡的最大值")
	private long memberCardLevelMax;
	public long getMemberCardLevelMax() {
	return memberCardLevelMax;
	}
 	 public void setMemberCardLevelMax(long memberCardLevelMax){
	 this.memberCardLevelMax = memberCardLevelMax;
	}
	@FieldConfig(fieldName = "memberCardLevel", fieldType =FieldTypeEnum.BIG_INT , description = "")
	private long memberCardLevel;
	public long getMemberCardLevel() {
	return memberCardLevel;
	}
 	 public void setMemberCardLevel(long memberCardLevel){
	 this.memberCardLevel = memberCardLevel;
	}
	@FieldConfig(fieldName = "memberCardBackgroupURL", fieldType =FieldTypeEnum.CHAR128 , description = "会员卡的背景图")
	private String memberCardBackgroupURL;
	public String getMemberCardBackgroupURL() {
	return memberCardBackgroupURL;
	}
 	 public void setMemberCardBackgroupURL(String memberCardBackgroupURL){
	 this.memberCardBackgroupURL = memberCardBackgroupURL;
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
	 entityMap.put("memberCardLevelId", String.valueOf(this.memberCardLevelId));
	 entityMap.put("memberCardLevelMin", String.valueOf(this.memberCardLevelMin));
	 entityMap.put("memberCardLevelMax", String.valueOf(this.memberCardLevelMax));
	 entityMap.put("memberCardLevel", String.valueOf(this.memberCardLevel));
	 entityMap.put("memberCardBackgroupURL", this.memberCardBackgroupURL);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.memberCardLevelId=Long.parseLong(entityMap.get("memberCardLevelId"));
	 this.memberCardLevelMin=Long.parseLong(entityMap.get("memberCardLevelMin"));
	 this.memberCardLevelMax=Long.parseLong(entityMap.get("memberCardLevelMax"));
	 this.memberCardLevel=Long.parseLong(entityMap.get("memberCardLevel"));
	 this.memberCardBackgroupURL=entityMap.get("memberCardBackgroupURL");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
