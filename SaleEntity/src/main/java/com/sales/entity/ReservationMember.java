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
        entityName = EntityNames.reservationMember,
        keyFields = {"reservationMemberId"},
        useCache = true,
        timeToIdleSeconds = 3000,
        timeToLiveSeconds = 6000)
public class ReservationMember extends Entity {

    public PrimaryKey getKeyValue() {
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("reservationMemberId", String.valueOf(this.reservationMemberId));
        return primaryKey;
    }
    @FieldConfig(fieldName = "reservationMemberId", fieldType = FieldTypeEnum.BIG_INT, description = "预约会员id")
    private long reservationMemberId;

    public long getReservationMemberId() {
        return reservationMemberId;
    }

    public void setReservationMemberId(long reservationMemberId) {
        this.reservationMemberId = reservationMemberId;
    }
    @FieldConfig(fieldName = "memberId", fieldType = FieldTypeEnum.BIG_INT, description = "预约会员id")
    private long memberId;

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
    @FieldConfig(fieldName = "reservationId", fieldType = FieldTypeEnum.BIG_INT, description = "预约id")
    private long reservationId;

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }
    @FieldConfig(fieldName = "reservationMemberName", fieldType = FieldTypeEnum.CHAR36, description = "预约人的姓名")
    private String reservationMemberName;

    public String getReservationMemberName() {
        return reservationMemberName;
    }

    public void setReservationMemberName(String reservationMemberName) {
        this.reservationMemberName = reservationMemberName;
    }
    @FieldConfig(fieldName = "reservationMemberTelephone", fieldType = FieldTypeEnum.CHAR36, description = "预约人的手机")
    private String reservationMemberTelephone;

    public String getReservationMemberTelephone() {
        return reservationMemberTelephone;
    }

    public void setReservationMemberTelephone(String reservationMemberTelephone) {
        this.reservationMemberTelephone = reservationMemberTelephone;
    }
    @FieldConfig(fieldName = "reservationMemberDateTime", fieldType = FieldTypeEnum.DATETIME, description = "预约的时间")
    private Date reservationMemberDateTime;

    public Date getReservationMemberDateTime() {
        return reservationMemberDateTime;
    }

    public void setReservationMemberDateTime(Date reservationMemberDateTime) {
        this.reservationMemberDateTime = reservationMemberDateTime;
    }
    @FieldConfig(fieldName = "reservationMemberAddress", fieldType = FieldTypeEnum.CHAR64, description = "预约的地址")
    private String reservationMemberAddress;

    public String getReservationMemberAddress() {
        return reservationMemberAddress;
    }

    public void setReservationMemberAddress(String reservationMemberAddress) {
        this.reservationMemberAddress = reservationMemberAddress;
    }
    @FieldConfig(fieldName = "reservationMemberDesc", fieldType = FieldTypeEnum.CHAR64, description = "备注")
    private String reservationMemberDesc;

    public String getReservationMemberDesc() {
        return reservationMemberDesc;
    }

    public void setReservationMemberDesc(String reservationMemberDesc) {
        this.reservationMemberDesc = reservationMemberDesc;
    }
    @FieldConfig(fieldName = "createTime", fieldType = FieldTypeEnum.DATETIME, description = "创建时间")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @FieldConfig(fieldName = "companyId", fieldType = FieldTypeEnum.BIG_INT, description = "公司id")
    private long companyId;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public Map<String, String> toMap() {
        Map<String, String> entityMap = new HashMap<String, String>(16, 1);
        entityMap.put("reservationMemberId", String.valueOf(this.reservationMemberId));
        entityMap.put("memberId", String.valueOf(this.memberId));
        entityMap.put("reservationId", String.valueOf(this.reservationId));
        entityMap.put("reservationMemberName", this.reservationMemberName);
        entityMap.put("reservationMemberTelephone", this.reservationMemberTelephone);
        try {
            entityMap.put("reservationMemberDateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reservationMemberDateTime));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("reservationMemberDateTime", "");
        }
        entityMap.put("reservationMemberAddress", this.reservationMemberAddress);
        entityMap.put("reservationMemberDesc", this.reservationMemberDesc);
        try {
            entityMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("createTime", "");
        }
        entityMap.put("companyId", String.valueOf(this.companyId));
        return entityMap;

    }

    public void parseMap(Map<String, String> entityMap) {
        this.reservationMemberId = Long.parseLong(entityMap.get("reservationMemberId"));
        this.memberId = Long.parseLong(entityMap.get("memberId"));
        this.reservationId = Long.parseLong(entityMap.get("reservationId"));
        this.reservationMemberName = entityMap.get("reservationMemberName");
        this.reservationMemberTelephone = entityMap.get("reservationMemberTelephone");

        try {
            this.reservationMemberDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("reservationMemberDateTime"));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("reservationMemberDateTime", "");
        }
        this.reservationMemberAddress = entityMap.get("reservationMemberAddress");
        this.reservationMemberDesc = entityMap.get("reservationMemberDesc");

        try {
            this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("createTime", "");
        }
        this.companyId = Long.parseLong(entityMap.get("companyId"));

    }
}
