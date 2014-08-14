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
    entityName = EntityNames.eventFlow, 
    keyFields = {"eventFlowId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class EventFlow extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("eventFlowId", String.valueOf(this.eventFlowId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "eventFlowId", fieldType =FieldTypeEnum.BIG_INT , description = "")
	private long eventFlowId;
	public long getEventFlowId() {
	return eventFlowId;
	}
 	 public void setEventFlowId(long eventFlowId){
	 this.eventFlowId = eventFlowId;
	}
	@FieldConfig(fieldName = "weixinId", fieldType =FieldTypeEnum.CHAR36 , description = "微信id")
	private String weixinId;
	public String getWeixinId() {
	return weixinId;
	}
 	 public void setWeixinId(String weixinId){
	 this.weixinId = weixinId;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "创建时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
	}
	@FieldConfig(fieldName = "eventId", fieldType =FieldTypeEnum.BIG_INT , description = "参加的活动")
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



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("eventFlowId", String.valueOf(this.eventFlowId));
	 entityMap.put("weixinId", this.weixinId);
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("eventId", String.valueOf(this.eventId));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.eventFlowId=Long.parseLong(entityMap.get("eventFlowId"));
	 this.weixinId=entityMap.get("weixinId");
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.eventId=Long.parseLong(entityMap.get("eventId"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
