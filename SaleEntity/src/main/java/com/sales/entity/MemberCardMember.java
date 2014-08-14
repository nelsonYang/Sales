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
    entityName = EntityNames.memberCardMember, 
    keyFields = {"memberCardMemberId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class MemberCardMember extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("memberCardMemberId", String.valueOf(this.memberCardMemberId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "memberCardMemberId", fieldType =FieldTypeEnum.BIG_INT , description = "会员卡记录id")
	private long memberCardMemberId;
	public long getMemberCardMemberId() {
	return memberCardMemberId;
	}
 	 public void setMemberCardMemberId(long memberCardMemberId){
	 this.memberCardMemberId = memberCardMemberId;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "memberCardId", fieldType =FieldTypeEnum.BIG_INT , description = "会员卡id")
	private long memberCardId;
	public long getMemberCardId() {
	return memberCardId;
	}
 	 public void setMemberCardId(long memberCardId){
	 this.memberCardId = memberCardId;
	}
	@FieldConfig(fieldName = "memberId", fieldType =FieldTypeEnum.BIG_INT , description = "会员id")
	private long memberId;
	public long getMemberId() {
	return memberId;
	}
 	 public void setMemberId(long memberId){
	 this.memberId = memberId;
	}
	@FieldConfig(fieldName = "memberNO", fieldType =FieldTypeEnum.CHAR36 , description = "会员号")
	private String memberNO;
	public String getMemberNO() {
	return memberNO;
	}
 	 public void setMemberNO(String memberNO){
	 this.memberNO = memberNO;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "会员领取的时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("memberCardMemberId", String.valueOf(this.memberCardMemberId));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("memberCardId", String.valueOf(this.memberCardId));
	 entityMap.put("memberId", String.valueOf(this.memberId));
	 entityMap.put("memberNO", this.memberNO);
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.memberCardMemberId=Long.parseLong(entityMap.get("memberCardMemberId"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.memberCardId=Long.parseLong(entityMap.get("memberCardId"));
	 this.memberId=Long.parseLong(entityMap.get("memberId"));
	 this.memberNO=entityMap.get("memberNO");
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}

    }
}
