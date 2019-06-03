package com.appmoviles.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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


public class SeleccionarClienteFragment extends Fragment implements AdapterTemplate_SlClientes.OnItemClickListener, View.OnClickListener {

    private ImageView fb_fragment_sl_cliente_agregar_cliente;
    private RecyclerView libreta;
    private AdapterTemplate_SlClientes adapter;
    private DatosClienteFragment datosClienteFragment;
    private Usuario origen;
    private Usuario destino;
    private String tipo_usuario;
    private ImageView iv_fragment_sl_clientes_perfil;

    private ImageView iv_fragment_sl_clientes_return;
    private EditText et_fragment_sl_clientes_filtro;

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

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (getArguments() != null) {
            fragment = (Fragment) getArguments().getSerializable(Constantes.TRANSACCIONES);
            tipo_usuario = getArguments().getString(Constantes.USUARIO);
        }
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_seleccionar_cliente_origen, container, false);
        iv_fragment_sl_clientes_perfil = v.findViewById(R.id.iv_fragment_sl_clientes_perfil);
        et_fragment_sl_clientes_filtro = v.findViewById(R.id.et_fragment_sl_clientes_filtro);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        libreta = v.findViewById(R.id.lista_sl_clientes);
        adapter = new AdapterTemplate_SlClientes();
        libreta.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        libreta.setLayoutManager(manager);
        libreta.setAdapter(adapter);
        adapter.setListener(this);

<<<<<<< HEAD
=======

>>>>>>> origin/master
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

        et_fragment_sl_clientes_filtro.setOnClickListener(this);

        iv_fragment_sl_clientes_return = v.findViewById(R.id.iv_fragment_sl_clientes_return);
<<<<<<< HEAD
        iv_fragment_sl_clientes_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (fragment == null) {
                    TransaccionesFragment transaccionesFragment = new TransaccionesFragment();
                    transaction.replace(R.id.contenido, transaccionesFragment);
                    transaction.commit();
                } else {
                    transaction.replace(R.id.contenido, fragment);
                    transaction.commit();
                }

            }
        });
=======
        iv_fragment_sl_clientes_return.setOnClickListener(this);
>>>>>>> origin/master

        fb_fragment_sl_cliente_agregar_cliente = v.findViewById(R.id.fb_fragment_sl_cliente_agregar_cliente);
        fb_fragment_sl_cliente_agregar_cliente.setOnClickListener(this);

        iv_fragment_sl_clientes_perfil.setOnClickListener(this);


        iv_fragment_sl_clientes_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.DATOS_FRAGMENT);
                i.putExtra(Constantes.DONDE_VIENE, Constantes.FRAGMENT_TRANSACCION);
                DatosClienteFragment datosClienteFragment = new DatosClienteFragment();
                i.putExtra(Constantes.FRAGMENT, datosClienteFragment);
                startActivity(i);
                getActivity().finish();
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

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.et_fragment_sl_clientes_filtro:
                et_fragment_sl_clientes_filtro.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        adapter.getFilter().filter(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case R.id.iv_fragment_sl_clientes_return:

                if (fragment == null) {
                    TransaccionesFragment transaccionesFragment = new TransaccionesFragment();
                    transaction.replace(R.id.contenido, transaccionesFragment);
                    transaction.commit();
                } else {
                    transaction.replace(R.id.contenido, fragment);
                    transaction.commit();
                }

                break;
            case R.id.fb_fragment_sl_cliente_agregar_cliente:
                datosClienteFragment = new DatosClienteFragment();
                transaction = getFragmentManager().beginTransaction();
                Bundle parametro = new Bundle();
                if (origen != null) {
                    if (tipo_usuario.equals(Constantes.USUARIO_ORIGEN)) {
                        parametro.putSerializable(Constantes.USUARIO_SERIALIZABLE, origen);
                        parametro.putString(Constantes.USUARIO, Constantes.USUARIO_ORIGEN);
                        parametro.putSerializable(Constantes.TRANSACCIONES_DATOS, (TransaccionesFragment) fragment);
                        parametro.putSerializable(Constantes.DONDE_VIENE, Constantes.TRANSACCIONES_DATOS);
                        datosClienteFragment.setInteractionListener((OnFragmentInteractionListener) fragment);
                        datosClienteFragment.setArguments(parametro);
                        transaction.replace(R.id.contenido, datosClienteFragment);
                        transaction.commit();
                    }
                } else {
                    Toast.makeText(getContext(), "Seleccione un usuario",
                            Toast.LENGTH_SHORT).show();
                }
                if (destino != null) {
                    if (tipo_usuario.equals(Constantes.USUARIO_DESTINO)) {
                        parametro.putSerializable(Constantes.USUARIO_SERIALIZABLE, destino);
                        parametro.putString(Constantes.USUARIO, Constantes.USUARIO_DESTINO);
                        parametro.putSerializable(Constantes.TRANSACCIONES_DATOS, (TransaccionesFragment) fragment);
                        parametro.putSerializable(Constantes.DONDE_VIENE, Constantes.TRANSACCIONES_DATOS);
                        datosClienteFragment.setInteractionListener((OnFragmentInteractionListener) fragment);
                        datosClienteFragment.setArguments(parametro);
                        transaction.replace(R.id.contenido, datosClienteFragment);
                        transaction.commit();
                    }
                } else {
                    Toast.makeText(getContext(), "Seleccione un usuario",
                            Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.iv_fragment_sl_clientes_perfil:
                Intent i = new Intent(getActivity(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.DATOS_FRAGMENT);
                i.putExtra(Constantes.DONDE_VIENE, Constantes.FRAGMENT_TRANSACCION);
                DatosClienteFragment datosClienteFragment = new DatosClienteFragment();
                i.putExtra(Constantes.FRAGMENT, datosClienteFragment);
                startActivity(i);
                getActivity().finish();
                break;
        }
    }
}
