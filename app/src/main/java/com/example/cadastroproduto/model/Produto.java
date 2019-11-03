package com.example.cadastroproduto.model;

import java.io.Serializable;

public class Produto implements Serializable {
    private String nome;
    private String dtEntrada;
    private String dtSaida = null;
    private Double preco;
    private String fotoPath = null;
    private String descricao;
    private String codBarra = null;

    public String getDtEntrada() {
        return dtEntrada;
    }

    public void setDtEntrada(String dtEntrada) {
        this.dtEntrada = dtEntrada;
    }

    public String getDtSaida() {
        return getDtSaida();
    }

    public void setDtSaida(String dtSaida) {
        this.dtSaida = dtSaida;
    }

    public void setPreco(Integer id) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public String getCodBarra() {
        return codBarra;
    }

    public void setCodBarra(String codBarra) {
        this.codBarra = codBarra;
    }

}