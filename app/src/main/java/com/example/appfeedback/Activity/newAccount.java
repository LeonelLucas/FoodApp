package com.example.appfeedback.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfeedback.R;
import com.example.appfeedback.configuration.ConffireBase;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class newAccount extends AppCompatActivity {
    EditText emailtxt, passwordtxt;
    TextView redefinirSenha;
    Switch tipoAcesso, tipoUsuario;
    Button enter;
    LinearLayout linearTipoUsuario;
    FirebaseAuth autentification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);
        emailtxt = findViewById(R.id.createEmailLog);
        passwordtxt = findViewById(R.id.createPasswordLog);
        redefinirSenha = findViewById(R.id.forguetP);
        tipoAcesso = findViewById(R.id.switchid);
        tipoUsuario = findViewById(R.id.switchTipoUsuario);
        linearTipoUsuario = findViewById(R.id.linearTipoUsuario);
        enter = findViewById(R.id.createAccountbtnLog);

        autentification = ConffireBase.Auth();
       autentification.signOut();
        verificarUsuarioLogado();

        redefinirSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
            }
        });

        tipoAcesso.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){//empresa
                    linearTipoUsuario.setVisibility(View.VISIBLE);
                }else{//usuario
                    linearTipoUsuario.setVisibility(View.GONE);
                }
            }
        });

        //Ações ao clicar no botão
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailtxt.getText().toString();
                String password = passwordtxt.getText().toString();
                if(!email.isEmpty()){
                    if(!password.isEmpty()){
                        if(tipoAcesso.isChecked()){
                            autentification.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(newAccount.this,"Cadastro concluído", Toast.LENGTH_SHORT).show();
                                        String tipoUsuario = getTipoUsuario();
                                        UsuarioFirebase.atualizarTipoUsuario(tipoUsuario);
                                        abrirTelaPrincipal(tipoUsuario);
                                    } else {
                                        String exception = "";
                                        try {
                                            throw task.getException();
                                        }catch (FirebaseAuthWeakPasswordException e){
                                            exception = "Senha fraca";
                                        }catch (FirebaseAuthInvalidCredentialsException e){
                                            exception = "Email inválido";
                                        }catch (FirebaseAuthUserCollisionException e){
                                            exception = "Essa conta já existe";
                                        } catch (Exception e) {
                                            exception = "Ocorreu um erro ao cadastrar"+e.getMessage();
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(newAccount.this, exception, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{//login
                            autentification.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(newAccount.this, "Olá, seja bem vindo!", Toast.LENGTH_SHORT);
                                        String tipoUsuario = task.getResult().getUser().getDisplayName();
                                        abrirTelaPrincipal(tipoUsuario);
                                    }else{
                                        Toast.makeText(newAccount.this, "Senha ou email incorretos", Toast.LENGTH_SHORT);
                                    }
                                }
                            });
                        }
                    }else{
                        Toast.makeText(newAccount.this,"Senha vázia (;", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(newAccount.this,"Emai vázio (;", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void verificarUsuarioLogado(){
        FirebaseUser usuarioAtual = autentification.getCurrentUser();
        if(usuarioAtual != null){
            String tipoUsuario = usuarioAtual.getDisplayName();
            abrirTelaPrincipal(tipoUsuario);
        }
    }

    private String getTipoUsuario(){
        return tipoUsuario.isChecked() ?"Empresa"  : "Usuario";
    }


    public void abrirTelaPrincipal(String tipoUsuario) {
        if(tipoUsuario.equals("Empresa")){//empresa
            startActivity(new Intent(getApplicationContext(),FeedUser.class));//empresa
        }else{//usuario
            startActivity(new Intent(getApplicationContext(),MenuCliente.class));
        }

    }

}