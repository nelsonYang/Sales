package com.sales.entity;

import com.framework.entity.pojo.Entity;
import com.framework.entity.annotation.EntityConfig;
import com.framework.entity.annotation.FieldConfig;
import com.framework.enumeration.FieldTypeEnum;
import com.framework.entity.pojo.PrimaryKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
@EntityConfig(
        entityName = EntityNames.responseMessageCfg,
        keyFields = {"responseMessageCfgId"},
        useCache = false
      )
public class ResponseMessageCfg extends Entity {

    public PrimaryKey getKeyValue() {
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.putKeyField("responseMessageCfgId", String.valueOf(this.responseMessageCfgId));
        return primaryKey;
    }
    @FieldConfig(fieldName = "responseMessageCfgId", fieldType = FieldTypeEnum.BIG_INT, description = "回复信息配置表")
    private long responseMessageCfgId;

    public long getResponseMessageCfgId() {
        return responseMessageCfgId;
    }

    public void setResponseMessageCfgId(long responseMessageCfgId) {
        this.responseMessageCfgId = responseMessageCfgId;
    }
    @FieldConfig(fieldName = "responseContent", fieldType = FieldTypeEnum.CHAR128, description = "回复内容")
    private String responseContent;

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }
    @FieldConfig(fieldName = "responseImageURL", fieldType = FieldTypeEnum.CHAR64, description = "回复图片")
    private String responseImageURL;

    public String getResponseImageURL() {
        return responseImageURL;
    }

    public void setResponseImageURL(String responseImageURL) {
        this.responseImageURL = responseImageURL;
    }
    @FieldConfig(fieldName = "responseAudio", fieldType = FieldTypeEnum.CHAR64, description = "回复音频")
    private String responseAudio;

    public String getResponseAudio() {
        return responseAudio;
    }

    public void setResponseAudio(String responseAudio) {
        this.responseAudio = responseAudio;
    }
    @FieldConfig(fieldName = "type", fieldType = FieldTypeEnum.TYINT, description = "回复的类型0-文本1-音频2-music3-vedio4-news5-image6-location7-link8-event_event9-event_scan10-event_subscribe11-event_unsubscribe")
    private byte type;

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
    
     @FieldConfig(fieldName = "cfgType", fieldType = FieldTypeEnum.TYINT, description = "基本配置和详细配置1-基本2-详细")
    private byte cfgType;

    public byte getCfgType() {
        return cfgType;
    }

    public void setCfgType(byte cfgType) {
        this.cfgType = cfgType;
    }
    @FieldConfig(fieldName = "companyId", fieldType = FieldTypeEnum.BIG_INT, description = "公司id")
    private long companyId;

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
    @FieldConfig(fieldName = "relatedEventId", fieldType = FieldTypeEnum.BIG_INT, description = "关联的活动id")
    private long relatedEventId;

    public long getRelatedEventId() {
        return relatedEventId;
    }

    public void setRelatedEventId(long relatedEventId) {
        this.relatedEventId = relatedEventId;
    }
    @FieldConfig(fieldName = "keyword", fieldType = FieldTypeEnum.CHAR36, description = "关键字")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    @FieldConfig(fieldName = "relatedURL", fieldType = FieldTypeEnum.CHAR64, description = "相关的url")
    private String relatedURL;

    public String getRelatedURL() {
        return relatedURL;
    }

    public void setRelatedURL(String relatedURL) {
        this.relatedURL = relatedURL;
    }
    @FieldConfig(fieldName = "isClose", fieldType = FieldTypeEnum.TYINT, description = "1-开启 2-关闭")
    private byte isClose;

    public byte getIsClose() {
        return isClose;
    }

    public void setIsClose(byte isClose) {
        this.isClose = isClose;
    }
    @FieldConfig(fieldName = "responseContentType", fieldType = FieldTypeEnum.TYINT, description = "回复类型1-文本2-图文 3-活动 4-预订 5-微吧6-商家7-会员卡8-微站9-优惠券")
    private byte responseContentType;

    public byte getResponseContentType() {
        return responseContentType;
    }

    public void setResponseContentType(byte responseContentType) {
        this.responseContentType = responseContentType;
    }
    @FieldConfig(fieldName = "relatedId", fieldType = FieldTypeEnum.BIG_INT, description = "关联id")
    private long relatedId;

    public long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(long relatedId) {
        this.relatedId = relatedId;
    }
    @FieldConfig(fieldName = "matchType", fieldType = FieldTypeEnum.TYINT, description = "匹配模式1-精确0-模糊")
    private byte matchType;

    public byte getMatchType() {
        return matchType;
    }

    public void setMatchType(byte matchType) {
        this.matchType = matchType;
    }
    private List<Map<String, String>> entityMapList = new ArrayList<Map<String, String>>();

    public List<Map<String, String>> getEntityMapList() {
        return entityMapList;
    }

    public void setEntityMapList(List<Map<String, String>> entityMapList) {
        this.entityMapList = entityMapList;
    }

    public Map<String, String> toMap() {
        Map<String, String> entityMap = new HashMap<String, String>(16, 1);
        entityMap.put("responseMessageCfgId", String.valueOf(this.responseMessageCfgId));
        entityMap.put("responseContent", this.responseContent);
        entityMap.put("responseImageURL", this.responseImageURL);
        entityMap.put("responseAudio", this.responseAudio);
        entityMap.put("type", String.valueOf(this.type));
        entityMap.put("companyId", String.valueOf(this.companyId));
        entityMap.put("relatedEventId", String.valueOf(this.relatedEventId));
        entityMap.put("keyword", this.keyword);
        entityMap.put("relatedURL", this.relatedURL);
        entityMap.put("isClose", String.valueOf(this.isClose));
        entityMap.put("responseContentType", String.valueOf(this.responseContentType));
        entityMap.put("relatedId", String.valueOf(this.relatedId));
        entityMap.put("matchType", String.valueOf(this.matchType));
        entityMap.put("cfgType", String.valueOf(cfgType));
        return entityMap;

    }

    public void parseMap(Map<String, String> entityMap) {
        this.responseMessageCfgId = Long.parseLong(entityMap.get("responseMessageCfgId"));
        this.responseContent = entityMap.get("responseContent");
        this.responseImageURL = entityMap.get("responseImageURL");
        this.responseAudio = entityMap.get("responseAudio");
        this.type = Byte.parseByte(entityMap.get("type"));
        this.companyId = Long.parseLong(entityMap.get("companyId"));
        this.relatedEventId = Long.parseLong(entityMap.get("relatedEventId"));
        this.keyword = entityMap.get("keyword");
        this.relatedURL = entityMap.get("relatedURL");
        this.isClose = Byte.parseByte(entityMap.get("isClose"));
        this.responseContentType = Byte.parseByte(entityMap.get("responseContentType"));
        this.relatedId = Long.parseLong(entityMap.get("relatedId"));
        this.matchType = Byte.parseByte(entityMap.get("matchType"));
        this.cfgType = Byte.parseByte(entityMap.get("cfgType"));

    }
}
