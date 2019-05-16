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


public class SeleccionarClienteOrigenFragment extends Fragment implements AdapterTemplate_SlClientes.OnItemClickListener {

    private ImageView fb_fragment_sl_cliente_agregar_cliente;
    private RecyclerView libreta;
    private AdapterTemplate_SlClientes adapter;
    private ArrayList<Cliente> clientes;
    private DatosClienteFragment datosClienteFragment;
    private Usuario cliente;
    private ImageView iv_fragment_sl_clientes_perfil;

    private ImageView iv_fragment_sl_clientes_return;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;
    private Fragment fragment;


    public SeleccionarClienteOrigenFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            fragment = (Fragment) getArguments().getSerializable(Constantes.TRANSACCIONES);
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

        rtdb.getReference().child(Constantes.CHILD_USUARIOS)
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
                datosClienteFragment = new DatosClienteFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                Bundle parametro = new Bundle();
                parametro.putSerializable(Constantes.TRANSACCIONES_DATOS, (TransaccionesFragment) fragment);
                datosClienteFragment.setArguments(parametro);
                transaction.replace(R.id.contenido, datosClienteFragment);
                transaction.commit();
            }
        });

        fb_fragment_sl_cliente_agregar_cliente = v.findViewById(R.id.fb_fragment_sl_cliente_agregar_cliente);
        fb_fragment_sl_cliente_agregar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datosClienteFragment = new DatosClienteFragment();
                //listener.onPassClickClienteOrigen(cliente);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Utilizado para enviar variables entre dos fragments
                Bundle parametro = new Bundle();
                if (cliente != null) {
                    parametro.putString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE, cliente.getNombre());
                    parametro.putSerializable(Constantes.TRANSACCIONES_DATOS, (TransaccionesFragment) fragment);
                    datosClienteFragment.setArguments(parametro);
                }
                transaction.replace(R.id.contenido, datosClienteFragment);
                transaction.commit();
            }
        });

        return v;
    }


    @Override
    public void onItemClick(Usuario amigo) {
        cliente = amigo;
        if (cliente != null) {
            fb_fragment_sl_cliente_agregar_cliente.setEnabled(true);
            fb_fragment_sl_cliente_agregar_cliente.setBackgroundResource(R.drawable.fragment_cliente_circular_seleccion);
        } else {
            fb_fragment_sl_cliente_agregar_cliente.setEnabled(false);
            fb_fragment_sl_cliente_agregar_cliente.setBackgroundResource(R.drawable.fragment_cliente_circular);
        }
    }


    //OBSERVER
    public interface OnItemPassClienteOrigen {
        void onPassClickClienteOrigen(String monto);
    }

    private OnItemPassClienteOrigen listener;

    public void setListener(OnItemPassClienteOrigen listener) {
        this.listener = listener;
    }
}
