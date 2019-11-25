package com.example.cadastroproduto.adapters;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

}
