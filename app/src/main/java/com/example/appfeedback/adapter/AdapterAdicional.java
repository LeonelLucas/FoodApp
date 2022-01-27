package com.example.appfeedback.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfeedback.R;
import com.example.appfeedback.model.Adicional;

import java.util.List;

public class AdapterAdicional extends RecyclerView.Adapter<AdapterAdicional.MyViewHolder> {

    private List<Adicional> adicionais;
    private Context context;

    public AdapterAdicional(List<Adicional> adicionais, Context contex) {
        this.adicionais = adicionais;
        this.context = contex;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapteradicional, parent, false);
        return new AdapterAdicional.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAdicional.MyViewHolder holder, int i) {
        Adicional adicionai = adicionais.get(i);
        holder.nomeAdicional.setText(adicionai.getNome());
    }

    @Override
    public int getItemCount() {
        return adicionais.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView nomeAdicional;


        public MyViewHolder(View itemView) {
            super(itemView);

            nomeAdicional = itemView.findViewById(R.id.textNomeAdicional);

        }
    }


}
