package com.sales.weixinservice.message.handler;

import com.framework.utils.Base64Utils;
import com.framework.utils.DateTimeUtils;
import com.frameworkLog.factory.LogFactory;
import com.sales.datacache.MessageBlockQueueCache;
import com.sales.datacache.ResponseMessageCache;
import com.sales.weixin.api.WeixinReceiveAPI;
import com.sales.weixin.api.WeixinResponseAPI;
import com.sales.weixin.enumeration.MessageTypeEnum;
import java.sql.Timestamp;
import java.util.HashMap;

import java.util.Map;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class TextMessageHandler implements MessageHandler {

    private ResponseMessageCache responseMessageCache = ResponseMessageCache.getInstance();
    private final Logger logger = LogFactory.getInstance().getLogger(TextMessageHandler.class);

    public String handleMessage(Map<String, String> messageMap, WeixinReceiveAPI request, WeixinResponseAPI response) {
        //定义一些关键字
//        logger.debug("text Messagehandler");

        String responseXML = "";
        String token = messageMap.get("token");
        String fromUser = messageMap.get("FromUserName");
        String toUser = messageMap.get("ToUserName");
        String content = messageMap.get("Content");
        String companyId = Base64Utils.decodeToString(token);
         String[] companyIds = companyId.split("-");
        Map<String, String> weixinMessageMap = new HashMap<String, String>(8, 1);
        weixinMessageMap.put("token", token);
        weixinMessageMap.put("companyId", companyIds[1]);
        weixinMessageMap.put("type", String.valueOf(MessageTypeEnum.text.ordinal()));
        weixinMessageMap.put("createTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
        weixinMessageMap.put("fromUser", fromUser);
        weixinMessageMap.put("toUser", toUser);
        weixinMessageMap.put("content", content);
        MessageBlockQueueCache messageBlockQueueCache = MessageBlockQueueCache.getInstance();
        messageBlockQueueCache.putMessage(weixinMessageMap);
       
        String companyType = companyIds[1] + "_" + MessageTypeEnum.text.ordinal() + "_" + content;
        logger.debug("companyType:{}", companyType);
        String xml = responseMessageCache.getXML(companyType);
        logger.debug("xml:{}", xml);
        if (xml != null && !xml.isEmpty()) {
            responseXML = xml.replace("#{ToUserName}", fromUser).replace("#{FromUserName}", toUser).replace("#{CreateTime}", String.valueOf(System.currentTimeMillis())).replace("#{token}", token).replace("#{weixinId}", fromUser);
        }
        //        logger.debug("companyType :={}", companyType);
//      
//        List<ResponseMessageCfg> resultResponseMessageCfgList = responseMessageCache.getResponseMessageCfgList(companyType);
//
//        // List<ResponseMessageCfg> resultResponseMessageCfgList = ResponseMessageCfgUtils.findKeyWord(content, responseMessageCfgList, (byte) 1);
//
//        byte responseType;
//        String responseContent;
//        String imageUrl;
//        List<Map<String, String>> itemMapList;
//        String messageTitle;
//        String messageURL;
//        logger.debug("resultResponseMessageCfgList.size:{}", resultResponseMessageCfgList.size());
//        for (ResponseMessageCfg responseMessageCfg : resultResponseMessageCfgList) {
//            responseType = responseMessageCfg.getResponseContentType();
//            messageTitle = responseMessageCfg.getResponseContent();
//            messageURL = responseMessageCfg.getResponseImageURL();
//            Map<String, String> responseMap = new HashMap<String, String>();
//            //获取event
//            responseMap.put("ToUserName", fromUser);
//            responseMap.put("FromUserName", toUser);
//            responseMap.put("MsgType", messageMap.get("MsgType"));
//            responseMap.put("CreateTime", String.valueOf(System.currentTimeMillis()));
//            logger.debug("responseType:{}", responseType);
//            responseContent = responseMessageCfg.getResponseContent();
//            imageUrl = responseMessageCfg.getResponseImageURL();
//            switch (responseType) {
//                case SalesConstant.TEXT_TYPE:
//                    responseMap.put("Content", responseContent);
//                    responseXML = response.responseTextMessage(responseMap);
//                    break;
//                case SalesConstant.TEXT_PICTURE_TYPE:
//                    responseMap.put("Content", responseContent);
//                    responseMap.put("MsgType", "news");
//                    responseMap.put("ArticleCount", "1");
//                    List<Map<String, String>> responseMapList = new ArrayList<Map<String, String>>();
//                    Map<String, String> itemMap = new HashMap<String, String>(4, 1);
//                    itemMap.put("Title", content);
//                    itemMap.put("Description", responseContent);
//                    itemMap.put("PicUrl", imageUrl);
//                    itemMap.put("Url", "");
//                    responseMapList.add(itemMap);
//                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
//                    break;
//                case SalesConstant.EVENT_TYPE:
//                    responseMap.put("ArticleCount", String.valueOf(resultResponseMessageCfgList.size()));
//                    responseMap.put("MsgType", "news");
//                    itemMapList = responseMessageCfg.getEntityMapList();
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String eventURL;
//                    for (Map<String, String> eventMap : itemMapList) {
//                        eventURL = SalesConstant.EVENT_URL + "?token=" + token + "&eventId=" + eventMap.get("eventId");
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("Title", eventMap.get("eventName"));
//                        itemMap.put("Description", messageTitle);
//                        itemMap.put("PicUrl", messageURL);
//                        itemMap.put("Url", eventURL);
//                        responseMapList.add(itemMap);
//                    }
//                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
//                    break;
//                case SalesConstant.RESERVATION_TYPE:
//                    responseMap.put("ArticleCount", String.valueOf(resultResponseMessageCfgList.size()));
//                    responseMap.put("MsgType", "news");
//                    itemMapList = responseMessageCfg.getEntityMapList();
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String reservationURL;
//                    for (Map<String, String> resvervationMap : itemMapList) {
//                        reservationURL = SalesConstant.RESERVATION_URL + "?token=" + token + "&reservationId=" + resvervationMap.get("reservationId");
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("Title", messageTitle);
//                        itemMap.put("Description", resvervationMap.get("reservationDesc"));
//                        itemMap.put("PicUrl", messageURL);
//                        itemMap.put("Url", reservationURL);
//                        responseMapList.add(itemMap);
//                    }
//                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
//                    break;
//                case SalesConstant.WEIBAR_TYPE:
//                    responseMap.put("ArticleCount", String.valueOf(resultResponseMessageCfgList.size()));
//                    responseMap.put("MsgType", "news");
//                    itemMapList = responseMessageCfg.getEntityMapList();
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String weiBarURL = SalesConstant.WEIBAR_URL + "?token=" + token + "&weiBarConfigId=" + itemMapList.get(0).get("weiBarConfigId");
//                    itemMap = new HashMap<String, String>(4, 1);
//                    itemMap.put("Title", messageTitle);
//                    itemMap.put("Description", messageTitle);
//                    itemMap.put("PicUrl", messageURL);
//                    itemMap.put("Url", weiBarURL);
//                    responseMapList.add(itemMap);
//                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
//                    break;
//                case SalesConstant.MEMBER_CARD_TYPE:
//                    responseMap.put("ArticleCount", String.valueOf(resultResponseMessageCfgList.size()));
//                    responseMap.put("MsgType", "news");
//                    itemMapList = responseMessageCfg.getEntityMapList();
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    for (Map<String, String> memberCardMap : itemMapList) {
//                        String memberCardURL = SalesConstant.MEMBER_CARD_URL + "?token=" + token + "&memberCardId=" + memberCardMap.get("memberCardId");
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("Title", memberCardMap.get("memberCardName"));
//                        itemMap.put("Description", messageTitle);
//                        itemMap.put("PicUrl", messageURL);
//                        itemMap.put("Url", memberCardURL);
//                        responseMapList.add(itemMap);
//                    }
//                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
//                    break;
//                case SalesConstant.COPOUN_TYPE:
//                    responseMap.put("ArticleCount", String.valueOf(resultResponseMessageCfgList.size()));
//                    responseMap.put("MsgType", "news");
//                    itemMapList = responseMessageCfg.getEntityMapList();
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String couponURL;
//                    for (Map<String, String> couponMap : itemMapList) {
//                        couponURL = SalesConstant.COPOUN_URL + "?token=" + token + "&couponId=" + couponMap.get("couponId");
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("Title", couponMap.get("couponName"));
//                        itemMap.put("Description", couponMap.get("messageDesc"));
//                        itemMap.put("PicUrl", couponMap.get("messageURL"));
//                        itemMap.put("Url", couponURL);
//                        responseMapList.add(itemMap);
//                    }
//                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
//                    break;
//                case SalesConstant.MERCHATE_TYPE:
//                    responseMap.put("ArticleCount", String.valueOf(resultResponseMessageCfgList.size()));
//                    responseMap.put("MsgType", "news");
//                    itemMapList = responseMessageCfg.getEntityMapList();
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String merchantURL;
//                    for (Map<String, String> merchantMap : itemMapList) {
//                        merchantURL = SalesConstant.MERCHANT_URL + "?token=" + token + "&merchantId=" + merchantMap.get("merchantId");
//                        itemMap = new HashMap<String, String>(4, 1);
//                        itemMap.put("Title", merchantMap.get("merchantName"));
//                        itemMap.put("Description", messageTitle);
//                        itemMap.put("PicUrl", merchantMap.get("backgroupImageURL"));
//                        itemMap.put("Url", merchantURL);
//                        responseMapList.add(itemMap);
//                    }
//                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
//                    break;
//
//                case SalesConstant.WEI_SITE_TYPE:
//                    responseMap.put("ArticleCount", String.valueOf(resultResponseMessageCfgList.size()));
//                    responseMap.put("MsgType", "news");
//                    itemMapList = responseMessageCfg.getEntityMapList();
//                    responseMapList = new ArrayList<Map<String, String>>();
//                    String weiSiteURL = SalesConstant.WEISITE_HOMEPAGE_URL + "?token=" + token + "&weiSiteConfigId=" + itemMapList.get(0).get("weiSiteConfigId");
//                    itemMap = new HashMap<String, String>(4, 1);
//                    itemMap.put("Title", "欢迎进入微网站" + messageTitle);
//                    itemMap.put("Description", messageTitle);
//                    itemMap.put("PicUrl", messageURL);
//                    itemMap.put("Url", weiSiteURL);
//                    responseMapList.add(itemMap);
//                    responseXML = response.responseTextAndImageMessage(responseMap, responseMapList);
//                    break;
//
//            }
//        }
        return responseXML;
    }
}
