package com.appmoviles.proyecto.util;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Usuario;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class AdapterTemplate_SlClientes extends RecyclerView.Adapter<AdapterTemplate_SlClientes.CustomViewHolder> implements Filterable {

    //Los datos que vamos a mostrar (View)
    private ArrayList<Usuario> data;
    int index = -1;
    CustomFilter filtro;
    public ArrayList<Usuario> dataFiltro;
    FirebaseStorage storage;

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

    public AdapterTemplate_SlClientes() {
        data = new ArrayList<>();
        dataFiltro = new ArrayList<>();
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


        storage = FirebaseStorage.getInstance();
        StorageReference ref = storage.getReference().child(Constantes.CHILD_IMAGENES_PERFIL).child(data.get(position).getTelefono());
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                ImageView img = holder.root.findViewById(R.id.iv_sl_datos_foto);
                Glide.with(holder.root.getContext()).load(uri).into(img);
            }
        });
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
