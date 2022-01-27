package com.example.appfeedback.model;

import com.example.appfeedback.configuration.ConffireBase;
import com.google.firebase.database.DatabaseReference;

public class Itempedido {

    private String idProduto;
    private String nomeProduto;
    private int quantidade;

    public Itempedido() {
    }

    public void Salvar(){
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference userRef = firebaseRef
                .child("pedidoItens")
                .child(getIdProduto());
        userRef.setValue(this);
    }


    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
