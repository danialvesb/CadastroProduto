package com.example.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;




import com.example.cadastroproduto.model.Produto;

public class CadastroProdutosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);

        Toolbar toolbar = findViewById(R.id.toolbarCadastro);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle(getString(R.string.cadastroproduto));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void clickSalvar(View view) {
        try {
            View vNome = findViewById(R.id.inputNome);
            View vDtEntrada = findViewById(R.id.inputDtHora);
            View vPreco = findViewById(R.id.inputValor);
//            View vDescricao = findViewById(R.id.inputDescricaoProId);

            Produto produto = new Produto();
            produto.setNome(vNome.toString());
        //    produto.setDescricao(vDescricao.toString());
            produto.setPreco(Double.parseDouble(vPreco.toString()));


        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
