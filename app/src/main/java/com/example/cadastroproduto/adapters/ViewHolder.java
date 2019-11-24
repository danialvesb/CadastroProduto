package com.example.cadastroproduto.adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroproduto.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    final TextView idProduto;
    final TextView nomeProduto;
    final ImageView imagem;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        idProduto = (TextView) itemView.findViewById(R.id.item_idProduto);
        nomeProduto = (TextView) itemView.findViewById(R.id.item_nomeProduto);
        imagem = (ImageView) itemView.findViewById(R.id.item_imagemProduto);
    }

    public TextView getIdProduto() {
        return idProduto;
    }

    public TextView getNomeProduto() {
        return nomeProduto;
    }

    public ImageView getImagem() {
        return imagem;
    }


}
