/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.security;

import com.loja.loja.model.Usuario;
import com.loja.loja.repository.UsuarioRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

/**
 *
 * @author laerton
 */

@Transactional
@Repository
public class UsuariosDetailsConfig implements UserDetailsService{
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.usuario(login);
        if (usuario == null) 
            throw new UsernameNotFoundException("Usuario não encontrado!");
        return new User(usuario.getLogin(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());             
    }
    
}
