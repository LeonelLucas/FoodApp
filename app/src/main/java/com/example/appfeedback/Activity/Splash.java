package com.example.appfeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.appfeedback.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Responsável por criar delay da splash quando iniciada
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showSplash();
            }
        }, 3000);

    }

    //Metodo para chamar a classe de login
    private void showSplash(){
            Intent intent = new Intent(Splash .this, newAccount.class);
            startActivity(intent);
    }
}