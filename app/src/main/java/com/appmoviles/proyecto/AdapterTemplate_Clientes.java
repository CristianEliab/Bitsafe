package com.appmoviles.proyecto;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Cliente;

import java.util.ArrayList;

public class AdapterTemplate_Clientes extends RecyclerView.Adapter<AdapterTemplate_Clientes.CustomViewHolder>{

    //Los datos que vamos a mostrar (View)
    private ArrayList<Cliente> data;

    //Renglon y construccion
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_Clientes(){
        data = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cliente_registro, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ((TextView) holder.root.findViewById(R.id.tv_cliente_registro_nombre)).setText(data.get(position).nombre);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarPersona(Cliente cliente){
        data.add(cliente);
        notifyDataSetChanged();
    }
}
