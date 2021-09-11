/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.repository;

import com.loja.loja.model.ClientePF;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author laerton
 */

@Transactional
@Repository
public class ClientePFRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(ClientePF clientePF){
        em.persist(clientePF);
    }
    
    public ClientePF clientePF(Long id){
        return em.find(ClientePF.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<ClientePF> clientesPF(){
        Query query = em.createQuery("from Cliente");
        return query.getResultList();
    }
    
    public void remove(Long id){
        ClientePF c = em.find(ClientePF.class, id);
        em.remove(c);
    }

    public void update(ClientePF clientePF){
        em.merge(clientePF);
    }
    
    public List<ClientePF> clientesPF(String cliente){
        String hql = "from ClientePF as c where c.nome like :cliente";
        Query query = em.createQuery(hql, ClientePF.class);
        query.setParameter("cliente", "%"+cliente+"%");
        return query.getResultList();
    }

}
