package com.appmoviles.proyecto.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cliente;

import java.util.ArrayList;

public class AdapterTemplate_Clientes extends RecyclerView.Adapter<AdapterTemplate_Clientes.CustomViewHolder> {

    //Los datos que vamos a mostrar (View)
    private ArrayList<Cliente> data;
    private LinearLayoutManager manage;
    private AdaptadorIconsBancos adaptadorIconsBancos;
    private ArrayList<Banco> listaBancos;

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
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_cliente_registro, parent, false);
        manage = new LinearLayoutManager(parent.getContext(), LinearLayoutManager.HORIZONTAL, false);
        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ((TextView) holder.root.findViewById(R.id.tv_cliente_registro_nombre)).setText(data.get(position).nombre);
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setHasFixedSize(true);
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setLayoutManager(this.manage);
        adaptadorIconsBancos = new AdaptadorIconsBancos();
        ((RecyclerView) holder.root.findViewById(R.id.lista_bancos_icons)).setAdapter(adaptadorIconsBancos);

        listaBancos = new ArrayList<Banco>();
        Banco b1 = new Banco();
        b1.setNombreBanco("Bancolombia");
        b1.setIcono("banco_davivienda");
        Banco b2 = new Banco();
        b2.setNombreBanco("Banco Bogota");
        b2.setIcono("banco_bogota");
        Banco b3 = new Banco();
        b3.setNombreBanco("Davivienda");
        b3.setIcono("banco_bancolombia");
        listaBancos.add(b1);
        listaBancos.add(b2);
        listaBancos.add(b3);
        listaBancos.add(b3);
        listaBancos.add(b3);

        agregarIconos();

    }

    private void agregarIconos() {
        for (Banco banco : listaBancos) {
            adaptadorIconsBancos.agregarBanco(banco);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void agregarCliente(Cliente cliente) {
        data.add(cliente);
        notifyDataSetChanged();
    }
}
