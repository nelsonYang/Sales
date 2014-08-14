package com.sales.datacache;

import com.framework.cache.spi.cache.RollBackCache;
import com.framework.context.ApplicationContext;
import com.framework.entity.context.DAOContext;
import com.framework.entity.dao.EntityDao;
import com.framework.entity.threadlocal.RollBackCacheThreadLocalManager;
import com.frameworkLog.factory.LogFactory;
import com.sales.entity.EntityNames;
import com.sales.entity.WeixinMessage;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class MessageBlockQueueCache {

    private static Logger logger = LogFactory.getInstance().getLogger(MessageBlockQueueCache.class);

    public static MessageBlockQueueCache getInstance() {
        return messageBlockQueueCacheInstance;
    }
    private static final MessageBlockQueueCache messageBlockQueueCacheInstance = new MessageBlockQueueCache();
    private static final BlockingQueue<Map<String, String>> messageQueue = new ArrayBlockingQueue<Map<String, String>>(100);
    private final ExecutorService scanExecutor = Executors.newFixedThreadPool(5);

    public boolean putMessage(Map<String, String> dataMap) {
        boolean isSuccess = false;
        try {
            messageQueue.put(dataMap);
            isSuccess = true;
        } catch (InterruptedException ex) {
        }
        return isSuccess;
    }

    public void takeMessage() {
        logger.info("message handler thread start ");
        scanExecutor.execute(new Runnable() {
            public void run() {
                  while (true) {
                   TaskWorker.execute();
                }
            }
        });


    }

//    private int handleMessage(int type, Map<String, String> dataMap, String accessToken) {
//        int resultCode = -1;
//        switch (type) {
//            case SalesConstant.RESPONSE_TEXT_TYPE:
//                resultCode = handleTextMessage(dataMap, accessToken);
//                break;
//            case SalesConstant.RESPONSE_AUDIO_TYPE:
//                break;
//            case SalesConstant.RESPONSE_EVENT_EVENT_TYPE:
//                break;
//            case SalesConstant.RESPONSE_EVENT_SCAN_TYPE:
//                break;
//            case SalesConstant.RESPONSE_EVENT_SUBSCRIBE_TYPE:
//                resultCode = handleSuscribleMessage(dataMap, accessToken);
//                break;
//            case SalesConstant.RESPONSE_EVENT_UNSUBSCRIBE_TYPE:
//                break;
//            case SalesConstant.RESPONSE_IMAGE_TYPE:
//                break;
//            case SalesConstant.RESPONSE_LINK_TYPE:
//                break;
//            case SalesConstant.RESPONSE_LOCATION_TYPE:
//                resultCode = handleLocationMessage(dataMap, accessToken);
//                break;
//            case SalesConstant.RESPONSE_MUSIC_TYPE:
//                break;
//            case SalesConstant.RESPONSE_NEWS_TYPE:
//                break;
//            case SalesConstant.RESPONSE_VEDIO_TYPE:
//                break;
//        }
//        logger.debug("resultCode={}", resultCode);
//        return resultCode;
//    }
   static class TaskWorker  {

        public static void  execute() {
            logger.info("task worker start work...");
            Map<String, String> dataMap;
            ApplicationContext applicationContext = ApplicationContext.CTX;
            EntityDao<WeixinMessage> weixinMessageDao = applicationContext.getEntityDAO(EntityNames.weixinMessage);
            DAOContext daoContext = applicationContext.getDaoContext();
            RollBackCacheThreadLocalManager<RollBackCache> rollbackThreadLocalManager = daoContext.getThreadLocalManager();
            try {
                dataMap = messageQueue.take();
                logger.debug("dataMap ={}", dataMap);
                RollBackCache rollBackCache = daoContext.buildRollBackCache();
                rollbackThreadLocalManager.openThreadLocal(rollBackCache);
                //业务代码
                weixinMessageDao.insert(dataMap);
//                String type = dataMap.get("type");
//                String companyId = dataMap.get("companyId");
//                EntityDao<WeixinToken> weixinTokenDAO = applicationContext.getEntityDAO(EntityNames.weixinToken);
//                List<Condition> conditionList = new ArrayList<Condition>(2);
//                Condition companyIdCondtion = new Condition("companyId", ConditionTypeEnum.EQUAL, companyId);
//                conditionList.add(companyIdCondtion);
//                List<WeixinToken> weixinTokenList = weixinTokenDAO.inquireByCondition(conditionList);
//                if (weixinTokenList != null && !weixinTokenList.isEmpty()) {
//                    WeixinAPI weixinAPI = WeixinReceiveServlet.getWeiXinAPI();
//                    WeixinToken weixinToken = weixinTokenList.get(0);
//                    String accessToken = weixinToken.getAccessToken();
//                    //请求接口判断是否成功，如果不成功执行accesstoken的请求
//                    int resultCode = handleMessage(Integer.parseInt(type), dataMap, accessToken);
//                    if (resultCode == 42001) {
//                        Map<String, String> parameters = new HashMap<String, String>(2, 1);
//                        parameters.put("appid", weixinToken.getAppId());
//                        parameters.put("secret", weixinToken.getAppSecret());
//                        Map<String, String> resultMap = weixinAPI.getAccessToken(parameters);
//                        accessToken = resultMap.get("access_token");
//                        if (accessToken != null && !accessToken.isEmpty()) {
//                            //执行上次的请求发送信息
//                            dataMap.put("tokenId", String.valueOf(weixinToken.getTokenId()));
//                            dataMap.put("accessToken", accessToken);
//                            handleMessage(Integer.parseInt(type), dataMap, accessToken);
//                            dataMap.put("tokenId", String.valueOf(weixinToken.getTokenId()));
//                            dataMap.put("accessToken", accessToken);
//                            weixinTokenDAO.update(dataMap);
//                        }
//                    }
//                } else {
//                    logger.error("please set appid and secret in the system!!!.");
//                    rollBackCache = rollbackThreadLocalManager.getValue();
//                    rollBackCache.rollback();
//                }
            } catch (Exception ex) {
                logger.error("rollback exception", ex);
                RollBackCache rollBackCache = rollbackThreadLocalManager.getValue();
                rollBackCache.rollback();
            } finally {
                rollbackThreadLocalManager.closeThreadLocal();
            }
            logger.info("task worker end ...");


        }
    }
//    private int handleLocationMessage(Map<String, String> dataMap, String accessToken) {
//        int resultCode = 1;
//        List<Condition> conditionList = new ArrayList<Condition>(3);
//        //添加地理位置的条件     
//        String companyId = dataMap.get("companyId");
//        String token = dataMap.get("token");
//        String latitude = dataMap.get("latitude");
//        String longitude = dataMap.get("longitude");
//        String precision = dataMap.get("precision");
//        
//        ApplicationContext applicationContext = ApplicationContext.CTX;
//        Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, companyId);
//        Condition statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
//        conditionList.add(companyIdCondition);
//        conditionList.add(statusCondition);
//        EntityDao<Merchant> merchantDAO = applicationContext.getEntityDAO(EntityNames.merchant);
//        List<Merchant> merchantList = merchantDAO.inquireByCondition(conditionList);
//        Merchant merchant = null;
//        Merchant tempMerchant;
//        double minDistance = Double.MAX_VALUE;
//        double tempDistance;
//        int minIndex = 0;
//        for (int index = 0; index < merchantList.size(); index++) {
//            tempMerchant = merchantList.get(index);
//            tempDistance = LatLonUtils.getDistance(tempMerchant.getLat(), tempMerchant.getLag(), Double.parseDouble(latitude), Double.parseDouble(longitude));
//            if (minDistance > tempDistance) {
//                minDistance = tempDistance;
//                minIndex = index;
//            }
//        }
//        merchant = merchantList.get(minIndex);
//        Map<String, String> responseMap = new HashMap<String, String>(8, 1);
//        responseMap.put("access_token", accessToken);
//        responseMap.put("touser", dataMap.get("fromUser"));
//        responseMap.put("fromuser", dataMap.get("toUser"));
//        responseMap.put("msgtype", "news");
//        List<Map<String, String>> responseMapList = new ArrayList<Map<String, String>>();
//        String merchantURL;
//        Map<String, String> itemMap;
//        merchantURL = SalesConstant.MERCHANT_URL + "?token=" + token + "&merchantId=" + merchant.getMerchantId();
//        itemMap = new HashMap<String, String>(4, 1);
//        itemMap.put("title", merchant.getMerchantName());
//        itemMap.put("description", merchant.getMerchantName());
//        itemMap.put("picurl", merchant.getBackgroupImageURL());
//        itemMap.put("url", merchantURL);
//        responseMapList.add(itemMap);
//        WeixinAPI weixinAPI = WeixinReceiveServlet.getWeiXinAPI();
//        Map<String, String> resultMap = weixinAPI.sendTextAndImageMessage(responseMap, responseMapList);
//        resultCode = Integer.parseInt(resultMap.get("errcode"));
//        return resultCode;
//    }
//    
//    private int handleSuscribleMessage(Map<String, String> dataMap, String accessToken) {
//        //回复微网站
//        int resultCode = 1;
//        String companyId = dataMap.get("companyId");
//        ApplicationContext applicationContext = ApplicationContext.CTX;
//        List<Map<String, String>> responseMapList = new ArrayList<Map<String, String>>();
//        EntityDao<ResponseMessageCfg> entityDao = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
//        List<Condition> conditionList = new ArrayList<Condition>();
//        Condition typeCondition = new Condition("type", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.FIRST_SUSCRIBE_TYPE));
//        Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, companyId);
//        conditionList.add(typeCondition);
//        conditionList.add(companyCondition);
//        List<ResponseMessageCfg> responseMessageCfgList = entityDao.inquireByCondition(conditionList);
//        if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
//            ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
//            logger.debug("SuscribeMessageHandler here");
//            Map<String, String> commonMap = new HashMap<String, String>();
//            commonMap.put("access_token", accessToken);
//            commonMap.put("touser", dataMap.get("fromUser"));
//            commonMap.put("fromuser", dataMap.get("toUser"));
//            commonMap.put("msgtype", "news");
//            logger.debug("SuscribeMessageHandler commonMap={}", commonMap);
//            long resourcesId = responseMessageCfg.getRelatedId();
//            conditionList = new ArrayList<Condition>(2);
//            Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, String.valueOf(companyId));
//            conditionList.add(companyIdCondition);
//            Condition resourcesIdCondition = new Condition("resourcesId", ConditionTypeEnum.EQUAL, String.valueOf(resourcesId));
//            conditionList.add(resourcesIdCondition);
//            EntityDao<ResourcesImage> resourcesImageDAO = applicationContext.getEntityDAO(EntityNames.resourcesImage);
//            List<ResourcesImage> resourcesImageList = resourcesImageDAO.inquireByCondition(conditionList);
//            Map<String, String> itemMap;
//            for (ResourcesImage resourcesImage : resourcesImageList) {
//                itemMap = new HashMap<String, String>(4, 1);
//                itemMap.put("title", resourcesImage.getResourcesTitle());
//                itemMap.put("description", resourcesImage.getResourcesContent());
//                itemMap.put("picurl", resourcesImage.getResoucesImageURL());
//                itemMap.put("url", resourcesImage.getLinkSite());
//                responseMapList.add(itemMap);
//            }
//            WeixinAPI weixinAPI = WeixinReceiveServlet.getWeiXinAPI();
//            Map<String, String> resultMap = weixinAPI.sendTextAndImageMessage(commonMap, responseMapList);
//            resultCode = Integer.parseInt(resultMap.get("errcode"));
//        }
//        
//        
//        return resultCode;
//    }
//    
//    private int handleTextMessage(Map<String, String> dataMap, String accessToken) {
//        int resultCode = 1;
//        String token = dataMap.get("token");
//        String companyId = dataMap.get("companyId");
//        String keyword = dataMap.get("content");
//        ApplicationContext applicationContext = ApplicationContext.CTX;
//        EntityDao<ResponseMessageCfg> entityDao = applicationContext.getEntityDAO(EntityNames.responseMessageCfg);
//        List<Condition> conditionList = new ArrayList<Condition>(3);
//        Condition typeCondition = new Condition("type", ConditionTypeEnum.EQUAL, String.valueOf(MessageTypeEnum.text.ordinal()));
//        Condition companyCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, companyId);
//        Condition keywordCondition = new Condition("keyword", ConditionTypeEnum.LIKE, "%" + keyword + "%");
//        conditionList.add(typeCondition);
//        conditionList.add(companyCondition);
//        conditionList.add(keywordCondition);
//        logger.debug("qurey responseMessage");
//        List<ResponseMessageCfg> responseMessageCfgList = entityDao.inquireByCondition(conditionList);
//        logger.debug("responseMessageCfgList.size=" + responseMessageCfgList.size());
//        if (responseMessageCfgList != null && !responseMessageCfgList.isEmpty()) {
//            logger.debug("text responseConfig");
//            Map<String, String> resultMap;
//            WeixinAPI weixinAPI = WeixinReceiveServlet.getWeiXinAPI();
//            ResponseMessageCfg responseMessageCfg = responseMessageCfgList.get(0);
//            byte responseType = responseMessageCfg.getResponseContentType();
//            String responseContent = responseMessageCfg.getResponseContent();
//            String imageUrl = responseMessageCfg.getResponseImageURL();
//            Map<String, String> responseMap = new HashMap<String, String>();
//            responseMap.put("access_token", accessToken);
//            responseMap.put("touser", dataMap.get("fromUser"));
//            responseMap.put("fromuser", dataMap.get("toUser"));
//            responseMap.put("msgtype", "news");
//            logger.debug("responseType:{}", responseType);
//            switch (responseType) {
//                case SalesConstant.TEXT_TYPE:
//                    responseMap.put("msgtype", "text");
//                    responseMap.put("content", responseContent);
//                    resultMap = weixinAPI.sendTextMessage(responseMap);
//                    resultCode = Integer.parseInt(resultMap.get("errcode"));
//                    break;
//                case SalesConstant.TEXT_PICTURE_TYPE:
//                    responseMap.put("content", responseContent);
//                    List<Map<String, String>> responseMapList = new ArrayList<Map<String, String>>();
//                    Map<String, String> itemMap = new HashMap<String, String>(4, 1);
//                    itemMap.put("title", responseContent);
//                    itemMap.put("description", responseContent);
//                    itemMap.put("picurl", imageUrl);
//                    itemMap.put("url", "");
//                    responseMapList.add(itemMap);
//                    resultMap = weixinAPI.sendTextAndImageMessage(responseMap, responseMapList);
//                    resultCode = Integer.parseInt(resultMap.get("errcode"));
//                    break;
//                case SalesConstant.EVENT_TYPE:
//                    
//                    long eventConfigId = responseMessageCfg.getRelatedId();
//                    PrimaryKey primaryKey = new PrimaryKey();
//                    primaryKey.putKeyField("eventConfigId", String.valueOf(eventConfigId));
//                    primaryKey.putKeyField("companyId", String.valueOf(companyId));
//                    EntityDao<EventConfig> entityDAO = applicationContext.getEntityDAO(EntityNames.eventConfig);
//                    EventConfig entity = entityDAO.inqurieByKey(primaryKey);
//                    String messageTitle = entity.getMessageTitle();
//                    String messageURL = entity.getMessageImageURL();
//                    EntityDao<Event> eventDao = applicationContext.getEntityDAO(EntityNames.event);
//                    conditionList = new ArrayList<Condition>(3);
//                    Condition eventConfigIdCondition = new Condition("eventConfigId", ConditionTypeEnum.EQUAL, String.valueOf(eventConfigId));
//                    Condition companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, companyId);
//                    Condition statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
//                    conditionList.add(companyIdCondition);
//                    conditionList.add(eventConfigIdCondition);
//                    conditionList.add(statusCondition);
//                    List<Event> eventList = eventDao.inquireByCondition(conditionList);
//                    
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String eventURL;
//                    for (Event event : eventList) {
//                        eventURL = SalesConstant.EVENT_URL + "?token=" + token + "&eventId=" + event.getEventId();
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("title", event.getEventName());
//                        itemMap.put("description", messageTitle);
//                        itemMap.put("picurl", messageURL);
//                        itemMap.put("url", eventURL);
//                        responseMapList.add(itemMap);
//                    }
//                    resultMap = weixinAPI.sendTextAndImageMessage(responseMap, responseMapList);
//                    resultCode = Integer.parseInt(resultMap.get("errcode"));
//                    break;
//                case SalesConstant.RESERVATION_TYPE:
//                    long reservationConfigId = responseMessageCfg.getRelatedId();
//                    primaryKey = new PrimaryKey();
//                    primaryKey.putKeyField("reservationConfigId", String.valueOf(reservationConfigId));
//                    primaryKey.putKeyField("companyId", String.valueOf(companyId));
//                    EntityDao<ReservationConfig> reservationConfigDAO = applicationContext.getEntityDAO(EntityNames.reservationConfig);
//                    ReservationConfig reservationConfig = reservationConfigDAO.inqurieByKey(primaryKey);
//                    messageTitle = reservationConfig.getMessageTitle();
//                    messageURL = reservationConfig.getMessageImageURL();
//                    
//                    EntityDao<Reservation> reservationDao = applicationContext.getEntityDAO(EntityNames.reservation);
//                    conditionList = new ArrayList<Condition>(3);
//                    Condition reservationConfigIdCondition = new Condition("reservationConfigId", ConditionTypeEnum.EQUAL, String.valueOf(reservationConfigId));
//                    companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, companyId);
//                    statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
//                    conditionList.add(companyIdCondition);
//                    conditionList.add(reservationConfigIdCondition);
//                    conditionList.add(statusCondition);
//                    List<Reservation> reservationList = reservationDao.inquireByCondition(conditionList);
//                    
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String reservationURL;
//                    for (Reservation reservation : reservationList) {
//                        reservationURL = SalesConstant.RESERVATION_URL + "?token=" + token + "&reservationId=" + reservation.getReservationId();
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("title", messageTitle);
//                        itemMap.put("description", reservation.getReservationDesc());
//                        itemMap.put("picurl", messageURL);
//                        itemMap.put("url", reservationURL);
//                        responseMapList.add(itemMap);
//                    }
//                    resultMap = weixinAPI.sendTextAndImageMessage(responseMap, responseMapList);
//                    resultCode = Integer.parseInt(resultMap.get("errcode"));
//                    break;
//                case SalesConstant.WEIBAR_TYPE:
//                    long weiBarConfigId = responseMessageCfg.getRelatedId();
//                    primaryKey = new PrimaryKey();
//                    primaryKey.putKeyField("weiBarConfigId", String.valueOf(weiBarConfigId));
//                    primaryKey.putKeyField("companyId", String.valueOf(companyId));
//                    EntityDao<WeiBarConfig> weiBarConfigDAO = applicationContext.getEntityDAO(EntityNames.weiBarConfig);
//                    WeiBarConfig weiBarConfig = weiBarConfigDAO.inqurieByKey(primaryKey);
//                    messageTitle = weiBarConfig.getMessageTitle();
//                    messageURL = weiBarConfig.getMessageImageURL();
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String weiBarURL = SalesConstant.WEIBAR_URL + "?token=" + token + "&weiBarConfigId=" + weiBarConfigId;
//                    itemMap = new HashMap<String, String>(4, 1);
//                    itemMap.put("title", messageTitle);
//                    itemMap.put("description", messageTitle);
//                    itemMap.put("picurl", messageURL);
//                    itemMap.put("url", weiBarURL);
//                    responseMapList.add(itemMap);
//                    resultMap = weixinAPI.sendTextAndImageMessage(responseMap, responseMapList);
//                    resultCode = Integer.parseInt(resultMap.get("errcode"));
//                    break;
//                case SalesConstant.MEMBER_CARD_TYPE:
//                    long memberCardConfigId = responseMessageCfg.getRelatedId();
//                    primaryKey = new PrimaryKey();
//                    primaryKey.putKeyField("reservationConfigId", String.valueOf(memberCardConfigId));
//                    primaryKey.putKeyField("companyId", String.valueOf(companyId));
//                    EntityDao<MemberCardConfig> memberCardConfigDAO = applicationContext.getEntityDAO(EntityNames.memberCardConfig);
//                    MemberCardConfig memberCardConfig = memberCardConfigDAO.inqurieByKey(primaryKey);
//                    messageTitle = memberCardConfig.getMemberCardName();
//                    messageURL = memberCardConfig.getMessageImageURL();
//                    
//                    EntityDao<MemberCard> memberCardDao = applicationContext.getEntityDAO(EntityNames.memberCard);
//                    conditionList = new ArrayList<Condition>(3);
//                    Condition memberCardConfigIdCondition = new Condition("memberCardConfigId", ConditionTypeEnum.EQUAL, String.valueOf(memberCardConfigId));
//                    companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, companyId);
//                    statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
//                    conditionList.add(companyIdCondition);
//                    conditionList.add(memberCardConfigIdCondition);
//                    conditionList.add(statusCondition);
//                    List<MemberCard> memberCardList = memberCardDao.inquireByCondition(conditionList);
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    for (MemberCard memberCard : memberCardList) {
//                        String memberCardURL = SalesConstant.MEMBER_CARD_URL + "?token=" + token + "&memberCardId=" + memberCard.getMemberCardId();
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("title", memberCard.getMemberCardName());
//                        itemMap.put("description", messageTitle);
//                        itemMap.put("picurl", messageURL);
//                        itemMap.put("url", memberCardURL);
//                        responseMapList.add(itemMap);
//                    }
//                    resultMap = weixinAPI.sendTextAndImageMessage(responseMap, responseMapList);
//                    resultCode = Integer.parseInt(resultMap.get("errcode"));
//                    break;
//                case SalesConstant.COPOUN_TYPE:
//                    long couponConfigId = responseMessageCfg.getRelatedId();
//                    primaryKey = new PrimaryKey();
//                    primaryKey.putKeyField("couponConfigId", String.valueOf(couponConfigId));
//                    primaryKey.putKeyField("companyId", String.valueOf(companyId));
//                    EntityDao<CouponConfig> couPonConfigDAO = applicationContext.getEntityDAO(EntityNames.coupon);
//                    CouponConfig couponConfig = couPonConfigDAO.inqurieByKey(primaryKey);
//                    messageTitle = couponConfig.getMessageTitle();
//                    messageURL = couponConfig.getMessageImageURL();
//                    EntityDao<Coupon> couponDao = applicationContext.getEntityDAO(EntityNames.coupon);
//                    conditionList = new ArrayList<Condition>(3);
//                    Condition couponConfigIdCondition = new Condition("couponConfigId", ConditionTypeEnum.EQUAL, String.valueOf(couponConfigId));
//                    companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, companyId);
//                    statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
//                    conditionList.add(companyIdCondition);
//                    conditionList.add(couponConfigIdCondition);
//                    conditionList.add(statusCondition);
//                    List<Coupon> couponList = couponDao.inquireByCondition(conditionList);
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String couponURL;
//                    for (Coupon coupon : couponList) {
//                        couponURL = SalesConstant.COPOUN_URL + "?token=" + token + "&couponId=" + coupon.getCouponId();
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("title", coupon.getCouponName());
//                        itemMap.put("description", coupon.getMessageDesc());
//                        itemMap.put("picurl", coupon.getMessageURL());
//                        itemMap.put("irl", couponURL);
//                        responseMapList.add(itemMap);
//                    }
//                    resultMap = weixinAPI.sendTextAndImageMessage(responseMap, responseMapList);
//                    resultCode = Integer.parseInt(resultMap.get("errcode"));
//                    break;
//                case SalesConstant.MERCHATE_TYPE:
//                    long merchantConfigId = responseMessageCfg.getRelatedId();
//                    primaryKey = new PrimaryKey();
//                    primaryKey.putKeyField("merchantConfigId", String.valueOf(merchantConfigId));
//                    primaryKey.putKeyField("companyId", String.valueOf(companyId));
//                    EntityDao<MerchantConfig> merchantConfigDAO = applicationContext.getEntityDAO(EntityNames.merchantConfig);
//                    MerchantConfig merchantConfig = merchantConfigDAO.inqurieByKey(primaryKey);
//                    messageTitle = merchantConfig.getMerchantName();
//                    messageURL = merchantConfig.getMessageImageURL();
//                    conditionList = new ArrayList<Condition>(3);
//                    Condition merchantConfigIdCondition = new Condition("merchantConfigId", ConditionTypeEnum.EQUAL, String.valueOf(merchantConfigId));
//                    companyIdCondition = new Condition("companyId", ConditionTypeEnum.EQUAL, companyId);
//                    statusCondition = new Condition("status", ConditionTypeEnum.EQUAL, String.valueOf(SalesConstant.NORMAL_EVENT_STATUS));
//                    conditionList.add(companyIdCondition);
//                    conditionList.add(merchantConfigIdCondition);
//                    conditionList.add(statusCondition);
//                    EntityDao<Merchant> merchantDAO = applicationContext.getEntityDAO(EntityNames.merchant);
//                    List<Merchant> merchantList = merchantDAO.inquireByCondition(conditionList);
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String merchantURL;
//                    for (Merchant merchant : merchantList) {
//                        merchantURL = SalesConstant.MERCHANT_URL + "?token=" + token + "&merchantId=" + merchant.getMerchantId();
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("title", merchant.getMerchantName());
//                        itemMap.put("description", messageTitle);
//                        itemMap.put("picurl", merchant.getBackgroupImageURL());
//                        itemMap.put("url", merchantURL);
//                        responseMapList.add(itemMap);
//                    }
//                    resultMap = weixinAPI.sendTextAndImageMessage(responseMap, responseMapList);
//                    resultCode = Integer.parseInt(resultMap.get("errcode"));
//                    break;
//                
//                case SalesConstant.WEI_SITE_TYPE:
//                    long weiSiteConfigId = responseMessageCfg.getRelatedId();
//                    primaryKey = new PrimaryKey();
//                    primaryKey.putKeyField("weiSiteConfigId", String.valueOf(weiSiteConfigId));
//                    primaryKey.putKeyField("companyId", String.valueOf(companyId));
//                    EntityDao<WeiSiteConfig> weiSiteConfigDAO = applicationContext.getEntityDAO(EntityNames.weiSiteConfig);
//                    WeiSiteConfig weiSiteConfig = weiSiteConfigDAO.inqurieByKey(primaryKey);
//                    messageTitle = weiSiteConfig.getWeiSiteName();
//                    messageURL = weiSiteConfig.getBackgroupImageURL();
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String weiSiteURL = SalesConstant.WEISITE_HOMEPAGE_URL + "?token=" + token + "&weiSiteConfigId=" + weiSiteConfigId;
//                    itemMap = new HashMap<String, String>(4, 1);
//                    itemMap.put("title", "欢迎进入微网站" + messageTitle);
//                    itemMap.put("description", messageTitle);
//                    itemMap.put("picurl", messageURL);
//                    itemMap.put("url", weiSiteURL);
//                    responseMapList.add(itemMap);
//                    resultMap = weixinAPI.sendTextAndImageMessage(responseMap, responseMapList);
//                    resultCode = Integer.parseInt(resultMap.get("errcode"));
//                    break;
//            }
//        }
//        return resultCode;
//    }
}
