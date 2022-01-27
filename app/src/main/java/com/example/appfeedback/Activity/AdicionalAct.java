package com.example.appfeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appfeedback.R;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.model.Adicional;
import com.google.firebase.auth.FirebaseAuth;

public class AdicionalAct extends AppCompatActivity {

   // EditText Txtnome;
  //  String idUsuarioLogado;
   // private FirebaseAuth autentificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicional);

        //iniciarComponentes();
     //   idUsuarioLogado = UsuarioFirebase.getidUsuario();
    }

  /*  public void validarAdicional(View view){
        String nome = Txtnome.getText().toString();
        if(!nome.isEmpty()){
            Adicional adicional = new Adicional();
            adicional.setIdUsuario(idUsuarioLogado);
            adicional.setNome(nome);
            adicional.Salvar();
            exibirMensagem("Salvo com sucesso! (:");
        } else{

            exibirMensagem("Por favor insira o nome do componente adicional!");

        }
    }

    private void exibirMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    public void iniciarComponentes(){
        Txtnome = findViewById(R.id.editAdicional);
    }

   */
}