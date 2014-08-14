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
        entityName = EntityNames.coupon,
        keyFields = {"couponId"},
        useCache = true,
        timeToIdleSeconds = 3000,
        timeToLiveSeconds = 6000)
public class Coupon extends Entity {

    public PrimaryKey getKeyValue() {
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("couponId", String.valueOf(this.couponId));
        return primaryKey;
    }
    @FieldConfig(fieldName = "couponId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券id")
    private long couponId;

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }
    @FieldConfig(fieldName = "companyId", fieldType = FieldTypeEnum.BIG_INT, description = "公司id")
    private long companyId;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
    @FieldConfig(fieldName = "couponConfigId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券配置id")
    private long couponConfigId;

    public long getCouponConfigId() {
        return couponConfigId;
    }

    public void setCouponConfigId(long couponConfigId) {
        this.couponConfigId = couponConfigId;
    }
    @FieldConfig(fieldName = "createTime", fieldType = FieldTypeEnum.DATETIME, description = "创建时间")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @FieldConfig(fieldName = "status", fieldType = FieldTypeEnum.TYINT, description = "活动0-禁止1-启用")
    private byte status;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
    @FieldConfig(fieldName = "couponName", fieldType = FieldTypeEnum.CHAR36, description = "优惠券名称")
    private String couponName;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
    @FieldConfig(fieldName = "availableCount", fieldType = FieldTypeEnum.TYINT, description = "不限")
    private byte availableCount;

    public byte getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(byte availableCount) {
        this.availableCount = availableCount;
    }
    @FieldConfig(fieldName = "takeConditionType", fieldType = FieldTypeEnum.TYINT, description = "领取条件-1不限1会员")
    private byte takeConditionType;

    public byte getTakeConditionType() {
        return takeConditionType;
    }

    public void setTakeConditionType(byte takeConditionType) {
        this.takeConditionType = takeConditionType;
    }
    @FieldConfig(fieldName = "couponDesc", fieldType = FieldTypeEnum.CHAR128, description = "优惠券说明")
    private String couponDesc;

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }
    @FieldConfig(fieldName = "effectiveStartTime", fieldType = FieldTypeEnum.DATETIME, description = "有效开始时间")
    private Date effectiveStartTime;

    public Date getEffectiveStartTime() {
        return effectiveStartTime;
    }

    public void setEffectiveStartTime(Date effectiveStartTime) {
        this.effectiveStartTime = effectiveStartTime;
    }
    @FieldConfig(fieldName = "effectiveEndTime", fieldType = FieldTypeEnum.DATETIME, description = "有效结束时间")
    private Date effectiveEndTime;

    public Date getEffectiveEndTime() {
        return effectiveEndTime;
    }

    public void setEffectiveEndTime(Date effectiveEndTime) {
        this.effectiveEndTime = effectiveEndTime;
    }
    @FieldConfig(fieldName = "messageTitle", fieldType = FieldTypeEnum.CHAR36, description = "消息标题")
    private String messageTitle;

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
    @FieldConfig(fieldName = "messageURL", fieldType = FieldTypeEnum.CHAR64, description = "消息图片地址")
    private String messageURL;

    public String getMessageURL() {
        return messageURL;
    }

    public void setMessageURL(String messageURL) {
        this.messageURL = messageURL;
    }
    @FieldConfig(fieldName = "messageDesc", fieldType = FieldTypeEnum.CHAR128, description = "消息描述")
    private String messageDesc;

    public String getMessageDesc() {
        return messageDesc;
    }

    public void setMessageDesc(String messageDesc) {
        this.messageDesc = messageDesc;
    }
    @FieldConfig(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Map<String, String> toMap() {
        Map<String, String> entityMap = new HashMap<String, String>(16, 1);
        entityMap.put("couponId", String.valueOf(this.couponId));
        entityMap.put("companyId", String.valueOf(this.companyId));
        entityMap.put("couponConfigId", String.valueOf(this.couponConfigId));
        try {
            entityMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("createTime", "");
        }
        entityMap.put("status", String.valueOf(this.status));
        entityMap.put("couponName", this.couponName);
        entityMap.put("availableCount", String.valueOf(this.availableCount));
        entityMap.put("takeConditionType", String.valueOf(this.takeConditionType));
        entityMap.put("couponDesc", this.couponDesc);
        try {
            entityMap.put("effectiveStartTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(effectiveStartTime));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("effectiveStartTime", "");
        }
        try {
            entityMap.put("effectiveEndTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(effectiveEndTime));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("effectiveEndTime", "");
        }
        entityMap.put("messageTitle", this.messageTitle);
        entityMap.put("messageURL", this.messageURL);
        entityMap.put("messageDesc", this.messageDesc);
        entityMap.put("keyword", this.keyword);
        return entityMap;

    }

    public void parseMap(Map<String, String> entityMap) {
        this.couponId = Long.parseLong(entityMap.get("couponId"));
        this.companyId = Long.parseLong(entityMap.get("companyId"));
        this.couponConfigId = Long.parseLong(entityMap.get("couponConfigId"));

        try {
            this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("createTime", "");
        }
        this.status = Byte.parseByte(entityMap.get("status"));
        this.couponName = entityMap.get("couponName");
        this.availableCount = Byte.parseByte(entityMap.get("availableCount"));
        this.takeConditionType = Byte.parseByte(entityMap.get("takeConditionType"));
        this.couponDesc = entityMap.get("couponDesc");

        try {
            this.effectiveStartTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("effectiveStartTime"));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("effectiveStartTime", "");
        }

        try {
            this.effectiveEndTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("effectiveEndTime"));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("effectiveEndTime", "");
        }
        this.messageTitle = entityMap.get("messageTitle");
        this.messageURL = entityMap.get("messageURL");
        this.messageDesc = entityMap.get("messageDesc");
        this.keyword = entityMap.get("keyword");

    }
}
