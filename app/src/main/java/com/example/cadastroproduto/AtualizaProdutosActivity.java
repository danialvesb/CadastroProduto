package com.example.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.service.ProdutoService;
import com.example.cadastroproduto.utils.AlertUtil;
import com.example.cadastroproduto.utils.ConfigSharedPreferences;
import com.example.cadastroproduto.utils.IAlertUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AtualizaProdutosActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private List<Produto> listProdutos;
    private TextView textViewMsgmAtz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualiza_produtos);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewDtUltAtz = findViewById(R.id.idDtUltAtz);
        String cfgDtUltAtz = ConfigSharedPreferences.getString(MyApp.getContext(), "cfgDtUltAtz");

        if (cfgDtUltAtz != null && !cfgDtUltAtz.isEmpty())
            textViewDtUltAtz.setText(cfgDtUltAtz);
        else
            textViewDtUltAtz.setText(R.string.msgmdtultatz);

        textViewMsgmAtz = findViewById(R.id.idMsgmAtz);
        textViewMsgmAtz.setTextColor(Color.parseColor("#FF851109"));  // Vermelho escuro
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

    //Não pode efetuar requisições com base na activity principal é necessário criar uma thread para não ocorrer erro
    private class TaskGetJsonServidor extends AsyncTask<String,Integer,List<Produto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = findViewById(R.id.progressBar);
            progressBar.setVisibility(ProgressBar.VISIBLE);
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
            progressBar.setVisibility(ProgressBar.GONE);
            finish();
        }
    }

    public void clickAtualizar(View view) {
        AlertUtil.getConfirmDialog(this, AlertUtil.DDM, "Atualizar produtos ?", "Sim", "Não", false,
                new IAlertUtil() {
                    @Override
                    public void PositiveMethod(final DialogInterface dialog, final int id) {
                        TaskGetJsonServidor taskGetJsonServidor = new TaskGetJsonServidor();
                        taskGetJsonServidor.execute();  // Pode-se passar n argumentos para este método execute que serão recebidos por "String... params" de doInBackground
                    }
                    @Override
                    public void NegativeMethod(DialogInterface dialog, int id) {
                    }
                });
    }

}
