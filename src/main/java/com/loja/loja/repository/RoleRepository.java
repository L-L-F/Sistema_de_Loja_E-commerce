/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.repository;

import com.loja.loja.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author laerton
 */
@Transactional
@Repository
public class RoleRepository {
    
    @PersistenceContext
    private EntityManager em;
    
    public Role role(Long id){
        return em.find(Role.class, id);
    }
}
