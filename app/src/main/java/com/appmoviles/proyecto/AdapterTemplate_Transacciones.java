package com.appmoviles.proyecto;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cliente;
import com.appmoviles.proyecto.modelo.Transaccion;

import java.util.ArrayList;

public class AdapterTemplate_Transacciones extends RecyclerView.Adapter<AdapterTemplate_Transacciones.CustomViewHolder> {

    ArrayList<Transaccion> data;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_Transacciones(){
        data = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.renglon_transaccion, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_renglon_transaccion_monto)).setText(data.get(position).getMontoTransaccion());
        ((TextView) holder.root.findViewById(R.id.tv_renglon_transaccion_categoria)).setText(data.get(position).getCategoriaID());
        ((TextView) holder.root.findViewById(R.id.tv_renglon_transaccion_descripcion)).setText(data.get(position).getDescripcion());
        //((ImageView) holder.root.findViewById(R.id.iv_renglon_transaccion_imagen_banco)).setImageIcon(data.get(position).getNota_2());
        ((TextView) holder.root.findViewById(R.id.tv_renglon_transaccion_fecha)).setText(data.get(position).getFechaTransaccion());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarTransaccion(Transaccion transaccion){
        data.add(transaccion);
        notifyDataSetChanged();
    }

}
