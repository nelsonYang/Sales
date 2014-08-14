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
    entityName = EntityNames.payFlow, 
    keyFields = {"payFlowId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class PayFlow extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("payFlowId", String.valueOf(this.payFlowId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "payFlowId", fieldType =FieldTypeEnum.BIG_INT , description = "支付流水id")
	private long payFlowId;
	public long getPayFlowId() {
	return payFlowId;
	}
 	 public void setPayFlowId(long payFlowId){
	 this.payFlowId = payFlowId;
	}
	@FieldConfig(fieldName = "fromAccount", fieldType =FieldTypeEnum.CHAR36 , description = "转出帐号")
	private String fromAccount;
	public String getFromAccount() {
	return fromAccount;
	}
 	 public void setFromAccount(String fromAccount){
	 this.fromAccount = fromAccount;
	}
	@FieldConfig(fieldName = "toAccount", fieldType =FieldTypeEnum.CHAR36 , description = "转入帐号")
	private String toAccount;
	public String getToAccount() {
	return toAccount;
	}
 	 public void setToAccount(String toAccount){
	 this.toAccount = toAccount;
	}
	@FieldConfig(fieldName = "money", fieldType =FieldTypeEnum.BIG_INT , description = "金额")
	private long money;
	public long getMoney() {
	return money;
	}
 	 public void setMoney(long money){
	 this.money = money;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "创建的时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("payFlowId", String.valueOf(this.payFlowId));
	 entityMap.put("fromAccount", this.fromAccount);
	 entityMap.put("toAccount", this.toAccount);
	 entityMap.put("money", String.valueOf(this.money));
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.payFlowId=Long.parseLong(entityMap.get("payFlowId"));
	 this.fromAccount=entityMap.get("fromAccount");
	 this.toAccount=entityMap.get("toAccount");
	 this.money=Long.parseLong(entityMap.get("money"));
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}

    }
}
