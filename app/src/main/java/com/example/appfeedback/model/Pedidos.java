package com.example.appfeedback.model;

import com.example.appfeedback.configuration.ConffireBase;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.List;

public class Pedidos {

    private String idUsuario;
    private String idEmpresa;
    private String idPedido;
    private String nome;
    private String endereco;
    private List<Itempedido> itens;
    private String status = "pedendente";
    private int tipoEntrega;
    private String observacao;

    public Pedidos() {
    }

    public Pedidos(String idUs, String idEmp ) {
        setIdUsuario(idUs);
        setIdEmpresa(idEmp);

        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference pedidoRef = firebaseRef
                .child("pedidos_user")
                .child(idEmp)
                .child(idUs);
        setIdPedido(pedidoRef.push().getKey());
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference pedidoRef = firebaseRef
                .child("pedidos_user")
                .child(getIdEmpresa())
                .child(getIdUsuario());
        pedidoRef.setValue(this);

    }

    public void confirmar(){
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference pedidoRef = firebaseRef
                .child("pedidosConfirm")
                .child(getIdEmpresa())
                .child(getIdPedido());
        pedidoRef.setValue(this);

    }

    public void remover(){
        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference pedidoRef = firebaseRef
                .child("pedidos_user")
                .child(getIdEmpresa())
                .child(getIdUsuario());
        pedidoRef.removeValue();

    }

    public void atualizaStatus(){

        HashMap<String, Object> status = new HashMap<>();
        status.put("status", getStatus());

        DatabaseReference firebaseRef = ConffireBase.Database();
        DatabaseReference pedidoRef = firebaseRef
                .child("pedidosConfirm")
                .child(getIdEmpresa())
                .child(getIdPedido());
        pedidoRef.updateChildren(status);

    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Itempedido> getItens() {
        return itens;
    }

    public void setItens(List<Itempedido> itens) {
        this.itens = itens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(int tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
