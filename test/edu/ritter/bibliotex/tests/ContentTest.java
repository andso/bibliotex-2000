/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex.tests;

import edu.ritter.bibliotex.Conteudo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/**
 *
 * @author anderson
 */
public class ContentTest {
    
    	String bookContent = 
		"This is a true love story. "
                + "Elizabeth and Mr. Darcy are my two favorite love story characters. This book is what makes me think of what first impressions are. I enjoyed the personal emotional growth of Elizabeth Bennett. Mr. Darcy makes me want to slap him and kiss him at the same time (thats how I knew my husband was the one). This is a true masterpiece."
	;
    
   // String book = "Chapter 1 The sky above the port was the color of television, sing, Case heard someone say, as he shouldered his way through the crowd around the door of the Chat. Its developed this massive drug deficiency. It was a Sprawl voice and a Sprawl joke. The Chatsubo was a bar for professional expatriates; you could drink there for a week and never hear two words in Japanese. Ratz was tending bar, h is prosthetic arm jerking monotonously as he filled a tray of glasses with draft Kirin. He saw Case and smiled, his teeth a webwork of East European steel and brown decay. Case found a place at the bar, between the unlikely tan on one of Lonny Zones whores and the crisp naval uniform of a tall African whose cheekbones were ridged with precise rows of tribal scars. Wage was in here early, with two joeboys, Ratz said, shoving a draft across the bar with his good hand. Maybe some business with you, Case?";
    
    
    public ContentTest() {
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

    @Test
    public void testSearch() {

        Conteudo content = new Conteudo(bookContent);
        List <String> results = content.lazySearch("love");
        
        assertEquals(results.get(0),  "This is a true love story");
    
        assertEquals(results.get(1),  " Darcy are my two favorite love story characters");
    
    
    }
}
