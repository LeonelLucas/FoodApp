package com.example.appfeedback.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.appfeedback.adapter.AdapterProduto;
import com.example.appfeedback.configuration.ConffireBase;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.listeber.RecyclerItemClickListener;
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

public class CltUser extends AppCompatActivity {
    private RecyclerView recyclerProdutos;
    private AdapterProduto adapterProduto;
    private List<Produto> produtos = new ArrayList<>();
    private FirebaseAuth autentification;
    private DatabaseReference firebaseRef;
    private String idUsuarioLogado;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clt_user);

        autentification = ConffireBase.Auth();
        firebaseRef = ConffireBase.Database();
        idUsuarioLogado = UsuarioFirebase.getidUsuario();

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Componentes");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        recyclerProdutos.setLayoutManager(new LinearLayoutManager(this));
        recyclerProdutos.setHasFixedSize(true);
        adapterProduto = new AdapterProduto(produtos, this );
        recyclerProdutos.setAdapter(adapterProduto);

        recuperarProdutos();
        //Click
        recyclerProdutos.addOnItemTouchListener(new RecyclerItemClickListener(this,
                recyclerProdutos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {
                Produto produtoSeleted = produtos.get(position);
                produtoSeleted.Remove();
                Toast.makeText(CltUser.this, "Componente excluído com sucesso", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

        searchView.setHint("Produtos");
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               selecionarProduto(newText);
                return true;
            }
        });
    }

    private void  selecionarProduto(String pesquisa){
        DatabaseReference produtosRef = firebaseRef.child("componente").child(idUsuarioLogado);
        Query query = produtosRef.orderByChild("nome")
                .startAt(pesquisa)
                .endAt(pesquisa + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                produtos.clear();

                for(DataSnapshot ds: snapshot.getChildren()){
                    produtos.add(ds.getValue(Produto.class));

                }
                adapterProduto.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void inicializarComponentes(){
        recyclerProdutos = findViewById(R.id.RecyclerProdutos);
        searchView = findViewById(R.id.search_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_empres,menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }

    private void recuperarProdutos(){

       DatabaseReference produtosRef = firebaseRef.child("componente").child(idUsuarioLogado);
       produtosRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               produtos.clear();

               for(DataSnapshot ds: snapshot.getChildren()){
                   produtos.add(ds.getValue(Produto.class));

               }
               adapterProduto.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuNovoProduto:
                //abrir
               callAddComponente();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void callAddComponente(){
        Intent intent = new Intent(CltUser.this,AddComponente.class);
        startActivity(intent);
    }

  /*  public void callAddAdicional(){
        Intent intent = new Intent(CltUser.this,AdicionalAct.class);
        startActivity(intent);
    }

    public void callAdicional(){
        Intent intent = new Intent(CltUser.this,verAdicional.class);
        startActivity(intent);
    }
   */

}