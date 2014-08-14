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
    entityName = EntityNames.tariffPackagesCfg, 
    keyFields = {"tariffPackagesCfgId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class TariffPackagesCfg extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("tariffPackagesCfgId", String.valueOf(this.tariffPackagesCfgId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "tariffPackagesCfgId", fieldType =FieldTypeEnum.BIG_INT , description = "资费套餐id")
	private long tariffPackagesCfgId;
	public long getTariffPackagesCfgId() {
	return tariffPackagesCfgId;
	}
 	 public void setTariffPackagesCfgId(long tariffPackagesCfgId){
	 this.tariffPackagesCfgId = tariffPackagesCfgId;
	}
	@FieldConfig(fieldName = "type", fieldType =FieldTypeEnum.TYINT , description = "资费套餐的类型")
	private byte type;
	public byte getType() {
	return type;
	}
 	 public void setType(byte type){
	 this.type = type;
	}
	@FieldConfig(fieldName = "money", fieldType =FieldTypeEnum.BIG_INT , description = "资费额")
	private long money;
	public long getMoney() {
	return money;
	}
 	 public void setMoney(long money){
	 this.money = money;
	}
	@FieldConfig(fieldName = "num", fieldType =FieldTypeEnum.TYINT , description = "资费额对应的时间")
	private byte num;
	public byte getNum() {
	return num;
	}
 	 public void setNum(byte num){
	 this.num = num;
	}
	@FieldConfig(fieldName = "tariffPackagesName", fieldType =FieldTypeEnum.CHAR36 , description = "资费套餐的名字")
	private String tariffPackagesName;
	public String getTariffPackagesName() {
	return tariffPackagesName;
	}
 	 public void setTariffPackagesName(String tariffPackagesName){
	 this.tariffPackagesName = tariffPackagesName;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("tariffPackagesCfgId", String.valueOf(this.tariffPackagesCfgId));
	 entityMap.put("type", String.valueOf(this.type));
	 entityMap.put("money", String.valueOf(this.money));
	 entityMap.put("num", String.valueOf(this.num));
	 entityMap.put("tariffPackagesName", this.tariffPackagesName);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.tariffPackagesCfgId=Long.parseLong(entityMap.get("tariffPackagesCfgId"));
	 this.type=Byte.parseByte(entityMap.get("type"));
	 this.money=Long.parseLong(entityMap.get("money"));
	 this.num=Byte.parseByte(entityMap.get("num"));
	 this.tariffPackagesName=entityMap.get("tariffPackagesName");

    }
}
