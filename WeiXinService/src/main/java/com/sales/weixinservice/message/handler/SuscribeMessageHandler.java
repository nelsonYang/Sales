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
public class SuscribeMessageHandler implements MessageHandler {

    private ResponseMessageCache responseMessageCache = ResponseMessageCache.getInstance();
    private final Logger logger = LogFactory.getInstance().getLogger(SuscribeMessageHandler.class);

    public String handleMessage(Map<String, String> messageMap, WeixinReceiveAPI request, WeixinResponseAPI response) {
        logger.debug("SuscribeMessageHandler here");
        String responseXML = "";
        String token = messageMap.get("token");
        String fromUser = messageMap.get("FromUserName");
        String toUser = messageMap.get("ToUserName");
        String companyId = Base64Utils.decodeToString(token);
        Map<String, String> weixinMessageMap = new HashMap<String, String>(8, 1);
        String[] companyIds = companyId.split("-");
        weixinMessageMap.put("token", token);
        weixinMessageMap.put("companyId", companyIds[1]);
        weixinMessageMap.put("type", String.valueOf(MessageTypeEnum.event_subscribe.ordinal()));
        weixinMessageMap.put("createTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
        weixinMessageMap.put("fromUser", fromUser);
        weixinMessageMap.put("toUser", toUser);
        weixinMessageMap.put("content", "订阅");
        MessageBlockQueueCache messageBlockQueueCache = MessageBlockQueueCache.getInstance();
        messageBlockQueueCache.putMessage(weixinMessageMap);
        logger.debug("SuscribeMessageHandler here");
        String companyType = companyIds[1] + "_" + MessageTypeEnum.event_subscribe.ordinal();
        logger.debug("companyType:{}", companyType);
        String xml = responseMessageCache.getXML(companyType);
        logger.debug("xml:{}", xml);
        if (xml != null && !xml.isEmpty()) {
            responseXML = xml.replace("#{ToUserName}", fromUser).replace("#{FromUserName}", toUser).replace("#{CreateTime}", String.valueOf(System.currentTimeMillis())).replace("#{token}", token).replace("#{weixinId}", fromUser);
        }
        return responseXML;
    }
}
