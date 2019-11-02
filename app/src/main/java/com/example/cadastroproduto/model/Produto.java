package com.example.cadastroproduto.model;

import java.io.Serializable;

public class Produto implements Serializable {

    private Integer id;
    private String nome;
    private String dtEntrada;
    private String dtSaida;
    private double preco;
    private String fotoPath;
    private String descricao;
    private String codBarra;

//    public Produto(String nome, String dtEntrada, String dtSaida, String descricao, String preco) {
//        this.nome = nome;
//        this.dtEntrada = dtEntrada;
//        this.dtSaida = dtSaida;
//        this.descricao = descricao;
//        this.preco = preco;
//    }

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public double getPreco() {
        return preco;
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


    @Override
    public String toString() {
        return "Produto: " + getNome()
                + "\nId: " + getId()
                + "\nPreço: " + getPreco()
                + "\nDescrição: " + getDescricao()
                + "\nData Entrada: " + getDtEntrada()
                + "\nData Saída: " + getDtSaida()
                + "\nCódigo de barra: " + getCodBarra()
                + "\nCaminho foto: " + getFotoPath();
    }

}