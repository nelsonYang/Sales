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
    entityName = EntityNames.couponMember, 
    keyFields = {"couponMemberId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class CouponMember extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("couponMemberId", String.valueOf(this.couponMemberId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "couponMemberId", fieldType =FieldTypeEnum.BIG_INT , description = "优惠券会员id")
	private long couponMemberId;
	public long getCouponMemberId() {
	return couponMemberId;
	}
 	 public void setCouponMemberId(long couponMemberId){
	 this.couponMemberId = couponMemberId;
	}
	@FieldConfig(fieldName = "memberId", fieldType =FieldTypeEnum.BIG_INT , description = "会员id")
	private long memberId;
	public long getMemberId() {
	return memberId;
	}
 	 public void setMemberId(long memberId){
	 this.memberId = memberId;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "")
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
	@FieldConfig(fieldName = "couponId", fieldType =FieldTypeEnum.BIG_INT , description = "优惠券id")
	private long couponId;
	public long getCouponId() {
	return couponId;
	}
 	 public void setCouponId(long couponId){
	 this.couponId = couponId;
	}
	@FieldConfig(fieldName = "memberNo", fieldType =FieldTypeEnum.CHAR36 , description = "手机号")
	private String memberNo;
	public String getMemberNo() {
	return memberNo;
	}
 	 public void setMemberNo(String memberNo){
	 this.memberNo = memberNo;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("couponMemberId", String.valueOf(this.couponMemberId));
	 entityMap.put("memberId", String.valueOf(this.memberId));
	 entityMap.put("companyId", String.valueOf(this.companyId));
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("couponId", String.valueOf(this.couponId));
	 entityMap.put("memberNo", this.memberNo);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.couponMemberId=Long.parseLong(entityMap.get("couponMemberId"));
	 this.memberId=Long.parseLong(entityMap.get("memberId"));
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.couponId=Long.parseLong(entityMap.get("couponId"));
	 this.memberNo=entityMap.get("memberNo");

    }
}
