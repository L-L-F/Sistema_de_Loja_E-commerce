/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.controller;

import com.loja.loja.model.Produto;
import com.loja.loja.repository.ProdutoRepository;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author laerton
 */
@Controller
@RequestMapping("produtos")
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/form")
    public ModelAndView form(Produto produto) {
        return new ModelAndView("/produtos/form");
    }

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        model.addAttribute("produtos", produtoRepository.produtos());
        return new ModelAndView("/produtos/list", model);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid Produto produto, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return form(produto);
        }
        produtoRepository.save(produto);
        return new ModelAndView("redirect:/produtos/list");
    }

    @GetMapping("/remove/{id}")
    public ModelAndView remove(@PathVariable("id") Long id) {
        
        produtoRepository.remove(id);
        return new ModelAndView("redirect:/produtos/list");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("produto", produtoRepository.produto(id));
        return new ModelAndView("/produtos/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors()) {
            return form(produto);
        }
        produtoRepository.update(produto);
        return new ModelAndView("redirect:/produtos/list");
    }
    
    @GetMapping("/listarProduto")
    public ModelAndView listarProdutos(@RequestParam(value = "nomeProduto")String nomeProduto, ModelMap model) {
         if (nomeProduto.equals("")) 
            return new ModelAndView("redirect:/produtos/list");
        model.addAttribute("produtos", produtoRepository.produtos(nomeProduto));
        return new ModelAndView("/produtos/list", model);
    }
}
