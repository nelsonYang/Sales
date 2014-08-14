package com.sales.weixin.api.impl;

import com.sales.weixin.api.WeixinResponseAPI;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author nelson
 */
public class WeixinResponseAPIImpl implements WeixinResponseAPI {

    public String responseTextMessage(Map<String, String> parameters) {
        String[] excludeCDATA = new String[]{"CreateTime", "MsgId"};
        Set<String> excludeHashSet = new HashSet<String>(2);
        excludeHashSet.addAll(Arrays.asList(excludeCDATA));
        return mapToXml(parameters, excludeHashSet);
    }

    public String responseImageMessage(Map<String, String> commonMap, Map<String, List<Map<String, String>>> parameters) {
        String[] excludeCDATA = new String[]{"CreateTime", "MsgId"};
        Set<String> excludeHashSet = new HashSet<String>(2);
        excludeHashSet.addAll(Arrays.asList(excludeCDATA));
        return mapToXml(commonMap, excludeHashSet);
    }

    public String responseAudioMessage(Map<String, String> commonMap, Map<String, List<Map<String, String>>> parameters) {
        String[] excludeCDATA = new String[]{"CreateTime", "MsgId"};
        Set<String> excludeHashSet = new HashSet<String>(2);
        excludeHashSet.addAll(Arrays.asList(excludeCDATA));
        return mapListToXml(commonMap, parameters, excludeHashSet);
    }

    public String responseVedioMessage(Map<String, String> commonMap, Map<String, List<Map<String, String>>> parameters) {
        String[] excludeCDATA = new String[]{"CreateTime", "MsgId"};
        Set<String> excludeHashSet = new HashSet<String>(2);
        excludeHashSet.addAll(Arrays.asList(excludeCDATA));
        return mapListToXml(commonMap, parameters, excludeHashSet);
    }

    public String resoponseMusicMessage(Map<String, String> commonMap, Map<String, List<Map<String, String>>> parameters) {
        String[] excludeCDATA = new String[]{"CreateTime", "MsgId"};
        Set<String> excludeHashSet = new HashSet<String>(2);
        excludeHashSet.addAll(Arrays.asList(excludeCDATA));
        return mapListToXml(commonMap, parameters, excludeHashSet);
    }

    public String responseTextAndImageMessage(Map<String, String> commonMap, List<Map<String, String>> parameters) {
        String[] excludeCDATA = new String[]{"CreateTime", "MsgId","ArticleCount"};
        Set<String> excludeHashSet = new HashSet<String>(2);
        excludeHashSet.addAll(Arrays.asList(excludeCDATA));
        return mapListToXml(commonMap, parameters, excludeHashSet);
    }

    private String mapListToXml(Map<String, String> commonMap, List<Map<String, String>> parameters, Set<String> excludeHashSet) {
        Element itemElement;
        Element keyElement;
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("xml");
        for (Entry<String, String> entry : commonMap.entrySet()) {
            keyElement = nodeElement.addElement(entry.getKey());
            if (excludeHashSet.contains(entry.getKey())) {
                keyElement.setText(String.valueOf(entry.getValue()));
            } else {
                keyElement.addCDATA(entry.getValue());
            }
        }
        Element articleElement = nodeElement.addElement("Articles");

        for (Map<String, String> values : parameters) {
            itemElement = articleElement.addElement("item");
            for (Entry<String, String> child : values.entrySet()) {
                keyElement = itemElement.addElement(child.getKey());
                keyElement.setText(String.valueOf(child.getValue()));
            }
        }

        return doc2String(document);
    }

    private String mapToXml(Map<String, String> map, Set<String> excludeHashSet) {
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("xml");
        Element keyElement;
        for (Entry<String, String> entry : map.entrySet()) {
            keyElement = nodeElement.addElement(entry.getKey());
            if (excludeHashSet.contains(entry.getKey())) {
                keyElement.setText(String.valueOf(entry.getValue()));
            } else {
                keyElement.addCDATA(entry.getValue());
            }
        }
        return doc2String(document);
    }

    private String mapListToXml(Map<String, String> commonMap, Map<String, List<Map<String, String>>> parameters, Set<String> excludeHashSet) {
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("xml");
        Element keyElement;
        Element childElement;
        for (Entry<String, String> entry : commonMap.entrySet()) {
            keyElement = nodeElement.addElement(entry.getKey());
            if (excludeHashSet.contains(entry.getKey())) {
                keyElement.setText(String.valueOf(entry.getValue()));
            } else {
                keyElement.addCDATA(entry.getValue());
            }
        }
        for (Entry<String, List<Map<String, String>>> entry : parameters.entrySet()) {
            childElement = nodeElement.addElement(entry.getKey());
            for (Map<String, String> values : entry.getValue()) {
                for (Entry<String, String> child : values.entrySet()) {
                    keyElement = childElement.addElement(child.getKey());
                    if (excludeHashSet.contains(child.getKey())) {
                        keyElement.setText(String.valueOf(child.getValue()));
                    } else {
                        keyElement.addCDATA(child.getValue());
                    }
                }
            }
        }
        return doc2String(document);
    }

    public static String doc2String(Document document) {
        String s = "";
        try {
            // 使用输出流来进行转化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用UTF-8编码
            OutputFormat format = new OutputFormat(" ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }
}
