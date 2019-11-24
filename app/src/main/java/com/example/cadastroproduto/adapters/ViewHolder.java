package com.example.cadastroproduto.adapters;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroproduto.R;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    final TextView idProduto;
    final TextView nomeProduto;
    final ImageView imagem;
    private static ItemClickListener itemClickListener;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        idProduto = (TextView) itemView.findViewById(R.id.item_idProduto);
        nomeProduto = (TextView) itemView.findViewById(R.id.item_nomeProduto);
        imagem = (ImageView) itemView.findViewById(R.id.item_imagemProduto);
        itemView.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        if(itemClickListener != null) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }


}
