package com.appmoviles.proyecto.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.Banco;

import java.util.ArrayList;

public class AdapterTemplate_Bancos extends RecyclerView.Adapter<AdapterTemplate_Bancos.CustomViewHolder> {

    //Los datos que vamos a mostrar (View)
    private ArrayList<Banco> data;

    //Renglon y construccion
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_Bancos(){
        data = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_clientes_bancos, parent, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        ((ImageView) customViewHolder.root.findViewById(R.id.iv_cliente_banco_icon)).setVisibility(View.VISIBLE);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarBanco(Banco banco){
        data.add(banco);
        notifyDataSetChanged();
    }
}
