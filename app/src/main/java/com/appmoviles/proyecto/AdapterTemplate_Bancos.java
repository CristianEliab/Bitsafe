package com.appmoviles.proyecto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Banco;

import java.util.ArrayList;

import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_BANCOLOMBIA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_BOGOTA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_DAVIVIENDA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_ITAU;

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
        ((TextView) holder.root.findViewById(R.id.tv_renglon_banco_monto)).setText(data.get(position).getSaldo());

        if(data.get(position).getIcono().equals(ICON_BANCO_DAVIVIENDA)){
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_banco_imagen)).setBackgroundResource(R.drawable.banco_davivienda);
        }
        else if(data.get(position).getIcono().equals(ICON_BANCO_BOGOTA)){
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_banco_imagen)).setBackgroundResource(R.drawable.banco_bogota);
        }
        else if(data.get(position).getIcono().equals(ICON_BANCO_BANCOLOMBIA)){
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_banco_imagen)).setBackgroundResource(R.drawable.banco_bancolombia);
        }
        else if(data.get(position).getIcono().equals(ICON_BANCO_ITAU)){
            ((ImageView) holder.root.findViewById(R.id.iv_renglon_banco_imagen)).setBackgroundResource(R.drawable.banco_itau);
        }

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