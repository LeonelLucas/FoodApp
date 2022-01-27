package com.example.appfeedback.model;

import com.example.appfeedback.configuration.ConffireBase;
import com.google.firebase.database.DatabaseReference;

public class User {
    String idCliente;
    String nome;
    String email;
    String password;
    String telefone;
    String enderco;
    String bairro;

    public void Salvar(){
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference userRef = firebaseRef
                .child("clientes")
                .child(getIdCliente());
        userRef.setValue(this);
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEnderco() {
        return enderco;
    }

    public void setEnderco(String enderco) {
        this.enderco = enderco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
