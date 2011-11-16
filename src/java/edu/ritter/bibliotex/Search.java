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
    
    public void setGoogleIt(String value){
       
        this.googleIt= value;
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
    
    
    
    @PersistenceUnit
    EntityManagerFactory emf;
    
    
    @PersistenceContext
    EntityManager em ;
    
    
    /**
     * 
     * @param param
     * @param resultClass
     * @return 
     */
      public String searchLazy(){
        EntityManagerFactory emf=Persistence.createEntityManagerFactory("bibliotexPU");
            
    
      // String param="SELECT o.conteudo FROM Obra o WHERE o.titulo LIKE :googleIt OR o.conteudo LIKE :googleIt";
       //  String param="SELECT o.conteudo, o.dataPublicacao FROM Obra o WHERE o.titulo LIKE :googleIt OR o.conteudo LIKE :googleIt";
        String param="SELECT o FROM Obra o WHERE o.titulo LIKE :googleIt OR o.conteudo LIKE :googleIt";
      
       List<Pesquisa> pesquisaGoogle = //<editor-fold defaultstate="collapsed" desc="comment">
               new ArrayList<Pesquisa>();
       //</editor-fold>
        try {
            
            em = emf.createEntityManager();
            
            Query query = em.createQuery(param , Obra.class).setParameter("googleIt","%"+this.googleIt+"%");
            result = query.getResultList();  
        } catch (Exception e) {  
            
            System.out.println(">>>  "+ this.googleIt + " "+ result);
            e.printStackTrace();
         
        }  
       // System.out.println(">>>  "+ result.get(0));

       
        setResultCount(result.size());
        
        
          
        String value = "event";
        
        Feed instance = new Feed();
        instance.createXML("data");
      
        
        
        
        for (int i = 0; i < result.size(); i++){
            System.out.println(result);
            
            
            Obra  obra = (Obra)result.get(i); //.getConteudo();
            Conteudo conteudo = new Conteudo(obra.getConteudo());
            List resultadoPesquisa = conteudo.lazySearch(this.googleIt);
            
            Iterator<String> iterator = resultadoPesquisa.iterator();
            while (iterator.hasNext()) {
                String quote = iterator.next().trim();
                Pesquisa p = new Pesquisa();
                p.setQuote(quote);
                p.setDate(obra.getDataPublicacao());
                p.setAutor(obra.getAutor());
                p.setTitulo(obra.getTitulo());
                setMaxDate(obra.getDataPublicacao());
               
                
                instance.addElements(value, obra, quote);
                pesquisa.add(p);
            }
            
          
        }
        
        
        
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String directory = ((ServletContext)context).getRealPath("/obra");
        
        System.err.println("Diretorio " + directory);
        instance.saveXml(directory+"/pesquisa.xml");
        return "LazyResult";
        
        
    }
      
      
    public String timeLine(){
        
        String value = "event";
        
        Feed instance = new Feed();
        instance.createXML("data");
       
       
        return "Timeline";
    }  
    
    
    

    
}
