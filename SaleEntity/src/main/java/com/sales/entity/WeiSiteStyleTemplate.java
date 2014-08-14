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
    entityName = EntityNames.weiSiteStyleTemplate, 
    keyFields = {"weiSiteStyleTemplateId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class WeiSiteStyleTemplate extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("weiSiteStyleTemplateId", String.valueOf(this.weiSiteStyleTemplateId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "weiSiteStyleTemplateId", fieldType =FieldTypeEnum.BIG_INT , description = "微站的模板id")
	private long weiSiteStyleTemplateId;
	public long getWeiSiteStyleTemplateId() {
	return weiSiteStyleTemplateId;
	}
 	 public void setWeiSiteStyleTemplateId(long weiSiteStyleTemplateId){
	 this.weiSiteStyleTemplateId = weiSiteStyleTemplateId;
	}
	@FieldConfig(fieldName = "weiSiteStyleTemplateCfgId", fieldType =FieldTypeEnum.BIG_INT , description = "所属的模板id")
	private long weiSiteStyleTemplateCfgId;
	public long getWeiSiteStyleTemplateCfgId() {
	return weiSiteStyleTemplateCfgId;
	}
 	 public void setWeiSiteStyleTemplateCfgId(long weiSiteStyleTemplateCfgId){
	 this.weiSiteStyleTemplateCfgId = weiSiteStyleTemplateCfgId;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司的id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "创建时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
	}
	@FieldConfig(fieldName = "isValid", fieldType =FieldTypeEnum.TYINT , description = "是否生效")
	private byte isValid;
	public byte getIsValid() {
	return isValid;
	}
 	 public void setIsValid(byte isValid){
	 this.isValid = isValid;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("weiSiteStyleTemplateId", String.valueOf(this.weiSiteStyleTemplateId));
	 entityMap.put("weiSiteStyleTemplateCfgId", String.valueOf(this.weiSiteStyleTemplateCfgId));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("isValid", String.valueOf(this.isValid));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.weiSiteStyleTemplateId=Long.parseLong(entityMap.get("weiSiteStyleTemplateId"));
	 this.weiSiteStyleTemplateCfgId=Long.parseLong(entityMap.get("weiSiteStyleTemplateCfgId"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.isValid=Byte.parseByte(entityMap.get("isValid"));

    }
}
