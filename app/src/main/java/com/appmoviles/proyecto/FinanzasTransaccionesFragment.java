package com.appmoviles.proyecto;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.Transaccion;
import com.appmoviles.proyecto.util.Constantes;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_CUENTA;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_GASTOS;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_INGRESOS;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_I_O;
import static com.appmoviles.proyecto.util.Constantes.CHILD_TRANSACCIONES;


public class FinanzasTransaccionesFragment extends Fragment implements AdapterTemplate_Transacciones.OnItemClickListener, View.OnClickListener {

    FirebaseDatabase database;
    FirebaseAuth auth;

    private TextView tv_fragment_finanzas_transacciones_actividad;
    private ImageView iv_fragment_finanzas_transacciones_return;
    private RecyclerView rv_fragment_finanzas_transacciones_lista;
    private AdapterTemplate_Transacciones adapterTemplate_transacciones;
    private LineChart lineChartTransacciones;
    private Button btn_fragment_finanzas_transacciones_agregar_transaccion;
    private TextView tv_fragment_finanzas_transacciones_titulo;

    private Cuenta cuentaSeleccionada;
    private String tipo_ingreso_o_gasto;

    private FinanzasCrearTransaccionFragment finanzasCrearTransaccionFragment;


    private ProgressDialog progressDialog;

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

        tv_fragment_finanzas_transacciones_actividad = v.findViewById(R.id.tv_fragment_finanzas_transacciones_actividad);
        iv_fragment_finanzas_transacciones_return = v.findViewById(R.id.iv_fragment_finanzas_transacciones_return);
        iv_fragment_finanzas_transacciones_return.setOnClickListener(this);

        rv_fragment_finanzas_transacciones_lista = v.findViewById(R.id.rv_fragment_finanzas_transacciones_lista);
        btn_fragment_finanzas_transacciones_agregar_transaccion = v.findViewById(R.id.btn_fragment_finanzas_transacciones_agregar_transaccion);
        btn_fragment_finanzas_transacciones_agregar_transaccion.setOnClickListener(this);
        lineChartTransacciones = v.findViewById(R.id.lc_fragment_finanzas_transacciones);
        tv_fragment_finanzas_transacciones_titulo = v.findViewById(R.id.tv_fragment_finanzas_transacciones_titulo);

        rv_fragment_finanzas_transacciones_lista.setHasFixedSize(true);
        rv_fragment_finanzas_transacciones_lista.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterTemplate_transacciones = new AdapterTemplate_Transacciones();
        rv_fragment_finanzas_transacciones_lista.setAdapter(adapterTemplate_transacciones);
        adapterTemplate_transacciones.setListener(this);

        if (this.getArguments() != null) {
            cuentaSeleccionada = (Cuenta) getArguments().get(BUNDLE_CUENTA);
            tipo_ingreso_o_gasto = (String) getArguments().get(BUNDLE_TIPO_I_O);

            tv_fragment_finanzas_transacciones_titulo.setText("Pertenecientes al número de cuenta " + cuentaSeleccionada.getNumeroCuenta());
            tv_fragment_finanzas_transacciones_actividad.setText("Transacciones " + tipo_ingreso_o_gasto);
        }

        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setMessage("Por favor espere mientras se cargan los datos");
        progressDialog.show();


        if (tipo_ingreso_o_gasto.equals(BUNDLE_TIPO_INGRESOS)) {
            cargarTransaccionesIngresos();
        } else if (tipo_ingreso_o_gasto.equals(BUNDLE_TIPO_GASTOS)) {
            cargarTransaccionesGastos();
        }


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

                Bundle bundle = new Bundle();
                bundle.putString(BUNDLE_TIPO_I_O, tipo_ingreso_o_gasto);
                bundle.putSerializable(BUNDLE_CUENTA, cuentaSeleccionada);

                finanzasCrearTransaccionFragment = new FinanzasCrearTransaccionFragment();
                finanzasCrearTransaccionFragment.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido_cliente, finanzasCrearTransaccionFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case R.id.iv_fragment_finanzas_transacciones_return:
                getFragmentManager().popBackStack();
                break;

        }
    }


    public void cargarTransaccionesIngresos() {
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
                cargarLineChartTransacciones();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cargarTransaccionesGastos() {
        database.getReference().child(CHILD_TRANSACCIONES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Transaccion transaccionTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    transaccionTmp = hijo.getValue(Transaccion.class);
                    //Solo se agregan transacciones de las cuales les llegó dinero a la cuenta seleccionada
                    if (transaccionTmp.getCuentaOrigenID().equals(cuentaSeleccionada.getCuentaID())) {
                        adapterTemplate_transacciones.agregarTransaccion(transaccionTmp);
                    }
                }
                cargarLineChartTransacciones();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private final void cargarLineChartTransacciones() {

        //Linechart
        List<Entry> entries_1 = new ArrayList<>();

        List<Transaccion> listaTransacciones = adapterTemplate_transacciones.darTransacciones();
        for (int i = 0; i < listaTransacciones.size(); i++) {

            String montoTmp = listaTransacciones.get(i).getMontoTransaccion();
            montoTmp = montoTmp.replaceAll(Constantes.PUNTO, "");

            float monto = Float.parseFloat(montoTmp);
            float contador = i + 1;
            entries_1.add(new Entry(contador, monto));

        }

        LineDataSet lineDataSet_1 = null;
        if (tipo_ingreso_o_gasto.equals(BUNDLE_TIPO_INGRESOS)) {
            lineDataSet_1 = new LineDataSet(entries_1, "flujo de dinero ingresado");
            lineDataSet_1.setColor(Color.GREEN);
        } else if (tipo_ingreso_o_gasto.equals(BUNDLE_TIPO_GASTOS)) {
            lineDataSet_1 = new LineDataSet(entries_1, "flujo de dinero saliente");
            lineDataSet_1.setColor(Color.RED);
        }

        LineData lineData = new LineData(lineDataSet_1);

        lineChartTransacciones.setData(lineData);
        lineChartTransacciones.invalidate(); // refresh

        //TODAS LAS POSIBLES INTERACCIONES TOUCH CON EL CHART
        lineChartTransacciones.setScaleEnabled(false);

        //Final de line chart


        /*
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
        */
    }


    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }
}
