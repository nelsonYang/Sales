package com.sales.weixin.context.builder;

import com.sales.weixin.configuration.ConfigurationParser;
import com.sales.weixin.context.InterfaceContext;
import com.sales.weixin.entity.InterfaceEntity;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class InterfaceContextBuilder {
    public InterfaceContext build(){
        InterfaceContext interfaceContext = new InterfaceContext();
        ConfigurationParser configurationParser = new ConfigurationParser();
        configurationParser.parse("weixinAPI.xml");
        Map<String,InterfaceEntity> interfaceMap =  configurationParser.getInterfaceMap();
        interfaceContext.setInterfaceMap(interfaceMap);
        return interfaceContext;
    }
}
