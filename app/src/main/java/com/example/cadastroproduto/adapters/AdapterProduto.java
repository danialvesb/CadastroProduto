package com.example.cadastroproduto.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroproduto.R;
import com.example.cadastroproduto.model.Produto;

import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter {
    public  List<Produto> produtos;
    private static ClickListener clickListener;


    public AdapterProduto(List<Produto> produtosPassados) {
        this.produtos = produtosPassados;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item_produto, parent, false);

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

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView idProduto;
        final TextView nomeProduto;
        final ImageView imagem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idProduto = (TextView) itemView.findViewById(R.id.item_idProduto);
            nomeProduto = (TextView) itemView.findViewById(R.id.item_nomeProduto);
            imagem = (ImageView) itemView.findViewById(R.id.item_imagemProduto);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);

        }
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        AdapterProduto.clickListener = clickListener;
    }


    public interface ClickListener {
        void onItemClick(int position, View v);
    }

}
