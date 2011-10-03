/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex.bd;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anderson
 */
@Entity
@Table(name = "obra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Obra.findAll", query = "SELECT o FROM Obra o"),
    @NamedQuery(name = "Obra.findByIdObra", query = "SELECT o FROM Obra o WHERE o.idObra = :idObra"),
    @NamedQuery(name = "Obra.findByDataPublicacao", query = "SELECT o FROM Obra o WHERE o.dataPublicacao = :dataPublicacao"),
    @NamedQuery(name = "Obra.findByIsbn", query = "SELECT o FROM Obra o WHERE o.isbn = :isbn"),
    @NamedQuery(name = "Obra.findByAutor", query = "SELECT o FROM Obra o WHERE o.autor = :autor"),
    @NamedQuery(name = "Obra.findByEditora", query = "SELECT o FROM Obra o WHERE o.editora = :editora"),
    @NamedQuery(name = "Obra.findByTipoDeObra", query = "SELECT o FROM Obra o WHERE o.tipoDeObra = :tipoDeObra"),
    @NamedQuery(name = "Obra.findByAreaRelacionada", query = "SELECT o FROM Obra o WHERE o.areaRelacionada = :areaRelacionada"),
    @NamedQuery(name = "Obra.findByCapa", query = "SELECT o FROM Obra o WHERE o.capa = :capa"),
    @NamedQuery(name = "Obra.findByNumeropaginas", query = "SELECT o FROM Obra o WHERE o.numeropaginas = :numeropaginas"),
    @NamedQuery(name = "Obra.findByTitulo", query = "SELECT o FROM Obra o WHERE o.titulo = :titulo"),
    @NamedQuery(name = "Obra.findContentByIdObra", query = "SELECT o.conteudo FROM Obra o WHERE o.idObra = :idObra"),
    @NamedQuery(
        name = "Obra.findContentByTitulo", 
        query = "SELECT o.conteudo FROM Obra o WHERE o.titulo = :googleIt"
        ),
    @NamedQuery(name = "Obra.findByDescricao", query = "SELECT o FROM Obra o WHERE o.descricao = :descricao")})
public class Obra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_OBRA")
    private Integer idObra;
    @Basic(optional = false)
    @Column(name = "DATA_PUBLICACAO")
    @Temporal(TemporalType.DATE)
    private Date dataPublicacao;
    @Column(name = "ISBN")
    private Integer isbn;
    @Column(name = "AUTOR")
    private String autor;
    @Column(name = "EDITORA")
    private String editora;
    @Column(name = "TIPO_DE_OBRA")
    private String tipoDeObra;
    @Column(name = "AREA_RELACIONADA")
    private String areaRelacionada;
    @Lob
    @Column(name = "CONTEUDO")
    private String conteudo;
    @Column(name = "CAPA")
    private String capa;
    @Column(name = "NUMEROPAGINAS")
    private Integer numeropaginas;
    @Column(name = "TITULO")
    private String titulo;
    @Column(name = "DESCRICAO")
    private String descricao;

    public Obra() {
    }

    public Obra(Integer idObra) {
        this.idObra = idObra;
    }

    public Obra(Integer idObra, Date dataPublicacao) {
        this.idObra = idObra;
        this.dataPublicacao = dataPublicacao;
    }

    public Integer getIdObra() {
        return idObra;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(Integer isbn) {
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getTipoDeObra() {
        return tipoDeObra;
    }

    public void setTipoDeObra(String tipoDeObra) {
        this.tipoDeObra = tipoDeObra;
    }

    public String getAreaRelacionada() {
        return areaRelacionada;
    }

    public void setAreaRelacionada(String areaRelacionada) {
        this.areaRelacionada = areaRelacionada;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getCapa() {
        return capa;
    }

    public void setCapa(String capa) {
        this.capa = capa;
    }

    public Integer getNumeropaginas() {
        return numeropaginas;
    }

    public void setNumeropaginas(Integer numeropaginas) {
        this.numeropaginas = numeropaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idObra != null ? idObra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obra)) {
            return false;
        }
        Obra other = (Obra) object;
        if ((this.idObra == null && other.idObra != null) || (this.idObra != null && !this.idObra.equals(other.idObra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ritter.bibliotex.bd.Obra[ idObra=" + idObra + " ]";
    }
    
}
