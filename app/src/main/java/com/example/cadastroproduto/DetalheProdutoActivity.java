package com.example.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
    Produto produto = new Produto();


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

        final Long idDoProduto = (Long) getIntent().getSerializableExtra("Idproduto");



        try {
            produto = ProdutoService.getProduto(idDoProduto);
            this.produto = produto;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle("Detalhe Produto");

        bitmaps = produto.getImagens();

        preco.setText(MoedaUtil.moeda2Decimais(produto.getPreco()));

        descricao.setText(produto.getDescricao());
        dtEntrada.setText(produto.getDtEntrada());
        nome.setText(produto.getNome());



        if (bitmaps.size() > 0) {
            recyclerView.setAdapter(new AdaperFoto(bitmaps));
            RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layout);

        }
    }

    public void clickDeletar(View view) {
        AlertUtil.getConfirmDialog(this, AlertUtil.DDM, "Deletar produto ?", "Sim", "Não", false,
                new IAlertUtil() {
                    @Override
                    public void PositiveMethod(DialogInterface dialog, int id) {
                                try {
                                    final Long idDoProduto = (Long) getIntent().getSerializableExtra("Idproduto");

                                    ProdutoService.deleteProduto(idDoProduto);
                                    mostrarMain();

                                }catch (Exception e) {
                                    e.printStackTrace();
                                }

                    }

                    @Override
                    public void NegativeMethod(DialogInterface dialog, int id) {

                    }
                });
        }

    public void clickEditar(View view) {

        mostrarCadastroDeProdutos(this.produto);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void mostrarCadastroDeProdutos(Produto produto) {
        Intent intent = new Intent(DetalheProdutoActivity.this, CadastroProdutosActivity.class);
        intent.putExtra("Idproduto", produto.getId());
        startActivity(intent);
        TaskGetJsonServidor taskGetJsonServidor = new TaskGetJsonServidor();
        taskGetJsonServidor.execute();
        finish();


    }

    private void mostrarMain() {
        Intent intent = new Intent(DetalheProdutoActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, R.string.produto_deletado, Toast.LENGTH_LONG).show();
    }

    private void setMensagemRetorno(boolean isAtualizou) {
        Intent intent = getIntent();
        intent.putExtra("mensagem", (isAtualizou ? "Sim" : "Não"));
        setResult(13, intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mostrarMain();
    }

    private class TaskGetJsonServidor extends AsyncTask<String,Integer,Produto> {
        final Long idDoProduto = (Long) getIntent().getSerializableExtra("Idproduto");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Produto doInBackground(String... params) {
            produto = new Produto();
            produto = null;

            try {

                produto = ProdutoService.getProduto(idDoProduto);


            } catch (IOException e) {
                produto = null;
            }

            return produto;
        }

        @Override
        protected void onPostExecute(Produto produto) {
            setMensagemRetorno(produto != null);
            finish();
        }
    }


}
