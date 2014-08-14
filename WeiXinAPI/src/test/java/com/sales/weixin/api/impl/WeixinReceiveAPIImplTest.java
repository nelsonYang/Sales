/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sales.weixin.api.impl;

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
public class WeixinReceiveAPIImplTest {
    
    public WeixinReceiveAPIImplTest() {
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
     * Test of carePublicWeixin method, of class WeixinReceiveAPIImpl.
     */
    @Test
    public void testCarePublicWeixin() {
        System.out.println("carePublicWeixin");
        String xmlMessage = " <xml>\n" +
" <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
" <FromUserName><![CDATA[fromUser]]></FromUserName> \n" +
" <CreateTime>1348831860</CreateTime>\n" +
" <MsgType><![CDATA[text]]></MsgType>\n" +
" <Content><![CDATA[this is a test]]></Content>\n" +
" <MsgId>1234567890123456</MsgId>\n" +
" </xml>";
        WeixinReceiveAPIImpl instance = new WeixinReceiveAPIImpl();
        Map expResult = null;
        Map result = instance.carePublicWeixin(xmlMessage);
        assertEquals(expResult, result);
    
    }

  
}