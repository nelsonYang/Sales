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
    entityName = EntityNames.reservation, 
    keyFields = {"reservationId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class Reservation extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("reservationId", String.valueOf(this.reservationId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "reservationId", fieldType =FieldTypeEnum.BIG_INT , description = "预约")
	private long reservationId;
	public long getReservationId() {
	return reservationId;
	}
 	 public void setReservationId(long reservationId){
	 this.reservationId = reservationId;
	}
	@FieldConfig(fieldName = "reservationConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "预约配置id")
	private long reservationConfigId;
	public long getReservationConfigId() {
	return reservationConfigId;
	}
 	 public void setReservationConfigId(long reservationConfigId){
	 this.reservationConfigId = reservationConfigId;
	}
	@FieldConfig(fieldName = "reservationAddress", fieldType =FieldTypeEnum.CHAR36 , description = "预约地址")
	private String reservationAddress;
	public String getReservationAddress() {
	return reservationAddress;
	}
 	 public void setReservationAddress(String reservationAddress){
	 this.reservationAddress = reservationAddress;
	}
	@FieldConfig(fieldName = "reservationTelephone", fieldType =FieldTypeEnum.CHAR36 , description = "预约的电话")
	private String reservationTelephone;
	public String getReservationTelephone() {
	return reservationTelephone;
	}
 	 public void setReservationTelephone(String reservationTelephone){
	 this.reservationTelephone = reservationTelephone;
	}
	@FieldConfig(fieldName = "reservationDesc", fieldType =FieldTypeEnum.CHAR128 , description = "预约说明")
	private String reservationDesc;
	public String getReservationDesc() {
	return reservationDesc;
	}
 	 public void setReservationDesc(String reservationDesc){
	 this.reservationDesc = reservationDesc;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "status", fieldType =FieldTypeEnum.TYINT , description = "活动 0-禁用 1-启用")
	private byte status;
	public byte getStatus() {
	return status;
	}
 	 public void setStatus(byte status){
	 this.status = status;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("reservationId", String.valueOf(this.reservationId));
	 entityMap.put("reservationConfigId", String.valueOf(this.reservationConfigId));
	 entityMap.put("reservationAddress", this.reservationAddress);
	 entityMap.put("reservationTelephone", this.reservationTelephone);
	 entityMap.put("reservationDesc", this.reservationDesc);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("status", String.valueOf(this.status));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.reservationId=Long.parseLong(entityMap.get("reservationId"));
	 this.reservationConfigId=Long.parseLong(entityMap.get("reservationConfigId"));
	 this.reservationAddress=entityMap.get("reservationAddress");
	 this.reservationTelephone=entityMap.get("reservationTelephone");
	 this.reservationDesc=entityMap.get("reservationDesc");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.status=Byte.parseByte(entityMap.get("status"));

    }
}
