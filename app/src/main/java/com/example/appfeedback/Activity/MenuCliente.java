package com.example.appfeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appfeedback.R;

public class MenuCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);
    }

    public void CallFeed(View view){
        Intent intent = new Intent(MenuCliente.this,Feed.class);
        startActivity(intent);
    }

    public void CallConf(View view){
        Intent intent = new Intent(MenuCliente.this,confCliente.class);
        startActivity(intent);
    }
}