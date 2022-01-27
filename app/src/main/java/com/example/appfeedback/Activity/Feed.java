package com.example.appfeedback.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.appfeedback.R;
import com.example.appfeedback.adapter.AdapterEmpresa;
import com.example.appfeedback.adapter.AdapterProduto;
import com.example.appfeedback.configuration.ConffireBase;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.listeber.RecyclerItemClickListener;
import com.example.appfeedback.model.Empresa;
import com.example.appfeedback.model.Produto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

public class Feed extends AppCompatActivity {
    private AdapterEmpresa adapterEmpresa;
    private RecyclerView getRecyclerEmpresa;
    private List<Empresa> empresas = new ArrayList<>();
    private FirebaseAuth autentification;
    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;
    private MaterialSearchView matarialSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        inicializarComponentes();
        firebaseRef = ConffireBase.Database();
        autentification = ConffireBase.Auth();
        idUsuarioLogado = UsuarioFirebase.getidUsuario();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Restaurantes");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        getRecyclerEmpresa.setLayoutManager(new LinearLayoutManager(this));
        getRecyclerEmpresa.setHasFixedSize(true);
        adapterEmpresa = new AdapterEmpresa(empresas);
        getRecyclerEmpresa.setAdapter(adapterEmpresa);

        recuperarEmpresa();
        getRecyclerEmpresa.addOnItemTouchListener(new RecyclerItemClickListener(this, getRecyclerEmpresa, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Empresa empresaSelecionadas = empresas.get(position);
                Intent i = new Intent(Feed.this,Cardapios.class);
                i.putExtra("empresas", empresaSelecionadas);
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

       matarialSearch.setHint("Pesquisar empresa");
       matarialSearch.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                selecionarEmpresa(newText);
                return true;
            }
        });


    }

   public void selecionarEmpresa(String pesquisa){
        DatabaseReference empresaRef = firebaseRef.child("empresas");
        Query query = empresaRef.orderByChild("nome")
                .startAt(pesquisa)
                .endAt(pesquisa + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                empresas.clear();

                for(DataSnapshot ds: snapshot.getChildren()){
                    empresas.add(ds.getValue(Empresa.class));

                }
                adapterEmpresa.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.pesquisaempresa,menu);
        MenuItem item = menu.findItem(R.id.menuempresaSearch);
        matarialSearch.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

   



    private void recuperarEmpresa(){
        DatabaseReference empresaRef = firebaseRef.child("empresas");
        empresaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                empresas.clear();

                for(DataSnapshot ds: snapshot.getChildren()){
                    empresas.add(ds.getValue(Empresa.class));

                }
                adapterEmpresa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void inicializarComponentes(){
        getRecyclerEmpresa = findViewById(R.id.recyclerempresa);
        matarialSearch = findViewById(R.id.search_view);

    }
}