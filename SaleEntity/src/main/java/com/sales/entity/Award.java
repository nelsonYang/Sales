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
    entityName = EntityNames.award, 
    keyFields = {"awardId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class Award extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("awardId", String.valueOf(this.awardId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "awardId", fieldType =FieldTypeEnum.BIG_INT , description = "奖项id")
	private long awardId;
	public long getAwardId() {
	return awardId;
	}
 	 public void setAwardId(long awardId){
	 this.awardId = awardId;
	}
	@FieldConfig(fieldName = "awardName", fieldType =FieldTypeEnum.CHAR36 , description = "奖项的名字")
	private String awardName;
	public String getAwardName() {
	return awardName;
	}
 	 public void setAwardName(String awardName){
	 this.awardName = awardName;
	}
	@FieldConfig(fieldName = "eventId", fieldType =FieldTypeEnum.BIG_INT , description = "归属的活动id")
	private long eventId;
	public long getEventId() {
	return eventId;
	}
 	 public void setEventId(long eventId){
	 this.eventId = eventId;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "awardDesc", fieldType =FieldTypeEnum.CHAR64 , description = "奖项的描述")
	private String awardDesc;
	public String getAwardDesc() {
	return awardDesc;
	}
 	 public void setAwardDesc(String awardDesc){
	 this.awardDesc = awardDesc;
	}
	@FieldConfig(fieldName = "awardNum", fieldType =FieldTypeEnum.INT , description = "奖项的数量")
	private int awardNum;
	public int getAwardNum() {
	return awardNum;
	}
 	 public void setAwardNum(int awardNum){
	 this.awardNum = awardNum;
	}
	@FieldConfig(fieldName = "awardNumPerPerson", fieldType =FieldTypeEnum.CHAR36 , description = "奖项每人的数量")
	private String awardNumPerPerson;
	public String getAwardNumPerPerson() {
	return awardNumPerPerson;
	}
 	 public void setAwardNumPerPerson(String awardNumPerPerson){
	 this.awardNumPerPerson = awardNumPerPerson;
	}
	@FieldConfig(fieldName = "awardCurrentNum", fieldType =FieldTypeEnum.INT , description = "剩余的数量")
	private int awardCurrentNum;
	public int getAwardCurrentNum() {
	return awardCurrentNum;
	}
 	 public void setAwardCurrentNum(int awardCurrentNum){
	 this.awardCurrentNum = awardCurrentNum;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("awardId", String.valueOf(this.awardId));
	 entityMap.put("awardName", this.awardName);
	 entityMap.put("eventId", String.valueOf(this.eventId));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("awardDesc", this.awardDesc);
	 entityMap.put("awardNum", String.valueOf(this.awardNum));
	 entityMap.put("awardNumPerPerson", this.awardNumPerPerson);
	 entityMap.put("awardCurrentNum", String.valueOf(this.awardCurrentNum));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.awardId=Long.parseLong(entityMap.get("awardId"));
	 this.awardName=entityMap.get("awardName");
	 this.eventId=Long.parseLong(entityMap.get("eventId"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.awardDesc=entityMap.get("awardDesc");
	 this.awardNum=Integer.parseInt(entityMap.get("awardNum"));
	 this.awardNumPerPerson=entityMap.get("awardNumPerPerson");
	 this.awardCurrentNum=Integer.parseInt(entityMap.get("awardCurrentNum"));

    }
}
