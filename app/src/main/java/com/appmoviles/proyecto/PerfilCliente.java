package com.appmoviles.proyecto;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
    private TextView tv_nombre_cliente_perfil_cliente;
    private TextView tv_numero_telefono_perfil_cliente;
    private TextView tv_cerrar_perfil_cliente;


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
        setAnimation();
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
        tv_nombre_cliente_perfil_cliente = v.findViewById(R.id.tv_nombre_cliente_perfil_cliente);
        tv_numero_telefono_perfil_cliente = v.findViewById(R.id.tv_numero_telefono_perfil_cliente);
        tv_cerrar_perfil_cliente = v.findViewById(R.id.tv_cerrar_perfil_cliente);

        iv_fragment_perfil_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.contenido, fragment);
                transaction.commit();

            }
        });

        tv_cerrar_perfil_cliente = v.findViewById(R.id.tv_cerrar_perfil_cliente);
        tv_cerrar_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, MiCuentaPerfil.class);
                startActivity(i);
                getActivity().finish();
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


        rtdb.getReference().child(Constantes.CHILD_USUARIOS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Respuesta de firebase
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            //Si es admin, loguearse
                            Usuario usuario = hijo.getValue(Usuario.class);
                            if (usuario.getUsuarioID().equals(auth.getCurrentUser().getUid())) {
                                tv_nombre_cliente_perfil_cliente.setText(usuario.getNombre());
                                tv_numero_telefono_perfil_cliente.setText(usuario.getTelefono());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


        return v;
    }


    public void setAnimation() {
        if (Build.VERSION.SDK_INT > 20) {
            Slide slide = new Slide();
            slide.setSlideEdge(Gravity.LEFT);
            slide.setDuration(400);
            slide.setInterpolator(new AccelerateDecelerateInterpolator());
            getActivity().getWindow().setExitTransition(slide);
            getActivity().getWindow().setEnterTransition(slide);
        }
    }
}
