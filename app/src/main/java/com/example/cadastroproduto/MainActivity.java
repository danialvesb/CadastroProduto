package com.example.cadastroproduto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.service.ProdutoService;
import com.example.cadastroproduto.utils.AlertUtil;
import com.example.cadastroproduto.utils.ConfigSharedPreferences;
import com.example.cadastroproduto.utils.IAlertUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.CircularBorderDrawable;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import android.support.v7.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {
    ListView list;
    ArrayAdapter<Produto> adapter;
    SearchView editsearch;
    List<Produto> listProdutos = new ArrayList<>();
    FloatingActionButton fab;
    Integer inputTypeEditSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //É criada uma thread para executar as requisições

        Toolbar toolbar =  findViewById(R.id.toolbar);
        Toolbar barraInferiorEsquerda = findViewById(R.id.barraInferiorEsquerda);
        Toolbar barraInferiorDireita = findViewById(R.id.barraInferiorDireita);

//        ((CoordinatorLayout.LayoutParams) barraInferiorEsquerda.getLayoutParams()).gravity = CircularBorderDrawable.resolveOpacity(13,3);

        //comentei isso pq estava fazendo o app parar de  funcionar
        setSupportActionBar(toolbar);
//        getSupportActionBar().setLogo(R.drawable.logoddmprecobar);
        getSupportActionBar().setTitle(getString(R.string.app_full_name));


        //Outro erro que estava ocorrendo, eu comentei essa linha para não ter scan, mas comentei o fab também, e ele ficou null,
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
//                startActivityForResult(intent, 14);
                Intent intent = new Intent(MyApp.getContext(), CadastroProdutosActivity.class);
                startActivity(intent);

                // Snackbar.make(view, "Leitor de código barras nã o disponível nesta versão.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        list = findViewById(R.id.listview);
        buscaProdutosServidor(false);
        list.setOnItemClickListener(this);
        editsearch = findViewById(R.id.search);


        if (listProdutos != null) {
            editsearch.setOnQueryTextListener(this);
            Toast.makeText(this, listProdutos.size() + " produtos cadastrados.", Toast.LENGTH_LONG).show();
        }


            //Essa arqui está definindo se
        Boolean bFocoCpoPesquisa = ConfigSharedPreferences.getBoolean(this, "cfgFocoCpoPesquisa");

        //Definindo o comportamento do layout
        if (bFocoCpoPesquisa)
            ((CoordinatorLayout.LayoutParams) fab.getLayoutParams()).gravity = Gravity.CENTER | Gravity.BOTTOM;
        else {
            list.requestFocus();
        }

        inputTypeEditSearch = editsearch.getInputType();

        if (inputTypeEditSearch == null) {
            inputTypeEditSearch = InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS;
        }

        editsearch.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View view, boolean has_focus) {
                if (has_focus) {
                    ((CoordinatorLayout.LayoutParams) fab.getLayoutParams()).gravity = Gravity.CENTER | Gravity.BOTTOM;
                } else {
                    ((CoordinatorLayout.LayoutParams) fab.getLayoutParams()).gravity = Gravity.CENTER | Gravity.BOTTOM;
                }
            }
        });
    }  // onCreate

    //Forçar atualização fica null pq é o usuário que vai atualizar eu acho
    private void buscaProdutosServidor(boolean bForcarAtualizacao) {
        listProdutos = null;

        try {
            listProdutos = ProdutoService.getProdutos(bForcarAtualizacao);
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.erroobterprodutos) + ":\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        if (listProdutos != null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listProdutos);
            list.setAdapter(adapter);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (listProdutos.contains(query)) {
            adapter.getFilter().filter(query);
        } else {
            if (adapter.getCount() == 1) {
                Produto produto = (Produto) adapter.getItem(0);
                mostraDetalheProduto(produto);
            } else {
                Toast.makeText(MainActivity.this, adapter.getCount() + " produtos com esta descrição.\n" +
                        this.getResources().getString(R.string.cliqueprodutodesejadoverdetalhes),Toast.LENGTH_LONG).show();
            }
        }

        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);

        if (newText.isEmpty()) {
            editsearch.setInputType(inputTypeEditSearch);
        } else {
            if (newText.substring(0, 1).matches("[0-9]")) {
//                editsearch.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else {
//                editsearch.setInputType(inputTypeEditSearch);
            }
        }

        return true;
    }


    //criando o menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_atualiza_produtos) {
            Intent intent = new Intent(this, AtualizaProdutosActivity.class);
            startActivityForResult(intent, 13);
            return true;
        }

        if (id == R.id.menu_configuracao) {
            Boolean bSenhaAcessoCfg = ConfigSharedPreferences.getBoolean(this, "cfgSenhaAcessoCfg");

            if (bSenhaAcessoCfg) {
                AlertUtil.getSenhaDialog(this, AlertUtil.DDM, "Digite sua senha", "Ok", "Cancela", false,
                        new IAlertUtil() {
                            @Override
                            public void PositiveMethod(final DialogInterface dialog, final int id) {
                                if (id == 1) {  // id == 1 significa que a senha digitada confere
                                    abreConfiguracoes();
                                } else {
                                    Toast.makeText(MainActivity.this, R.string.senhaincorreta, Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void NegativeMethod(DialogInterface dialog, int id) {}
                        });
            } else {
                abreConfiguracoes();
            }

            return true;
        }

        if (id == R.id.menu_sobre) {
            Intent intent = new Intent(this, SobreActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.menu_sair) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void abreConfiguracoes() {
        Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int codigo, int resultado, Intent it) {
        if (codigo == 13) {  // AtualizaProdutosActivity
            String resposta = it.getStringExtra("mensagem");

            if (resposta.equals("Sim")) {
                try {
                    listProdutos = ProdutoService.getListaProdutosConfiguracao();
                } catch (IOException e) {
                    String mensagem = this.getBaseContext().getResources().getString(R.string.erroobterprodutos) + ".\n" + e.getMessage();
                    AlertUtil.showOKDialog(this, AlertUtil.DDM, mensagem);
                }

                if (listProdutos != null) {
                    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listProdutos);
                    list.setAdapter(adapter);
                    Toast.makeText(this, R.string.produtosatzsucesso, Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, R.string.atznaorealizada, Toast.LENGTH_LONG).show();
            }
        } else {
            if (codigo == 14) {  // ScanActivity
                String resposta = it.getStringExtra("mensagem");

                if (!resposta.isEmpty()) {
                    editsearch.setQuery(resposta, false);
                }
            } else {
                AlertUtil.showOKDialog(this, AlertUtil.DDM, this.getBaseContext().getResources().getString(R.string.erroreturntelaatz));
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertUtil.getConfirmDialog(this, AlertUtil.DDM, "Encerrar o aplicativo ?", "Sim", "Não", false,
                new IAlertUtil() {
                    @Override
                    public void PositiveMethod(final DialogInterface dialog, final int id) {
                        finish();
                    }
                    @Override
                    public void NegativeMethod(DialogInterface dialog, int id) {
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int idx, long id) {
        Produto produto = (Produto) adapterView.getAdapter().getItem(idx);
        mostraDetalheProduto(produto);
    }

    private void mostraDetalheProduto(Produto produto) {
        Intent intent = new Intent(this, DetalheProdutoActivity.class);
        intent.putExtra("produto", produto);
        startActivity(intent);
    }
}
