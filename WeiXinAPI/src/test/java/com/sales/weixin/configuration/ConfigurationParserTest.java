/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sales.weixin.configuration;

import com.sales.weixin.enumeration.MessageTypeEnum;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nelson
 */
public class ConfigurationParserTest {
    
    public ConfigurationParserTest() {
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
     * Test of parse method, of class ConfigurationParser.
     */
    @Test
    public void testParse() {
//        System.out.println("parse");
//        String fileName = "weixinAPI.xml";
//        ConfigurationParser instance = new ConfigurationParser();
//        instance.parse(fileName);
        //5ec7e36e-e9a2-4f90-a9c6-2f17afe2ba95
//       System.out.println(UUID.randomUUID().toString());
        System.out.println(MessageTypeEnum.text.ordinal());
   
    }

 
}