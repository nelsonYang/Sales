package com.sales.weixin.utils;

import com.frameworkLog.factory.LogFactory;
import com.sales.weixin.enumeration.MessageTypeEnum;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;

/**
 *
 * @author nelson
 */
public class MessageUtils {

    private static final Map<String, MessageTypeEnum> messageTypeEnumMap = new HashMap<String, MessageTypeEnum>(8, 1);

    static {
        messageTypeEnumMap.put(MessageTypeEnum.text.toString(), MessageTypeEnum.text);
        messageTypeEnumMap.put(MessageTypeEnum.audio.toString(), MessageTypeEnum.audio);
        messageTypeEnumMap.put(MessageTypeEnum.image.toString(), MessageTypeEnum.image);
        messageTypeEnumMap.put(MessageTypeEnum.music.toString(), MessageTypeEnum.music);
        messageTypeEnumMap.put(MessageTypeEnum.news.toString(), MessageTypeEnum.news);
        messageTypeEnumMap.put(MessageTypeEnum.vedio.toString(), MessageTypeEnum.vedio);
    }

    private MessageUtils() {
    }
    private static final Logger logger = LogFactory.getInstance().getLogger(MessageUtils.class);

    public static Map<String, String> handleReceivedMessage(String xmlMessage) {
        logger.debug("xmlMessage: {}", xmlMessage);
        Map<String, String> resultMap = null;
        try {
            Document document = DocumentHelper.parseText(xmlMessage);
            Element root = document.getRootElement();
            List<Element> elements = root.elements();
            resultMap = new HashMap<String, String>(elements.size(), 1);
            for (Element element : elements) {
                resultMap.put(element.getName(), element.getTextTrim());
            }
        } catch (DocumentException ex) {
            logger.error("MessageUtils:", ex);
        }
        logger.debug("messageMap: {}", resultMap);
        return resultMap;
    }
    
    public static MessageTypeEnum getMessageTypeEnum(String messgeType){
        return messageTypeEnumMap.get(messgeType);
    }
}
