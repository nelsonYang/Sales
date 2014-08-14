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
    entityName = EntityNames.weiSiteTemplateCfg, 
    keyFields = {"weiSiteTemplateCfgId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class WeiSiteTemplateCfg extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("weiSiteTemplateCfgId", String.valueOf(this.weiSiteTemplateCfgId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "weiSiteTemplateCfgId", fieldType =FieldTypeEnum.BIG_INT , description = "微网站模板id")
	private long weiSiteTemplateCfgId;
	public long getWeiSiteTemplateCfgId() {
	return weiSiteTemplateCfgId;
	}
 	 public void setWeiSiteTemplateCfgId(long weiSiteTemplateCfgId){
	 this.weiSiteTemplateCfgId = weiSiteTemplateCfgId;
	}
	@FieldConfig(fieldName = "weiSiteTemplateName", fieldType =FieldTypeEnum.CHAR36 , description = "微网站模板名称")
	private String weiSiteTemplateName;
	public String getWeiSiteTemplateName() {
	return weiSiteTemplateName;
	}
 	 public void setWeiSiteTemplateName(String weiSiteTemplateName){
	 this.weiSiteTemplateName = weiSiteTemplateName;
	}
	@FieldConfig(fieldName = "type", fieldType =FieldTypeEnum.TYINT , description = "微网站类型")
	private byte type;
	public byte getType() {
	return type;
	}
 	 public void setType(byte type){
	 this.type = type;
	}
	@FieldConfig(fieldName = "weiSiteTemplateURL", fieldType =FieldTypeEnum.CHAR64 , description = "图片url")
	private String weiSiteTemplateURL;
	public String getWeiSiteTemplateURL() {
	return weiSiteTemplateURL;
	}
 	 public void setWeiSiteTemplateURL(String weiSiteTemplateURL){
	 this.weiSiteTemplateURL = weiSiteTemplateURL;
	}
	@FieldConfig(fieldName = "weiSiteTemplateHtml", fieldType =FieldTypeEnum.CHAR1024 , description = "模板的html")
	private String weiSiteTemplateHtml;
	public String getWeiSiteTemplateHtml() {
	return weiSiteTemplateHtml;
	}
 	 public void setWeiSiteTemplateHtml(String weiSiteTemplateHtml){
	 this.weiSiteTemplateHtml = weiSiteTemplateHtml;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("weiSiteTemplateCfgId", String.valueOf(this.weiSiteTemplateCfgId));
	 entityMap.put("weiSiteTemplateName", this.weiSiteTemplateName);
	 entityMap.put("type", String.valueOf(this.type));
	 entityMap.put("weiSiteTemplateURL", this.weiSiteTemplateURL);
	 entityMap.put("weiSiteTemplateHtml", this.weiSiteTemplateHtml);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.weiSiteTemplateCfgId=Long.parseLong(entityMap.get("weiSiteTemplateCfgId"));
	 this.weiSiteTemplateName=entityMap.get("weiSiteTemplateName");
	 this.type=Byte.parseByte(entityMap.get("type"));
	 this.weiSiteTemplateURL=entityMap.get("weiSiteTemplateURL");
	 this.weiSiteTemplateHtml=entityMap.get("weiSiteTemplateHtml");

    }
}
