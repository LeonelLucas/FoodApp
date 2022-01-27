package com.example.appfeedback.model;

import com.example.appfeedback.configuration.ConffireBase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;


public class Empresa implements Serializable {
    private String IdUsuario;
    private String nome;
    private String tempo;
    private String categoria;
    private Double precoEntrega;


    public Empresa() {
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public void Salvar(){
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference empresaRef = firebaseRef.child("empresas")
                .child(getIdUsuario());
        empresaRef.setValue(this);
    }
    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        IdUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public Double getPrecoEntrega() {
        return precoEntrega;
    }

    public void setPrecoEntrega(Double precoEntrega) {
        this.precoEntrega = precoEntrega;
    }
}
