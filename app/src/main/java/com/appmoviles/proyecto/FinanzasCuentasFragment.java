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

import com.appmoviles.proyecto.modelo.Cuenta;

public class FinanzasCuentasFragment extends Fragment {

    private RecyclerView rv_fragment_finanzas_cuentas_lista;
    private AdapterTemplate_Cuentas adapterTemplate_cuentas;

    public FinanzasCuentasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finanzas_cuentas, container, false);


        rv_fragment_finanzas_cuentas_lista = v.findViewById(R.id.rv_fragment_finanzas_cuentas_lista);
        adapterTemplate_cuentas = new AdapterTemplate_Cuentas();
        rv_fragment_finanzas_cuentas_lista.setHasFixedSize(true);
        rv_fragment_finanzas_cuentas_lista.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_fragment_finanzas_cuentas_lista.setAdapter(adapterTemplate_cuentas);


        Cuenta c1 = new Cuenta();
        c1.setUsuarioID("usuario 1");
        c1.setNumeroCuenta("numero 1");

        Cuenta c2 = new Cuenta();
        c2.setUsuarioID("usuario 2");
        c2.setNumeroCuenta("numero 2");

        Cuenta c3 = new Cuenta();
        c3.setUsuarioID("usuario 3");
        c3.setNumeroCuenta("numero 3");

        Cuenta c4 = new Cuenta();
        c4.setUsuarioID("usuario 4");
        c4.setNumeroCuenta("numero 4");

        Cuenta c5 = new Cuenta();
        c5.setUsuarioID("usuario 5");
        c5.setNumeroCuenta("numero 5");

        Cuenta c6 = new Cuenta();
        c6.setUsuarioID("usuario 6");
        c6.setNumeroCuenta("numero 6");

        Cuenta c7 = new Cuenta();
        c7.setUsuarioID("usuario 7");
        c7.setNumeroCuenta("numero 7");

        Cuenta c8 = new Cuenta();
        c8.setUsuarioID("usuario 8");
        c8.setNumeroCuenta("numero 8");

        Cuenta c9 = new Cuenta();
        c9.setUsuarioID("usuario 9");
        c9.setNumeroCuenta("numero 9");

        Cuenta c10 = new Cuenta();
        c10.setUsuarioID("usuario 10");
        c10.setNumeroCuenta("numero 10");

        adapterTemplate_cuentas.agregarCuenta(c1);
        adapterTemplate_cuentas.agregarCuenta(c2);
        adapterTemplate_cuentas.agregarCuenta(c3);
        adapterTemplate_cuentas.agregarCuenta(c4);
        adapterTemplate_cuentas.agregarCuenta(c5);
        adapterTemplate_cuentas.agregarCuenta(c6);
        adapterTemplate_cuentas.agregarCuenta(c7);
        adapterTemplate_cuentas.agregarCuenta(c8);
        adapterTemplate_cuentas.agregarCuenta(c9);
        adapterTemplate_cuentas.agregarCuenta(c10);

        return v;
    }
}
