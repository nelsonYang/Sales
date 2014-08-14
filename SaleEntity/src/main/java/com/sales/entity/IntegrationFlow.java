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
    entityName = EntityNames.integrationFlow, 
    keyFields = {"integrationFlowId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class IntegrationFlow extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("integrationFlowId", String.valueOf(this.integrationFlowId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "integrationFlowId", fieldType =FieldTypeEnum.BIG_INT , description = "积分流水")
	private long integrationFlowId;
	public long getIntegrationFlowId() {
	return integrationFlowId;
	}
 	 public void setIntegrationFlowId(long integrationFlowId){
	 this.integrationFlowId = integrationFlowId;
	}
	@FieldConfig(fieldName = "integrationAmount", fieldType =FieldTypeEnum.BIG_INT , description = "积分数量")
	private long integrationAmount;
	public long getIntegrationAmount() {
	return integrationAmount;
	}
 	 public void setIntegrationAmount(long integrationAmount){
	 this.integrationAmount = integrationAmount;
	}
	@FieldConfig(fieldName = "memberId", fieldType =FieldTypeEnum.BIG_INT , description = "会员id")
	private long memberId;
	public long getMemberId() {
	return memberId;
	}
 	 public void setMemberId(long memberId){
	 this.memberId = memberId;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "type", fieldType =FieldTypeEnum.TYINT , description = "操作类型")
	private byte type;
	public byte getType() {
	return type;
	}
 	 public void setType(byte type){
	 this.type = type;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "操作时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("integrationFlowId", String.valueOf(this.integrationFlowId));
	 entityMap.put("integrationAmount", String.valueOf(this.integrationAmount));
	 entityMap.put("memberId", String.valueOf(this.memberId));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("type", String.valueOf(this.type));
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.integrationFlowId=Long.parseLong(entityMap.get("integrationFlowId"));
	 this.integrationAmount=Long.parseLong(entityMap.get("integrationAmount"));
	 this.memberId=Long.parseLong(entityMap.get("memberId"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.type=Byte.parseByte(entityMap.get("type"));
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}

    }
}
