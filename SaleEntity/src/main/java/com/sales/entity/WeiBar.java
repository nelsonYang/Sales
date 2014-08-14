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
    entityName = EntityNames.weiBar, 
    keyFields = {"weiBarId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class WeiBar extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("weiBarId", String.valueOf(this.weiBarId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "weiBarId", fieldType =FieldTypeEnum.BIG_INT , description = "微吧id")
	private long weiBarId;
	public long getWeiBarId() {
	return weiBarId;
	}
 	 public void setWeiBarId(long weiBarId){
	 this.weiBarId = weiBarId;
	}
	@FieldConfig(fieldName = "weiBarConfigId", fieldType =FieldTypeEnum.BIG_INT , description = "微吧配置id")
	private long weiBarConfigId;
	public long getWeiBarConfigId() {
	return weiBarConfigId;
	}
 	 public void setWeiBarConfigId(long weiBarConfigId){
	 this.weiBarConfigId = weiBarConfigId;
	}
	@FieldConfig(fieldName = "weiBarTitle", fieldType =FieldTypeEnum.CHAR36 , description = "微吧标题")
	private String weiBarTitle;
	public String getWeiBarTitle() {
	return weiBarTitle;
	}
 	 public void setWeiBarTitle(String weiBarTitle){
	 this.weiBarTitle = weiBarTitle;
	}
	@FieldConfig(fieldName = "weiBarContent", fieldType =FieldTypeEnum.CHAR128 , description = "微吧内容")
	private String weiBarContent;
	public String getWeiBarContent() {
	return weiBarContent;
	}
 	 public void setWeiBarContent(String weiBarContent){
	 this.weiBarContent = weiBarContent;
	}
	@FieldConfig(fieldName = "memberId", fieldType =FieldTypeEnum.BIG_INT , description = "会员id")
	private long memberId;
	public long getMemberId() {
	return memberId;
	}
 	 public void setMemberId(long memberId){
	 this.memberId = memberId;
	}
	@FieldConfig(fieldName = "parentId", fieldType =FieldTypeEnum.BIG_INT , description = "文章的id")
	private long parentId;
	public long getParentId() {
	return parentId;
	}
 	 public void setParentId(long parentId){
	 this.parentId = parentId;
	}
	@FieldConfig(fieldName = "memberNo", fieldType =FieldTypeEnum.CHAR36 , description = "会员no")
	private String memberNo;
	public String getMemberNo() {
	return memberNo;
	}
 	 public void setMemberNo(String memberNo){
	 this.memberNo = memberNo;
	}
	@FieldConfig(fieldName = "memberName", fieldType =FieldTypeEnum.CHAR36 , description = "会员名字")
	private String memberName;
	public String getMemberName() {
	return memberName;
	}
 	 public void setMemberName(String memberName){
	 this.memberName = memberName;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "发布时间")
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



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("weiBarId", String.valueOf(this.weiBarId));
	 entityMap.put("weiBarConfigId", String.valueOf(this.weiBarConfigId));
	 entityMap.put("weiBarTitle", this.weiBarTitle);
	 entityMap.put("weiBarContent", this.weiBarContent);
	 entityMap.put("memberId", String.valueOf(this.memberId));
	 entityMap.put("parentId", String.valueOf(this.parentId));
	 entityMap.put("memberNo", this.memberNo);
	 entityMap.put("memberName", this.memberName);
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.weiBarId=Long.parseLong(entityMap.get("weiBarId"));
	 this.weiBarConfigId=Long.parseLong(entityMap.get("weiBarConfigId"));
	 this.weiBarTitle=entityMap.get("weiBarTitle");
	 this.weiBarContent=entityMap.get("weiBarContent");
	 this.memberId=Long.parseLong(entityMap.get("memberId"));
	 this.parentId=Long.parseLong(entityMap.get("parentId"));
	 this.memberNo=entityMap.get("memberNo");
	 this.memberName=entityMap.get("memberName");
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.companyId=Long.parseLong(entityMap.get("companyId"));

    }
}
