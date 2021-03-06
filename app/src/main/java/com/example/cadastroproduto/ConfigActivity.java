package com.example.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import com.example.cadastroproduto.utils.ConfigSharedPreferences;

public class ConfigActivity extends AppCompatActivity {
    private EditText editTextServidorIP;
    private EditText editTextServidorPorta;
    private Switch switchFocoCpoPesquisa;
    private Switch switchSenhaAcessoCfg;
    private EditText editTextSenhaAdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Toolbar toolbar = findViewById(R.id.toolbarConfig);


        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle(getString(R.string.menu_configuracao));



        //Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String sServidorIP = ConfigSharedPreferences.getString(this, "cfgServidorIP");
        String sServidorPorta = ConfigSharedPreferences.getString(this, "cfgServidorPorta");

        Boolean bFocoCpoPesquisa = ConfigSharedPreferences.getBoolean(this, "cfgFocoCpoPesquisa");
        Boolean bSenhaAcessoCfg = ConfigSharedPreferences.getBoolean(this, "cfgSenhaAcessoCfg");
        String sSenhaAdm = ConfigSharedPreferences.getString(this, "cfgSenhaAdm");

        editTextServidorIP = findViewById(R.id.idServidorIP);
        editTextServidorIP.setText(sServidorIP);

        editTextServidorPorta = findViewById(R.id.id_porta);
        editTextServidorPorta.setText(sServidorPorta);


        switchFocoCpoPesquisa = findViewById(R.id.idFocoCpoPesquisa);
        switchFocoCpoPesquisa.setChecked(bFocoCpoPesquisa);

        switchSenhaAcessoCfg = findViewById(R.id.idSenhaAcessoCfg);
        switchSenhaAcessoCfg.setChecked(bSenhaAcessoCfg);

        editTextSenhaAdm = findViewById(R.id.idSenha);
        editTextSenhaAdm.setText(sSenhaAdm);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickGravar(View view) {
        // Ao criar uma nova configuração do tipo boolean a mesma é adicionada com valor default true

        String sServidorIP = editTextServidorIP.getText().toString();
        String sServidorPorta = editTextServidorPorta.getText().toString();
        Boolean bFocoCpoPesquisa = switchFocoCpoPesquisa.isChecked();
        Boolean bSenhaAcessoCfg = switchSenhaAcessoCfg.isChecked();
        String sSenhaAdm = editTextSenhaAdm.getText().toString();

        ConfigSharedPreferences.setString(this, "cfgServidorIP", sServidorIP);
        ConfigSharedPreferences.setString(this, "cfgServidorPorta", sServidorPorta);
        ConfigSharedPreferences.setBoolean(this, "cfgFocoCpoPesquisa", bFocoCpoPesquisa);
        ConfigSharedPreferences.setBoolean(this, "cfgSenhaAcessoCfg", bSenhaAcessoCfg);
        ConfigSharedPreferences.setString(this, "cfgSenhaAdm", sSenhaAdm);

        Toast.makeText(this, getString(R.string.gravado), Toast.LENGTH_LONG).show();
        finish();
    }
}