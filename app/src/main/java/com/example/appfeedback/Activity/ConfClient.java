package com.example.appfeedback.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appfeedback.R;
import com.example.appfeedback.configuration.ConffireBase;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.model.Empresa;
import com.example.appfeedback.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ConfClient extends AppCompatActivity {
    EditText name;
    EditText numero;
    EditText endereco;
    EditText bairro;
    private String idUsuarioLogado;
    private DatabaseReference firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_client);

        inicializarComponetes();
        firebaseRef = ConffireBase.Database();
        idUsuarioLogado = UsuarioFirebase.getidUsuario();
        recuperarInfo();

    }

    private void recuperarInfo(){
        DatabaseReference clienteRef = firebaseRef.child("clientes").child(idUsuarioLogado);
        clienteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    User user = snapshot.getValue(User.class);
                    name.setText(user.getNome());
                    numero.setText(user.getTelefone());
                    endereco.setText(user.getEnderco());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void salvaInfo(View view){
        String txtName = name.getText().toString();
        String txtNumber = numero.getText().toString();
        String txtEndereco = endereco.getText().toString();

        if(!txtName.isEmpty()){
            if(!txtNumber.isEmpty()){
                if(!txtEndereco.isEmpty()){
                        User user = new User();
                        user.setIdCliente(idUsuarioLogado);
                        user.setNome(txtName);
                        user.setTelefone(txtNumber);
                        user.setEnderco(txtEndereco);
                        user.Salvar();
                        finish();

                }else{
                    Toast.makeText(this, "Endereço vazio", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Número vazio", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Nome vazio", Toast.LENGTH_SHORT).show();
        }

    }

    public  void inicializarComponetes(){

        name = findViewById(R.id.editTextNome);
        numero =  findViewById(R.id.editTextTelefone);
        endereco = findViewById(R.id.editTextEndereco);
    }
}