/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author jonatas
 */
@ManagedBean(name="textoBean")

@RequestScoped
public class Texto {

private String texto;

    public String getTexto() {

    return texto;

    }

    public void setTexto(String texto) {

    this.texto = texto;

    }

}
