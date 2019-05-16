package com.appmoviles.proyecto.util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.Cliente;
import com.appmoviles.proyecto.modelo.Cuenta;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterDatosCuentas extends RecyclerView.Adapter<AdapterDatosCuentas.CustomViewHolder> implements Serializable {

    private ArrayList<Cuenta> data;
    int index = -1;

    //Renglon y construccion
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterDatosCuentas() {
        this.data = new ArrayList<>();
    }


    @Override
    public AdapterDatosCuentas.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_list, parent, false);
        AdapterDatosCuentas.CustomViewHolder vh = new AdapterDatosCuentas.CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AdapterDatosCuentas.CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_datos_lista)).setText(data.get(position).getNumeroCuenta());
        holder.root.findViewById(R.id.ll_datos_lista).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                listener.onItemClick(data.get(position));
                notifyDataSetChanged();
            }
        });

        if (index == position) {
            holder.root.findViewById(R.id.ll_datos_lista).setBackgroundResource(R.drawable.fragment_datos_cuentas_bordes);
        } else {
            holder.root.findViewById(R.id.ll_datos_lista).setBackgroundResource(R.drawable.fragment_clientes_bordes_registros);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //OBSERVER
    public interface OnItemClickListener {
        void onItemClick(Cuenta amigo);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void agregarCuenta(Cuenta cuenta) {
        data.add(cuenta);
        notifyDataSetChanged();
    }

}
