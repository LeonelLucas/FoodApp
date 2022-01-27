package com.example.appfeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appfeedback.R;
import com.google.firebase.auth.FirebaseAuth;

public class FeedUser extends AppCompatActivity {

    FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_user);

        fireAuth = FirebaseAuth.getInstance();

    }

    public void callComponents(View view){
        Intent intent = new Intent(FeedUser.this,CltUser.class);
        startActivity(intent);
    }

    public void callInfo(View view){
        Intent intent = new Intent(FeedUser.this,confEmp.class);
        startActivity(intent);
    }

    public  void chamarPedidos(View view ){
        Intent intent = new Intent(FeedUser.this,PedidosClt.class);
        startActivity(intent);
    }

    public void Logout(View view){
        fireAuth.signOut();

        Intent intent = new Intent(FeedUser.this,newAccount.class);
        startActivity(intent);

    }
}