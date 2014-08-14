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
    entityName = EntityNames.payOrder, 
    keyFields = {"payOrderId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class PayOrder extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("payOrderId", String.valueOf(this.payOrderId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "payOrderId", fieldType =FieldTypeEnum.BIG_INT , description = "支付订单id")
	private long payOrderId;
	public long getPayOrderId() {
	return payOrderId;
	}
 	 public void setPayOrderId(long payOrderId){
	 this.payOrderId = payOrderId;
	}
	@FieldConfig(fieldName = "payOrderName", fieldType =FieldTypeEnum.CHAR36 , description = "支付订单名称")
	private String payOrderName;
	public String getPayOrderName() {
	return payOrderName;
	}
 	 public void setPayOrderName(String payOrderName){
	 this.payOrderName = payOrderName;
	}
	@FieldConfig(fieldName = "payOrderSuject", fieldType =FieldTypeEnum.CHAR36 , description = "支付项目")
	private String payOrderSuject;
	public String getPayOrderSuject() {
	return payOrderSuject;
	}
 	 public void setPayOrderSuject(String payOrderSuject){
	 this.payOrderSuject = payOrderSuject;
	}
	@FieldConfig(fieldName = "payOrderWay", fieldType =FieldTypeEnum.TYINT , description = "支付方式")
	private byte payOrderWay;
	public byte getPayOrderWay() {
	return payOrderWay;
	}
 	 public void setPayOrderWay(byte payOrderWay){
	 this.payOrderWay = payOrderWay;
	}
	@FieldConfig(fieldName = "payOrderMoney", fieldType =FieldTypeEnum.BIG_INT , description = "支付金额")
	private long payOrderMoney;
	public long getPayOrderMoney() {
	return payOrderMoney;
	}
 	 public void setPayOrderMoney(long payOrderMoney){
	 this.payOrderMoney = payOrderMoney;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "支付时间")
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
	@FieldConfig(fieldName = "payOrderStatus", fieldType =FieldTypeEnum.TYINT , description = "支付状态")
	private byte payOrderStatus;
	public byte getPayOrderStatus() {
	return payOrderStatus;
	}
 	 public void setPayOrderStatus(byte payOrderStatus){
	 this.payOrderStatus = payOrderStatus;
	}
	@FieldConfig(fieldName = "thirdPartyPayOrder", fieldType =FieldTypeEnum.CHAR64 , description = "第三方的订单号")
	private String thirdPartyPayOrder;
	public String getThirdPartyPayOrder() {
	return thirdPartyPayOrder;
	}
 	 public void setThirdPartyPayOrder(String thirdPartyPayOrder){
	 this.thirdPartyPayOrder = thirdPartyPayOrder;
	}
	@FieldConfig(fieldName = "payAccount", fieldType =FieldTypeEnum.CHAR36 , description = "支付的帐号")
	private String payAccount;
	public String getPayAccount() {
	return payAccount;
	}
 	 public void setPayAccount(String payAccount){
	 this.payAccount = payAccount;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("payOrderId", String.valueOf(this.payOrderId));
	 entityMap.put("payOrderName", this.payOrderName);
	 entityMap.put("payOrderSuject", this.payOrderSuject);
	 entityMap.put("payOrderWay", String.valueOf(this.payOrderWay));
	 entityMap.put("payOrderMoney", String.valueOf(this.payOrderMoney));
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("payOrderStatus", String.valueOf(this.payOrderStatus));
	 entityMap.put("thirdPartyPayOrder", this.thirdPartyPayOrder);
	 entityMap.put("payAccount", this.payAccount);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.payOrderId=Long.parseLong(entityMap.get("payOrderId"));
	 this.payOrderName=entityMap.get("payOrderName");
	 this.payOrderSuject=entityMap.get("payOrderSuject");
	 this.payOrderWay=Byte.parseByte(entityMap.get("payOrderWay"));
	 this.payOrderMoney=Long.parseLong(entityMap.get("payOrderMoney"));
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.payOrderStatus=Byte.parseByte(entityMap.get("payOrderStatus"));
	 this.thirdPartyPayOrder=entityMap.get("thirdPartyPayOrder");
	 this.payAccount=entityMap.get("payAccount");

    }
}
