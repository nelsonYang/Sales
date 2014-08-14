package com.sales.weixin.api;

import java.util.Map;

/**
 *
 * @author nelson
 */
public interface WeixinReceiveAPI {

    public Map<String, String> carePublicWeixin(String xmlMessage);

    public Map<String, String> cancelPublicWeixin(String xmlMessage);

    public Map<String, String> scanQRCode(String xmlMessge);

    public Map<String, String> getPosition(String xmlMessage);

    public Map<String, String> getMenuClick(String xmlMessage);

    public Map<String, String> getAudio(String xmlMessage);
    
    public Map<String,String> getText(String xmlMessage);
    
    public Map<String,String> getVedio(String xmlMessage);
    
    public Map<String,String> getPicture(String xmlMessage);
    
   
}
