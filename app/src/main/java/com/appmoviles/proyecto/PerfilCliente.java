package com.appmoviles.proyecto;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class PerfilCliente extends Fragment {

    private TextView tv_nombre_iniciales;
    private TextView tv_nombre;
    private TextView tv_telefono;
    private ImageView image_atras;

    private TextView tv_micuenta_perfil_cliente;
    private TextView tv_ajustes_perfil_cliente;
    private TextView tv_ayuda_perfil_cliente;
    private TextView tv_informacion_perfil_cliente;
    private ImageView iv_cerrar_perfil_cliente;
    private ImageView iv_fragment_perfil_clientes;


    FirebaseAuth auth;
    FirebaseDatabase rtdb;
    Fragment fragment;

    Context contexto;
    private String volver_a;

    public PerfilCliente() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.getArguments() != null) {
            volver_a = getArguments().getString(Constantes.GO_TO_PERFIL);
            fragment = (Fragment) getArguments().getSerializable(Constantes.FRAGMENT);
        }

        // Iniciar todos los fragmentes

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil_cliente, container, false);
        contexto = v.getContext();

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        tv_nombre_iniciales = v.findViewById(R.id.iv_nombre_perfil_cliente);
        tv_nombre = v.findViewById(R.id.tv_nombre_cliente_perfil_cliente);
        tv_telefono = v.findViewById(R.id.tv_numero_telefono_perfil_cliente);
        image_atras = v.findViewById(R.id.iv_fragment_perfil_clientes);
        iv_cerrar_perfil_cliente = v.findViewById(R.id.iv_cerrar_perfil_cliente);
        iv_fragment_perfil_clientes = v.findViewById(R.id.iv_fragment_perfil_clientes);

        iv_fragment_perfil_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (volver_a.equals(Constantes.CLIENTES)) {
                    transaction.replace(R.id.contenido, fragment);
                    transaction.commit();
                }
                if (volver_a.equals(Constantes.ESTADISTICAS)) {
                    transaction.replace(R.id.contenido, fragment);
                    transaction.commit();
                }
                if (volver_a.equals(Constantes.CARGAR)) {
                    transaction.replace(R.id.contenido, fragment);
                    transaction.commit();
                }
                if (volver_a.equals(Constantes.TRANSACCIONES)) {
                    transaction.replace(R.id.contenido, fragment);
                    transaction.commit();
                }

            }
        });

        tv_micuenta_perfil_cliente = v.findViewById(R.id.tv_micuenta_perfil_cliente);
        tv_micuenta_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, MiCuentaPerfil.class);
                startActivity(i);
                getActivity().finish();
            }
        });


        iv_cerrar_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(getActivity(), Splash.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return v;
    }

}
