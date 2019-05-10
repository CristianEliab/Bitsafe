package com.appmoviles.proyecto;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class PerfilCliente extends Fragment {

    private TextView tv_nombre_iniciales;
    private TextView tv_nombre;
    private TextView tv_telefono;
    private ImageView image_atras;


    public PerfilCliente() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_perfil_cliente, container, false);

        tv_nombre_iniciales = v.findViewById(R.id.iv_nombre_perfil_cliente);
        tv_nombre= v.findViewById(R.id.tv_nombre_cliente_perfil_cliente);
        tv_telefono= v.findViewById(R.id.tv_numero_telefono_perfil_cliente);
        image_atras = v.findViewById(R.id.iv_fragment_perfil_clientes);





        return v;
    }

}
