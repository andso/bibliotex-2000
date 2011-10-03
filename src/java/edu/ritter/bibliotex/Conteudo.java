/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex;



import java.util.*;


/**
 *
 * @author anderson
 */
public class Conteudo {
    private String textLook="";
    
    public Conteudo(String textLook){
        
        System.out.println("texto para tokenizar [[[["+ textLook+ "]]]]]]");
		this.textLook = textLook;
    }
    
    public List lazySearch(String lookUp){
		
		//int index = this.textLook.indexOf(lookUp);
                
                String delims = "[.?!]+";
		String[] tokens = this.textLook.split(delims );
                List <String> result  = new ArrayList()   ;
              //  int count =0;
                for (int i = 0; i < tokens.length; i++){
                    if (tokens[i].contains(lookUp)){
                       result.add(tokens[i]); 
                       System.out.println(">>>"+ tokens[i] +"<<<");
                    }
                }
                
		return result;
	}
    
  
}
