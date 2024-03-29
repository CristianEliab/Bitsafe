package com.appmoviles.proyecto.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.Banco;

import java.util.ArrayList;

import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_BANCOLOMBIA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_BOGOTA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_DAVIVIENDA;
import static com.appmoviles.proyecto.util.Constantes.ICON_BANCO_ITAU;

public class AdaptadorIconsBancos extends RecyclerView.Adapter<AdaptadorIconsBancos.CustomViewHolder>{

    private ArrayList<Banco> data;
    int index = -1;

    //Renglon y construccion
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdaptadorIconsBancos() {
        this.data = new ArrayList<>();
    }

    @Override
    public AdaptadorIconsBancos.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cliente_renglon_bancos, parent, false);
        AdaptadorIconsBancos.CustomViewHolder vh = new AdaptadorIconsBancos.CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AdaptadorIconsBancos.CustomViewHolder holder, final int position) {
        if(data.get(position).getIcono().equals(ICON_BANCO_DAVIVIENDA)){
            ((ImageView) holder.root.findViewById(R.id.iv_icon_bancos)).setBackgroundResource(R.drawable.banco_davivienda);
        }
        else if(data.get(position).getIcono().equals(ICON_BANCO_BOGOTA)){
            ((ImageView) holder.root.findViewById(R.id.iv_icon_bancos)).setBackgroundResource(R.drawable.banco_bogota);
        }
        else if(data.get(position).getIcono().equals(ICON_BANCO_BANCOLOMBIA)){
            ((ImageView) holder.root.findViewById(R.id.iv_icon_bancos)).setBackgroundResource(R.drawable.banco_bancolombia);
        }
        else if(data.get(position).getIcono().equals(ICON_BANCO_ITAU)){
            ((ImageView) holder.root.findViewById(R.id.iv_icon_bancos)).setBackgroundResource(R.drawable.banco_itau);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarBanco(Banco banco) {
        data.add(banco);
        notifyDataSetChanged();
    }

    public boolean contains(Banco banco){
        return data.contains(banco);
    }

}
