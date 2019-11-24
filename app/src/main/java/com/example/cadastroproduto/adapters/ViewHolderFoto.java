package com.example.cadastroproduto.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroproduto.R;

public class ViewHolderFoto extends RecyclerView.ViewHolder {
    final ImageView imageView;


    public ViewHolderFoto(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.item_imageView);
    }
}
