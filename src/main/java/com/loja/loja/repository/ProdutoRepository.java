/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.repository;

import com.loja.loja.model.Produto;
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
public class ProdutoRepository {
    
    @PersistenceContext
    private EntityManager em;

    public void save(Produto produto){
        em.persist(produto);
    }
    
    public Produto produto(Long id){
        return em.find(Produto.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Produto> produtos(){
        Query query = em.createQuery("from Produto");
        return query.getResultList();
    }
    
    public void remove(Long id){
        Produto p = em.find(Produto.class, id);
        em.remove(p);
    }

    public void update(Produto produto){
        em.merge(produto);
    }
    
    public List<Produto> produtos(String nomeProduto){
        String hql = "from Produto as p where p.nome like :nomeProduto";
        Query query = em.createQuery(hql, Produto.class);
        query.setParameter("nomeProduto", "%"+nomeProduto+"%");
        return query.getResultList();
    }
}
