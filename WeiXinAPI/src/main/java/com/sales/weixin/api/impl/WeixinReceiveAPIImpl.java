package com.sales.weixin.api.impl;

import com.sales.weixin.api.WeixinReceiveAPI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 * @author nelson
 */
public class WeixinReceiveAPIImpl implements WeixinReceiveAPI {

    public Map<String, String> carePublicWeixin(String xmlMessage) {
        return this.handleReceivedMessage(xmlMessage);
    }

    public Map<String, String> cancelPublicWeixin(String xmlMessage) {
        return this.handleReceivedMessage(xmlMessage);
    }

    public Map<String, String> scanQRCode(String xmlMessge) {
        return this.handleReceivedMessage(xmlMessge);
    }

    public Map<String, String> getPosition(String xmlMessage) {
        return this.handleReceivedMessage(xmlMessage);
    }

    public Map<String, String> getMenuClick(String xmlMessage) {
        return this.handleReceivedMessage(xmlMessage);
    }

    public Map<String, String> getAudio(String xmlMessage) {
        return this.handleReceivedMessage(xmlMessage);
    }

    public Map<String, String> getText(String xmlMessage) {
        return this.handleReceivedMessage(xmlMessage);
    }

    public Map<String, String> getVedio(String xmlMessage) {
        return this.handleReceivedMessage(xmlMessage);
    }

    public Map<String, String> getPicture(String xmlMessage) {
        return this.handleReceivedMessage(xmlMessage);
    }

    public Map<String, String> handleReceivedMessage(String xmlMessage) {
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
        }
        return resultMap;
    }
}
