package com.appmoviles.proyecto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.util.AdapterTemplate_Clientes;

import java.util.ArrayList;

public class ClientesBancos extends Fragment {

    private RecyclerView lista_clientes_view;
    private AdapterTemplate_Clientes adapter;
    private ArrayList<Banco> bancos;

    public ClientesBancos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_clientes, container, false);


        bancos = new ArrayList<>();

        /**

         bancos.add(cliente_p_1);
         bancos.add(cliente_p_2);
         bancos.add(cliente_p_3);
         bancos.add(cliente_p_4);
         bancos.add(cliente_p_5);

         adapter = new AdapterTemplate_Bancos();
         lista_clientes_view = v.findViewById(R.id.lista_clientes);
         lista_clientes_view.setHasFixedSize(true);
         LinearLayoutManager manager = new LinearLayoutManager(getContext());
         lista_clientes_view.setLayoutManager(manager);
         lista_clientes_view.setAdapter(adapter);

         agregarBancos();
         *
         */

        return v;
    }

    //private void agregarBancos() {
    //  for (Banco banco:bancos){
    //    adapter.agregarPersona(banco);
    //}
    //}
}
