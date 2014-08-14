package com.sales.weixinservice.message.handler;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class MessageManager {

    public static final Map<String, MessageHandler> messageHandlerMap = new HashMap<String, MessageHandler>(16, 1);

    static {
        messageHandlerMap.put("text", new TextMessageHandler());
        messageHandlerMap.put("video", new VedioMessageHandler());
        messageHandlerMap.put("image", new ImageMessageHandler());
        messageHandlerMap.put("voice", new AudioMessageHandler());
        messageHandlerMap.put("music", new MusicMessageHandler());
        messageHandlerMap.put("news", new NewsMessageHandler());
        messageHandlerMap.put("LOCATION", new LocationMessageHandler());
        messageHandlerMap.put("scan", new ScanMessageHandler());
        messageHandlerMap.put("subscribe", new SuscribeMessageHandler());
        messageHandlerMap.put("unsubscribe", new UnSuscribeMessageHandler());
        messageHandlerMap.put("CLICK", new LinkMessageHandler());

    }

    public MessageHandler getMessageHandler(String msgType) {
        return messageHandlerMap.get(msgType);
    }
}
