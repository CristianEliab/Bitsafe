package com.appmoviles.proyecto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class TransaccionesFragment extends Fragment {

    private EditText et_fragment_transacciones_agregar_monto;
    private EditText et_fragment_transacciones_clientes_origen;
    private EditText et_fragment_transacciones_clientes_destino;
    private EditText et_fragment_transacciones_agregar_fecha;
    private EditText et_fragment_transacciones_descripcion_clientes;

    public TransaccionesFragment() {
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

        et_fragment_transacciones_agregar_monto = v.findViewById(R.id.et_fragment_transacciones_agregar_monto);
        et_fragment_transacciones_clientes_origen = v.findViewById(R.id.et_fragment_transacciones_clientes_origen);
        et_fragment_transacciones_clientes_destino = v.findViewById(R.id.et_fragment_transacciones_clientes_destino);
        et_fragment_transacciones_agregar_fecha = v.findViewById(R.id.et_fragment_transacciones_agregar_fecha);
        et_fragment_transacciones_descripcion_clientes = v.findViewById(R.id.et_fragment_transacciones_descripcion_clientes) ;

        et_fragment_transacciones_agregar_monto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }

}
