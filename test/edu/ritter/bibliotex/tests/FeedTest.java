/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex.tests;

import java.util.Calendar;
import java.io.File;
import edu.ritter.bibliotex.Feed;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author anderson
 */
public class FeedTest {
    
    public FeedTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    

    /**
     * Test of createXML method, of class Feed.
     */
    @Test
    public void testCreateXML() {
       
        long timestamp = System.currentTimeMillis() / 1000L;
        String fileName = "pesquisa"+ timestamp+".xml";
        
        Feed instance = new Feed();
        instance.createXML( "data");
        instance.saveXml(fileName);
        assertTrue((new File(fileName)).exists());

       
    }
}
