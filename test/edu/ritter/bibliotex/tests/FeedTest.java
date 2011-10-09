/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex.tests;

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
     * Test of addElements method, of class Feed.
     */
    @Test
    public void testAddElements() {
        System.out.println("addElements");
        String value = "event";
        Feed instance = new Feed();
        instance.createXML("data");
       
        
       instance.addElements(value, "2008", "titledd ddd1");
       instance.addElements(value, "2011", "title   dsfs  dd2");
        instance.saveXml("pesquisa.xml");
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of createXML method, of class Feed.
     */
    @Test
    public void testCreateXML() {
        System.out.println("createXML");
       // Feed instance = new Feed();
        //instance.createXML();
        
    }
}
