package com.example.cadastroproduto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.service.ProdutoService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;



import static android.Manifest.permission_group.CAMERA;

public class CadastroProdutosActivity extends AppCompatActivity{
    private static final int REQUEST_CAMERA = 1;
    private List<ImageView> listImages;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);

        ImageButton btAdicionar = findViewById(R.id.fab2);
        final EditText nome = findViewById(R.id.inputNome);
        final EditText descricao = findViewById(R.id.inputDescricao);
        final EditText dtHora = findViewById(R.id.inputDtHora);

        Toolbar toolbar = findViewById(R.id.toolbarCadastro);

        FloatingActionButton fabImage = findViewById(R.id.fabImage);
//        imageView = findViewById(R.id.imageView);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle(getString(R.string.cadastroproduto));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

        //        SALVAR
        btAdicionar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Produto produto = new Produto();
                produto.setDtEntrada(dtHora.getText().toString());
                produto.setNome(nome.getText().toString());
                produto.setDescricao(descricao.getText().toString());

                try {
                    ProdutoService.setProdutos(produto);
                } catch (IOException e) {
                    Log.e("daniel", e.getMessage(), e);
                }
            }
        });
    }

    public void onClickImage(View view) {
        tirarFoto();
    }

    public void tirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imagemBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imagemBitmap);



        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
