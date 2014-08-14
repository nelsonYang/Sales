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
    entityName = EntityNames.weixinMessage, 
    keyFields = {"weixinMessageId"},
    useCache = false
)
public class WeixinMessage extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("weixinMessageId", String.valueOf(this.weixinMessageId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "weixinMessageId", fieldType =FieldTypeEnum.BIG_INT , description = "")
	private long weixinMessageId;
	public long getWeixinMessageId() {
	return weixinMessageId;
	}
 	 public void setWeixinMessageId(long weixinMessageId){
	 this.weixinMessageId = weixinMessageId;
	}
	@FieldConfig(fieldName = "fromUser", fieldType =FieldTypeEnum.CHAR36 , description = "消息的来源")
	private String fromUser;
	public String getFromUser() {
	return fromUser;
	}
 	 public void setFromUser(String fromUser){
	 this.fromUser = fromUser;
	}
	@FieldConfig(fieldName = "toUser", fieldType =FieldTypeEnum.CHAR36 , description = "消息的接收者")
	private String toUser;
	public String getToUser() {
	return toUser;
	}
 	 public void setToUser(String toUser){
	 this.toUser = toUser;
	}
	@FieldConfig(fieldName = "type", fieldType =FieldTypeEnum.TYINT , description = "消息的类型")
	private byte type;
	public byte getType() {
	return type;
	}
 	 public void setType(byte type){
	 this.type = type;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
	}
	@FieldConfig(fieldName = "content", fieldType =FieldTypeEnum.CHAR128 , description = "消息的文本内容")
	private String content;
	public String getContent() {
	return content;
	}
 	 public void setContent(String content){
	 this.content = content;
	}
	@FieldConfig(fieldName = "imageURL", fieldType =FieldTypeEnum.CHAR64 , description = "图片的内容")
	private String imageURL;
	public String getImageURL() {
	return imageURL;
	}
 	 public void setImageURL(String imageURL){
	 this.imageURL = imageURL;
	}
	@FieldConfig(fieldName = "vedioURL", fieldType =FieldTypeEnum.CHAR64 , description = "图片的内容")
	private String vedioURL;
	public String getVedioURL() {
	return vedioURL;
	}
 	 public void setVedioURL(String vedioURL){
	 this.vedioURL = vedioURL;
	}
	@FieldConfig(fieldName = "audioURL", fieldType =FieldTypeEnum.CHAR64 , description = "音频的内容")
	private String audioURL;
	public String getAudioURL() {
	return audioURL;
	}
 	 public void setAudioURL(String audioURL){
	 this.audioURL = audioURL;
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
	 entityMap.put("weixinMessageId", String.valueOf(this.weixinMessageId));
	 entityMap.put("fromUser", this.fromUser);
	 entityMap.put("toUser", this.toUser);
	 entityMap.put("type", String.valueOf(this.type));
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("content", this.content);
	 entityMap.put("imageURL", this.imageURL);
	 entityMap.put("vedioURL", this.vedioURL);
	 entityMap.put("audioURL", this.audioURL);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.weixinMessageId=Long.parseLong(entityMap.get("weixinMessageId"));
	 this.fromUser=entityMap.get("fromUser");
	 this.toUser=entityMap.get("toUser");
	 this.type=Byte.parseByte(entityMap.get("type"));

	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.content=entityMap.get("content");
	 this.imageURL=entityMap.get("imageURL");
	 this.vedioURL=entityMap.get("vedioURL");
	 this.audioURL=entityMap.get("audioURL");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
