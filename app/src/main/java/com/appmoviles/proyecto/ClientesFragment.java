package com.appmoviles.proyecto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appmoviles.proyecto.modelo.Cliente;

import java.util.ArrayList;

public class ClientesFragment extends Fragment {

    private RecyclerView libreta;
    private AdapterTemplate_Clientes adapter;
    private ArrayList<Cliente> clientes;

    public ClientesFragment() {
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


        clientes = new ArrayList<>();

        // Pruba de la vista de los clientes
        Cliente cliente_p_1 = new Cliente("Cristian");
        Cliente cliente_p_2 = new Cliente("Steven");
        Cliente cliente_p_3 = new Cliente("Alejandra");
        Cliente cliente_p_4 = new Cliente("Johan");
        Cliente cliente_p_5 = new Cliente("Toto");

        clientes.add(cliente_p_1);
        clientes.add(cliente_p_2);
        clientes.add(cliente_p_3);
        clientes.add(cliente_p_4);
        clientes.add(cliente_p_5);


        libreta = v.findViewById(R.id.lista_clientes);
        adapter = new AdapterTemplate_Clientes();
        libreta.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        libreta.setLayoutManager(manager);
        libreta.setAdapter(adapter);

        agregarClientes();

        return v;
    }

    private void agregarClientes() {
        for (Cliente cliente : clientes) {
            adapter.agregarPersona(cliente);
        }
    }
}
