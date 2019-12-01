package com.example.cadastroproduto;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroproduto.adapters.AdapterProduto;
import com.example.cadastroproduto.model.Produto;
import com.example.cadastroproduto.service.ProdutoService;
import com.example.cadastroproduto.utils.AlertUtil;
import com.example.cadastroproduto.utils.ConfigSharedPreferences;
import com.example.cadastroproduto.utils.IAlertUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    SearchView editsearch;
    List<Produto> listProdutos = new ArrayList<>();
    FloatingActionButton fab;

    RecyclerView recyclerView;
    AdapterProduto adapterProduto = new AdapterProduto(listProdutos);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //É criada uma thread para executar as requisições

        Toolbar toolbar =  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_full_name));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyApp.getContext(), CadastroProdutosActivity.class);
                startActivity(intent);

            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        buscaProdutosServidor(true);


        editsearch = findViewById(R.id.search);

        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                
                adapterProduto.getFilter().filter(text);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                adapterProduto.getFilter().filter(text);
                return true;
            }
        });

        if (listProdutos != null) {
            Toast.makeText(this, listProdutos.size() + " produtos cadastrados.", Toast.LENGTH_LONG).show();

            adapterProduto.setOnItemClickListener(new AdapterProduto.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    mostraDetalheProduto(listProdutos.get(position));
                }
            });

        }

            //Essa arqui está definindo se
        Boolean bFocoCpoPesquisa = ConfigSharedPreferences.getBoolean(this, "cfgFocoCpoPesquisa");

        //Definindo o comportamento do layout
        if (false)
            ((CoordinatorLayout.LayoutParams) fab.getLayoutParams()).gravity = Gravity.CENTER | Gravity.BOTTOM;
        else {
            recyclerView.requestFocus();
        }



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

            recyclerView.setAdapter(adapterProduto);

            RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layout);



        }
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
                    recyclerView.setAdapter(new AdapterProduto(listProdutos));
                    RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layout);
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





    private void mostraDetalheProduto(Produto produto) {
        Intent intent = new Intent(this, DetalheProdutoActivity.class);
        intent.putExtra("produto", produto);
        startActivity(intent);
    }





}
