package com.sales.weixin.context;

import com.sales.weixin.entity.InterfaceEntity;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class InterfaceContext {
    private  Map<String,InterfaceEntity> interfaceMap;

    /**
     * @return the interfaceMap
     */
    public Map<String,InterfaceEntity> getInterfaceMap() {
        return interfaceMap;
    }
    public void putInterface(String name,InterfaceEntity interfaceEntity){
        this.interfaceMap.put(name, interfaceEntity);
    }

    /**
     * @param interfaceMap the interfaceMap to set
     */
    public void setInterfaceMap(Map<String,InterfaceEntity> interfaceMap) {
        this.interfaceMap = interfaceMap;
    }
    
    public InterfaceEntity getInterface(String key){
        return this.interfaceMap.get(key);
    }


}
