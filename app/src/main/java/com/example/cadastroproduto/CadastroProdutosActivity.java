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
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cadastroproduto.adapters.AdaperFoto;
import com.example.cadastroproduto.adapters.AdapterProduto;
import com.example.cadastroproduto.adapters.ViewHolderFoto;
import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.service.ProdutoService;
import com.example.cadastroproduto.utils.AlertUtil;
import com.example.cadastroproduto.utils.DateUtil;
import com.example.cadastroproduto.utils.IAlertUtil;
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
    private ProgressBar progressBar;
    private List<Produto> listProdutos;
    private TextView textViewMsgmAtz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);
        final Produto produto = (Produto) getIntent().getSerializableExtra("produto");
        Toolbar toolbar = findViewById(R.id.toolbarCadastro);

        recyclerView = findViewById(R.id.recyclerViewImagens);
        EditText nome = findViewById(R.id.inputNome);
        EditText preco = findViewById(R.id.inputValor);
        TextInputEditText descricao = findViewById(R.id.inputDescricao);

        if (produto != null) {
            if (produto.getNome() != null)
                nome.setText(produto.getNome());

            if (produto.getPreco() != null)
                preco.setText(produto.getPreco().toString());

            if (produto.getDescricao() != null)
                descricao.setText(produto.getDescricao());

//            if (produto.getImagens() != null)
//                recyclerView.setAdapter(new AdaperFoto(produto.getImagens()));
//                RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//                recyclerView.setLayoutManager(layout);

        }


        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (produto != null)
            getSupportActionBar().setTitle(getString(R.string.editar_produto));

        getSupportActionBar().setTitle(getString(R.string.cadastroproduto));



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }

    }

    public void onclickSalvar(View view) {
        EditText nome = findViewById(R.id.inputNome);
        EditText preco = findViewById(R.id.inputValor);
        TextInputEditText descricao = findViewById(R.id.inputDescricao);
        final Produto produto = (Produto) getIntent().getSerializableExtra("produto");

        try {
                this.produto.setNome(nome.getText().toString());
                String valor = preco.getText().toString();
                this.produto.setPreco(Double.parseDouble(valor));
                this.produto.setDescricao(descricao.getText().toString());


                if(imageBitmap != null)
                    this.produto.addImagens(imageBitmap);

                if (produto == null) {
                    ProdutoService.setProduto(this.produto);
                    Intent intent = new Intent(CadastroProdutosActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    this.produto.setId(produto.getId());
                    this.produto.setDtEntrada(produto.getDtEntrada());
                    this.produto.setDtSaida(produto.getDtSaida());

                    ProdutoService.setProduto(this.produto);
                    clickAtualizar(this.produto);
                }


        }catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            setMensagemRetorno(false);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setMensagemRetorno(boolean isAtualizou) {
        Intent intent = getIntent();
        intent.putExtra("mensagem", (isAtualizou ? "Sim" : "Não"));
        setResult(13, intent);
    }


    private class TaskGetJsonServidor extends AsyncTask<String,Integer,List<Produto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Produto> doInBackground(String... params) {
            listProdutos = new ArrayList<>();
            listProdutos = null;

            try {
                listProdutos = ProdutoService.getProdutos(true);

            } catch (IOException e) {
                listProdutos = null;
            }

            return listProdutos;
        }

        @Override
        protected void onPostExecute(List<Produto> listProdutos) {
            setMensagemRetorno(listProdutos != null);
            finish();
        }
    }

    public void clickAtualizar(Produto produtoa) {
        AlertUtil.getConfirmDialog(this, AlertUtil.DDM, "Editar produto?", "Sim", "Não", false,
                new IAlertUtil() {
                    @Override
                    public void PositiveMethod(final DialogInterface dialog, final int id) {
                        TaskGetJsonServidor taskGetJsonServidor = new TaskGetJsonServidor();

                        Intent intent = new Intent(MyApp.getContext(), DetalheProdutoActivity.class);
                        intent.putExtra("produto", produto);
                        startActivity(intent);

                        taskGetJsonServidor.execute();  // Pode-se passar n argumentos para este método execute que serão recebidos por "String... params" de doInBackground
                    }
                    @Override
                    public void NegativeMethod(DialogInterface dialog, int id) {
                    }
                });
    }



}
