/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.controller;

import com.loja.loja.model.ClientePF;
import com.loja.loja.repository.ClientePFRepository;
import com.loja.loja.repository.RoleRepository;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author laerton
 */

@Transactional
@Controller
@RequestMapping("clientes")
public class ClienteController {
    
     @Autowired
     ClientePFRepository clienteRepository;
     
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/form")
    public ModelAndView form(ClientePF clientePF){
        return new ModelAndView("/clientes/form") ;
    }
    
    @GetMapping("/cadastro")
    public ModelAndView cadastro(ClientePF clientePF){
        return new ModelAndView("/clientes/cadastro") ;
    }
    
    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("clientesPF", clienteRepository.clientesPF());
        return new ModelAndView("/clientes/list", model);
    }
    
    
    @PostMapping("/save")
    public ModelAndView save(@Valid ClientePF clientePF, BindingResult result) {
        
        if (result.hasErrors())
            return cadastro(clientePF);
        
        clientePF.getUsuario().setPassword(criptografarPassword(clientePF.getUsuario().getPassword()));
        clientePF.getUsuario().getRoles().add(roleRepository.role(2L));
        
        clienteRepository.save(clientePF);
       
        return new ModelAndView("redirect:/login");
    }
    
    public String criptografarPassword (String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
    
    @PostMapping("/savecliente")
    public ModelAndView savec(@Valid ClientePF clientePF, BindingResult result) {
        if (result.hasErrors())
            return form(clientePF);
        clienteRepository.save(clientePF);
        return new ModelAndView("redirect:/clientes/list");
    }
    
    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id){
        clienteRepository.remove(id);
        return new ModelAndView("redirect:/clientes/list");
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("clientePF", clienteRepository.clientePF(id));
        return new ModelAndView("/clientes/form", model);
    }
    
    @PostMapping("/update")
    public ModelAndView update(@Valid ClientePF clientePF, BindingResult result) {
        if (result.hasErrors())
            return form(clientePF);
        clienteRepository.update(clientePF);
        return new ModelAndView("redirect:/clientes/list");
    }
    
    @GetMapping("/listarNome")
    public ModelAndView listarNome(@RequestParam(value = "cliente")String nomeCliente, ModelMap model) {
        if (nomeCliente.equals("")) 
            return new ModelAndView("redirect:/clientes/list");
        
        model.addAttribute("clientesPF", clienteRepository.clientesPF(nomeCliente));
        return new ModelAndView("/clientes/list", model);
    }
    
    
}
