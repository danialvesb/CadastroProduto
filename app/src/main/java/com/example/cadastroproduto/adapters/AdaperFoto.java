package com.example.cadastroproduto.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroproduto.MyApp;
import com.example.cadastroproduto.R;

import java.util.List;

public class AdaperFoto extends RecyclerView.Adapter {
    List<Bitmap> imageViews;


    public AdaperFoto(List<Bitmap> imageViewsPassadas) {
        this.imageViews = imageViewsPassadas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(MyApp.getContext()).inflate(R.layout.fragment_item_foto, parent, false);
        ViewHolderFoto holder = new ViewHolderFoto(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderFoto meuViewHolderFoto = (ViewHolderFoto) holder;
        Bitmap imageBitmap = imageViews.get(position);
        meuViewHolderFoto.imageView.setImageBitmap(imageBitmap);

    }

    @Override
    public int getItemCount() {
        return imageViews.size();
    }
}
