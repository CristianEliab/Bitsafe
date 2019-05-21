package com.appmoviles.proyecto;

import android.content.Context;
import android.content.Intent;
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

import com.appmoviles.proyecto.util.Constantes;

import java.io.Serializable;


public class CuentasFragment extends Fragment implements Serializable {

    private ImageView iv_fragment_cuentas_perfil;


    public CuentasFragment() {
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
        View v= inflater.inflate(R.layout.fragment_cuentas, container, false);

        iv_fragment_cuentas_perfil = v.findViewById(R.id.iv_fragment_cuentas_perfil);

        iv_fragment_cuentas_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PerfilCliente.class);
                i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CUENTAS);
                startActivity(i);
                getActivity().finish();

            }
        });

        return v;
    }
}
