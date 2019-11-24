package com.example.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        Toolbar toolbar = findViewById(R.id.toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        getSupportActionBar().setTitle(getString(R.string.menu_sobre));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView textViewSite = findViewById(R.id.idCurso);
        textViewSite.setText(R.string.curso);

        TextView textViewNome = findViewById(R.id.idNome);
        textViewNome.setText(R.string.nome0);

        TextView textViewNome2 = findViewById(R.id.idNome2);
        textViewNome2.setText(R.string.nome01);

        TextView textViewProf = findViewById(R.id.idProfessor);
        textViewProf.setText(R.string.professor);

        TextView textViewVersao = findViewById(R.id.idVersao);
        textViewVersao.setText(" Versão " + MyApp.versionName(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
