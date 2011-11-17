package edu.ritter.bibliotex;

import edu.ritter.bibliotex.bd.util.PaginationHelper;

import edu.ritter.bibliotex.bd.Obra;
import edu.ritter.bibliotex.bd.Pesquisa;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;


import javax.faces.model.DataModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletContext;

@ManagedBean(name = "obraSearch")
@SessionScoped
public class Search {

    
    private DataModel items = null;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private String googleIt="";
    private List<Obra>  result;
   
    private List<Pesquisa> pesquisa=  new ArrayList<Pesquisa>();
    private Date maxDate;
    
    private int resultCount=0;
    private String feedName="pesquisa.xml";
    private boolean cached = false;
    private int quotesResult;
    
    
    public int getQuotesResult(){
        return quotesResult;
    }
    
    public void setGoogleIt(String value){
        if (!value.equals(this.googleIt)){
            System.out.println("Invalidando cache... OLD: "+ this.googleIt + " NEW: "+ value );
            this.googleIt= value;
            this.cached=false;
        }
    }
    
    
    public String getGoogleIt(){
        return this.googleIt;
    }
    
    public void setMaxDate(Date date){
        this.maxDate = date;
    }
    
    public Date getMaxDate(){
        return this.maxDate;
    }
    
    public List<Pesquisa> getPesquisa(){
        return this.pesquisa;
    }
    
    public void setPesquisa(List pesquisa){
        
        this.pesquisa = pesquisa;
    }
    
    public void setResult(List result){
        this.result = result;
    }
    
    public List getResult(){
        return this.result;
    }
    
    public int getResultCount(){
        return this.resultCount;
    }
    
    
    public void setResultCount(int i ){
         this.resultCount = i ;
    }
    
    public String getFeedPath(){
        return feedName;
    }
    
    @PersistenceUnit
    EntityManagerFactory emf;
    
    
    @PersistenceContext
    EntityManager em ;
    
    
    
    public void query(){
        
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("bibliotexPU");
      
        String param="SELECT o FROM Obra o WHERE o.titulo LIKE :googleIt OR o.conteudo LIKE :googleIt";
      
        try {
            em = emf.createEntityManager();  
            Query query = em.createQuery(param , Obra.class).setParameter("googleIt","%"+this.googleIt+"%");
            result = query.getResultList();  
        } catch (Exception e) {  
            e.printStackTrace();
        }  
        setResultCount(result.size());
     
        this.cached = true;
    }
    
    /**
     * 
     * @param param
     * @param resultClass
     * @return 
     */
      public String searchLazy(){
        
        /*  
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("bibliotexPU");
        String param="SELECT o FROM Obra o WHERE o.titulo LIKE :googleIt OR o.conteudo LIKE :googleIt";
      
      
        try {
            em = emf.createEntityManager();  
            Query query = em.createQuery(param , Obra.class).setParameter("googleIt","%"+this.googleIt+"%");
            result = query.getResultList();  
        } catch (Exception e) {  
           e.printStackTrace();
        }  
      
       
        setResultCount(result.size());
        */
          
       if (!this.cached){
           query();
       }   
       //TODO: refactor this 
        for (int i = 0; i < result.size(); i++){
            Obra  obra = (Obra)result.get(i); 
            Conteudo conteudo = new Conteudo(obra.getConteudo());
            List resultadoPesquisa = conteudo.lazySearch(this.googleIt);
            this.quotesResult =  resultadoPesquisa.size();
            Iterator<String> iterator = resultadoPesquisa.iterator();
            while (iterator.hasNext()) {
                String quote = iterator.next().trim();
                Pesquisa p = new Pesquisa();
                p.setQuote(quote);
                p.setDate(obra.getDataPublicacao());
                p.setAutor(obra.getAutor());
                p.setTitulo(obra.getTitulo());
                setMaxDate(obra.getDataPublicacao());
               
                
                pesquisa.add(p);
            }
        }
        
        return "LazyResult";
        
        
    }
      
    /**
       * 
       * 
       * @return timeline 
       */  
    public String timeLine(){
       
        String eventTag = "event";
        
        Feed bookFeed = new Feed();
        bookFeed.createXML("data");
      
        if (!this.cached){
           query();
        } 
        //TODO: refactor this
        for (int i = 0; i < result.size(); i++){
           
            Obra  obra = (Obra)result.get(i); 
            Conteudo conteudo = new Conteudo(obra.getConteudo());
            List resultadoPesquisa = conteudo.lazySearch(this.googleIt);

            Iterator<String> iterator = resultadoPesquisa.iterator();
            while (iterator.hasNext()) {
                String quote = iterator.next().trim();
                setMaxDate(obra.getDataPublicacao());
                bookFeed.addElements(eventTag, obra, quote);
            }

        } 

        
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String directory = ((ServletContext)context).getRealPath("/obra");
        
        long timestamp = System.currentTimeMillis() / 1000L;
        this.feedName = "pesquisa_"+ timestamp+".xml";
        System.out.println("Saving "+ this.feedName);
        bookFeed.saveXml(directory+"/" + this.feedName);
       
        return "Timeline";
    }  
    
    
    

    
}
