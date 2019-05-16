package com.appmoviles.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.appmoviles.proyecto.modelo.Cliente;
import com.appmoviles.proyecto.modelo.RolUsuario;
import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.AdapterTemplate_Clientes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClientesFragment extends Fragment {

    private RecyclerView libreta;
    private AdapterTemplate_Clientes adapter;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;


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

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        final ArrayList<Usuario> usuarios = new ArrayList<>();

        rtdb.getReference().child("usuarios")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Respuesta de firebase
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            //Si es admin, loguearse
                            Usuario usuario = hijo.getValue(Usuario.class);
                            adapter.agregarUsuario(usuario);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


        libreta = v.findViewById(R.id.lista_clientes);
        adapter = new AdapterTemplate_Clientes();
        libreta.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        libreta.setLayoutManager(manager);
        libreta.setAdapter(adapter);


        return v;
    }

}
