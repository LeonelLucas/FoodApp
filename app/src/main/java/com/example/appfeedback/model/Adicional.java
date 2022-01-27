package com.example.appfeedback.model;

import com.example.appfeedback.configuration.ConffireBase;
import com.google.firebase.database.DatabaseReference;

public class Adicional {

    String nome;

    String idUsuario;
    String idAdicional;

    public  Adicional(){
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference adicionalRef = firebaseRef
                .child("adicional");
        setIdAdicional(adicionalRef.push().getKey());
    }

    public void Salvar(){
        DatabaseReference firebaseRef  = ConffireBase.Database();
        DatabaseReference adicionalRef = firebaseRef.child("adicional")
                .child(getIdUsuario())
                .child(getIdAdicional());
        adicionalRef.setValue(this);

    }

    public void Remove(){
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference adicionalRef = firebaseRef.child("adicional")
                .child(getIdUsuario())
                .child(getIdAdicional());
        adicionalRef.removeValue();
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdAdicional() {
        return idAdicional;
    }

    public void setIdAdicional(String idAdicional) {
        this.idAdicional = idAdicional;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
