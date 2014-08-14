/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sales.weixin.api.impl;

import com.framework.enumeration.HttpConnectionEnum;
import com.framework.httpclient.HttpConnection;
import com.framework.httpclient.HttpConnectionFactory;
import com.sales.weixin.api.WeixinAPI;
import com.sales.weixin.context.InterfaceContext;
import com.sales.weixin.context.builder.InterfaceContextBuilder;
import java.io.File;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nelson
 */
public class WeixinAPIImplTest {

    private static WeixinAPI instance;
    private static String access_token;

    public WeixinAPIImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {

        InterfaceContext interfaceContext = new InterfaceContextBuilder().build();
        HttpConnection httpConnection = HttpConnectionFactory.getInstance().getHttpClient(HttpConnectionEnum.MULTION);
        instance = new WeixinAPIImpl(httpConnection, interfaceContext);
        access_token = testGetAccessToken();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAccessToken method, of class WeixinAPIImpl.
     */
    public static String testGetAccessToken() {
        System.out.println("getAccessToken");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("appid", "wx2d1a0936cb4d5859");
        parameters.put("secret", "51fb771ff35d6af35e6cb68bd6d2bef3");
        Map<String, String> result = instance.getAccessToken(parameters);
        System.out.print(result.toString());
        return result.get("access_token");
    }

    //@Test
    public void testUploadMedia() {
        System.out.println("testUploadMedia");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", access_token);
        parameters.put("type", "image");
        File file = new File("/home/nelson/11.jpg");
        Map result = instance.uploadMedia(file, "image/jpeg", parameters);
        System.out.println(result);
    }

    /**
     * Test of sendTextMessage method, of class WeixinAPIImpl.
     */
     @Test
    public void testSendTextMessage() {
        System.out.println("sendTextMessage");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", access_token);
        parameters.put("touser", "or-SJt7KtqY89CFV0ykkE0xCUDm8");
        parameters.put("msgtype", "text");
        parameters.put("content", "你好。。。。 ");
        Map result = instance.sendTextMessage(parameters);
        System.out.println(result);
    }
//    /**
//     * Test of sendTextAndImageMessage method, of class WeixinAPIImpl.
//     */

    //@Test
    public void testSendTextAndImageMessage() {
        System.out.println("sendTextAndImageMessage");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", access_token);
        parameters.put("touser", "oQd9AuHYdfbVr1avdpzHnofmqYo8");
        parameters.put("msgtype", "news");
        List<Map<String, String>> parametersList = new ArrayList<Map<String, String>>(2);
        /**
         * "title":"Happy Day", "description":"Is Really A Happy Day",
         * "url":"URL", "picurl":"PIC_URL"
         */
        Map<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("title", "Happy Day");
        dataMap.put("description", "Is Really A Happy Day");
        dataMap.put("url", "http://www.baidu.com");
        dataMap.put("picurl", "http://img1.bdstatic.com/img/image/228a044ad345982b2b74baadeba33adcbef76099b93.jpg");
        parametersList.add(dataMap);

        Map result = instance.sendTextAndImageMessage(parameters, parametersList);
        System.out.println(result);
    }

//    /**
//     * Test of sendImageMessage method, of class WeixinAPIImpl.
//     */
    //@Test
    public void testSendImageMessage() {
        System.out.println("sendImageMessage");

        Map<String, String> parameters = new HashMap<String, String>();
        Map expResult = null;
        parameters.put("access_token", access_token);
        parameters.put("touser", "oQd9AuHYdfbVr1avdpzHnofmqYo8");
        parameters.put("msgtype", "image");
        parameters.put("media_id", "z6Ii8X45gG9OpxQ03RftVe_NxMDbiaYlUSEEJbmlmheGufc3pHBmtj4zVpNvHDut");
        Map result = instance.sendImageMessage(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of sendAudioMessage method, of class WeixinAPIImpl.
//     */
    // @Test

    public void testSendAudioMessage() {
        System.out.println("sendAudioMessage");
        Map<String, String> parameters = new HashMap<String, String>();
        Map expResult = null;
        parameters.put("access_token", access_token);
        parameters.put("touser", "oQd9AuHYdfbVr1avdpzHnofmqYo8");
        parameters.put("msgtype", "voice");
        parameters.put("media_id", "z6Ii8X45gG9OpxQ03RftVe_NxMDbiaYlUSEEJbmlmheGufc3pHBmtj4zVpNvHDut");
        Map result = instance.sendAudioMessage(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of sendVedioMessage method, of class WeixinAPIImpl.
//     */

    //@Test
    public void testSendVedioMessage() {
        System.out.println("sendVedioMessage");
        Map<String, String> parameters = new HashMap<String, String>();
        Map expResult = null;
        parameters.put("access_token", access_token);
        parameters.put("touser", "oQd9AuHYdfbVr1avdpzHnofmqYo8");
        parameters.put("msgtype", "voice");
        parameters.put("title", "hello");
        parameters.put("description", "hello description");
        parameters.put("media_id", "z6Ii8X45gG9OpxQ03RftVe_NxMDbiaYlUSEEJbmlmheGufc3pHBmtj4zVpNvHDut");
        Map result = instance.sendVedioMessage(parameters);
        System.out.println(result);
    }
//
//    /**
/* Test of sendMusicMessage method, of class WeixinAPIImpl.   
     "musicurl":"MUSIC_URL",
     "hqmusicurl":"HQ_MUSIC_URL",
     "thumb_media_id":"THUMB_MEDIA_ID" 
  
     //     */

    // @Test
    public void testSendMusicMessage() {
        System.out.println("sendMusicMessage");
        Map<String, String> parameters = new HashMap<String, String>();
        Map expResult = null;
        parameters.put("access_token", access_token);
        parameters.put("touser", "oQd9AuHYdfbVr1avdpzHnofmqYo8");
        parameters.put("msgtype", "music");
        parameters.put("title", "hello");
        parameters.put("description", "hello description");
        parameters.put("musicurl", " http://pan.baidu.com/share/link?shareid=971801371&uk=2805102733");
        parameters.put("hqmusicurl", " http://pan.baidu.com/share/link?shareid=971801371&uk=2805102733");
        parameters.put("thumb_media_id", "z6Ii8X45gG9OpxQ03RftVe_NxMDbiaYlUSEEJbmlmheGufc3pHBmtj4zVpNvHDut");
        Map result = instance.sendMusicMessage(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of createMenu method, of class WeixinAPIImpl.
//     */

    //@Test
    public void testCreateMenu() {
        System.out.println("createMenu");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", access_token);

        String postJsonData = "{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"type\":\"click\",\"name\":\"歌手简介\",\"key\":\"V1001_TODAY_SINGER\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}";
        Map expResult = null;
        Map result = instance.createMenu(parameters, postJsonData);
        System.out.println(result);
    }
//
//    /**
//     * Test of getGroup method, of class WeixinAPIImpl.
//     */
    //@Test
    public void testGetGroup() {
        System.out.println("getGroup");
         Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", access_token);
        List expResult = null;
        List result = instance.getGroup(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of createGroup method, of class WeixinAPIImpl.
//     */
    //@Test

    public void testCreateGroup() {
        System.out.println("createGroup");
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        parameters.put("access_token", access_token);
        parameters.put("name", "friends");
        Map expResult = null;
        Map result = instance.createGroup(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of updateGroup method, of class WeixinAPIImpl.
//     */

    //@Test
    public void testUpdateGroup() {
        System.out.println("updateGroup");
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        parameters.put("access_token", access_token);
        parameters.put("id", "100");
        parameters.put("name", "test_friends");
        Map result = instance.updateGroup(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of moveUser method, of class WeixinAPIImpl.
//     */

    //@Test
    public void testMoveUser() {
        System.out.println("moveUser");
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        parameters.put("access_token", access_token);
        parameters.put("openid ", "oQd9AuHYdfbVr1avdpzHnofmqYo8");
        parameters.put("to_groupid ", "100");
        Map expResult = null;
        Map result = instance.moveUser(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of getUserInfo method, of class WeixinAPIImpl.
//     */

    // @Test
    public void testGetUserInfo() {
        System.out.println("getUserInfo");
        Map<String, String> parameters = new HashMap<String, String>(2, 1);
        //oQd9AuHYdfbVr1avdpzHnofmqYo8
        parameters.put("access_token", access_token);
        parameters.put("openid", "oQd9AuHYdfbVr1avdpzHnofmqYo8");
        Map expResult = null;
        Map result = instance.getUserInfo(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of getCareUserList method, of class WeixinAPIImpl.
//     */

    //@Test
    public void testGetCareUserList() {
        System.out.println("getCareUserList");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", access_token);
        parameters.put("next_openid ", "");
        String expResult = "";
        String result = instance.getCareUserList(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of getMenu method, of class WeixinAPIImpl.
//     */

    //@Test
    public void testGetMenu() {
        System.out.println("getMenu");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", access_token);
        String expResult = "";
        String result = instance.getMenu(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of deleteMenu method, of class WeixinAPIImpl.
//     */
    //@Test

    public void testDeleteMenu() {
        System.out.println("deleteMenu");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", access_token);

        Map expResult = null;
        Map result = instance.deleteMenu(parameters);
        System.out.println(result);
    }
//
//    /**
//     * Test of getQRCodeName method, of class WeixinAPIImpl.
//     */

    //@Test
    public void testGetQRCodeName() {
        System.out.println("getQRCodeName");
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("access_token", access_token);
        parameters.put("action_name", "QR_LIMIT_SCENE");
        parameters.put("scene_id", "123");
        Map result = instance.getQRCodeName(parameters);
        System.out.println(result);
    }
}