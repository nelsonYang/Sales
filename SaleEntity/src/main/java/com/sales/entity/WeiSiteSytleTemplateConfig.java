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
        entityName = EntityNames.weiSiteSytleTemplateConfig,
        keyFields = {"weiSiteSytleTemplateId"},
        useCache = true,
        timeToIdleSeconds = 3000,
        timeToLiveSeconds = 6000)
public class WeiSiteSytleTemplateConfig extends Entity {

    public PrimaryKey getKeyValue() {
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("weiSiteSytleTemplateId", String.valueOf(this.weiSiteSytleTemplateId));
        return primaryKey;
    }
    @FieldConfig(fieldName = "weiSiteSytleTemplateId", fieldType = FieldTypeEnum.BIG_INT, description = "微网站风格配置id")
    private long weiSiteSytleTemplateId;

    public long getWeiSiteSytleTemplateId() {
        return weiSiteSytleTemplateId;
    }

    public void setWeiSiteSytleTemplateId(long weiSiteSytleTemplateId) {
        this.weiSiteSytleTemplateId = weiSiteSytleTemplateId;
    }
    @FieldConfig(fieldName = "weiSiteSytleTemplateName", fieldType = FieldTypeEnum.CHAR36, description = "模板名称")
    private String weiSiteSytleTemplateName;

    public String getWeiSiteSytleTemplateName() {
        return weiSiteSytleTemplateName;
    }

    public void setWeiSiteSytleTemplateName(String weiSiteSytleTemplateName) {
        this.weiSiteSytleTemplateName = weiSiteSytleTemplateName;
    }
    @FieldConfig(fieldName = "weiSiteSytleTemplateURL", fieldType = FieldTypeEnum.CHAR64, description = "风格配置的url")
    private String weiSiteSytleTemplateURL;

    public String getWeiSiteSytleTemplateURL() {
        return weiSiteSytleTemplateURL;
    }

    public void setWeiSiteSytleTemplateURL(String weiSiteSytleTemplateURL) {
        this.weiSiteSytleTemplateURL = weiSiteSytleTemplateURL;
    }
    @FieldConfig(fieldName = "weiSiteSytleTemplateImageURL", fieldType = FieldTypeEnum.CHAR64, description = "风格配置的url")
    private String weiSiteSytleTemplateImageURL;

    public String getWeiSiteSytleTemplateImageURL() {
        return weiSiteSytleTemplateImageURL;
    }

    public void setWeiSiteSytleTemplateImageURL(String weiSiteSytleTemplateImageURL) {
        this.weiSiteSytleTemplateImageURL = weiSiteSytleTemplateImageURL;
    }
    @FieldConfig(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "模板风格的类型")
    private byte type;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
    @FieldConfig(fieldName = "createTime", fieldType = FieldTypeEnum.DATETIME, description = "创建的时间")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Map<String, String> toMap() {
        Map<String, String> entityMap = new HashMap<String, String>(16, 1);
        entityMap.put("weiSiteSytleTemplateId", String.valueOf(this.weiSiteSytleTemplateId));
        entityMap.put("weiSiteSytleTemplateName", this.weiSiteSytleTemplateName);
        entityMap.put("weiSiteSytleTemplateURL", this.weiSiteSytleTemplateURL);
        entityMap.put("weiSiteSytleTemplateImageURL", this.weiSiteSytleTemplateImageURL);
        entityMap.put("type", String.valueOf(this.type));
        try {
            entityMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("createTime", "");
        }
        return entityMap;

    }

    public void parseMap(Map<String, String> entityMap) {
        this.weiSiteSytleTemplateId = Long.parseLong(entityMap.get("weiSiteSytleTemplateId"));
        this.weiSiteSytleTemplateName = entityMap.get("weiSiteSytleTemplateName");
        this.weiSiteSytleTemplateURL = entityMap.get("weiSiteSytleTemplateURL");
        this.weiSiteSytleTemplateImageURL = entityMap.get("weiSiteSytleTemplateImageURL");
        this.type = Byte.parseByte(entityMap.get("type"));

        try {
            this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(entityMap.get("createTime"));
        } catch (Exception ex) {
            System.err.print(ex);
            entityMap.put("createTime", "");
        }

    }
}
