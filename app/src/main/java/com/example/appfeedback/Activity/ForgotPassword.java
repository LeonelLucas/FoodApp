package com.example.appfeedback.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appfeedback.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
     EditText resetpass;
    Button resetpassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        resetpass = findViewById(R.id.resetPass);
        resetpassBtn = findViewById(R.id.resetButton);

        resetpassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = resetpass.getText().toString();
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if(email.isEmpty()){
                    Toast.makeText(ForgotPassword.this,"Email vázio", Toast.LENGTH_SHORT).show();
                }else{
                   auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(ForgotPassword.this,"Verifique seu email", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(getApplicationContext(),newAccount.class));
                               finish();
                           }else{
                               Toast.makeText(ForgotPassword.this,"Erro ao enviar email", Toast.LENGTH_SHORT).show();

                           }
                       }
                   });
                }
            }
        });

    }


}