package com.example.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cadastroproduto.adapters.AdaperFoto;
import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.service.ProdutoService;
import com.example.cadastroproduto.utils.AlertUtil;
import com.example.cadastroproduto.utils.IAlertUtil;
import com.example.cadastroproduto.utils.MoedaUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetalheProdutoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Bitmap> bitmaps = new ArrayList<>();
    private List<Produto> listProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.detalhe_imagens);
        TextView preco = findViewById(R.id.detalhe_preco);
        TextView descricao = findViewById(R.id.detalhe_descricao);
        TextView dtEntrada = findViewById(R.id.detalhe_dt_entrada);
        TextView nome = findViewById(R.id.detalhe_nome);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        final Produto produto = (Produto) getIntent().getSerializableExtra("produto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bitmaps = produto.getImagens();

        String precoS = ""+produto.getPreco();
        preco.setText(precoS);
        descricao.setText(produto.getDescricao());
        dtEntrada.setText(produto.getDtEntrada());
        nome.setText(produto.getNome());



        if (bitmaps.size() > 0) {
            recyclerView.setAdapter(new AdaperFoto(bitmaps));
            RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layout);

        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    ProdutoService.deleteProduto(produto.getId());
                    Toast.makeText(DetalheProdutoActivity.this, "Produto excluído" ,Toast.LENGTH_LONG).show();

                    mostrarMain();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    private class TaskGetJsonServidor extends AsyncTask<String,Integer,List<Produto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private void setMensagemRetorno(boolean isAtualizou) {
            Intent intent = getIntent();
            intent.putExtra("mensagem", (isAtualizou ? "Sim" : "Não"));
            setResult(13, intent);
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



    public void clickAtualizar(View view) {
        AlertUtil.getConfirmDialog(this, AlertUtil.DDM, "Atualizar produtos ?", "Sim", "Não", false,
                new IAlertUtil() {
                    @Override
                    public void PositiveMethod(final DialogInterface dialog, final int id) {
                        DetalheProdutoActivity.TaskGetJsonServidor taskGetJsonServidor = new DetalheProdutoActivity.TaskGetJsonServidor();

                        taskGetJsonServidor.execute();  // Pode-se passar n argumentos para este método execute que serão recebidos por "String... params" de doInBackground
                    }
                    @Override
                    public void NegativeMethod(DialogInterface dialog, int id) {
                    }
                });
    }




    private void mostrarMain() {
        Intent intent = new Intent(DetalheProdutoActivity.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
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



}
