package com.example.cadastroproduto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroproduto.MyApp;
import com.example.cadastroproduto.R;
import com.example.cadastroproduto.model.Produto;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter {
    public  List<Produto> produtos;

    public AdapterProduto(List<Produto> produtosPassados) {
        this.produtos = produtosPassados;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.fragment_item_produto, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder meuViewHolder = (ViewHolder) holder;
        Produto produto = produtos.get(position);
        String id = ""+produto.getId();
        meuViewHolder.idProduto.setText((id));
        meuViewHolder.nomeProduto.setText(produto.getNome());
        meuViewHolder.imagem.setImageBitmap(produto.getImagens().get(1)); //Sempre vai pegar a primeira pois não precisa mais de uma

    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }




}
