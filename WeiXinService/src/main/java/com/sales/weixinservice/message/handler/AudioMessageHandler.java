package com.sales.weixinservice.message.handler;

import com.framework.utils.Base64Utils;
import com.framework.utils.DateTimeUtils;
import com.sales.datacache.MessageBlockQueueCache;
import com.sales.weixin.api.WeixinReceiveAPI;
import com.sales.weixin.api.WeixinResponseAPI;
import com.sales.weixin.enumeration.MessageTypeEnum;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class AudioMessageHandler implements MessageHandler {

    public String handleMessage(Map<String, String> messageMap, WeixinReceiveAPI request, WeixinResponseAPI response) {
        String responseXML = "";
        String token = messageMap.get("token");
        String fromUser = messageMap.get("FromUserName");
        String toUser = messageMap.get("ToUserName");
        String companyId = Base64Utils.decodeToString(token);
        Map<String, String> weixinMessageMap = new HashMap<String, String>(8, 1);
        String[] companyIds = companyId.split("-");
        weixinMessageMap.put("token", token);
        weixinMessageMap.put("companyId", companyIds[1]);
        weixinMessageMap.put("type", String.valueOf(MessageTypeEnum.audio.ordinal()));
        weixinMessageMap.put("createTime", DateTimeUtils.timestamp2Str(new Timestamp(System.currentTimeMillis())));
        weixinMessageMap.put("fromUser", fromUser);
        weixinMessageMap.put("toUser", toUser);
        weixinMessageMap.put("content", "音频信息");
        MessageBlockQueueCache messageBlockQueueCache = MessageBlockQueueCache.getInstance();
        messageBlockQueueCache.putMessage(weixinMessageMap);
        return responseXML;
    }
}
