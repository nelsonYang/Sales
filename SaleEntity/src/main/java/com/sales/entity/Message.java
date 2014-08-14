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
    entityName = EntityNames.message, 
    keyFields = {"messageId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class Message extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("messageId", String.valueOf(this.messageId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "messageId", fieldType =FieldTypeEnum.BIG_INT , description = "消息id")
	private long messageId;
	public long getMessageId() {
	return messageId;
	}
 	 public void setMessageId(long messageId){
	 this.messageId = messageId;
	}
	@FieldConfig(fieldName = "imageURL", fieldType =FieldTypeEnum.CHAR64 , description = "消息图片的url")
	private String imageURL;
	public String getImageURL() {
	return imageURL;
	}
 	 public void setImageURL(String imageURL){
	 this.imageURL = imageURL;
	}
	@FieldConfig(fieldName = "content", fieldType =FieldTypeEnum.CHAR128 , description = "消息内容")
	private String content;
	public String getContent() {
	return content;
	}
 	 public void setContent(String content){
	 this.content = content;
	}
	@FieldConfig(fieldName = "openID", fieldType =FieldTypeEnum.CHAR64 , description = "发送对象")
	private String openID;
	public String getOpenID() {
	return openID;
	}
 	 public void setOpenID(String openID){
	 this.openID = openID;
	}
	@FieldConfig(fieldName = "type", fieldType =FieldTypeEnum.TYINT , description = "消息类别")
	private byte type;
	public byte getType() {
	return type;
	}
 	 public void setType(byte type){
	 this.type = type;
	}
	@FieldConfig(fieldName = "isSend", fieldType =FieldTypeEnum.TYINT , description = "是否已经发送")
	private byte isSend;
	public byte getIsSend() {
	return isSend;
	}
 	 public void setIsSend(byte isSend){
	 this.isSend = isSend;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.CHAR36 , description = "消息创建的时间")
	private String createTime;
	public String getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(String createTime){
	 this.createTime = createTime;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("messageId", String.valueOf(this.messageId));
	 entityMap.put("imageURL", this.imageURL);
	 entityMap.put("content", this.content);
	 entityMap.put("openID", this.openID);
	 entityMap.put("type", String.valueOf(this.type));
	 entityMap.put("isSend", String.valueOf(this.isSend));
	 entityMap.put("createTime", this.createTime);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.messageId=Long.parseLong(entityMap.get("messageId"));
	 this.imageURL=entityMap.get("imageURL");
	 this.content=entityMap.get("content");
	 this.openID=entityMap.get("openID");
	 this.type=Byte.parseByte(entityMap.get("type"));
	 this.isSend=Byte.parseByte(entityMap.get("isSend"));
	 this.createTime=entityMap.get("createTime");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
