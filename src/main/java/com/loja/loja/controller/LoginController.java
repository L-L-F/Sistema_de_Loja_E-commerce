/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.controller;

import javax.transaction.Transactional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author laerton
 */
@Transactional
@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String form() {
        return "authentication/login";
    }
 
    
}
