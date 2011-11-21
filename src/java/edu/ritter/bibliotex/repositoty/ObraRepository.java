/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ritter.bibliotex.repositoty;

import edu.ritter.bibliotex.bd.Obra;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class ObraRepository {

    private final EntityManagerFactory emf;
    
    public ObraRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public void create(Obra obra) {
        EntityManager em = this.emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.persist(obra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
