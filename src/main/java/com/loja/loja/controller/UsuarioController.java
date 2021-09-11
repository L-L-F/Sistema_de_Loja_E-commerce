/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.controller;

import com.loja.loja.model.Usuario;
import com.loja.loja.repository.UsuarioRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author laerton
 */

@Transactional
@Controller
@RequestMapping("cadastro")
public class UsuarioController {
    
    @Autowired
    UsuarioRepository usuarioRepository;
    
    @GetMapping
    public ModelAndView form(Usuario usuario){
        return new ModelAndView("/cadastro") ;
    }
    
}
