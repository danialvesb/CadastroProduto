package com.example.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cadastroproduto.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class CadastroProdutosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);


    }

    public void clickSalvar(View view) {
        try {
            View vNome = findViewById(R.id.inputNomeProdutoId);
            View vDtEntrada = findViewById(R.id.inputDtTimeId);
            View vPreco = findViewById(R.id.inputValorId);
            View vDescricao = findViewById(R.id.inputDescricaoProId);

            Produto produto = new Produto();
            produto.setNome(vNome.toString());
            produto.setDescricao(vDescricao.toString());
            produto.setPreco(Double.parseDouble(vPreco.toString()));


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
