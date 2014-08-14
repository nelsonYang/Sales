/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sales.weixin.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
public class WeixinResponseAPIImplTest {

    public WeixinResponseAPIImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
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
     * Test of responseTextMessage method, of class WeixinResponseAPIImpl.
     */
   //@Test
    public void testResponseTextMessage() {
        System.out.println("responseTextMessage");
        Map<String, String> parameters = new HashMap<String, String>(4, 1);
        parameters.put("toUser", "nelson");
        parameters.put("fromUser", "nelson1");
        parameters.put("MsgId", "20011111");
        WeixinResponseAPIImpl instance = new WeixinResponseAPIImpl();
        String expResult = "";
        String result = instance.responseTextMessage(parameters);
        assertEquals(expResult, result);

    }
    
    @Test
    public void testResponseTextAndImageMessage() {
        System.out.println("responseTextMessage");
      Map<String, String> responseMap = new HashMap<String, String>();
        //获取event
        responseMap.put("toUserName", "aaaa");
        responseMap.put("fromUser", "bbbbb");
        responseMap.put("MsgType", "text");
        responseMap.put("CreateTime", String.valueOf(System.currentTimeMillis()));
        responseMap.put("ArticleCount", "1");
        List<Map<String,String>>responseMapList = new ArrayList<Map<String, String>>();
        String weiSiteURL =  "?token=xxx&weiSiteConfigId=" + 1;
        Map<String,String>itemMap = new HashMap<String, String>(4, 1);
        itemMap.put("Title", "欢迎进入微网站");
        itemMap.put("Description", "欢迎进入微网站");
        itemMap.put("PicUrl", "http://www.baidu.com");
        itemMap.put("Url", weiSiteURL);
        responseMapList.add(itemMap);
         WeixinResponseAPIImpl instance = new WeixinResponseAPIImpl();
       String responseXML = instance.responseTextAndImageMessage(responseMap, responseMapList);
       System.out.println(responseXML);
    }
}