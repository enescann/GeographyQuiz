package com.example.geographyquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        new Beklet().start();
    }
    private class Beklet extends Thread{
        @Override
        public void run() {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent); //ilgili Activity sayfasını açar
            finish(); //Activity sayfasını kalıcı olarak kapatır

        }
    }
}
