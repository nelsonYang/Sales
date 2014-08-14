package com.sales.weixin.api;

import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public interface WeixinResponseAPI {
    
   

    public String responseTextMessage(Map<String,String> parameters);

    public String responseImageMessage(Map<String, String> commonMap, Map<String, List<Map<String, String>>> parameters);

    public String responseAudioMessage(Map<String, String> commonMap, Map<String, List<Map<String, String>>> parameters);

    public String responseVedioMessage(Map<String, String> commonMap, Map<String, List<Map<String, String>>> parameters);

    public String resoponseMusicMessage(Map<String, String> commonMap, Map<String, List<Map<String, String>>> parameters);

    public String responseTextAndImageMessage(Map<String, String> commonMap, List<Map<String, String>> parameters);
}
