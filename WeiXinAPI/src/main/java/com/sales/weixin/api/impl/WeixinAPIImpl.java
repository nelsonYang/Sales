package com.sales.weixin.api.impl;

import com.framework.httpclient.HttpConnection;
import com.framework.httpclient.HttpResponseEntity;
import com.framework.utils.JsonUtils;
import com.sales.weixin.api.WeixinAPI;
import com.sales.weixin.context.InterfaceContext;
import com.sales.weixin.entity.InterfaceEntity;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author nelson
 */
public class WeixinAPIImpl implements WeixinAPI {

    private HttpConnection httpConnection;
    private InterfaceContext interfaceContext;

    public WeixinAPIImpl(HttpConnection httpConnection, InterfaceContext interfaceContext) {
        this.httpConnection = httpConnection;
        this.interfaceContext = interfaceContext;
    }
    

    public Map<String, String> getAccessToken(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("getAccessToken");
        String url = interfaceEntity.getUrl();
        for (Entry<String, String> parameterEntry : parameters.entrySet()) {
            url = url.concat("&").concat(parameterEntry.getKey()).concat("=").concat(parameterEntry.getValue());
        }
        HttpResponseEntity httpResponseEntity = httpConnection.executeGet(url, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    public Map<String, String> sendTextMessage(Map<String, String> parameters) {
        //map tojson
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("sendMessage");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        String[] differentParameterNames = {"content"};
        String jsonData = this.jsonBuilder(parameters, differentParameterNames);
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    /**
     *
     * @param url
     * @param accessToken
     * @param toUser
     * @param parametersList
     * @return
     */
    public Map<String, String> sendTextAndImageMessage(Map<String, String> parameters, List<Map<String, String>> parametersList) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("sendMessage");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        String jsonData = this.jsonBuilder(parameters, parametersList);
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    public Map<String, String> sendImageMessage(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("sendMessage");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        String[] differentParameterNames = {"media_id"};
        String jsonData = this.jsonBuilder(parameters, differentParameterNames);
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    public Map<String, String> sendAudioMessage(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("sendMessage");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        String[] differentParameterNames = {"media_id"};
        String jsonData = this.jsonBuilder(parameters, differentParameterNames);
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;

    }

    public Map<String, String> sendVedioMessage(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("sendMessage");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        String[] differentParameterNames = {"media_id", "title", "description"};
        String jsonData = this.jsonBuilder(parameters, differentParameterNames);
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    /**
     * @param parameters
     * @return
     */
    public Map<String, String> sendMusicMessage(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("sendMessage");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        String[] differentParameterNames = {"title", "description", "musicurl", "hqmusicurl", "thumb_media_id"};
        String jsonData = this.jsonBuilder(parameters, differentParameterNames);
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    /**
     *
     * @param commonParameter
     * @param parametersList
     * @return
     */
    public Map<String, String> createMenu(Map<String, String> parameters, String postJsonData) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("createMenu");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, postJsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;

    }

    public List<Map<String, String>> getGroup(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("getGroup");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        HttpResponseEntity httpResponseEntity = httpConnection.executeGet(url, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        String groups = resultMap.get("groups");
        List<Map<String, String>> resultMapList = JsonUtils.parseJsonArrayToMap(groups);
        return resultMapList;
    }

    public Map<String, String> createGroup(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("createGroup");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        Map<String, String> groupMap = new HashMap<String, String>(2, 1);
        groupMap.put("group", "{\"name\": \"" + parameters.get("name") + "\"}");
        String jsonData = JsonUtils.mapToJson(groupMap, new String[]{"group"});
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> goupMap = JsonUtils.parseJsonToMap(result);
        String groupJson = goupMap.get("group");
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(groupJson);
        return resultMap;
    }

    public Map<String, String> updateGroup(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("updateGroup");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        Map<String, String> group = new HashMap<String, String>(2, 1);
        group.put("id", parameters.get("id"));
        group.put("name", parameters.get("name"));
        String jsonGroup = JsonUtils.mapToJson(group);
        Map<String, String> groupMap = new HashMap<String, String>(2, 1);
        groupMap.put("group", jsonGroup);
        String jsonData = JsonUtils.mapToJson(groupMap, new String[]{"group"});
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    public Map<String, String> moveUser(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("moveUser");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        Map<String, String> group = new HashMap<String, String>(2, 1);
        group.put("openid", parameters.get("openid"));
        group.put("to_groupid", parameters.get("to_groupid"));
        String jsonData = JsonUtils.mapToJson(group);
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonData, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    public Map<String, String> getUserInfo(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("getUserInfo");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        url = url.concat("&openid=").concat(parameters.get("openid"));
        HttpResponseEntity httpResponseEntity = httpConnection.executeGet(url, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    public String getCareUserList(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("getCareUserList");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        String next_openid = parameters.get("next_openid");
        if (next_openid != null && !next_openid.isEmpty()) {
            url = url.concat("&next_openid=").concat(parameters.get("next_openid"));
        }
        HttpResponseEntity httpResponseEntity = httpConnection.executeGet(url, null);
        String result = httpResponseEntity.getResult();
        return result;
    }

    public String getMenu(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("getMenu");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        HttpResponseEntity httpResponseEntity = httpConnection.executeGet(url, null);
        String result = httpResponseEntity.getResult();
        return result;
    }

    public Map<String, String> deleteMenu(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("deleteMenu");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        HttpResponseEntity httpResponseEntity = httpConnection.executeGet(url, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    public Map<String, String> getQRCodeName(Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("getQRCodeName");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        String jsonTemplage = "{\"action_name\": \"${action_name}\", \"action_info\": {\"scene\": {\"scene_id\": ${scene_id}}}}";
        jsonTemplage = jsonTemplage.replace("${action_name}", parameters.get("action_name")).replace("${scene_id}", parameters.get("scene_id"));
        HttpResponseEntity httpResponseEntity = httpConnection.executePost(url, jsonTemplage, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    public Map<String, String> uploadMedia(File file, String fileName, Map<String, String> parameters) {
        InterfaceEntity interfaceEntity = interfaceContext.getInterface("uploadMedia");
        String url = interfaceEntity.getUrl();
        url = url.concat("&access_token=").concat(parameters.get("access_token"));
        url = url.concat("&type=").concat(parameters.get("type"));
        HttpResponseEntity httpResponseEntity = httpConnection.executePostFile(url, file, fileName, parameters, null);
        String result = httpResponseEntity.getResult();
        Map<String, String> resultMap = JsonUtils.parseJsonToMap(result);
        return resultMap;
    }

    /**
     *
     * @param parameters
     * @param differentParameterNames
     * @return
     */
    private String jsonBuilder(Map<String, String> parameters, String[] differentParameterNames) {
        String result = "";
        String msgType = parameters.get("msgtype");
        String toUser = parameters.get("touser");
        Map<String, String> parameterMap = new HashMap<String, String>(8, 1);
        parameterMap.put("touser", toUser);
        parameterMap.put("msgtype", msgType);
        Map<String, String> differentMap = new HashMap<String, String>(differentParameterNames.length, 1);
        for (String paramterName : differentParameterNames) {
            differentMap.put(paramterName, parameters.get(paramterName));
        }
        String json = JsonUtils.mapToJson(differentMap);
        parameterMap.put(msgType, json);
        result = JsonUtils.mapJsonToJson(parameterMap);
        return result;
    }

    public String jsonBuilder(Map<String, String> parameters, List<Map<String, String>> paramtersMapList) {
        String result = "";
        String msgType = parameters.get("msgtype");
        String toUser = parameters.get("touser");
        Map<String, String> parameterMap = new HashMap<String, String>(8, 1);
        parameterMap.put("touser", toUser);
        parameterMap.put("msgtype", msgType);
        String json = JsonUtils.mapListToJsonArray(paramtersMapList);
        StringBuilder newsBuilder = new StringBuilder(100);
        newsBuilder.append("{\"articles\":").append(json).append("}");
        parameterMap.put(msgType, newsBuilder.toString());
        result = JsonUtils.mapToJson(parameterMap, new String[]{"touser", "msgtype", "news"});
        return result;
    }
}
