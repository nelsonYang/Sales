package com.sales.weixin.entity;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author nelson
 */
public class InterfaceEntity {

    private String interfaceName;
    private String url;
    private String method;
    private String desc;
    private Set<String> parameters = new HashSet<String>();
    private Set<String> returnParametes = new HashSet<String>();

    /**
     * @return the interfaceName
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * @param interfaceName the interfaceName to set
     */
    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the parameters
     */
    public Set<String> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(HashSet<String> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the returnParametes
     */
    public Set<String> getReturnParametes() {
        return returnParametes;
    }

    /**
     * @param returnParametes the returnParametes to set
     */
    public void setReturnParametes(Set<String> returnParametes) {
        this.returnParametes = returnParametes;
    }
}
