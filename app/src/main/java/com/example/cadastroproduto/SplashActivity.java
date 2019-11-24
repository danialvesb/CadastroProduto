package com.example.cadastroproduto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        TextView textView = findViewById(R.id.idVersao);
        textView.setText("Versão " + MyApp.versionName(this));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Esse método é executado sempre que o timer acabar e inicia a activity principal
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // SplashActivity
            }
        }, SPLASH_TIME_OUT);
    }
}
