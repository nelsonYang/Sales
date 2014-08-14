package com.sales.weixin.configuration;

import com.sales.weixin.entity.InterfaceEntity;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 *
 * @author nelson
 */
public class ConfigurationParser implements Parser {

    private final Map<String, InterfaceEntity> interfaceMap = new HashMap<String, InterfaceEntity>(16, 1);

    public void parse(String fileName) {
        try {
            SAXReader reader = new SAXReader();
            InputStream is = this.getClass().getResourceAsStream("/" + fileName);
            Document document = reader.read(is);
            Element rootElement = document.getRootElement();
            List<Element> interfaceElementLists = rootElement.elements("interface");
            String name;
            String url;
            String desc;
            String method;
            Element parameters;
            Element returnParameters;
            List<Element> parametersList;
            List<Element> returnParametersList;
            InterfaceEntity interfaceEntity;
            for (Element element : interfaceElementLists) {
                interfaceEntity = new InterfaceEntity();
                name = element.element("name").getTextTrim();
                url = element.element("url").getTextTrim();
                desc = element.element("desc").getTextTrim();
                method = element.element("method").getTextTrim();
                parameters = element.element("parameters");
                returnParameters = element.element("returnParameters");
                if (parameters != null) {
                    parametersList = parameters.elements("name");
                    if (parametersList != null && !parametersList.isEmpty()) {
                        for (Element parameter : parametersList) {
                            interfaceEntity.getParameters().add(parameter.getTextTrim());
                        }
                    }
                }
                if (returnParameters != null) {
                    returnParametersList = returnParameters.elements("name");
                    if (returnParametersList != null && !returnParametersList.isEmpty()) {
                        for (Element parameter : returnParametersList) {
                            interfaceEntity.getReturnParametes().add(parameter.getTextTrim());
                        }
                    }

                }

                interfaceEntity.setInterfaceName(name);
                interfaceEntity.setDesc(desc);
                interfaceEntity.setMethod(method);
                interfaceEntity.setUrl(url);
                this.interfaceMap.put(name, interfaceEntity);
            }
        } catch (DocumentException ex) {
            ex.printStackTrace();
            throw new RuntimeException("解析weixinapi.xml出错");
        }

    }

    /**
     * @return the interfaceMap
     */
    public Map<String, InterfaceEntity> getInterfaceMap() {
        return interfaceMap;
    }
}
