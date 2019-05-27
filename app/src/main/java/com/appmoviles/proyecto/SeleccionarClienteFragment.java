package com.appmoviles.proyecto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appmoviles.proyecto.modelo.Cliente;
import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.AdapterTemplate_SlClientes;
import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


public class SeleccionarClienteFragment extends Fragment implements AdapterTemplate_SlClientes.OnItemClickListener {

    private ImageView fb_fragment_sl_cliente_agregar_cliente;
    private RecyclerView libreta;
    private AdapterTemplate_SlClientes adapter;
    private DatosClienteFragment datosClienteFragment;
    private Usuario origen;
    private Usuario destino;
    private String tipo_usuario;
    private ImageView iv_fragment_sl_clientes_perfil;

    private ImageView iv_fragment_sl_clientes_return;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;
    private Fragment fragment;


    public SeleccionarClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            fragment = (Fragment) getArguments().getSerializable(Constantes.TRANSACCIONES);
            tipo_usuario = getArguments().getString(Constantes.USUARIO);
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_seleccionar_cliente_origen, container, false);
        iv_fragment_sl_clientes_perfil = v.findViewById(R.id.iv_fragment_sl_clientes_perfil);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        libreta = v.findViewById(R.id.lista_sl_clientes);
        adapter = new AdapterTemplate_SlClientes();
        libreta.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        libreta.setLayoutManager(manager);
        libreta.setAdapter(adapter);
        adapter.setListener(this);

        rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID)
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


        iv_fragment_sl_clientes_return = v.findViewById(R.id.iv_fragment_sl_clientes_return);
        iv_fragment_sl_clientes_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido, fragment);
                transaction.commit();
            }
        });

        fb_fragment_sl_cliente_agregar_cliente = v.findViewById(R.id.fb_fragment_sl_cliente_agregar_cliente);
        fb_fragment_sl_cliente_agregar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datosClienteFragment = new DatosClienteFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Bundle parametro = new Bundle();
                if (tipo_usuario.equals(Constantes.USUARIO_ORIGEN)) {
                    parametro.putSerializable(Constantes.USUARIO_SERIALIZABLE, origen);
                    parametro.putString(Constantes.USUARIO, Constantes.USUARIO_ORIGEN);
                    parametro.putSerializable(Constantes.TRANSACCIONES_DATOS, (TransaccionesFragment) fragment);
                    parametro.putSerializable(Constantes.DONDE_VIENE, Constantes.TRANSACCIONES_DATOS);
                    datosClienteFragment.setInteractionListener((OnFragmentInteractionListener) fragment);
                    datosClienteFragment.setArguments(parametro);
                }
                if (tipo_usuario.equals(Constantes.USUARIO_DESTINO)) {
                    parametro.putSerializable(Constantes.USUARIO_SERIALIZABLE, destino);
                    parametro.putString(Constantes.USUARIO, Constantes.USUARIO_DESTINO);
                    parametro.putSerializable(Constantes.TRANSACCIONES_DATOS, (TransaccionesFragment) fragment);
                    parametro.putSerializable(Constantes.DONDE_VIENE, Constantes.TRANSACCIONES_DATOS);
                    datosClienteFragment.setInteractionListener((OnFragmentInteractionListener) fragment);
                    datosClienteFragment.setArguments(parametro);
                }
                transaction.replace(R.id.contenido, datosClienteFragment);
                transaction.commit();
            }
        });

        return v;
    }


    @Override
    public void onItemClick(Usuario usuario) {
        if (tipo_usuario.equals(Constantes.USUARIO_ORIGEN)) {
            origen = usuario;
        }
        if (tipo_usuario.equals(Constantes.USUARIO_DESTINO)) {
            destino = usuario;
        }
        if (usuario != null) {
            fb_fragment_sl_cliente_agregar_cliente.setEnabled(true);
            fb_fragment_sl_cliente_agregar_cliente.setBackgroundResource(R.drawable.fragment_cliente_circular_seleccion);
        } else {
            fb_fragment_sl_cliente_agregar_cliente.setEnabled(false);
            fb_fragment_sl_cliente_agregar_cliente.setBackgroundResource(R.drawable.fragment_cliente_circular);
        }
    }
}
