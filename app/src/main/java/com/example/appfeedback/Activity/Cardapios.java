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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.appfeedback.R;
import com.example.appfeedback.adapter.AdapterAdicional;
import com.example.appfeedback.adapter.AdapterProduto;
import com.example.appfeedback.configuration.ConffireBase;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.listeber.RecyclerItemClickListener;
import com.example.appfeedback.model.Adicional;
import com.example.appfeedback.model.Empresa;
import com.example.appfeedback.model.Itempedido;
import com.example.appfeedback.model.Pedidos;
import com.example.appfeedback.model.Produto;
import com.example.appfeedback.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class Cardapios extends AppCompatActivity {

    TextView textNomeEmpresaCardapio;
    private Empresa empresaSelecionada;

    //Variáveis da recycler do produto

    RecyclerView recyclerProduto;
    private AdapterProduto adapterProduto;
    private List<Produto> produtos = new ArrayList<>();
    private DatabaseReference firebaseRef;

    //Variáveis da recycler adicionais

    RecyclerView recyclerAdd;
    private AdapterAdicional adapterAdd;
    //private List<Adicional> adicionais = new ArrayList<>();

    //Empresa

    private String idEmpresa;

    //Lista para inserir produto

    private List<Itempedido> itensCarrinho = new ArrayList<>();


    //Recuperar dados

    private AlertDialog dialog;

    //
    private String idUsuarioLogado;
    private User usuario;


    private Pedidos pedidoRecuperado;

    private int metodoEntrega;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapios);

        inicializarComponetes();



        firebaseRef = ConffireBase.Database();
        idUsuarioLogado = UsuarioFirebase.getidUsuario();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            empresaSelecionada = (Empresa) bundle.getSerializable("empresas");

            textNomeEmpresaCardapio.setText(empresaSelecionada.getNome());
            idEmpresa = empresaSelecionada.getIdUsuario();
        }


        recyclerProduto.setLayoutManager(new LinearLayoutManager(this));
        recyclerProduto.setHasFixedSize(true);
        adapterProduto = new AdapterProduto(produtos, this );
        recyclerProduto.setAdapter(adapterProduto);

        //recyclerAdd.setLayoutManager(new LinearLayoutManager(this));
        //recyclerAdd.setHasFixedSize(true);
        //adapterAdd = new AdapterAdicional(adicionais, this);
        //recyclerAdd.setAdapter(adapterAdd);

       recuperarProdutos();
       //recuperarAdicionais();
       recuperarDadosUsuario();

       recyclerProduto.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerProduto, new RecyclerItemClickListener.OnItemClickListener() {
           @Override
           public void onItemClick(View view, int position) {

               confirmarProduto(position);
           }

           @Override
           public void onLongItemClick(View view, int position) {

           }

           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

           }
       }));


    }


    private void recuperarProdutos(){

        DatabaseReference produtosRef = firebaseRef.child("componente")
                .child(idEmpresa);
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

   /* private void recuperarAdicionais(){

        DatabaseReference produtosRef = firebaseRef.child("adicional")
                .child(idEmpresa);
        produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adicionais.clear();

                for(DataSnapshot ds: snapshot.getChildren()){
                    adicionais.add(ds.getValue(Adicional.class));

                }
                adapterAdd.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    */

    private void confirmarProduto( int posicao){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quantidade");
        builder.setMessage("Digite a quantidade");

        EditText editQauntidade = new EditText(this);
        editQauntidade.setText("1");

        builder.setView(editQauntidade);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String quantidade = editQauntidade.getText().toString();

                Produto produtoSelecionado = produtos.get(posicao);
                Itempedido itemPedido = new Itempedido();
                itemPedido.setIdProduto( produtoSelecionado.getIdProduto() );
                itemPedido.setNomeProduto( produtoSelecionado.getNome() );
                itemPedido.setQuantidade( Integer.parseInt(quantidade) );

                itensCarrinho.add( itemPedido );

                if( pedidoRecuperado == null ){
                    pedidoRecuperado = new Pedidos(idUsuarioLogado, idEmpresa);
                }

                pedidoRecuperado.setNome( usuario.getNome() );
                pedidoRecuperado.setEndereco( usuario.getEnderco() );
                pedidoRecuperado.setItens( itensCarrinho );
                pedidoRecuperado.salvar();
                Toast.makeText(Cardapios.this, "Itens salvos", Toast.LENGTH_SHORT).show();


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


    private void recuperarDadosUsuario(){

        dialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando ...")
                .setCancelable(false)
                .build();
        dialog.show();

        DatabaseReference usuariosRef = firebaseRef
                .child("clientes")
                .child(idUsuarioLogado);

        usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    usuario = snapshot.getValue(User.class);
                }
                recuperarPedido();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void recuperarPedido(){
        dialog.dismiss();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cardapio, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuPedido :
                confirmarPedido();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmar(View view){
        confirmarPedido();
    }


   private void confirmarPedido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecione o método para entrega");

        CharSequence [] itens = new CharSequence[]{
                "Entregar", "Buscar"
        };
        builder.setSingleChoiceItems(itens, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                metodoEntrega = i;

            }
        });

        EditText editObservacao = new EditText(this);
        editObservacao.setHint("Ex: Quero meu sanduíche sem milho");
        builder.setView(editObservacao);

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String observacao = editObservacao.getText().toString();
                pedidoRecuperado.setTipoEntrega(metodoEntrega);
                pedidoRecuperado.setObservacao(observacao);
                pedidoRecuperado.setStatus("confirmado");
                pedidoRecuperado.confirmar();
                pedidoRecuperado.remover();
                pedidoRecuperado = null;

            }
        });

        builder.setNegativeButton("Cancerlar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void inicializarComponetes(){
        textNomeEmpresaCardapio = findViewById(R.id.textNomeEmpresaCardapio);
        recyclerProduto = findViewById(R.id.recyclerProdutosCardapio);
        //recyclerAdd = findViewById(R.id.recyclerAdicionais);
    }

}