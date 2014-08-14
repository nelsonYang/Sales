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
        entityName = EntityNames.consuption,
        keyFields = {"consuptionId"},
        useCache = true,
        timeToIdleSeconds = 3000,
        timeToLiveSeconds = 6000)
public class Consuption extends Entity {

    public PrimaryKey getKeyValue() {
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("consuptionId", String.valueOf(this.consuptionId));
        return primaryKey;
    }
    @FieldConfig(fieldName = "consuptionId", fieldType = FieldTypeEnum.BIG_INT, description = "消费记录id")
    private long consuptionId;

    public long getConsuptionId() {
        return consuptionId;
    }

    public void setConsuptionId(long consuptionId) {
        this.consuptionId = consuptionId;
    }
    @FieldConfig(fieldName = "couponId", fieldType = FieldTypeEnum.BIG_INT, description = "优惠券id")
    private long couponId;

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }
    @FieldConfig(fieldName = "couponName", fieldType = FieldTypeEnum.CHAR36, description = "优惠券名称")
    private String couponName;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }
    @FieldConfig(fieldName = "availableCount", fieldType = FieldTypeEnum.TYINT, description = "可用次数-1表示不限")
    private byte availableCount;

    public byte getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(byte availableCount) {
        this.availableCount = availableCount;
    }
    @FieldConfig(fieldName = "consumeMoney", fieldType = FieldTypeEnum.DOUBLE, description = "消费金额")
    private double consumeMoney;

    public double getConsumeMoney() {
        return consumeMoney;
    }

    public void setConsumeMoney(double consumeMoney) {
        this.consumeMoney = consumeMoney;
    }
    @FieldConfig(fieldName = "operatorName", fieldType = FieldTypeEnum.CHAR11, description = "操作员")
    private String operatorName;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    @FieldConfig(fieldName = "createTime", fieldType = FieldTypeEnum.DATETIME, description = "记录时间")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @FieldConfig(fieldName = "companyId", fieldType = FieldTypeEnum.BIG_INT, description = "")
    private long companyId;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public Map<String, String> toMap() {
        Map<String, String> entityMap = new HashMap<String, String>(16, 1);
        entityMap.put("consuptionId", String.valueOf(this.consuptionId));
        entityMap.put("couponId", String.valueOf(this.couponId));
        entityMap.put("couponName", this.couponName);
        entityMap.put("companyId", String.valueOf(this.companyId));
        entityMap.put("availableCount", String.valueOf(this.availableCount));
        entityMap.put("consumeMoney", String.valueOf(this.consumeMoney));
        entityMap.put("operatorName", this.operatorName);
        try {
            entityMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("createTime", "");
        }
        return entityMap;

    }

    public void parseMap(Map<String, String> entityMap) {
        this.consuptionId = Long.parseLong(entityMap.get("consuptionId"));
        this.couponId = Long.parseLong(entityMap.get("couponId"));
        this.companyId = Long.parseLong(entityMap.get("companyId"));
        this.couponName = entityMap.get("couponName");
        this.availableCount = Byte.parseByte(entityMap.get("availableCount"));
        this.consumeMoney = Double.parseDouble(entityMap.get("consumeMoney"));
        this.operatorName = entityMap.get("operatorName");

        try {
            this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("createTime", "");
        }

    }
}
