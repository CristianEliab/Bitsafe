package com.appmoviles.proyecto.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.Cliente;
import com.appmoviles.proyecto.modelo.Usuario;

import java.util.ArrayList;


public class AdapterTemplate_SlClientes extends RecyclerView.Adapter<AdapterTemplate_SlClientes.CustomViewHolder> {

    //Los datos que vamos a mostrar (View)
    private ArrayList<Usuario> data;
    int index = -1;

    //Renglon y construccion
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_SlClientes() {
        data = new ArrayList<>();
    }

    @Override
    public AdapterTemplate_SlClientes.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sl_cliente_seleccion, parent, false);
        AdapterTemplate_SlClientes.CustomViewHolder vh = new AdapterTemplate_SlClientes.CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AdapterTemplate_SlClientes.CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_sl_datos_nombre)).setText(data.get(position).getNombre());
        holder.root.findViewById(R.id.ll_fragment_sl_datos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                listener.onItemClick(data.get(position));
                notifyDataSetChanged();
            }
        });
        if (index == position) {
            holder.root.findViewById(R.id.ll_fragment_sl_datos).setBackgroundResource(R.drawable.fragment_datos_cuentas_bordes);
        } else {
            holder.root.findViewById(R.id.ll_fragment_sl_datos).setBackgroundResource(R.drawable.fragment_clientes_bordes_registros);
        }
    }

    //OBSERVER
    public interface OnItemClickListener {
        void onItemClick(Usuario usuario);
    }

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarUsuario(Usuario usuario) {
        data.add(usuario);
        notifyDataSetChanged();
    }
}
