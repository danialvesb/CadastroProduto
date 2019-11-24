package com.example.cadastroproduto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.example.cadastroproduto.adapters.AdaperFoto;
import com.example.cadastroproduto.adapters.AdapterProduto;
import com.example.cadastroproduto.adapters.ViewHolderFoto;
import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.service.ProdutoService;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import static android.Manifest.permission_group.CAMERA;

public class CadastroProdutosActivity extends AppCompatActivity{
    private static final int REQUEST_CAMERA = 1;
    private List<Bitmap> listImages = new ArrayList<>();
    private Produto produto = new Produto();
    private Bitmap imageBitmap;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);

        Toolbar toolbar = findViewById(R.id.toolbarCadastro);

        FloatingActionButton fabImage = findViewById(R.id.fabImage);
//        imageView = findViewById(R.id.imageView);
        recyclerView = findViewById(R.id.recyclerViewImagens);

        //Mascáras
        EditText data = findViewById(R.id.inputDtHora);
        EditText preco = findViewById(R.id.inputValor);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(data, smf);
        data.addTextChangedListener(mtw);

        SimpleMaskFormatter smf1 = new SimpleMaskFormatter("000.000.000.000.000,00");
        MaskTextWatcher mtw1 = new MaskTextWatcher(preco, smf1);
        preco.addTextChangedListener(mtw1);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle(getString(R.string.cadastroproduto));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

    }

    public void onclickSalvar(View view) {

        try {
            EditText nome = findViewById(R.id.inputNome);
            EditText data = findViewById(R.id.inputDtHora);
            EditText preco = findViewById(R.id.inputValor);
            TextInputEditText descricao = findViewById(R.id.inputDescricao);

            produto.setDtEntrada(data.getText().toString());
            produto.setNome(nome.getText().toString());
            String valor = preco.getText().toString();
            produto.setPreco(Double.parseDouble(valor));
            produto.setDescricao(descricao.getText().toString());


            if(imageBitmap != null)
                produto.addImagens(imageBitmap);

            ProdutoService.setProduto(produto);

            Toast.makeText(this, getString(R.string.gravado), Toast.LENGTH_LONG).show();
            mostrarMain();



        }catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    public void onClickImage(View view) {
        tirarFoto();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickVoltar(View view) {
        finish();
    }

    public void tirarFoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    private void mostrarMain() {
        Intent intent = new Intent(CadastroProdutosActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imagemBitmap = (Bitmap) extras.get("data");
            this.imageBitmap = imagemBitmap;
            this.listImages.add(imagemBitmap);

            if(listImages.size() <= 5) {
                recyclerView.setAdapter(new AdaperFoto(listImages));
                RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(layout);

            }



        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
