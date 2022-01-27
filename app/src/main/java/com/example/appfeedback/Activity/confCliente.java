package com.example.appfeedback.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appfeedback.R;
import com.example.appfeedback.configuration.ConffireBase;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class confCliente extends AppCompatActivity {
    TextView txtName, txtCell, txtAdress;

    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_cliente);

        firebaseRef = ConffireBase.Database();
        idUsuarioLogado = UsuarioFirebase.getidUsuario();

        componentes();
        RecuperarDados();

    }

    public void callEdit(View view ){
        Intent intent = new Intent(confCliente.this,ConfClient.class);
        startActivity(intent);

    }

    private void  RecuperarDados() {
        DatabaseReference clienteRef = firebaseRef.child("clientes").child(idUsuarioLogado);
        clienteRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null) {
                    User user = snapshot.getValue(User.class);
                    txtName.setText(user.getNome());
                    txtCell.setText(user.getTelefone());
                    txtAdress.setText(user.getEnderco());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void componentes (){
        txtName = findViewById(R.id.textViewName);
        txtCell = findViewById(R.id.textViewCell);
        txtAdress = findViewById(R.id.textViewAdress);
    }
}