package com.example.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.utils.MoedaUtil;

public class DetalheProdutoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_produto);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Produto produto = (Produto) getIntent().getSerializableExtra("produto");

        TextView textViewEan = findViewById(R.id.idEan);
        TextView textViewDescricao = findViewById(R.id.idDescricao);
        TextView textViewPcoVenda = findViewById(R.id.idPcoVenda);

//        textViewEan.setText("Código " + produto.getEan().toString());
        textViewDescricao.setText(produto.getDescricao() + " : ");
        textViewPcoVenda.setText("R$ " + MoedaUtil.moeda2Decimais(produto.get()));

        getSupportActionBar().setTitle(produto.getDescricao());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
