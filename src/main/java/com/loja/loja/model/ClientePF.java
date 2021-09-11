/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.loja.loja.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

/**
 *
 * @author laerton
 */

@Entity
@Table(name = "tb_clientes_f")
public class ClientePF extends Cliente implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @NotBlank(message = "Nome é obrigatório!")
    private String nome;
    
    @NotBlank(message = "CPF é obrigatório!")
    @Size(min=11, max=11, message = "Quantidade de caracteres deve ser 11!")
    @Pattern(regexp = "[0-9]+", message = "Informe apenas números!")
    private String cpf;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
}