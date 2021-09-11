/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.repository;

import com.loja.loja.model.Venda;
import java.time.LocalDate;
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
public class VendaRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Venda venda){
        em.persist(venda);
    }
    
    public Venda venda(Long id){
        return em.find(Venda.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Venda> vendas(){
        Query query = em.createQuery("from Venda");
        return query.getResultList();
    }
    
    public void remove(Long id){
        Venda v = em.find(Venda.class, id);
        em.remove(v);
    }

    public void update(Venda venda){
        em.merge(venda);
    }
    
    public List<Venda> vendas(LocalDate buscarData){
        String hql = "from Venda as v where v.data = :buscarData";
        Query query = em.createQuery(hql, Venda.class);
        query.setParameter("buscarData",buscarData);
        return query.getResultList();
    }
    
    public List<Venda> vendas(String cliente){
        String hql = "from Venda as v where v.cliente.nome like :IdCliente";
        Query query = em.createQuery(hql, Venda.class);
        query.setParameter("IdCliente", "%"+cliente+"%");
        return query.getResultList();
    }
    
    public List<Venda> vendas(Long IdCliente){
        String hql = "from Venda as v where v.cliente.id = :IdCliente";
        Query query = em.createQuery(hql, Venda.class);
        query.setParameter("IdCliente", IdCliente);
        return query.getResultList();
    }
    
    public List<Venda> vendas(Long IdCliente, LocalDate buscarData){
        String hql = "from Venda as v where v.cliente.id = :IdCliente and v.data = :buscarData";
        Query query = em.createQuery(hql, Venda.class);
        query.setParameter("IdCliente", IdCliente);
        query.setParameter("buscarData",buscarData);
        return query.getResultList();
    }
    
}
