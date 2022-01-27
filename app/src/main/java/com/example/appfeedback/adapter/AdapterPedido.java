package com.example.appfeedback.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfeedback.R;
import com.example.appfeedback.model.Itempedido;
import com.example.appfeedback.model.Pedidos;

import java.util.ArrayList;
import java.util.List;





public class AdapterPedido extends RecyclerView.Adapter<AdapterPedido.MyViewHolder> {


    private List<Pedidos> pedidos;

    public AdapterPedido(List<Pedidos> pedidos) {
        this.pedidos = pedidos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pedidos, parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {

        Pedidos pedido = pedidos.get(i);
        holder.nome.setText( pedido.getNome() );
        holder.endereco.setText( "Endereço: "+pedido.getEndereco() );
        holder.observacao.setText( "Obs: "+ pedido.getObservacao() );

        List<Itempedido> itens = new ArrayList<>();
        itens = pedido.getItens();
        String descricaoItens = "";

        int numeroItem = 1;

        for( Itempedido itemPedido : itens ){

            int qtde = itemPedido.getQuantidade();


            String nome = itemPedido.getNomeProduto();
            descricaoItens += numeroItem + ") " + nome + " / (" + qtde + ") \n";
            numeroItem++;
        }

        holder.itens.setText(descricaoItens);

        int metodoEntrega = pedido.getTipoEntrega();
        String entrega = metodoEntrega == 0 ? "Entregar" : "Buscar" ;
        holder.entrega.setText( "Entrega: " + entrega );

    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nome;
        TextView endereco;
        TextView entrega;
        TextView observacao;
        TextView itens;

        public MyViewHolder(View itemView) {
            super(itemView);

            nome        = itemView.findViewById(R.id.textPedidoNome);
            endereco    = itemView.findViewById(R.id.textPedidoEndereco);
            entrega        = itemView.findViewById(R.id.textPedidoEntrega);
            observacao  = itemView.findViewById(R.id.textPedidoObs);
            itens       = itemView.findViewById(R.id.textPedidoItens);
        }
    }

}
