package com.appmoviles.proyecto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class FinanzasCrearTransaccionFragment extends Fragment implements View.OnClickListener {


    private Button btn_fragment_finanzas_crear_transaccion_agregar_monto;
    private Button btn_fragment_finanzas_crear_transaccion_agregar_cagetoria;
    private Button btn_fragment_finanzas_crear_transaccion_agregar_fecha;
    private EditText et_fragment_finanzas_crear_transaccion_descripcion;

    private Button btn_fragment_finanzas_crear_transaccion_cancelar;
    private Button btn_fragment_finanzas_crear_transaccion_guardar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finanzas_crear_transaccion, container, false);

        btn_fragment_finanzas_crear_transaccion_agregar_monto = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_agregar_monto);
        btn_fragment_finanzas_crear_transaccion_agregar_cagetoria = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_agregar_cagetoria);
        btn_fragment_finanzas_crear_transaccion_agregar_fecha = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_agregar_fecha);
        et_fragment_finanzas_crear_transaccion_descripcion = v.findViewById(R.id.et_fragment_finanzas_crear_transaccion_descripcion);

        btn_fragment_finanzas_crear_transaccion_cancelar = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_cancelar);
        btn_fragment_finanzas_crear_transaccion_guardar = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_guardar);


        btn_fragment_finanzas_crear_transaccion_agregar_monto.setOnClickListener(this);
        btn_fragment_finanzas_crear_transaccion_agregar_cagetoria.setOnClickListener(this);
        btn_fragment_finanzas_crear_transaccion_agregar_fecha.setOnClickListener(this);

        btn_fragment_finanzas_crear_transaccion_cancelar.setOnClickListener(this);
        btn_fragment_finanzas_crear_transaccion_guardar.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_fragment_finanzas_crear_transaccion_agregar_monto:
                mostrarMensaje("agregar monto");
                break;

            case R.id.btn_fragment_finanzas_crear_transaccion_agregar_cagetoria:
                mostrarMensaje("agregar categoría");
                break;

            case R.id.btn_fragment_finanzas_crear_transaccion_agregar_fecha:
                mostrarMensaje("agregar fecha");
                break;

            case R.id.btn_fragment_finanzas_crear_transaccion_cancelar:
                getFragmentManager().popBackStack();
                break;


            case R.id.btn_fragment_finanzas_crear_transaccion_guardar:
                mostrarMensaje("btn guardar");
                break;

        }
    }

    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }
}
