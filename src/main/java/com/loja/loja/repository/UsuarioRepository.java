/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.repository;

import com.loja.loja.model.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author laerton
 */
@Repository
public class UsuarioRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    public Usuario usuarioId(Long id) {
        return em.find(Usuario.class, id);
    }
    
    public Usuario usuario(String login){
        String hql = "from Usuario as u where u.login = :login";
        TypedQuery<Usuario> query = em.createQuery(hql, Usuario.class);
        query.setParameter("login",login);
        return query.getSingleResult();
    }
    
    public void save(Usuario usuario){
        em.persist(usuario);
    }
}
