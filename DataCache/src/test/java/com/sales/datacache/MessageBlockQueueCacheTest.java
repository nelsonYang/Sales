/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sales.datacache;

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
public class MessageBlockQueueCacheTest {
    
    public MessageBlockQueueCacheTest() {
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
     * Test of getInstance method, of class MessageBlockQueueCache.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        MessageBlockQueueCache expResult = null;
        //MessageBlockQueueCache result = MessageBlockQueueCache.getInstance();
        MessageBlockQueueCache messageBlockQueueCache = MessageBlockQueueCache.getInstance();
        messageBlockQueueCache.takeMessage();
 
        
    }

}