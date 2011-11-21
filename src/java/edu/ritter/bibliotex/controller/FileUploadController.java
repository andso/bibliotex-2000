
package edu.ritter.bibliotex.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.event.FileUploadEvent;

@ManagedBean
@RequestScoped
public class FileUploadController {
    private String nomeArquivoSelecionado;
    
    private String nomeArquivoSalvo;
    
    public String getNomeArquivoSelecionado() {
        return this.nomeArquivoSelecionado;
    }
    
    public String getNomeArquivoSalvo() {
        if(this.nomeArquivoSalvo == null || this.nomeArquivoSalvo.isEmpty())
            this.nomeArquivoSalvo = "semCapa.jpg";
        
        return this.nomeArquivoSalvo;
    }

    public void setNomeArquivoSelecionado(String nomeArquivoSelecionado) {
        this.nomeArquivoSelecionado = nomeArquivoSelecionado;
    }

    public void fileUploadAction(FileUploadEvent event) {
        this.setNomeArquivoSelecionado(event.getFile().getFileName());
    }

    
    public void imageUploadAction(FileUploadEvent event) throws IOException
    {
        this.nomeArquivoSelecionado = event.getFile().getFileName();
        
        ServletContext context = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String directory = ((ServletContext)context).getRealPath("/files/");
        
        DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        
        this.nomeArquivoSalvo = (dateFormate.format(date)+ "-" + event.getFile().getFileName());
                
        File arquivo = new File(directory +"/"+ this.nomeArquivoSalvo);
        
        FileOutputStream fileOutputStream = new FileOutputStream(arquivo);
        
        byte[] buffer = new byte[(int)event.getFile().getSize()];
        
        int bulk;
        
        InputStream inputStream = event.getFile().getInputstream();
        
        while (true) {
            bulk = inputStream.read(buffer);
            
            if (bulk < 0)
                break;
            
            fileOutputStream.write(buffer, 0, bulk);

            fileOutputStream.flush();
        }
        
        fileOutputStream.close();
        
        inputStream.close();
    }
    
}
