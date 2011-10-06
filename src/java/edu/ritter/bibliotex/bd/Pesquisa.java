/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex.bd;

import java.util.Date;

/**
 *
 * @author anderson
 */
public class Pesquisa {
    
    private String quote ="";
    private Date date;
    private String autor="";
    private String titulo = "";
    
    public void setQuote(String quote){
        this.quote = quote;
    }
    
    public String getQuote(){
        return this.quote;
    }
    
    public void setDate(Date date){
        this.date = date;
    }
    
    public Date getDate(){
        return this.date;
    }
    
    public String getAutor(){
        return this.autor;
    }
    
    public void setAutor(String autor){
        this.autor = autor;
    }
    
    public String getTitulo(){
        return this.titulo;
    }
    
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
}
