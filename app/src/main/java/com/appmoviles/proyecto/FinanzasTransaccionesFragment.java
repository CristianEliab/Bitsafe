package com.appmoviles.proyecto;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_ID_BANCO;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_ID_CUENTA;
import static com.appmoviles.proyecto.util.Constantes.CHILD_TRANSACCIONES;


public class FinanzasTransaccionesFragment extends Fragment implements AdapterTemplate_Transacciones.OnItemClickListener, View.OnClickListener {

    FirebaseDatabase database;
    FirebaseAuth auth;

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

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

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
            cuentaSeleccionada.setCuentaID(id_cuenta);

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

        cargarTransacciones();

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


    public void cargarTransacciones() {
        database.getReference().child(CHILD_TRANSACCIONES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Transaccion transaccionTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    transaccionTmp = hijo.getValue(Transaccion.class);
                    //Solo se agregan transacciones de las cuales les llegó doinero a la cuenta seleccionada
                    if (transaccionTmp.getCuentaDestinoID().equals(cuentaSeleccionada.getCuentaID())) {
                        adapterTemplate_transacciones.agregarTransaccion(transaccionTmp);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }
}
