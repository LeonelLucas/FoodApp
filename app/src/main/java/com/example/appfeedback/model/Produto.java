package com.example.appfeedback.model;

import com.example.appfeedback.configuration.ConffireBase;
import com.google.firebase.database.DatabaseReference;

public class Produto {
  private  String  idUsuario, nome, descricao, idProduto;



    public Produto() {
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference produtoRef = firebaseRef
                .child("componente");
        setIdProduto(produtoRef.push().getKey());
    }

    public String getIdUsuario() {
        return idUsuario;
    }
    public void Salvar(){

        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference produtoRef = firebaseRef
                .child("componente")
                .child(getIdUsuario())
                .child(getIdProduto());
        produtoRef.setValue(this);

    }

    public void Remove(){
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference produtoRef = firebaseRef
                .child("componente")
                .child(getIdUsuario())
                .child(getIdProduto());
        produtoRef.removeValue();

    }


    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
