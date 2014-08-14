package com.sales.weixin.api;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nelson
 */
public interface WeixinAPI {
    //String url
    public Map<String,String> getAccessToken(Map<String,String> parameters);

    public Map<String,String> uploadMedia(File file,String fileName,Map<String,String> parameters);
//
//    public String downloadMedia(String url,String accessToken, String mediaId);

    public Map<String,String> sendTextMessage(Map<String,String> parameters);

    public Map<String,String> sendImageMessage(Map<String,String> parameters);

    public Map<String,String> sendAudioMessage(Map<String,String> parameters);

    public Map<String,String> sendVedioMessage(Map<String,String> parameters);

    public Map<String,String> sendMusicMessage(Map<String,String> parameters);

    public Map<String,String> sendTextAndImageMessage(Map<String,String> commonParameter,List<Map<String, String>> parametersList);

    public List<Map<String,String>> getGroup(Map<String,String> parameters);

    public Map<String,String> createGroup(Map<String,String> parameters);

    public Map<String,String> updateGroup(Map<String,String> parameters);

    public Map<String,String> moveUser(Map<String,String> parameters);

    public Map<String,String> getUserInfo(Map<String,String> parameters);

    public String getCareUserList(Map<String,String> parameters);

    public Map<String, String> createMenu(Map<String, String> parameters, String postJsonData);
   
    public String getMenu(Map<String,String> parameters);

    public  Map<String,String> deleteMenu(Map<String,String> parameters);

    public  Map<String,String> getQRCodeName(Map<String,String> parameters);

}
