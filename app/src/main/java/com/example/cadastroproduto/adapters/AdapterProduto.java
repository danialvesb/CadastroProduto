package com.example.cadastroproduto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroproduto.R;
import com.example.cadastroproduto.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduto extends RecyclerView.Adapter implements Filterable {
    public  List<Produto> produtos;
    public  List<Produto> produtosCopia;

    private static ClickListener clickListener;


    public AdapterProduto(List<Produto> produtosPassados) {
        this.produtos = produtosPassados;
        produtosCopia = new ArrayList<>(produtosPassados);
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
        meuViewHolder.imagem.setImageBitmap(produto.getImagens().get(0)); //Sempre vai pegar a primeira pois não precisa mais de uma

    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Produto> filteredList = new ArrayList<>();



                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(produtosCopia);
                }else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Produto produto : produtosCopia) {
                        String idProduto = produto.getId()+"";
                        if (produto.getNome().toLowerCase().contains(filterPattern) || idProduto.toLowerCase().contains(filterPattern)) {
                            filteredList.add(produto);
                        }
                        idProduto = null;
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;


                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                produtos.clear();
                produtos.addAll((List)results.values);
                notifyDataSetChanged();
            }

        };
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
