/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author laerton
 */
@Scope("session")
@Component
@Entity
public class Venda implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data = LocalDate.now();
    
    @ManyToOne(cascade = CascadeType.MERGE)
    private ClientePF cliente;
    
    @OneToMany(mappedBy = "venda", cascade =  CascadeType.PERSIST)
    private List<ItemVenda> itensVenda = new ArrayList<>();
    
    public double valorTotal() {
        double res = 0;
        for ( ItemVenda i : itensVenda) {
            res += i.totalItem();
        }
        return res;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<ItemVenda> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItemVenda> itensVenda) {
        this.itensVenda = itensVenda;
    }

    public ClientePF getCliente() {
        return cliente;
    }

    public void setCliente(ClientePF cliente) {
        this.cliente = cliente;
    }

    
}
