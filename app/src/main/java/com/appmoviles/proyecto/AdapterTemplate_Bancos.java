package com.appmoviles.proyecto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Banco;

import java.util.ArrayList;

public class AdapterTemplate_Bancos extends RecyclerView.Adapter<AdapterTemplate_Bancos.CustomViewHolder> {

    ArrayList<Banco> data;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_Bancos() {
        data = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_banco, parent, false);
        AdapterTemplate_Bancos.CustomViewHolder vh = new AdapterTemplate_Bancos.CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterTemplate_Bancos.CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_renglon_banco_nombre)).setText(data.get(position).getNombreBanco());
        ((TextView) holder.root.findViewById(R.id.tv_renglon_banco_monto)).setText(data.get(position).getBancoID());
        holder.root.findViewById(R.id.rl_renglon_relative_marco_banco).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(data.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarBanco(Banco banco) {
        data.add(banco);
        notifyDataSetChanged();
    }

    //Patrón observer para identifcar cual transacción se seleccionó

    public interface OnItemClickListener {
        void onItemClick(Banco banco);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    //Hasta aquí patrón observer
}