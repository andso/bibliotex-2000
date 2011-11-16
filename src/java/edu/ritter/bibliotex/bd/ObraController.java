package edu.ritter.bibliotex.bd;

import edu.ritter.bibliotex.Conteudo;
//import edu.ritter.bibliotex.bd.Obra;
import edu.ritter.bibliotex.Feed;
import edu.ritter.bibliotex.bd.util.JsfUtil;
import edu.ritter.bibliotex.bd.util.PaginationHelper;
//import edu.ritter.bibliotex.bd.ObraJpaController;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.servlet.ServletContext;

@ManagedBean(name = "obraController")
@SessionScoped
public class ObraController implements Serializable {

    private Obra current;
    private DataModel items = null;
    private ObraJpaController jpaController = null;
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
       
        
//        instance.addElements(value, "2008", "titledd ddd1");
//        instance.addElements(value, "2011", "title   dsfs  dd2");
//        instance.saveXml("pesquisa.xml");
        return "Timeline";
    }  
    
    
    
    public ObraController() {
    }

    
            
            
    public Obra getSelected() {
        if (current == null) {
            current = new Obra();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ObraJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new ObraJpaController(Persistence.createEntityManagerFactory("bibliotexPU"));
        }
        return jpaController;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getJpaController().getObraCount();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getJpaController().findObraEntities(getPageSize(), getPageFirstItem()));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Obra) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    
    
    /**
     * 
     */
    public String getMagicAnswer() {
        current = (Obra) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "LazyResult";
    }
    
    /**
     * 
     */
    public String getContentView() {
        current = (Obra) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "lazy_quote";
    }
    
    public String prepareCreate() {
        current = new Obra();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getJpaController().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ObraCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Obra) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getJpaController().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ObraUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Obra) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getJpaController().destroy(current.getIdObra());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ObraDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getJpaController().getObraCount();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getJpaController().findObraEntities(1, selectedItemIndex).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(getJpaController().findObraEntities(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(getJpaController().findObraEntities(), true);
    }

    @FacesConverter(forClass = Obra.class)
    public static class ObraControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ObraController controller = (ObraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "obraController");
            return controller.getJpaController().findObra(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Obra) {
                Obra o = (Obra) object;
                return getStringKey(o.getIdObra());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ObraController.class.getName());
            }
        }
    }
}
