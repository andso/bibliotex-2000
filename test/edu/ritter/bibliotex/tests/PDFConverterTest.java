/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex.tests;

import edu.ritter.bibliotex.PDFConverter;
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
public class PDFConverterTest {
    
    public PDFConverterTest() {
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
     * Test of pdftoText method, of class PDFConverter.
     */
    @Test
    public void testPdftoTextComAcento() {
        String pdfName = System.getProperty("user.dir")+"/testfiles/test_acentos.pdf";
        PDFConverter instance = new PDFConverter();
        String expResult = "Esse é um teste.";
        String result = instance.pdftoText(pdfName);
        assertEquals(expResult, result.trim());
        
    }

    
    /**
     * Test of pdftoText method, of class PDFConverter.
     */
    @Test
    public void testPdftoTextSimple() {
        String pdfName = System.getProperty("user.dir")+"/testfiles/test_simple.pdf";
        PDFConverter instance = new PDFConverter();
        String expResult = "simple";
        String result = instance.pdftoText(pdfName);
        assertEquals(expResult, result.trim());
        
    }
    
}
