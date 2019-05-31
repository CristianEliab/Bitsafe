package com.appmoviles.proyecto.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Usuario;

import java.io.Serializable;
import java.util.ArrayList;

public class AdapterTemplate_Clientes extends RecyclerView.Adapter<AdapterTemplate_Clientes.CustomViewHolder> implements Serializable, Filterable {

    //Los datos que vamos a mostrar (View)
    public ArrayList<Usuario> data;
    public ArrayList<Usuario> dataFiltro;
    private LinearLayoutManager manage;
    private AdaptadorIconsBancos adaptadorIconsBancos;
    CustomFilter filtro;
    int index = -1;

    @Override
    public Filter getFilter() {
        if (filtro == null) {
            filtro = new CustomFilter();
        }

        return filtro;
    }


    //Renglon y construccion
    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;

        public CustomViewHolder(LinearLayout v) {
            super(v);
            root = v;
        }
    }

    public AdapterTemplate_Clientes() {
        data = new ArrayList<>();
        dataFiltro = new ArrayList<>();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cliente_registro, parent, false);
        manage = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        ((TextView) holder.root.findViewById(R.id.tv_cliente_registro_nombre)).setText(data.get(position).getNombre());
        LinearLayoutManager manager = new LinearLayoutManager(holder.root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setLayoutManager(manager);
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setHasFixedSize(true);
        adaptadorIconsBancos = new AdaptadorIconsBancos();
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setAdapter(adaptadorIconsBancos);

        holder.root.findViewById(R.id.ll_fragment_clientes_registro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                listener.onItemUsuario(data.get(position));
                notifyDataSetChanged();
            }
        });

        ArrayList<Banco> list = data.get(position).getListaBancos();
        if (list != null) {
            for (Banco banco : list) {
                adaptadorIconsBancos.agregarBanco(banco);
            }
        }
    }

    //OBSERVER
    public interface OnItemClickUsuario {
        void onItemUsuario(Usuario usuario);
    }

    private OnItemClickUsuario listener;

    public void setListener(OnItemClickUsuario listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarUsuario(Usuario usuario) {
        data.add(usuario);
        dataFiltro.add(usuario);
        notifyDataSetChanged();
    }

    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults resulst = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<Usuario> filtro = new ArrayList<>();
                for (Integer i = 0; i < dataFiltro.size(); i++) {
                    if (dataFiltro.get(i).getNombre().toUpperCase().contains(constraint)) {
                        Usuario udataFiltro = dataFiltro.get(i);
                        filtro.add(udataFiltro);
                    }
                }
                resulst.count = filtro.size();
                resulst.values = filtro;
            } else {
                resulst.count = dataFiltro.size();
                resulst.values = dataFiltro;
            }

            return resulst;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data = (ArrayList<Usuario>) results.values;
            notifyDataSetChanged();

        }
    }
}
