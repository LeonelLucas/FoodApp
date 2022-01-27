package com.example.appfeedback.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appfeedback.R;
import com.example.appfeedback.helper.UsuarioFirebase;
import com.example.appfeedback.model.Empresa;
import com.example.appfeedback.model.Produto;
import com.google.firebase.auth.FirebaseAuth;

public class AddComponente extends AppCompatActivity {
    EditText editProdutoNome, editProdutoDescricao, editPreco;
    String idUsuarioLogado;
    private FirebaseAuth autentificação;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_componente);

        iniciaComponentes();
        idUsuarioLogado = UsuarioFirebase.getidUsuario();

    }

    public void validarDadosProdutos(View view) {
        String nomeComponente = editProdutoNome.getText().toString();
        String descricao = editProdutoDescricao.getText().toString();

        if (!nomeComponente.isEmpty()) {
            if (!descricao.isEmpty()) {

                Produto produto = new Produto();
                produto.setIdUsuario(idUsuarioLogado);
                produto.setNome(nomeComponente);
                produto.setDescricao(descricao);

                produto.Salvar();
                finish();
                exibirMensagem("Componente salvo");

            } else {
                exibirMensagem("Digite um nome do componente");
            }
            } else {
                exibirMensagem("Digite um seguimento para a empresa");
            }

    }

    private void exibirMensagem(String texto) {
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    public  void iniciaComponentes(){
        editProdutoNome = findViewById(R.id.editProdutoNome);
        editProdutoDescricao = findViewById(R.id.editProdutoDescricao);
        
    }

}