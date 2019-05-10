package com.appmoviles.proyecto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.Transaccion;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_ID_BANCO;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_ID_CUENTA;


public class FinanzasTransaccionesFragment extends Fragment implements AdapterTemplate_Transacciones.OnItemClickListener, View.OnClickListener {

    private RecyclerView rv_fragment_finanzas_transacciones_lista;
    private AdapterTemplate_Transacciones adapterTemplate_transacciones;
    private LineChart lineChartTransacciones;
    private Button btn_fragment_finanzas_transacciones_agregar_transaccion;
    private TextView tv_fragment_finanzas_transacciones_titulo;

    private Cuenta cuentaSeleccionada;

    private FinanzasCrearTransaccionFragment finanzasCrearTransaccionFragment;


    public FinanzasTransaccionesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finanzas_transacciones, container, false);

        rv_fragment_finanzas_transacciones_lista = v.findViewById(R.id.rv_fragment_finanzas_transacciones_lista);
        btn_fragment_finanzas_transacciones_agregar_transaccion = v.findViewById(R.id.btn_fragment_finanzas_transacciones_agregar_transaccion);
        btn_fragment_finanzas_transacciones_agregar_transaccion.setOnClickListener(this);
        lineChartTransacciones = v.findViewById(R.id.lc_fragment_finanzas_transacciones);
        tv_fragment_finanzas_transacciones_titulo = v.findViewById(R.id.tv_fragment_finanzas_transacciones_titulo);

        if (this.getArguments() != null) {
            String id_cuenta = (String) getArguments().get(BUNDLE_ID_CUENTA);
            //SIMULO QUE : --> SE VA A BUSCAR EL ID DE LA CUENTA EN LA BASE DE DATOS
            cuentaSeleccionada = new Cuenta();
            cuentaSeleccionada.setNumeroCuenta(id_cuenta);

            tv_fragment_finanzas_transacciones_titulo.setText("Pertenecientes al número de cuenta " + cuentaSeleccionada.getNumeroCuenta());
        }


        //Linechart

        List<Entry> entries_1 = new ArrayList<>();
        entries_1.add(new Entry(0f, 1000f));
        entries_1.add(new Entry(1f, 1400f));
        entries_1.add(new Entry(2f, 1300f));
        entries_1.add(new Entry(3f, 800f));
        entries_1.add(new Entry(4f, 1000f));
        entries_1.add(new Entry(5f, 500f));
        entries_1.add(new Entry(6f, 1500f));
        entries_1.add(new Entry(7f, 300f));


        LineDataSet lineDataSet_1 = new LineDataSet(entries_1, "datos 1");
        lineDataSet_1.setColor(Color.GREEN);

        LineData lineData = new LineData(lineDataSet_1);

        lineChartTransacciones.setData(lineData);
        lineChartTransacciones.invalidate(); // refresh


        //Cambio de formato de las x Labels del lineChart
        final String[] quarters = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agostos"};

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };

        XAxis xAxis = lineChartTransacciones.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);


        //TODAS LAS POSIBLES INTERACCIONES TOUCH CON EL CHART
        lineChartTransacciones.setScaleEnabled(false);

        //Final de line chart


        rv_fragment_finanzas_transacciones_lista.setHasFixedSize(true);
        rv_fragment_finanzas_transacciones_lista.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterTemplate_transacciones = new AdapterTemplate_Transacciones();
        rv_fragment_finanzas_transacciones_lista.setAdapter(adapterTemplate_transacciones);
        adapterTemplate_transacciones.setListener(this);


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
        t3.setCategoriaID("categoria 3");
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

        Transaccion t6 = new Transaccion();
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

    @Override
    public void onItemClick(Transaccion transaccion) {
        mostrarMensaje(transaccion.getMontoTransaccion());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fragment_finanzas_transacciones_agregar_transaccion:
                finanzasCrearTransaccionFragment = new FinanzasCrearTransaccionFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido_cliente, finanzasCrearTransaccionFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

        }
    }

    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }
}
