package edu.ritter.bibliotex.controller;

import edu.ritter.bibliotex.bd.Obra;
import edu.ritter.bibliotex.repositoty.ObraRepository;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.persistence.Persistence;

@ManagedBean(name = "obraControllerBean")
public class ObraController {

    private Obra obra;

    public Obra getObra() {
        if (this.obra == null) {
            this.obra = new Obra();
        }

        return this.obra;
    }

    public String salvar() {
        UIComponent uiComponent = buscarComponenteJavaFaces("nomeImagemCapa");
        
        String capa = (String)((UIInput)uiComponent).getValue();
        
        obra.setCapa(capa);  
        
        ObraRepository repository = new ObraRepository(Persistence.createEntityManagerFactory("bibliotexPU"));
        repository.create(obra);

        return "sucesso";
    }

    private UIComponent buscarComponenteJavaFaces(String id) {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        
        return buscarComponente(facesContext.getViewRoot(), id);
    }

    private UIComponent buscarComponente(UIComponent uiComponent, String id) {
        if (id.equals(uiComponent.getId())) {
            return uiComponent;
        }

        UIComponent filho = null;
        UIComponent atual = null;
        Iterator filhos = uiComponent.getFacetsAndChildren();

        while (filhos.hasNext() && (atual == null)) {
            filho = (UIComponent)filhos.next();
            
            if (id.equals(filho.getId())) {
                atual = filho;
                break;
            }

            atual = buscarComponente(filho, id);
        }
        
        return atual;
    }
}
