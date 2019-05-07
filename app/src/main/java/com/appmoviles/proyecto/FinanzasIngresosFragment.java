package com.appmoviles.proyecto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appmoviles.proyecto.modelo.Transaccion;


public class FinanzasIngresosFragment extends Fragment {

    private RecyclerView rv_fragment_finanzas_ingresos_lista_transacciones;
    private AdapterTemplate_Transacciones adapterTemplate_transacciones;


    public FinanzasIngresosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finanzas_ingresos, container, false);

        rv_fragment_finanzas_ingresos_lista_transacciones = v.findViewById(R.id.rv_fragment_finanzas_ingresos_lista_transacciones);
        rv_fragment_finanzas_ingresos_lista_transacciones.setHasFixedSize(true);
        rv_fragment_finanzas_ingresos_lista_transacciones.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterTemplate_transacciones = new AdapterTemplate_Transacciones();
        rv_fragment_finanzas_ingresos_lista_transacciones.setAdapter(adapterTemplate_transacciones);


        Transaccion t1 = new Transaccion();
        t1.setMontoTransaccion("monto 1");
        t1.setCategoriaID("categoria 1");
        t1.setDescripcion("descripcion 1");
        t1.setFechaTransaccion("fecha 1");

        Transaccion t2 = new Transaccion();
        t2.setMontoTransaccion("monto 2");
        t2.setCategoriaID("categoria 2");
        t2.setDescripcion("descripcion 2");
        t2.setFechaTransaccion("fecha 2");

        Transaccion t3 = new Transaccion();
        t3.setMontoTransaccion("monto 3");
        t3.setCategoriaID("categoria 4");
        t3.setDescripcion("descripcion 3");
        t3.setFechaTransaccion("fecha 3");

        Transaccion t4 = new Transaccion();
        t4.setMontoTransaccion("monto 4");
        t4.setCategoriaID("categoria 4");
        t4.setDescripcion("descripcion 4");
        t4.setFechaTransaccion("fecha 4");

        Transaccion t5 = new Transaccion();
        t5.setMontoTransaccion("monto 5");
        t5.setCategoriaID("categoria 5");
        t5.setDescripcion("descripcion 5");
        t5.setFechaTransaccion("fecha 5");

        Transaccion t6= new Transaccion();
        t6.setMontoTransaccion("monto 6");
        t6.setCategoriaID("categoria 6");
        t6.setDescripcion("descripcion 6");
        t6.setFechaTransaccion("fecha 6");

        Transaccion t7 = new Transaccion();
        t7.setMontoTransaccion("monto 7");
        t7.setCategoriaID("categoria 7");
        t7.setDescripcion("descripcion 7");
        t7.setFechaTransaccion("fecha 7");

        Transaccion t8 = new Transaccion();
        t8.setMontoTransaccion("monto 8");
        t8.setCategoriaID("categoria 8");
        t8.setDescripcion("descripcion 8");
        t8.setFechaTransaccion("fecha 8");

        Transaccion t9 = new Transaccion();
        t9.setMontoTransaccion("monto 9");
        t9.setCategoriaID("categoria 9");
        t9.setDescripcion("descripcion 9");
        t9.setFechaTransaccion("fecha 9");

        adapterTemplate_transacciones.agregarTransaccion(t1);
        adapterTemplate_transacciones.agregarTransaccion(t2);
        adapterTemplate_transacciones.agregarTransaccion(t3);
        adapterTemplate_transacciones.agregarTransaccion(t4);
        adapterTemplate_transacciones.agregarTransaccion(t5);
        adapterTemplate_transacciones.agregarTransaccion(t6);
        adapterTemplate_transacciones.agregarTransaccion(t7);
        adapterTemplate_transacciones.agregarTransaccion(t8);
        adapterTemplate_transacciones.agregarTransaccion(t9);


        return v;
    }

}
