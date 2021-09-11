/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.repository;

import com.loja.loja.model.ItemVenda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author laerton
 */

@Repository
public class ItemVendaRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    public List<ItemVenda> ItensVenda(Long idVenda){
        String hql = "from ItemVenda as i where i.venda.id = :idVendav";
        Query query = em.createQuery(hql, ItemVenda.class);
        query.setParameter("idVendav", idVenda);
        return query.getResultList();
    }

}
