package com.example.alpe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity{
    private static final int SPLASH_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Inicio.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIMEOUT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
