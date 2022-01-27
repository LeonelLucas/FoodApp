package com.example.appfeedback.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;


import com.example.appfeedback.R;
import com.example.appfeedback.adapter.AdapterAdicional;
import com.example.appfeedback.configuration.ConffireBase;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.listeber.RecyclerItemClickListener;
import com.example.appfeedback.model.Adicional;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class verAdicional extends AppCompatActivity {
    private RecyclerView recyclerAdicional;
    private AdapterAdicional adapterAdicional;
    private List<Adicional> adicionais  = new ArrayList<>();
    private FirebaseAuth autentification;
    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_adicional);

        autentification = ConffireBase.Auth();
        firebaseRef = ConffireBase.Database();
        idUsuarioLogado = UsuarioFirebase.getidUsuario();

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Adicionais");
        setSupportActionBar(toolbar);

        recyclerAdicional.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdicional.setHasFixedSize(true);
        adapterAdicional = new AdapterAdicional(adicionais, this);
        recyclerAdicional.setAdapter(adapterAdicional);

        recuperarAdicional();

        recyclerAdicional.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerAdicional, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {

                Adicional adicionalSeleted = adicionais.get(position);
                adicionalSeleted.Remove();
                Toast.makeText(verAdicional.this, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

    }

    private void recuperarAdicional(){

        DatabaseReference adicionalRef = firebaseRef.child("adicional")
                .child(idUsuarioLogado);

        adicionalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adicionais.clear();

                for(DataSnapshot ds: snapshot.getChildren()){
                    adicionais.add(ds.getValue(Adicional.class));
                }
                adapterAdicional.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void inicializarComponentes(){
        recyclerAdicional = findViewById(R.id.RecyclerAdicional);
    }
}