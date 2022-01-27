package com.example.appfeedback.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.appfeedback.R;
import com.example.appfeedback.adapter.AdapterPedido;
import com.example.appfeedback.adapter.AdapterProduto;
import com.example.appfeedback.configuration.ConffireBase;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.listeber.RecyclerItemClickListener;
import com.example.appfeedback.model.Pedidos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class PedidosClt extends AppCompatActivity {

    private RecyclerView  recyclerPedidos;
    private AdapterPedido adapterPedido;
    private List<Pedidos> pedidos = new ArrayList<>();
    private AlertDialog dialog;
    private DatabaseReference firebaseRef;
    private  String idEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_clt);

        Toolbar toolbar = findViewById(R.id.toolbarcl);
        toolbar.setTitle("Pedidos");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        inicializarComponentes();
        firebaseRef = ConffireBase.Database();
        idEmpresa = UsuarioFirebase.getidUsuario();

        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));
        recyclerPedidos.setHasFixedSize(true);
        adapterPedido = new AdapterPedido(pedidos);
        recyclerPedidos.setAdapter(adapterPedido);

        recyclerPedidos.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerPedidos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                imprimir(position);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Pedidos pedido = pedidos.get(position);
                pedido.setStatus("finalizado");
                pedido.atualizaStatus();

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

        recuperarPedido();
    }

    private void imprimir(int posicao){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Imprimir pedido");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void recuperarPedido() {
        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando ...")
                .setCancelable(false)
                .build();
        dialog.show();

        DatabaseReference pedidoRef =  firebaseRef
                .child("pedidosConfirm")
                .child(idEmpresa);
        Query pedidoPesquisa = pedidoRef.orderByChild("status")
                .equalTo("confirmado");
        pedidoPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pedidos.clear();
                if(snapshot.getValue() != null){
                    for(DataSnapshot ds: snapshot.getChildren()){
                        Pedidos pedido = ds.getValue(Pedidos.class);
                        pedidos.add(pedido);
                    }
                    adapterPedido.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void inicializarComponentes() {
        recyclerPedidos = findViewById(R.id.recyclerPedido);
    }
}