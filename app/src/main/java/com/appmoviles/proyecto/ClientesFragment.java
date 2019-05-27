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

import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.AdapterTemplate_Clientes;
import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class ClientesFragment extends Fragment implements Serializable, AdapterTemplate_Clientes.OnItemClickUsuario, View.OnClickListener {

    private RecyclerView libreta;
    private AdapterTemplate_Clientes adapter;
    private ImageView iv_fragment_clientes_perfil;
    private DatosClienteFragment datosClienteFragment;
    private EditText et_fragment_clientes_filtro;

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
        iv_fragment_clientes_perfil = v.findViewById(R.id.iv_fragment_clientes_perfil);
        et_fragment_clientes_filtro = v.findViewById(R.id.et_fragment_clientes_filtro);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        libreta = v.findViewById(R.id.lista_clientes);
        adapter = new AdapterTemplate_Clientes();
        adapter.setListener(this);
        libreta.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        libreta.setLayoutManager(manager);
        libreta.setAdapter(adapter);


        iv_fragment_clientes_perfil.setOnClickListener(this);
        et_fragment_clientes_filtro.setOnClickListener(this);


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

        return v;
    }

    @Override
    public void onItemUsuario(Usuario usuario) {
        datosClienteFragment = new DatosClienteFragment();
        //listener.onPassClickClienteOrigen(cliente);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        // Utilizado para enviar variables entre dos fragments
        Bundle parametro = new Bundle();
        if (usuario != null) {
            parametro.putSerializable(Constantes.USUARIO_SERIALIZABLE, usuario);
            parametro.putSerializable(Constantes.CLIENTES, this);
            parametro.putString(Constantes.DONDE_VIENE, Constantes.FRAGMENT_CLIENTE);
            datosClienteFragment.setArguments(parametro);
        }
        transaction.replace(R.id.contenido, datosClienteFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fragment_clientes_perfil:
                Intent i = new Intent(getActivity(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.FRAGMENT_CLIENTE);
                startActivity(i);
                getActivity().finish();
                break;
            case R.id.et_fragment_clientes_filtro:
                et_fragment_clientes_filtro.addTextChangedListener(new TextWatcher() {
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
        }
    }
}
