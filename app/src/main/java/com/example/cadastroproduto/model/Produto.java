package com.example.cadastroproduto.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Produto implements Serializable {
    private long id;
    private String nome;
    private Double preco;
    private String dtEntrada;
    private String dtSaida = null;
    private String descricao;
    private List<Bitmap> imagens = new ArrayList<>();


    public Produto() {}

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtEntrada() {
        return dtEntrada;
    }

    public void setDtEntrada(String dtEntrada) {
        this.dtEntrada = dtEntrada;
    }

    public String getDtSaida() {
        return dtSaida;
    }

    public void setDtSaida(String dtSaida) {
        this.dtSaida = dtSaida;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public List<Bitmap> getImagens() {
        return imagens;
    }

    public void setImagens(List<Bitmap> imagens) {
        this.imagens = imagens;
    }

    public void addImagens(Bitmap imagens) {
        this.imagens.add(imagens);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}