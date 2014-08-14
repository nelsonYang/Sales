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
    entityName = EntityNames.responseToolConfig, 
    keyFields = {"responseToolsConfigId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class ResponseToolConfig extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("responseToolsConfigId", String.valueOf(this.responseToolsConfigId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "responseToolsConfigId", fieldType =FieldTypeEnum.INT , description = "配置id")
	private int responseToolsConfigId;
	public int getResponseToolsConfigId() {
	return responseToolsConfigId;
	}
 	 public void setResponseToolsConfigId(int responseToolsConfigId){
	 this.responseToolsConfigId = responseToolsConfigId;
	}
	@FieldConfig(fieldName = "responseKeyword", fieldType =FieldTypeEnum.CHAR36 , description = "配置关键子")
	private String responseKeyword;
	public String getResponseKeyword() {
	return responseKeyword;
	}
 	 public void setResponseKeyword(String responseKeyword){
	 this.responseKeyword = responseKeyword;
	}
	@FieldConfig(fieldName = "responseToolName", fieldType =FieldTypeEnum.CHAR36 , description = "配置的关键名称")
	private String responseToolName;
	public String getResponseToolName() {
	return responseToolName;
	}
 	 public void setResponseToolName(String responseToolName){
	 this.responseToolName = responseToolName;
	}
	@FieldConfig(fieldName = "type", fieldType =FieldTypeEnum.TYINT , description = "回复的类型")
	private byte type;
	public byte getType() {
	return type;
	}
 	 public void setType(byte type){
	 this.type = type;
	}
	@FieldConfig(fieldName = "isClose", fieldType =FieldTypeEnum.TYINT , description = "1-开启2关闭")
	private byte isClose;
	public byte getIsClose() {
	return isClose;
	}
 	 public void setIsClose(byte isClose){
	 this.isClose = isClose;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.CHAR36 , description = "公司id")
	private String companyId;
	public String getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(String companyId){
	 this.companyId = companyId;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("responseToolsConfigId", String.valueOf(this.responseToolsConfigId));
	 entityMap.put("responseKeyword", this.responseKeyword);
	 entityMap.put("responseToolName", this.responseToolName);
	 entityMap.put("type", String.valueOf(this.type));
	 entityMap.put("isClose", String.valueOf(this.isClose));
	 entityMap.put("companyId", this.companyId);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.responseToolsConfigId=Integer.parseInt(entityMap.get("responseToolsConfigId"));
	 this.responseKeyword=entityMap.get("responseKeyword");
	 this.responseToolName=entityMap.get("responseToolName");
	 this.type=Byte.parseByte(entityMap.get("type"));
	 this.isClose=Byte.parseByte(entityMap.get("isClose"));
	 this.companyId=entityMap.get("companyId");

    }
}
