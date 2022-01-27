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
import com.example.appfeedback.model.Empresa;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class confEmp extends AppCompatActivity {
    TextView txtName, txtType, txtTime, txtMoney;

    private DatabaseReference firebaseRef;
    private String idUsuarioLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_emp);

        firebaseRef = ConffireBase.Database();
        idUsuarioLog = UsuarioFirebase.getidUsuario();

        componentes();
        RecuperarDados();
    }

    public void CallEdit(View view){
        Intent intent = new Intent(confEmp.this, ConfUser.class);
        startActivity(intent);
    }

    private void RecuperarDados(){
        DatabaseReference empresaRef = firebaseRef.child("empresas").child(idUsuarioLog);
        empresaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    Empresa empresa = snapshot.getValue(Empresa.class);
                    txtName.setText(empresa.getNome());
                    txtType.setText(empresa.getCategoria());
                    txtTime.setText(empresa.getTempo());
                    txtMoney.setText(empresa.getPrecoEntrega().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void componentes(){
        txtName = findViewById(R.id.textViewNameEnte);
        txtType = findViewById(R.id.textViewTypeEn);
        txtTime = findViewById(R.id.textViewTime);
        txtMoney = findViewById(R.id.textViewMoney);
    }
}