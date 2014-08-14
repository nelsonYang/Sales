package com.sales.datacache;

import com.framework.context.ApplicationContext;
import com.framework.entity.condition.Condition;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.enumeration.ConditionTypeEnum;
import com.framework.entity.pojo.PrimaryKey;
import com.framework.utils.SetUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.config.SalesConstant;
import com.sales.entity.Coupon;
import com.sales.entity.CouponConfig;
import com.sales.entity.EntityNames;
import com.sales.entity.Event;
import com.sales.entity.EventConfig;
import com.sales.entity.MemberCard;
import com.sales.entity.MemberCardConfig;
import com.sales.entity.Merchant;
import com.sales.entity.MerchantConfig;
import com.sales.entity.Reservation;
import com.sales.entity.ReservationConfig;
import com.sales.entity.Resources;
import com.sales.entity.ResourcesImage;
import com.sales.entity.ResponseMessageCfg;
import com.sales.entity.WeiBarConfig;
import com.sales.entity.WeiSiteConfig;
import com.sales.entity.WeiSiteStyleTemplate;
import com.sales.entity.WeiSiteSytleTemplateConfig;
import com.sales.xml.utils.XmlUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class ResponseMessageCache {

    private final Logger logger = LogFactory.getInstance().getLogger(ResponseMessageCache.class);
    private List<Reservation> reservationList = new ArrayList<Reservation>();
    private List<MemberCard> memberCardList = new ArrayList<MemberCard>();
    private List<Coupon> couponList = new ArrayList<Coupon>();
    private List<Merchant> merchantList = new ArrayList<Merchant>();
    private List<WeiSiteConfig> weiSiteConfigList = new ArrayList<WeiSiteConfig>();
    private List<WeiBarConfig> weiBarConfigList = new ArrayList<WeiBarConfig>();
    private List<Event> eventList = new ArrayList<Event>();
    private List<WeiSiteStyleTemplate> weiSiteStyleTemplateList = new ArrayList<WeiSiteStyleTemplate>();
    private List<WeiSiteSytleTemplateConfig> weiSiteStyleTemplcateConfigList = new ArrayList<WeiSiteSytleTemplateConfig>();

    public static ResponseMessageCache getInstance() {
        return responseMessageCacheInstance;
    }
    private static final ResponseMessageCache responseMessageCacheInstance = new ResponseMessageCache();
    private final Map<String, List<ResponseMessageCfg>> cacheMap = new ConcurrentHashMap<String, List<ResponseMessageCfg>>(64, 1);
    private final Map<String, String> xmlCacheMap = new ConcurrentHashMap<String, String>();

    public void init() {

        ApplicationContext applicationContext = ApplicationContext.CTX;
        EntityDao<ResponseMessageCfg> entityDao = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        EntityDao<Event> eventDao = applicationContext.getEntityDAO(EntityNames.event);
        EntityDao<Reservation> reservationDao = applicationContext.getEntityDAO(EntityNames.reservation);
        EntityDao<Merchant> merchantDAO = applicationContext.getEntityDAO(EntityNames.merchant);
        EntityDao<WeiBarConfig> weiBarConfigDAO = applicationContext.getEntityDAO(EntityNames.weiBarConfig);
        EntityDao<MemberCard> memberCardDao = applicationContext.getEntityDAO(EntityNames.memberCard);
        EntityDao<Coupon> couponDao = applicationContext.getEntityDAO(EntityNames.coupon);
        EntityDao<WeiSiteConfig> weiSiteConfigDAO = applicationContext.getEntityDAO(EntityNames.weiSiteConfig);
        EntityDao<WeiSiteStyleTemplate> weiSiteStyleTemplateDAO = applicationContext.getEntityDAO(EntityNames.weiSiteStyleTemplate);
        EntityDao<WeiSiteSytleTemplateConfig> weiSiteStyleTemplateCfgDAO = applicationContext.getEntityDAO(EntityNames.weiSiteSytleTemplateConfig);

        Set<Long> eventSet = new HashSet<Long>();
        Set<Long> reservationSet = new HashSet<Long>();
        Set<Long> merchantSet = new HashSet<Long>();
        Set<Long> weiBarConfigSet = new HashSet<Long>();
        Set<Long> memberCardSet = new HashSet<Long>();
        Set<Long> couponSet = new HashSet<Long>();
        Set<Long> weiSiteConfigSet = new HashSet<Long>();
        List<Condition> responseMessageCfgconditionList = new ArrayList<Condition>(1);
        Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
        responseMessageCfgconditionList.add(cfgTypeCondition);
        List<ResponseMessageCfg> responseMessageCfgList = entityDao.inquireByCondition(responseMessageCfgconditionList);
        weiSiteStyleTemplateList = weiSiteStyleTemplateDAO.inquireByCondition(new ArrayList<Condition>(0));
        weiSiteStyleTemplcateConfigList = weiSiteStyleTemplateCfgDAO.inquireByCondition(new ArrayList<Condition>(0));
        byte responseType;
        List<Condition> conditionList;
        for (ResponseMessageCfg responseMessageCfg : responseMessageCfgList) {
            responseType = responseMessageCfg.getResponseContentType();
            switch (responseType) {
                case SalesConstant.EVENT_TYPE:
                    long eventConfigId = responseMessageCfg.getRelatedId();
                    eventSet.add(eventConfigId);
                    break;
                case SalesConstant.RESERVATION_TYPE:
                    long reservationConfigId = responseMessageCfg.getRelatedId();
                    reservationSet.add(reservationConfigId);
                    break;
                case SalesConstant.MEMBER_CARD_TYPE:
                    long memberCardConfigId = responseMessageCfg.getRelatedId();
                    memberCardSet.add(memberCardConfigId);
                    break;
                case SalesConstant.WEIBAR_TYPE:
                    long weiBarConfigId = responseMessageCfg.getRelatedId();
                    weiBarConfigSet.add(weiBarConfigId);
                    break;
                case SalesConstant.COPOUN_TYPE:
                    long couponConfigId = responseMessageCfg.getRelatedId();
                    couponSet.add(couponConfigId);
                    break;
                case SalesConstant.MERCHATE_TYPE:
                    long merchantConfigId = responseMessageCfg.getRelatedId();
                    merchantSet.add(merchantConfigId);
                    break;
                case SalesConstant.WEI_SITE_TYPE:
                    long weiSiteConfigId = responseMessageCfg.getRelatedId();
                    weiSiteConfigSet.add(weiSiteConfigId);
                    break;
            }
            if (!eventSet.isEmpty()) {
                String eventConfigIdStr = SetUtils.LongSet2Str(eventSet);
                conditionList = new ArrayList<Condition>(3);;
                Condition eventConfigIdCondition = new Condition("eventId", ConditionTypeEnum.IN, eventConfigIdStr);
                Condition statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
                conditionList.add(eventConfigIdCondition);
                conditionList.add(statusCondition);
                eventList = eventDao.inquireByCondition(conditionList);
            }
            if (!reservationSet.isEmpty()) {
                String reservationConfigIdStr = SetUtils.LongSet2Str(reservationSet);
                conditionList = new ArrayList<Condition>(3);;
                Condition reservationConfigIdCondition = new Condition("reservationId", ConditionTypeEnum.IN, reservationConfigIdStr);
                Condition statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
                conditionList.add(reservationConfigIdCondition);
                conditionList.add(statusCondition);
                reservationList = reservationDao.inquireByCondition(conditionList);
            }
            if (!weiBarConfigSet.isEmpty()) {
                String weiBarConfigIdStr = SetUtils.LongSet2Str(weiBarConfigSet);
                conditionList = new ArrayList<Condition>(3);;
                Condition reservationConfigIdCondition = new Condition("weiBarConfigId", ConditionTypeEnum.IN, weiBarConfigIdStr);
                conditionList.add(reservationConfigIdCondition);
                weiBarConfigList = weiBarConfigDAO.inquireByCondition(conditionList);
            }
            if (!memberCardSet.isEmpty()) {
                String memberCardConfigIdStr = SetUtils.LongSet2Str(memberCardSet);
                conditionList = new ArrayList<Condition>(3);;
                Condition reservationConfigIdCondition = new Condition("memberCardId", ConditionTypeEnum.IN, memberCardConfigIdStr);
                Condition statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
                conditionList.add(reservationConfigIdCondition);
                conditionList.add(statusCondition);
                memberCardList = memberCardDao.inquireByCondition(conditionList);
            }
            if (!couponSet.isEmpty()) {
                String couponConfigIdStr = SetUtils.LongSet2Str(couponSet);
                conditionList = new ArrayList<Condition>(3);;
                Condition reservationConfigIdCondition = new Condition("couponId", ConditionTypeEnum.IN, couponConfigIdStr);
                Condition statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
                conditionList.add(reservationConfigIdCondition);
                conditionList.add(statusCondition);
                couponList = couponDao.inquireByCondition(conditionList);
            }
            if (!merchantSet.isEmpty()) {
                String merchantConfigIdStr = SetUtils.LongSet2Str(merchantSet);
                conditionList = new ArrayList<Condition>(3);;
                Condition reservationConfigIdCondition = new Condition("merchantId", ConditionTypeEnum.IN, merchantConfigIdStr);
                Condition statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
                conditionList.add(reservationConfigIdCondition);
                conditionList.add(statusCondition);
                merchantList = merchantDAO.inquireByCondition(conditionList);
            }
            if (!weiSiteConfigSet.isEmpty()) {
                String weiSiteConfigIdStr = SetUtils.LongSet2Str(weiSiteConfigSet);
                conditionList = new ArrayList<Condition>(2);
                Condition reservationConfigIdCondition = new Condition("weiSiteConfigId", ConditionTypeEnum.IN, weiSiteConfigIdStr);
                conditionList.add(reservationConfigIdCondition);
                weiSiteConfigList = weiSiteConfigDAO.inquireByCondition(conditionList);
            }
        }


    }

    public void putResponseMessageCfgWithKeyWord(ResponseMessageCfg responseMessageCfg) {
        String companyType;
        byte responseType;
        String keyword;
        List<ResponseMessageCfg> cacheResponseMessageCfgList;
        responseType = responseMessageCfg.getResponseContentType();
        keyword = responseMessageCfg.getKeyword();
        if (responseMessageCfg.getType() == 0) {
            companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType() + "_" + keyword;
        } else {
            companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType();
        }
        cacheResponseMessageCfgList = cacheMap.get(companyType);
        if (cacheResponseMessageCfgList != null) {
            switch (responseType) {
                case SalesConstant.EVENT_TYPE:
                    for (Event event : eventList) {
                        if (event.getEventId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    }
                    break;
                case SalesConstant.RESERVATION_TYPE:
                    for (Reservation reservation : reservationList) {
                        if (reservation.getReservationId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(reservation.toMap());
                        }
                    }
                    break;
                case SalesConstant.MEMBER_CARD_TYPE:
                    for (MemberCard memberCard : memberCardList) {
                        if (memberCard.getMemberCardId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(memberCard.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEIBAR_TYPE:
                    for (WeiBarConfig weiBarConfig : weiBarConfigList) {
                        if (weiBarConfig.getWeiBarConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            responseMessageCfg.getEntityMapList().add(weiBarConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.COPOUN_TYPE:
                    for (Coupon coupon : couponList) {
                        if (coupon.getCouponId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(coupon.toMap());
                        }
                    }
                    break;
                case SalesConstant.MERCHATE_TYPE:
                    for (Merchant merchant : merchantList) {
                        if (merchant.getMerchantId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(merchant.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEI_SITE_TYPE:
                    for (WeiSiteConfig weiSiteConfig : weiSiteConfigList) {
                        if (weiSiteConfig.getWeiSiteConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            long companyId = weiSiteConfig.getCompanyId();
                            long weiSiteStyleTemplateConfigId = -1;
                            String weiSiteSytleTemplateURL = null;
                            for (WeiSiteStyleTemplate weiSiteStyleTemplate : weiSiteStyleTemplateList) {
                                if (companyId == weiSiteStyleTemplate.getCompanyId()) {
                                    weiSiteStyleTemplateConfigId = weiSiteStyleTemplate.getWeiSiteStyleTemplateCfgId();
                                    break;
                                }
                            }
                            for (WeiSiteSytleTemplateConfig weiSiteStyleTemplcateConfig : weiSiteStyleTemplcateConfigList) {
                                if (weiSiteStyleTemplateConfigId == weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateId()) {
                                    weiSiteSytleTemplateURL = weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateURL();
                                    break;
                                }
                            }
                            Map<String, String> eventConfigMap = weiSiteConfig.toMap();
                            if (weiSiteSytleTemplateURL != null) {
                                eventConfigMap.put("weiSiteSytleTemplateURL", weiSiteSytleTemplateURL);
                            }
                            responseMessageCfg.getEntityMapList().add(eventConfigMap);
                        }
                    }
                    break;
            }

            cacheResponseMessageCfgList.add(responseMessageCfg);
        } else {
            cacheResponseMessageCfgList = new ArrayList<ResponseMessageCfg>();
            switch (responseType) {
                case SalesConstant.EVENT_TYPE:
                    for (Event event : eventList) {
                        if (event.getEventId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    }
                    break;
                case SalesConstant.RESERVATION_TYPE:
                    for (Reservation reservation : reservationList) {
                        if (reservation.getReservationId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(reservation.toMap());
                        }
                    }
                    break;
                case SalesConstant.MEMBER_CARD_TYPE:
                    for (MemberCard memberCard : memberCardList) {
                        if (memberCard.getMemberCardId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(memberCard.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEIBAR_TYPE:
                    for (WeiBarConfig weiBarConfig : weiBarConfigList) {
                        if (weiBarConfig.getWeiBarConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            responseMessageCfg.getEntityMapList().add(weiBarConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.COPOUN_TYPE:
                    for (Coupon coupon : couponList) {
                        if (coupon.getCouponId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(coupon.toMap());
                        }
                    }
                    break;
                case SalesConstant.MERCHATE_TYPE:
                    for (Merchant merchant : merchantList) {
                        if (merchant.getMerchantId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(merchant.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEI_SITE_TYPE:
                    for (WeiSiteConfig weiSiteConfig : weiSiteConfigList) {
                        if (weiSiteConfig.getWeiSiteConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            long companyId = weiSiteConfig.getCompanyId();
                            long weiSiteStyleTemplateConfigId = -1;
                            String weiSiteSytleTemplateURL = null;
                            for (WeiSiteStyleTemplate weiSiteStyleTemplate : weiSiteStyleTemplateList) {
                                if (companyId == weiSiteStyleTemplate.getCompanyId()) {
                                    weiSiteStyleTemplateConfigId = weiSiteStyleTemplate.getWeiSiteStyleTemplateCfgId();
                                    break;
                                }
                            }
                            for (WeiSiteSytleTemplateConfig weiSiteStyleTemplcateConfig : weiSiteStyleTemplcateConfigList) {
                                if (weiSiteStyleTemplateConfigId == weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateId()) {
                                    weiSiteSytleTemplateURL = weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateURL();
                                    break;
                                }
                            }
                            Map<String, String> eventConfigMap = weiSiteConfig.toMap();
                            if (weiSiteSytleTemplateURL != null) {
                                eventConfigMap.put("weiSiteSytleTemplateURL", weiSiteSytleTemplateURL);
                            }
                            responseMessageCfg.getEntityMapList().add(eventConfigMap);
                        }
                    }
                    break;
            }
            cacheResponseMessageCfgList.add(responseMessageCfg);
            cacheMap.put(companyType, cacheResponseMessageCfgList);
        }


    }

    public void putResponseMessageCfg(ResponseMessageCfg responseMessageCfg) {
        String companyType;
        byte responseType;
        List<ResponseMessageCfg> cacheResponseMessageCfgList;
        responseType = responseMessageCfg.getResponseContentType();
        companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType();
        cacheResponseMessageCfgList = cacheMap.get(companyType);
        if (cacheResponseMessageCfgList != null) {
            switch (responseType) {
                case SalesConstant.EVENT_TYPE:
                    for (Event event : eventList) {
                        if (event.getEventId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    }
                    break;
                case SalesConstant.RESERVATION_TYPE:
                    for (Reservation reservation : reservationList) {
                        if (reservation.getReservationId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(reservation.toMap());
                        }
                    }
                    break;
                case SalesConstant.MEMBER_CARD_TYPE:
                    for (MemberCard memberCard : memberCardList) {
                        if (memberCard.getMemberCardId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(memberCard.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEIBAR_TYPE:
                    for (WeiBarConfig weiBarConfig : weiBarConfigList) {
                        if (weiBarConfig.getWeiBarConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            responseMessageCfg.getEntityMapList().add(weiBarConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.COPOUN_TYPE:
                    for (Coupon coupon : couponList) {
                        if (coupon.getCouponId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(coupon.toMap());
                        }
                    }
                    break;
                case SalesConstant.MERCHATE_TYPE:
                    for (Merchant merchant : merchantList) {
                        if (merchant.getMerchantId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(merchant.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEI_SITE_TYPE:
                    for (WeiSiteConfig weiSiteConfig : weiSiteConfigList) {
                        if (weiSiteConfig.getWeiSiteConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            long companyId = weiSiteConfig.getCompanyId();
                            long weiSiteStyleTemplateConfigId = -1;
                            String weiSiteSytleTemplateURL = null;
                            for (WeiSiteStyleTemplate weiSiteStyleTemplate : weiSiteStyleTemplateList) {
                                if (companyId == weiSiteStyleTemplate.getCompanyId()) {
                                    weiSiteStyleTemplateConfigId = weiSiteStyleTemplate.getWeiSiteStyleTemplateCfgId();
                                    break;
                                }
                            }
                            for (WeiSiteSytleTemplateConfig weiSiteStyleTemplcateConfig : weiSiteStyleTemplcateConfigList) {
                                if (weiSiteStyleTemplateConfigId == weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateId()) {
                                    weiSiteSytleTemplateURL = weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateURL();
                                    break;
                                }
                            }
                            Map<String, String> eventConfigMap = weiSiteConfig.toMap();
                            if (weiSiteSytleTemplateURL != null) {
                                eventConfigMap.put("weiSiteSytleTemplateURL", weiSiteSytleTemplateURL);
                            }
                            responseMessageCfg.getEntityMapList().add(eventConfigMap);
                        }
                    }
                    break;
            }

            cacheResponseMessageCfgList.add(responseMessageCfg);
        } else {
            cacheResponseMessageCfgList = new ArrayList<ResponseMessageCfg>();
            switch (responseType) {
                case SalesConstant.EVENT_TYPE:
                    for (Event event : eventList) {
                        if (event.getEventId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    }
                    break;
                case SalesConstant.RESERVATION_TYPE:
                    for (Reservation reservation : reservationList) {
                        if (reservation.getReservationId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(reservation.toMap());
                        }
                    }
                    break;
                case SalesConstant.MEMBER_CARD_TYPE:
                    for (MemberCard memberCard : memberCardList) {
                        if (memberCard.getMemberCardId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(memberCard.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEIBAR_TYPE:
                    for (WeiBarConfig weiBarConfig : weiBarConfigList) {
                        if (weiBarConfig.getWeiBarConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            responseMessageCfg.getEntityMapList().add(weiBarConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.COPOUN_TYPE:
                    for (Coupon coupon : couponList) {
                        if (coupon.getCouponId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(coupon.toMap());
                        }
                    }
                    break;
                case SalesConstant.MERCHATE_TYPE:
                    for (Merchant merchant : merchantList) {
                        if (merchant.getMerchantId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(merchant.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEI_SITE_TYPE:

                    for (WeiSiteConfig weiSiteConfig : weiSiteConfigList) {
                        if (weiSiteConfig.getWeiSiteConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            long companyId = weiSiteConfig.getCompanyId();
                            long weiSiteStyleTemplateConfigId = -1;
                            String weiSiteSytleTemplateURL = null;
                            for (WeiSiteStyleTemplate weiSiteStyleTemplate : weiSiteStyleTemplateList) {
                                if (companyId == weiSiteStyleTemplate.getCompanyId()) {
                                    weiSiteStyleTemplateConfigId = weiSiteStyleTemplate.getWeiSiteStyleTemplateCfgId();
                                    break;
                                }
                            }
                            for (WeiSiteSytleTemplateConfig weiSiteStyleTemplcateConfig : weiSiteStyleTemplcateConfigList) {
                                if (weiSiteStyleTemplateConfigId == weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateId()) {
                                    weiSiteSytleTemplateURL = weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateURL();
                                    break;
                                }
                            }
                            Map<String, String> eventConfigMap = weiSiteConfig.toMap();
                            if (weiSiteSytleTemplateURL != null) {
                                eventConfigMap.put("weiSiteSytleTemplateURL", weiSiteSytleTemplateURL);
                            }
                            responseMessageCfg.getEntityMapList().add(eventConfigMap);
                        }
                    }
                    break;
            }
            cacheResponseMessageCfgList.add(responseMessageCfg);
            cacheMap.put(companyType, cacheResponseMessageCfgList);
        }
    }

    public void convertToXml() {

        for (Entry<String, List<ResponseMessageCfg>> entry : cacheMap.entrySet()) {
            String key = entry.getKey();
            List<ResponseMessageCfg> responseMessageCfgList = entry.getValue();
            String responseXML = this.convertResponseMessageToXml(responseMessageCfgList);
            logger.debug("key:{}", key);
            logger.debug("value:{}", responseXML);
            xmlCacheMap.put(key, responseXML);
        }

    }

    private String convertResponseMessageToXml(List<ResponseMessageCfg> responseMessageCfgList) {
        byte responseType;
        byte receiveType;
        String messageTitle;
        String messageURL;
        String responseContent;
        String imageUrl;
        String responseXML = "";
        XmlUtils response = XmlUtils.getInstance();
        List<Map<String, String>> itemMapList = null;
        ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
        receiveType = responseMessageCfg.getType();
        responseType = responseMessageCfg.getResponseContentType();
        messageTitle = responseMessageCfg.getResponseContent();
        messageURL = responseMessageCfg.getResponseImageURL();
        String title = messageTitle;
        String description = messageTitle;
        String picURL = messageURL;
        itemMapList = responseMessageCfg.getEntityMapList();
        Map<String, String> responseMap = new HashMap<String, String>(8, 1);
        //获取event
        responseMap.put("ToUserName", "#{ToUserName}");
        responseMap.put("FromUserName", "#{FromUserName}");
        responseMap.put("CreateTime", "#{CreateTime}");
        logger.debug("responseType:{}", responseType);
        responseContent = responseMessageCfg.getResponseContent();
        imageUrl = responseMessageCfg.getResponseImageURL();
        if (receiveType == SalesConstant.RESPONSE_TEXT_TYPE) {
            switch (responseType) {
                case SalesConstant.TEXT_TYPE:
                    responseMap.put("MsgType", "text");
                    responseMap.put("Content", responseContent);
                    responseXML = response.responseTextMessage(responseMap);
                    break;
                case SalesConstant.TEXT_PICTURE_TYPE:
                    responseMap.put("Content", responseContent);
                    responseMap.put("MsgType", "news");
                    responseMap.put("ArticleCount", "1");
                    List<Map<String, String>> responseMapList = new ArrayList<Map<String, String>>();
                    Map<String, String> itemMap = new HashMap<String, String>(4, 1);
                    itemMap.put("Title", messageTitle);
                    itemMap.put("Description", responseContent);
                    itemMap.put("PicUrl", imageUrl);
                    itemMap.put("Url", "");
                    responseMapList.add(itemMap);
                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
                    break;
                case SalesConstant.EVENT_TYPE:
                    responseMap.put("ArticleCount", String.valueOf(itemMapList.size()));
                    responseMap.put("MsgType", "news");
                    itemMapList = responseMessageCfg.getEntityMapList();
                    responseMapList = new ArrayList<Map<String, String>>();
                    String eventURL;
                    byte type;

                    for (Map<String, String> eventMap : itemMapList) {
                        type = Byte.parseByte(eventMap.get("type"));
                        if (type == SalesConstant.DA_ZHUAN_PAN_TYPE) {
                            eventURL = SalesConstant.DA_ZHUAN_PAN_EVENT_URL;
                        } else if (type == SalesConstant.GUA_GUA_KA_TYPE) {
                            eventURL = SalesConstant.GUA_GUA_KA_EVENT_URL;
                        } else {
                            eventURL = SalesConstant.SHUI_GUO_DA_REN_EVENT_URL;
                        }
                        eventURL = eventURL + "?weixinId=#{weixinId}&token=#{token}&eventId=" + eventMap.get("eventId");
                        itemMap = new HashMap<String, String>(4, 1);
                        if (eventMap.get("eventName") != null && !eventMap.get("eventName").isEmpty()) {
                            title = eventMap.get("eventName");
                        } else {
                            if (eventMap.get("messageTitle") != null && !eventMap.get("messageTitle").isEmpty()) {
                                title = eventMap.get("messageTitle");
                            }
                        }
                        if (eventMap.get("eventDesc") != null && !eventMap.get("eventDesc").isEmpty()) {
                            description = eventMap.get("eventDesc");
                        } else {
                            if (eventMap.get("messageTitle") != null && !eventMap.get("messageTitle").isEmpty()) {
                                description = eventMap.get("messageTitle");
                            }
                        }
                        if (eventMap.get("eventStartURL") != null && !eventMap.get("eventStartURL").isEmpty()) {
                            picURL = eventMap.get("eventStartURL");
                        } else {
                            if (eventMap.get("messageImageURL") != null && !eventMap.get("messageImageURL").isEmpty()) {
                                picURL = eventMap.get("messageImageURL");
                            }
                        }
                        itemMap.put("Title", title);
                        itemMap.put("Description", description);
                        itemMap.put("PicUrl", picURL);
                        itemMap.put("Url", eventURL);
                        responseMapList.add(itemMap);
                    }
                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
                    break;
                case SalesConstant.RESERVATION_TYPE:
                    responseMap.put("ArticleCount", String.valueOf(itemMapList.size()));
                    responseMap.put("MsgType", "news");
                    itemMapList = responseMessageCfg.getEntityMapList();
                    responseMapList = new ArrayList<Map<String, String>>();
                    String reservationURL;
                    for (Map<String, String> resvervationMap : itemMapList) {
                        reservationURL = SalesConstant.RESERVATION_URL + "?weixinId=#{weixinId}&token=#{token}&reservationId=" + resvervationMap.get("reservationId");
                        itemMap = new HashMap<String, String>(4, 1);
                        if (resvervationMap.get("messageTitle") != null && !resvervationMap.get("messageTitle").isEmpty()) {
                            title = resvervationMap.get("messageTitle");
                        }
                        if (resvervationMap.get("reservationDesc") != null && !resvervationMap.get("reservationDesc").isEmpty()) {
                            description = resvervationMap.get("reservationDesc");
                        } else {
                            if (resvervationMap.get("messageTitle") != null && !resvervationMap.get("messageTitle").isEmpty()) {
                                description = resvervationMap.get("messageTitle");
                            }
                        }
                        if (resvervationMap.get("messageImageURL") != null && !resvervationMap.get("messageImageURL").isEmpty()) {
                            picURL = resvervationMap.get("messageImageURL");
                        }
                        itemMap.put("Title", title);
                        itemMap.put("Description", description);
                        itemMap.put("PicUrl", picURL);
                        itemMap.put("Url", reservationURL);
                        responseMapList.add(itemMap);
                    }
                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
                    break;

                case SalesConstant.WEIBAR_TYPE:
                    responseMap.put("ArticleCount", "1");
                    responseMap.put("MsgType", "news");
                    responseMapList = new ArrayList<Map<String, String>>();
                    String weiBarURL = SalesConstant.WEIBAR_URL + "?weixinId=#{weixinId}&token=#{token}&weiBarConfigId=" + responseMessageCfg.getRelatedEventId();
                    itemMap = new HashMap<String, String>(4, 1);
                    itemMap.put("Title", messageTitle);
                    itemMap.put("Description", messageTitle);
                    itemMap.put("PicUrl", messageURL);
                    itemMap.put("Url", weiBarURL);
                    responseMapList.add(itemMap);
                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
                    break;
                case SalesConstant.MEMBER_CARD_TYPE:
                    responseMap.put("ArticleCount", String.valueOf(itemMapList.size()));
                    responseMap.put("MsgType", "news");
                    itemMapList = responseMessageCfg.getEntityMapList();
                    responseMapList = new ArrayList<Map<String, String>>();
                    for (Map<String, String> memberCardMap : itemMapList) {
                        String memberCardURL = SalesConstant.MEMBER_CARD_URL + "?weixinId=#{weixinId}&token=#{token}&memberCardId=" + memberCardMap.get("memberCardId");
                        itemMap = new HashMap<String, String>(4, 1);
                        if (memberCardMap.get("memberCardName") != null && !memberCardMap.get("memberCardName").isEmpty()) {
                            title = memberCardMap.get("memberCardName");
                        }
                        if (memberCardMap.get("memberCardDesc") != null && !memberCardMap.get("memberCardDesc").isEmpty()) {
                            description = memberCardMap.get("memberCardDesc");
                        }
                        if (memberCardMap.get("memberCardURL") != null && !memberCardMap.get("memberCardURL").isEmpty()) {
                            picURL = memberCardMap.get("memberCardURL");
                        } else {
                            if (memberCardMap.get("messageImageURL") != null && !memberCardMap.get("messageImageURL").isEmpty()) {
                                picURL = memberCardMap.get("messageImageURL");
                            }
                        }
                        itemMap.put("Title", title);
                        itemMap.put("Description", description);
                        itemMap.put("PicUrl", picURL);
                        itemMap.put("Url", memberCardURL);
                        responseMapList.add(itemMap);
                    }
                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
                    break;
                case SalesConstant.COPOUN_TYPE:
                    responseMap.put("ArticleCount", String.valueOf(itemMapList.size()));
                    responseMap.put("MsgType", "news");
                    itemMapList = responseMessageCfg.getEntityMapList();
                    responseMapList = new ArrayList<Map<String, String>>();
                    String couponURL;
                    for (Map<String, String> couponMap : itemMapList) {
                        couponURL = SalesConstant.COPOUN_URL + "?weixinId=#{weixinId}&token=#{token}&couponId=" + couponMap.get("couponId");
                        itemMap = new HashMap<String, String>(4, 1);
                        if (couponMap.get("couponName") != null && !couponMap.get("couponName").isEmpty()) {
                            title = couponMap.get("couponName");
                        } else {
                            if (couponMap.get("messageTitle") != null && !couponMap.get("messageTitle").isEmpty()) {
                                title = couponMap.get("messageTitle");
                            }
                        }
                        if (couponMap.get("couponDesc") != null && !couponMap.get("couponDesc").isEmpty()) {
                            description = couponMap.get("couponDesc");
                        } else {
                            if (couponMap.get("messageTitle") != null && !couponMap.get("messageTitle").isEmpty()) {
                                description = couponMap.get("messageTitle");
                            }
                        }
                        if (couponMap.get("messsagetURL") != null && !couponMap.get("messsagetURL").isEmpty()) {
                            picURL = couponMap.get("messsagetURL");
                        } else {
                            if (couponMap.get("messageImageURL") != null && !couponMap.get("messageImageURL").isEmpty()) {
                                picURL = couponMap.get("messageImageURL");
                            }
                        }
                        itemMap.put("Title", title);
                        itemMap.put("Description", description);
                        itemMap.put("PicUrl", picURL);
                        itemMap.put("Url", couponURL);
                        responseMapList.add(itemMap);
                    }
                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
                    break;
                case SalesConstant.MERCHATE_TYPE:
                    responseMap.put("ArticleCount", String.valueOf(itemMapList.size()));
                    responseMap.put("MsgType", "news");
                    itemMapList = responseMessageCfg.getEntityMapList();
                    responseMapList = new ArrayList<Map<String, String>>();
                    String merchantURL;
                    for (Map<String, String> merchantMap : itemMapList) {
                        merchantURL = SalesConstant.MERCHANT_URL + "?weixinId=#{weixinId}&token=#{token}&merchantId=" + merchantMap.get("merchantId");
                        itemMap = new HashMap<String, String>(4, 1);
                        if (merchantMap.get("merchantName") != null && !merchantMap.get("merchantName").isEmpty()) {
                            title = merchantMap.get("merchantName");
                        } else {
                            if (merchantMap.get("messageTitle") != null && !merchantMap.get("messageTitle").isEmpty()) {
                                title = merchantMap.get("messageTitle");
                            }
                        }

                        if (merchantMap.get("messageTitle") != null && !merchantMap.get("messageTitle").isEmpty()) {
                            description = merchantMap.get("messageTitle");
                        }

                        if (merchantMap.get("backgroupImageURL") != null && !merchantMap.get("backgroupImageURL").isEmpty()) {
                            picURL = merchantMap.get("backgroupImageURL");
                        } else {
                            if (merchantMap.get("messageImageURL") != null && !merchantMap.get("messageImageURL").isEmpty()) {
                                picURL = merchantMap.get("messageImageURL");
                            }
                        }
                        itemMap.put("Title", title);
                        itemMap.put("Description", description);
                        itemMap.put("PicUrl", picURL);
                        itemMap.put("Url", merchantURL);
                        responseMapList.add(itemMap);
                    }
                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
                    break;

                case SalesConstant.WEI_SITE_TYPE:
                    responseMap.put("ArticleCount", "1");
                    responseMap.put("MsgType", "news");
                    responseMapList = new ArrayList<Map<String, String>>();
                    String weiSiteURL = "";
                    itemMapList = responseMessageCfg.getEntityMapList();
                    if (itemMapList != null && !itemMapList.isEmpty()) {
                        weiSiteURL = itemMapList.get(0).get("weiSiteSytleTemplateURL");
                    }
                    weiSiteURL = weiSiteURL + "?weixinId=#{weixinId}&token=#{token}&weiSiteConfigId=" + responseMessageCfg.getRelatedEventId();
                    itemMap = new HashMap<String, String>(4, 1);
                    itemMap.put("Title", "欢迎进入微网站" + messageTitle);
                    itemMap.put("Description", messageTitle);
                    itemMap.put("PicUrl", messageURL);
                    itemMap.put("Url", weiSiteURL);
                    responseMapList.add(itemMap);
                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
                    break;
            }
        } else {
            if (responseMessageCfg.getResponseContentType() == SalesConstant.TEXT_PICTURE_TYPE) {
                responseMap.put("ArticleCount", String.valueOf(itemMapList.size()));
                responseMap.put("MsgType", "news");
                itemMapList = responseMessageCfg.getEntityMapList();
                List<Map<String, String>> responseMapList = new ArrayList<Map<String, String>>();
                String url;
                Map<String, String> itemMap;
                for (Map<String, String> merchantMap : itemMapList) {
                    itemMap = new HashMap<String, String>(4, 1);
                    title = merchantMap.get("resourcesTitle");
                    description = merchantMap.get("resourcesContent");
                    picURL = merchantMap.get("resoucesImageURL");
                    url = merchantMap.get("linkSite");
                    itemMap.put("Title", title);
                    itemMap.put("Description", description);
                    itemMap.put("PicUrl", picURL);
                    itemMap.put("Url", url);
                    responseMapList.add(itemMap);
                }
                responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);

            } else {
                responseMap.put("MsgType", "text");
                responseMap.put("Content", responseContent);
                responseXML = response.responseTextMessage(responseMap);
            }
        }

        return responseXML;
    }

    public List<ResponseMessageCfg> getResponseMessageCfgList(String companyType) {
        return cacheMap.get(companyType);
    }

    public String getXML(String companyType) {
        return xmlCacheMap.get(companyType);
    }

    public void synchronizeCache(ResponseMessageCfg responseMessageCfg) {
    }

    public void sysnchronizeResponseMessageCfg(ResponseMessageCfg responseMessageCfg) {
        String companyType;
        byte responseType;
        List<ResponseMessageCfg> cacheResponseMessageCfgList;
        responseType = responseMessageCfg.getResponseContentType();
        companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType();
        cacheResponseMessageCfgList = cacheMap.get(companyType);
        if (cacheResponseMessageCfgList != null) {
            switch (responseType) {
                case SalesConstant.EVENT_TYPE:
                    for (Event event : eventList) {
                        if (event.getEventConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    }
                    break;
                case SalesConstant.RESERVATION_TYPE:
                    for (Reservation reservation : reservationList) {
                        if (reservation.getReservationConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(reservation.toMap());
                        }
                    }
                    break;
                case SalesConstant.MEMBER_CARD_TYPE:
                    for (MemberCard memberCard : memberCardList) {
                        if (memberCard.getMemberCardConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(memberCard.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEIBAR_TYPE:
                    for (WeiBarConfig weiBarConfig : weiBarConfigList) {
                        if (weiBarConfig.getWeiBarConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            responseMessageCfg.getEntityMapList().add(weiBarConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.COPOUN_TYPE:
                    for (Coupon coupon : couponList) {
                        if (coupon.getCouponConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(coupon.toMap());
                        }
                    }
                    break;
                case SalesConstant.MERCHATE_TYPE:
                    for (Merchant merchant : merchantList) {
                        if (merchant.getMerchantConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(merchant.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEI_SITE_TYPE:
                    for (WeiSiteConfig weiSiteConfig : weiSiteConfigList) {
                        if (weiSiteConfig.getWeiSiteConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            long companyId = weiSiteConfig.getCompanyId();
                            long weiSiteStyleTemplateConfigId = -1;
                            String weiSiteSytleTemplateURL = null;
                            for (WeiSiteStyleTemplate weiSiteStyleTemplate : weiSiteStyleTemplateList) {
                                if (companyId == weiSiteStyleTemplate.getCompanyId()) {
                                    weiSiteStyleTemplateConfigId = weiSiteStyleTemplate.getWeiSiteStyleTemplateCfgId();
                                    break;
                                }
                            }
                            for (WeiSiteSytleTemplateConfig weiSiteStyleTemplcateConfig : weiSiteStyleTemplcateConfigList) {
                                if (weiSiteStyleTemplateConfigId == weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateId()) {
                                    weiSiteSytleTemplateURL = weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateURL();
                                    break;
                                }
                            }
                            Map<String, String> eventConfigMap = weiSiteConfig.toMap();
                            if (weiSiteSytleTemplateURL != null) {
                                eventConfigMap.put("weiSiteSytleTemplateURL", weiSiteSytleTemplateURL);
                            }
                            responseMessageCfg.getEntityMapList().add(eventConfigMap);
                        }
                    }
                    break;
            }

            cacheResponseMessageCfgList.add(responseMessageCfg);
        } else {
            cacheResponseMessageCfgList = new ArrayList<ResponseMessageCfg>();
            switch (responseType) {
                case SalesConstant.EVENT_TYPE:
                    for (Event event : eventList) {
                        if (event.getEventConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    }
                    break;
                case SalesConstant.RESERVATION_TYPE:
                    for (Reservation reservation : reservationList) {
                        if (reservation.getReservationConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(reservation.toMap());
                        }
                    }
                    break;
                case SalesConstant.MEMBER_CARD_TYPE:
                    for (MemberCard memberCard : memberCardList) {
                        if (memberCard.getMemberCardConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(memberCard.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEIBAR_TYPE:
                    for (WeiBarConfig weiBarConfig : weiBarConfigList) {
                        if (weiBarConfig.getWeiBarConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            responseMessageCfg.getEntityMapList().add(weiBarConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.COPOUN_TYPE:
                    for (Coupon coupon : couponList) {
                        if (coupon.getCouponConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(coupon.toMap());
                        }
                    }
                    break;
                case SalesConstant.MERCHATE_TYPE:
                    for (Merchant merchant : merchantList) {
                        if (merchant.getMerchantConfigId() == responseMessageCfg.getRelatedId() && responseMessageCfg.getRelatedId() != -1) {
                            responseMessageCfg.getEntityMapList().add(merchant.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEI_SITE_TYPE:
                    for (WeiSiteConfig weiSiteConfig : weiSiteConfigList) {
                        if (weiSiteConfig.getWeiSiteConfigId() == responseMessageCfg.getRelatedEventId() && responseMessageCfg.getRelatedEventId() != -1) {
                            long companyId = weiSiteConfig.getCompanyId();
                            long weiSiteStyleTemplateConfigId = -1;
                            String weiSiteSytleTemplateURL = null;
                            for (WeiSiteStyleTemplate weiSiteStyleTemplate : weiSiteStyleTemplateList) {
                                if (companyId == weiSiteStyleTemplate.getCompanyId()) {
                                    weiSiteStyleTemplateConfigId = weiSiteStyleTemplate.getWeiSiteStyleTemplateCfgId();
                                    break;
                                }
                            }
                            for (WeiSiteSytleTemplateConfig weiSiteStyleTemplcateConfig : weiSiteStyleTemplcateConfigList) {
                                if (weiSiteStyleTemplateConfigId == weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateId()) {
                                    weiSiteSytleTemplateURL = weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateURL();
                                    break;
                                }
                            }
                            Map<String, String> eventConfigMap = weiSiteConfig.toMap();
                            if (weiSiteSytleTemplateURL != null) {
                                eventConfigMap.put("weiSiteSytleTemplateURL", weiSiteSytleTemplateURL);
                            }
                            responseMessageCfg.getEntityMapList().add(eventConfigMap);
                        }
                    }
                    break;
            }
            cacheResponseMessageCfgList.add(responseMessageCfg);
            cacheMap.put(companyType, cacheResponseMessageCfgList);
        }
    }

    public void setEntityMap(ResponseMessageCfg responseMessageCfg) {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        EntityDao<Event> eventDao = applicationContext.getEntityDAO(EntityNames.event);
        EntityDao<EventConfig> eventConfigDao = applicationContext.getEntityDAO(EntityNames.eventConfig);
        EntityDao<Reservation> reservationDao = applicationContext.getEntityDAO(EntityNames.reservation);
        EntityDao<ReservationConfig> reservationConfigDao = applicationContext.getEntityDAO(EntityNames.reservationConfig);
        EntityDao<Merchant> merchantDAO = applicationContext.getEntityDAO(EntityNames.merchant);
        EntityDao<MerchantConfig> merchantConfigDAO = applicationContext.getEntityDAO(EntityNames.merchantConfig);
        EntityDao<WeiBarConfig> weiBarConfigDAO = applicationContext.getEntityDAO(EntityNames.weiBarConfig);
        EntityDao<MemberCard> memberCardDao = applicationContext.getEntityDAO(EntityNames.memberCard);
        EntityDao<MemberCardConfig> memberCardConfigDao = applicationContext.getEntityDAO(EntityNames.memberCardConfig);
        EntityDao<Coupon> couponDao = applicationContext.getEntityDAO(EntityNames.coupon);
        EntityDao<CouponConfig> couponConfigDao = applicationContext.getEntityDAO(EntityNames.couponConfig);
        EntityDao<WeiSiteConfig> weiSiteConfigDAO = applicationContext.getEntityDAO(EntityNames.weiSiteConfig);
        EntityDao<Resources> resourceDAO = applicationContext.getEntityDAO(EntityNames.resources);
        EntityDao<ResourcesImage> resourceImageDAO = applicationContext.getEntityDAO(EntityNames.resourcesImage);
        byte responseContentType = responseMessageCfg.getResponseContentType();
        byte receiveType = responseMessageCfg.getType();
        long basicRelatedEventId = responseMessageCfg.getRelatedEventId();
        long relatedId = responseMessageCfg.getRelatedId();
        PrimaryKey primaryKey = new PrimaryKey();
        if (receiveType == SalesConstant.RESPONSE_TEXT_TYPE) {
            switch (responseContentType) {
                case SalesConstant.EVENT_TYPE:
                    if (relatedId != -1) {
                        primaryKey.putKeyField("eventId", String.valueOf(relatedId));
                        Event event = eventDao.inqurieByKey(primaryKey);
                        if (event != null) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    } else if (basicRelatedEventId != -1) {
                        primaryKey.putKeyField("eventConfigId", String.valueOf(basicRelatedEventId));
                        EventConfig eventConfig = eventConfigDao.inqurieByKey(primaryKey);
                        if (eventConfig != null) {
                            responseMessageCfg.getEntityMapList().add(eventConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.RESERVATION_TYPE:
                    if (relatedId != -1) {
                        primaryKey.putKeyField("reservationId", String.valueOf(relatedId));
                        Reservation event = reservationDao.inqurieByKey(primaryKey);
                        if (event != null) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    } else if (basicRelatedEventId != -1) {
                        primaryKey.putKeyField("reservationConfigId", String.valueOf(basicRelatedEventId));
                        ReservationConfig eventConfig = reservationConfigDao.inqurieByKey(primaryKey);
                        if (eventConfig != null) {
                            responseMessageCfg.getEntityMapList().add(eventConfig.toMap());
                        }
                    }

                    break;
                case SalesConstant.MEMBER_CARD_TYPE:
                    if (relatedId != -1) {
                        primaryKey.putKeyField("memberCardId", String.valueOf(relatedId));
                        MemberCard event = memberCardDao.inqurieByKey(primaryKey);
                        if (event != null) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    } else if (basicRelatedEventId != -1) {
                        primaryKey.putKeyField("memberCardConfigId", String.valueOf(basicRelatedEventId));
                        MemberCardConfig eventConfig = memberCardConfigDao.inqurieByKey(primaryKey);
                        if (eventConfig != null) {
                            responseMessageCfg.getEntityMapList().add(eventConfig.toMap());
                        }
                    }

                    break;
                case SalesConstant.WEIBAR_TYPE:

                    if (basicRelatedEventId != -1) {
                        primaryKey.putKeyField("weiBarConfigId", String.valueOf(basicRelatedEventId));
                        WeiBarConfig eventConfig = weiBarConfigDAO.inqurieByKey(primaryKey);
                        if (eventConfig != null) {
                            responseMessageCfg.getEntityMapList().add(eventConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.COPOUN_TYPE:
                    if (relatedId != -1) {
                        primaryKey.putKeyField("couponId", String.valueOf(relatedId));
                        Coupon event = couponDao.inqurieByKey(primaryKey);
                        if (event != null) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    } else if (basicRelatedEventId != -1) {
                        primaryKey.putKeyField("couponConfigId", String.valueOf(basicRelatedEventId));
                        CouponConfig eventConfig = couponConfigDao.inqurieByKey(primaryKey);
                        if (eventConfig != null) {
                            responseMessageCfg.getEntityMapList().add(eventConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.MERCHATE_TYPE:
                    if (relatedId != -1) {
                        primaryKey.putKeyField("merchantId", String.valueOf(relatedId));
                        Merchant event = merchantDAO.inqurieByKey(primaryKey);
                        if (event != null) {
                            responseMessageCfg.getEntityMapList().add(event.toMap());
                        }
                    } else if (basicRelatedEventId != -1) {
                        primaryKey.putKeyField("merchantConfigId", String.valueOf(basicRelatedEventId));
                        MerchantConfig eventConfig = merchantConfigDAO.inqurieByKey(primaryKey);
                        if (eventConfig != null) {
                            responseMessageCfg.getEntityMapList().add(eventConfig.toMap());
                        }
                    }
                    break;
                case SalesConstant.WEI_SITE_TYPE:
                    if (basicRelatedEventId != -1) {
                        primaryKey.putKeyField("weiSiteConfigId", String.valueOf(basicRelatedEventId));
                        WeiSiteConfig eventConfig = weiSiteConfigDAO.inqurieByKey(primaryKey);
                        if (eventConfig != null) {

                            long companyId = eventConfig.getCompanyId();
                            long weiSiteStyleTemplateConfigId = -1;
                            String weiSiteSytleTemplateURL = null;
                            for (WeiSiteStyleTemplate weiSiteStyleTemplate : weiSiteStyleTemplateList) {
                                if (companyId == weiSiteStyleTemplate.getCompanyId()) {
                                    weiSiteStyleTemplateConfigId = weiSiteStyleTemplate.getWeiSiteStyleTemplateCfgId();
                                    break;
                                }
                            }
                            for (WeiSiteSytleTemplateConfig weiSiteStyleTemplcateConfig : weiSiteStyleTemplcateConfigList) {
                                if (weiSiteStyleTemplateConfigId == weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateId()) {
                                    weiSiteSytleTemplateURL = weiSiteStyleTemplcateConfig.getWeiSiteSytleTemplateURL();
                                    break;
                                }
                            }
                            Map<String, String> eventConfigMap = eventConfig.toMap();
                            if (weiSiteSytleTemplateURL != null) {
                                eventConfigMap.put("weiSiteSytleTemplateURL", weiSiteSytleTemplateURL);
                            }
                            responseMessageCfg.getEntityMapList().add(eventConfigMap);

                        }
                    }
                    break;
            }
        } else {
            if (relatedId != -1) {
                primaryKey.putKeyField("resourcesId", String.valueOf(relatedId));
                Resources resources = resourceDAO.inqurieNoCacheByKey(primaryKey);
                if (resources != null) {
                    long resourcesId = resources.getResourcesId();
                    List<Condition> conditionList = new ArrayList<Condition>();
                    Condition resourcesIdCondition = new Condition("resourcesId", ConditionTypeEnum.EQUAL, String.valueOf(resourcesId));
                    conditionList.add(resourcesIdCondition);
                    List<ResourcesImage> resourceImageList = resourceImageDAO.inquireByCondition(conditionList);
                    responseMessageCfg.setResponseContent(resources.getResourcesName());
                    responseMessageCfg.setResponseImageURL(resources.getResourcesURL());
                    for (ResourcesImage resource : resourceImageList) {
                        responseMessageCfg.getEntityMapList().add(resource.toMap());
                    }
                }
            }
        }

    }

    public void insertCache(ResponseMessageCfg responseMessageCfg) {
        String keyword = responseMessageCfg.getKeyword();
        String companyType;
        if (responseMessageCfg.getType() == SalesConstant.RESPONSE_TEXT_TYPE) {
            companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType() + "_" + keyword;
        } else {
            companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType();
        }
        List<ResponseMessageCfg> responseMessageCfgList = cacheMap.get(companyType);
        logger.debug("companyType={}", companyType);
        logger.debug("responseMessageCfgList={}", responseMessageCfgList);
        if (responseMessageCfgList != null) {
            this.setEntityMap(responseMessageCfg);
            responseMessageCfgList.add(responseMessageCfg);
        } else {
            responseMessageCfgList = new ArrayList<ResponseMessageCfg>();
            this.setEntityMap(responseMessageCfg);
            responseMessageCfgList.add(responseMessageCfg);
            cacheMap.put(companyType, responseMessageCfgList);
        }
        String xml = this.convertResponseMessageToXml(responseMessageCfgList);
        logger.debug("companyType={}", companyType);
        logger.debug("xml={}", xml);
        this.xmlCacheMap.put(companyType, xml);

    }

    public void updateCache(Resources resource) {
        ApplicationContext applicationContext = ApplicationContext.CTX;
        EntityDao<ResponseMessageCfg> entityDao = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
        List<Condition> responseMessageCfgconditionList = new ArrayList<Condition>(2);
        Condition cfgTypeCondition = new Condition("cfgType", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.DETAIL_CFG_TYPE));
        Condition typeCondition = new Condition("type", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.RESPONSE_EVENT_SUBSCRIBE_TYPE));
        Condition relatedIdCondition = new Condition("relatedId", ConditionTypeEnum.EQUAL, String.valueOf(resource.getResourcesId()));
        responseMessageCfgconditionList.add(cfgTypeCondition);
        responseMessageCfgconditionList.add(typeCondition);
        responseMessageCfgconditionList.add(relatedIdCondition);
        List<ResponseMessageCfg> responseMessageCfgList = entityDao.inquireByCondition(responseMessageCfgconditionList);
        String companyType;
        if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
            this.updateCache(responseMessageCfgList.get(0), null);
        }
    }

    public void updateCache(ResponseMessageCfg responseMessageCfg, String keyword) {
        String companyType;
        if (responseMessageCfg.getType() == SalesConstant.RESPONSE_TEXT_TYPE) {
            companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType() + "_" + keyword;
        } else {
            companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType();
        }
        logger.debug("companyType={}", companyType);
        List<ResponseMessageCfg> responseMessageCfgList = cacheMap.get(companyType);
        logger.debug("responseMessageCfgList={}", responseMessageCfgList);
        if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
            Iterator<ResponseMessageCfg> iter = responseMessageCfgList.iterator();
            ResponseMessageCfg tempResponseMessageCfg;
            while (iter.hasNext()) {
                tempResponseMessageCfg = iter.next();
                if (tempResponseMessageCfg.getResponseMessageCfgId() == responseMessageCfg.getResponseMessageCfgId()) {
                    iter.remove();
                    break;
                }
            }
        }
        this.setEntityMap(responseMessageCfg);
        if (responseMessageCfgList == null) {
            responseMessageCfgList = new ArrayList<ResponseMessageCfg>();
        }
        responseMessageCfgList.add(responseMessageCfg);
        cacheMap.put(companyType, responseMessageCfgList);
        String xml = this.convertResponseMessageToXml(responseMessageCfgList);
        logger.debug("companyType={}", companyType);
        logger.debug("xml={}", xml);
        this.xmlCacheMap.put(companyType, xml);
    }

    public void deleteCache(ResponseMessageCfg responseMessageCfg) {
        String keyword = responseMessageCfg.getKeyword();
        String companyType;
        if (responseMessageCfg.getType() == SalesConstant.RESPONSE_TEXT_TYPE) {
            companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType() + "_" + keyword;
        } else {
            companyType = responseMessageCfg.getCompanyId() + "_" + responseMessageCfg.getType();
        }
        List<ResponseMessageCfg> responseMessageCfgList = cacheMap.get(companyType);
        logger.debug("companyType={}", companyType);
        logger.debug("responseMessageCfgList={}", responseMessageCfgList);
        if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
            Iterator<ResponseMessageCfg> iter = responseMessageCfgList.iterator();
            ResponseMessageCfg tempResponseMessageCfg;
            while (iter.hasNext()) {
                tempResponseMessageCfg = iter.next();
                if (tempResponseMessageCfg.getResponseMessageCfgId() == responseMessageCfg.getResponseMessageCfgId()) {
                    iter.remove();
                    break;
                }
            }
        }
        String xml = this.convertResponseMessageToXml(responseMessageCfgList);
        logger.debug("companyType={}", companyType);
        logger.debug("xml={}", xml);
        this.xmlCacheMap.put(companyType, xml);
    }
}
