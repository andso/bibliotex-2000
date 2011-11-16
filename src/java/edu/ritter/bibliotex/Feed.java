/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import edu.ritter.bibliotex.bd.Obra;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 *
 * @author anderson
 */
public class Feed {
    private Document document;
    private Element root;
    private String fileName="";
    
    public void addElements(String value, Obra obra, String quote){
        
        Element eventTag = document.createElement(value);
        eventTag.setAttribute("title", obra.getTitulo());
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy");  
                  
        String ano = format.format(obra.getDataPublicacao());  

        eventTag.setAttribute("start", ano);
        
        
        Text text = document.createTextNode(quote);
        eventTag.appendChild(text);

        
        
        root.appendChild(eventTag);
        
       
    }
    
    
    
    public void saveXml(String search){
        try {
            XMLSerializer serializer = new XMLSerializer();
            serializer.setOutputCharStream(new java.io.FileWriter(search));
            serializer.serialize(document);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Feed.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void createXML( String value){
        try {
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // then we have to create document-loader:
            DocumentBuilder loader = factory.newDocumentBuilder();

            // creating a new DOM-document...
            document = loader.newDocument();
    
            root = document.createElement(value);
            document.appendChild(root);
           
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(Feed.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
}
