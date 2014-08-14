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
    entityName = EntityNames.member, 
    keyFields = {"memberId"},
    useCache = true,
    timeToIdleSeconds=3000,
    timeToLiveSeconds=6000
)
public class Member extends Entity {
  
 
    public PrimaryKey getKeyValue() {
       	PrimaryKey primaryKey = new PrimaryKey();
	 primaryKey.putKeyField("memberId", String.valueOf(this.memberId));
	 return primaryKey;
    }

    	@FieldConfig(fieldName = "memberId", fieldType =FieldTypeEnum.INT , description = "会员id")
	private int memberId;
	public int getMemberId() {
	return memberId;
	}
 	 public void setMemberId(int memberId){
	 this.memberId = memberId;
	}
	@FieldConfig(fieldName = "realName", fieldType =FieldTypeEnum.CHAR36 , description = "真实姓名")
	private String realName;
	public String getRealName() {
	return realName;
	}
 	 public void setRealName(String realName){
	 this.realName = realName;
	}
	@FieldConfig(fieldName = "weixinNo", fieldType =FieldTypeEnum.CHAR36 , description = "")
	private String weixinNo;
	public String getWeixinNo() {
	return weixinNo;
	}
 	 public void setWeixinNo(String weixinNo){
	 this.weixinNo = weixinNo;
	}
	@FieldConfig(fieldName = "qqNO", fieldType =FieldTypeEnum.CHAR36 , description = "qq号")
	private String qqNO;
	public String getQqNO() {
	return qqNO;
	}
 	 public void setQqNO(String qqNO){
	 this.qqNO = qqNO;
	}
	@FieldConfig(fieldName = "email", fieldType =FieldTypeEnum.CHAR36 , description = "邮箱")
	private String email;
	public String getEmail() {
	return email;
	}
 	 public void setEmail(String email){
	 this.email = email;
	}
	@FieldConfig(fieldName = "mobile", fieldType =FieldTypeEnum.CHAR36 , description = "手机")
	private String mobile;
	public String getMobile() {
	return mobile;
	}
 	 public void setMobile(String mobile){
	 this.mobile = mobile;
	}
	@FieldConfig(fieldName = "birthday", fieldType =FieldTypeEnum.DATE , description = "生日")
	private Date birthday;
	public Date getBirthday() {
	return birthday;
	}
 	 public void setBirthday(Date birthday){
	 this.birthday = birthday;
	}
	@FieldConfig(fieldName = "createTime", fieldType =FieldTypeEnum.DATETIME , description = "创建时间")
	private Date createTime;
	public Date getCreateTime() {
	return createTime;
	}
 	 public void setCreateTime(Date createTime){
	 this.createTime = createTime;
	}
	@FieldConfig(fieldName = "memberNO", fieldType =FieldTypeEnum.CHAR36 , description = "会员号")
	private String memberNO;
	public String getMemberNO() {
	return memberNO;
	}
 	 public void setMemberNO(String memberNO){
	 this.memberNO = memberNO;
	}
	@FieldConfig(fieldName = "address", fieldType =FieldTypeEnum.CHAR128 , description = "会员地址")
	private String address;
	public String getAddress() {
	return address;
	}
 	 public void setAddress(String address){
	 this.address = address;
	}
	@FieldConfig(fieldName = "companyId", fieldType =FieldTypeEnum.BIG_INT , description = "公司id")
	private long companyId;
	public long getCompanyId() {
	return companyId;
	}
 	 public void setCompanyId(long companyId){
	 this.companyId = companyId;
	}
	@FieldConfig(fieldName = "integration", fieldType =FieldTypeEnum.BIG_INT , description = "积分")
	private long integration;
	public long getIntegration() {
	return integration;
	}
 	 public void setIntegration(long integration){
	 this.integration = integration;
	}
	@FieldConfig(fieldName = "source", fieldType =FieldTypeEnum.TYINT , description = "来源1-注册2-微信")
	private byte source;
	public byte getSource() {
	return source;
	}
 	 public void setSource(byte source){
	 this.source = source;
	}
	@FieldConfig(fieldName = "password", fieldType =FieldTypeEnum.CHAR36 , description = "密码")
	private String password;
	public String getPassword() {
	return password;
	}
 	 public void setPassword(String password){
	 this.password = password;
	}



 
    public Map<String, String> toMap() {
        	Map<String, String> entityMap = new HashMap<String, String>(16, 1);
	 entityMap.put("memberId", String.valueOf(this.memberId));
	 entityMap.put("realName", this.realName);
	 entityMap.put("weixinNo", this.weixinNo);
	 entityMap.put("qqNO", this.qqNO);
	 entityMap.put("email", this.email);
	 entityMap.put("mobile", this.mobile);
	try{
	 entityMap.put("birthday",new SimpleDateFormat("yyyy-MM-dd").format(birthday));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("birthday","");
	}
	try{
	 entityMap.put("createTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 entityMap.put("memberNO", this.memberNO);
	 entityMap.put("address", this.address);
	 entityMap.put("companyId", String.valueOf(this.companyId));
	 entityMap.put("integration", String.valueOf(this.integration));
	 entityMap.put("source", String.valueOf(this.source));
	 entityMap.put("password", this.password);
	 return entityMap;

    }
    
   
    public void parseMap(Map<String, String> entityMap) {
        	 this.memberId=Integer.parseInt(entityMap.get("memberId"));
	 this.realName=entityMap.get("realName");
	 this.weixinNo=entityMap.get("weixinNo");
	 this.qqNO=entityMap.get("qqNO");
	 this.email=entityMap.get("email");
	 this.mobile=entityMap.get("mobile");
	try{
	 this.birthday=new SimpleDateFormat("yyyy-MM-dd").parse(entityMap.get("birthday"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("birthday","");
	}
	try{
	 this.createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
	}catch(Exception ex){ 
	System.err.print(ex);
	 entityMap.put("createTime","");
	}
	 this.memberNO=entityMap.get("memberNO");
	 this.address=entityMap.get("address");
	 this.companyId=Long.parseLong(entityMap.get("companyId"));
	 this.integration=Long.parseLong(entityMap.get("integration"));
	 this.source=Byte.parseByte(entityMap.get("source"));
	 this.password=entityMap.get("password");

    }
}
