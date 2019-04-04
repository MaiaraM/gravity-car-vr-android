package br.com.galaxyware.androidcar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarInicial();
            }
        }, 2000);
    }

    private void mostrarInicial() {
        Intent intent = new Intent(SplashScreen.this,
                MainActivity.class);
        startActivity(intent);
        finish();
    }

}
