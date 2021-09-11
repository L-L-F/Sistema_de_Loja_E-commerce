package com.loja.loja.controller;

import com.loja.loja.model.ClientePF;
import com.loja.loja.model.ItemVenda;
import com.loja.loja.model.Venda;
import com.loja.loja.repository.ClientePFRepository;
import com.loja.loja.repository.ItemVendaRepository;
import com.loja.loja.repository.ProdutoRepository;
import com.loja.loja.repository.UsuarioRepository;
import com.loja.loja.repository.VendaRepository;
import com.loja.loja.security.UsuariosDetailsConfig;
import java.time.LocalDate;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author laerton
 */
@Scope("request")
@Transactional
@Controller
@RequestMapping("vendas")
public class VendaController {

    @Autowired
    VendaRepository vendaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ClientePFRepository clienteRepository;

    @Autowired
    ItemVendaRepository itemVendaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    Venda venda;

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @GetMapping("/form")
    public ModelAndView form(ItemVenda itemVenda) {
        ModelMap model = new ModelMap();
        model.addAttribute("clientesPF", clienteRepository.clientesPF());
        model.addAttribute("produtos", produtoRepository.produtos());
        return new ModelAndView("/vendas/form", model);
    }

    @GetMapping("/list")
    public ModelAndView listar(ModelMap model) {
        String user = auth.getName();

        if (user.equals("admin")) {
            model.addAttribute("vendas", vendaRepository.vendas());
        } else {
            model.addAttribute("vendas", vendaRepository.vendas(usuarioRepository.usuario(user).getId()));
        }

        return new ModelAndView("/vendas/list", model);
    }

    @PostMapping("/add")
    public ModelAndView additem(@Valid ItemVenda itemVenda, BindingResult result) {
        if (result.hasErrors()) {
            return catalogo(itemVenda, result);
        }
        itemVenda.setVenda(venda);
        itemVenda.setProduto(produtoRepository.produto(itemVenda.getProduto().getId()));
        itemVenda.totalItem();
        venda.getItensVenda().add(itemVenda);
        venda.valorTotal();
        return new ModelAndView("redirect:/vendas/form");
    }

    @PostMapping("/save")
    public ModelAndView save(ClientePF cliente, RedirectAttributes attributes) {
        if (this.venda.getItensVenda().isEmpty()) {
            attributes.addFlashAttribute("erroItens", "Não tem itens no seu carrinho!");
        }

        if (!attributes.getFlashAttributes().isEmpty()) {
            return new ModelAndView("redirect:/vendas/form");
        }

        String user = auth.getName();

        if (user.equals("admin")) {
            if (cliente.getId() == null) {
                attributes.addFlashAttribute("erroCliente", "Insira o cliente");
            }
            this.venda.setCliente(clienteRepository.clientePF(cliente.getId()));
        } else {
            this.venda.setCliente(clienteRepository.clientePF(usuarioRepository.usuario(user).getId()));
        }

        this.venda.setId(null);
        this.venda.setData(venda.getData());
        vendaRepository.save(this.venda);
        this.venda.getItensVenda().clear();
        return new ModelAndView("redirect:/vendas/list");
    }

    @GetMapping("/removeitem/{id}")
    public ModelAndView removeItem(@PathVariable("id") Long id) {
        for (int i = 0; i < venda.getItensVenda().size(); i++) {
            if (venda.getItensVenda().get(i).getProduto().getId().equals(id)) {
                venda.getItensVenda().remove(i);
            }
        }
        return new ModelAndView("redirect:/vendas/form");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("venda", vendaRepository.venda(id));
        return new ModelAndView("/vendas/form", model);
    }

    @PostMapping("/update")
    public ModelAndView update(Venda venda) {
        vendaRepository.update(venda);
        return new ModelAndView("redirect:/vendas/list");
    }

    @GetMapping("/catalogo")
    public ModelAndView catalogo(ItemVenda itemVenda, BindingResult result) {
        ModelMap model = new ModelMap();
        model.addAttribute("produtos", produtoRepository.produtos());
        return new ModelAndView("/vendas/catalogo", model);
    }

    @GetMapping("/catalogoBusca")
    public ModelAndView catalogoBusca(@RequestParam(value = "nomeProduto") String nomeProduto, ItemVenda itemVenda, BindingResult result) {
        if (nomeProduto.equals("")) {
            return new ModelAndView("redirect:/vendas/catalogo");
        }

        ModelMap model = new ModelMap();
        model.addAttribute("produtos", produtoRepository.produtos(nomeProduto));
        return new ModelAndView("/vendas/catalogo", model);
    }

    @GetMapping("/listarData")
    public ModelAndView listarData(@RequestParam(value = "buscarData") String buscarData, RedirectAttributes attribute, ModelMap model) {
        if (buscarData.equals("")) {
            return new ModelAndView("redirect:/vendas/list");
        }

        String user = auth.getName();

        LocalDate data = LocalDate.parse(buscarData);
        if (user.equals("admin")) {
            model.addAttribute("vendas", vendaRepository.vendas(data));
        } else {
            model.addAttribute("vendas", vendaRepository.vendas(usuarioRepository.usuario(user).getId(), data));
        }
        return new ModelAndView("/vendas/list", model);
    }

    @GetMapping("/listarCliente/{id}")
    public ModelAndView listarCliente(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("vendas", vendaRepository.vendas(id));
        return new ModelAndView("/vendas/list", model);
    }

    @GetMapping("/detalhes/{id}")
    public ModelAndView listarItens(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("itensVenda", itemVendaRepository.ItensVenda(id));
        return new ModelAndView("/vendas/detalhes", model);
    }
    
    @GetMapping("/listarNome")
    public ModelAndView listarNome(@RequestParam(value = "cliente")String nomeCliente, ModelMap model) {
        if (nomeCliente.equals("")) 
            return new ModelAndView("redirect:/vendas/list");
        
        model.addAttribute("vendas", vendaRepository.vendas(nomeCliente));
        return new ModelAndView("/vendas/list", model);
    }
}
