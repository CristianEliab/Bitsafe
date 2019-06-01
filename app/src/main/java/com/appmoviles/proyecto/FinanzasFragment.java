package com.appmoviles.proyecto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Categoria;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.Transaccion;
import com.appmoviles.proyecto.util.Constantes;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_GASTOS;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_INGRESOS;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_I_O;
import static com.appmoviles.proyecto.util.Constantes.CHILD_CATEGORIAS;
import static com.appmoviles.proyecto.util.Constantes.CHILD_CUENTAS;
import static com.appmoviles.proyecto.util.Constantes.CHILD_TRANSACCIONES;

public class FinanzasFragment extends Fragment implements View.OnClickListener, Serializable {

    FirebaseAuth auth;
    FirebaseDatabase database;

    private LineChart lineChart;
    private BarChart barChart;
    private PieChart pieChart;

    private TextView tv_fragment_finanzas_total_ingresos;
    private TextView tv_fragment_finanzas_total_gastos;

    private LinearLayout ly_fragment_finanzas_total_ingresos;
    private LinearLayout ly_fragment_finanzas_total_gastos;

    //Para navegar a otros fragments
    private FinanzasBancosFragment finanzasBancosFragment;
    private ImageView iv_fragment_finanzas_perfil;

    private List<Cuenta> listaCuentas;
    private List<Transaccion> listaTransaccionesIngresos;
    private List<Transaccion> listaTransaccionesGastos;

    private float totalIngresos;
    private float totalGastos;

    //Para el Pie chart
    Hashtable<String, Integer> diccionarioIdCategorias;
    Hashtable<String, String> diccionarioNombreCategorias;


    //Para el Bar chart
    Hashtable<String, Integer> diccionarioIdCategoriasIngresos;
    //Hashtable<String, String> diccionarioNombreCategoriasIngresos;

    Hashtable<String, Integer> diccionarioIdCategoriasGastos;
    //Hashtable<String, String> diccionarioNombreCategoriasGastos;


    private ProgressDialog progressDialog;


    public FinanzasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_finanzas, container, false);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        lineChart = v.findViewById(R.id.lc_fragment_finanzas_ingresos);
        barChart = v.findViewById(R.id.bc_fragment_finanzas_gastos);
        pieChart = v.findViewById(R.id.pc_fragment_finanzas_gastos);
        iv_fragment_finanzas_perfil = v.findViewById(R.id.iv_fragment_finanzas_perfil);
        iv_fragment_finanzas_perfil.setOnClickListener(this);

        ly_fragment_finanzas_total_ingresos = v.findViewById(R.id.ly_fragment_finanzas_total_ingresos);
        ly_fragment_finanzas_total_ingresos.setOnClickListener(this);
        ly_fragment_finanzas_total_gastos = v.findViewById(R.id.ly_fragment_finanzas_total_gastos);
        ly_fragment_finanzas_total_gastos.setOnClickListener(this);

        tv_fragment_finanzas_total_ingresos = v.findViewById(R.id.tv_fragment_finanzas_total_ingresos);
        tv_fragment_finanzas_total_gastos = v.findViewById(R.id.tv_fragment_finanzas_total_gastos);

        cargarTodoLaInformacion(v);

        return v;
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = null;

        switch (v.getId()) {

            case R.id.ly_fragment_finanzas_total_ingresos:
                finanzasBancosFragment = new FinanzasBancosFragment();
                bundle = new Bundle();
                bundle.putString(BUNDLE_TIPO_I_O, BUNDLE_TIPO_INGRESOS);
                finanzasBancosFragment.setArguments(bundle);
                transaction.replace(R.id.contenido_cliente, finanzasBancosFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case R.id.ly_fragment_finanzas_total_gastos:
                finanzasBancosFragment = new FinanzasBancosFragment();
                bundle = new Bundle();
                bundle.putString(BUNDLE_TIPO_I_O, BUNDLE_TIPO_GASTOS);
                finanzasBancosFragment.setArguments(bundle);
                transaction.replace(R.id.contenido_cliente, finanzasBancosFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            //Por alguna razón el Piechart no sirve así
            case R.id.pc_fragment_finanzas_gastos:
                mostrarMensaje("Pie chart ");
                break;
            case R.id.iv_fragment_finanzas_perfil:
                //Cuando se presiona el botón perfil
                Intent i = new Intent(getContext(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.FRAGMENT_FINANZAS);
                startActivity(i);
                getActivity().finish();
                break;

        }

    }

    public void cargarTodoLaInformacion(View v) {

        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setMessage("Por favor espere mientras se cargan los datos");
        progressDialog.show();

        cargarCuentas();
        cargarTransaccionesIngresos();
        cargarTransaccionesGastos();

    }


    public void cargarCuentas() {

        listaCuentas = new ArrayList<Cuenta>();
        database.getReference().child(CHILD_CUENTAS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cuenta cuentaTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    cuentaTmp = hijo.getValue(Cuenta.class);
                    //Solo las cuentas que pertenezcan al usuario logueado
                    if (cuentaTmp.getUsuarioID().equals(auth.getCurrentUser().getUid())) {
                        listaCuentas.add(cuentaTmp);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void cargarTransaccionesIngresos() {

        listaTransaccionesIngresos = new ArrayList<Transaccion>();
        database.getReference().child(CHILD_TRANSACCIONES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Transaccion transaccionTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    transaccionTmp = hijo.getValue(Transaccion.class);

                    //Solo las transacciones que pertenecen a las cuentas del usuario
                    for (int i = 0; i < listaCuentas.size(); i++) {

                        String cuentaId = listaCuentas.get(i).getCuentaID();

                        //Solo se agregan transacciones de las cuales les llegó dinero a una de las cuentas del usuario
                        // --> !listaTransacciones.contains(transaccionTmp)
                        if (transaccionTmp.getCuentaDestinoID().equals(cuentaId)) {
                            listaTransaccionesIngresos.add(transaccionTmp);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cargarTransaccionesGastos() {

        listaTransaccionesGastos = new ArrayList<Transaccion>();
        database.getReference().child(CHILD_TRANSACCIONES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Transaccion transaccionTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    transaccionTmp = hijo.getValue(Transaccion.class);

                    //Solo las transacciones que pertenecen a las cuentas del usuario
                    for (int i = 0; i < listaCuentas.size(); i++) {

                        String cuentaId = listaCuentas.get(i).getCuentaID();

                        //Solo se agregan transacciones de las cuales salió dinero de una de las cuentas del usuario
                        // --> !listaTransacciones.contains(transaccionTmp)
                        if (transaccionTmp.getCuentaOrigenID().equals(cuentaId)) {
                            listaTransaccionesGastos.add(transaccionTmp);
                        }
                    }
                }
                cargarLineChart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cargarLineChart() {

        totalIngresos = 0;
        totalGastos = 0;

        List<Entry> entries_1 = new ArrayList<>();
        for (int i = 0; i < listaTransaccionesIngresos.size(); i++) {
            String montoTmp = listaTransaccionesIngresos.get(i).getMontoTransaccion();
            montoTmp = montoTmp.replaceAll(Constantes.PUNTO, "");

            float monto = Float.parseFloat(montoTmp);
            float contador = i + 1;

            totalIngresos += monto;

            entries_1.add(new Entry(contador, monto));
        }


        List<Entry> entries_2 = new ArrayList<>();
        for (int i = 0; i < listaTransaccionesGastos.size(); i++) {
            String montoTmp = listaTransaccionesGastos.get(i).getMontoTransaccion();
            montoTmp = montoTmp.replaceAll(Constantes.PUNTO, "");

            float monto = Float.parseFloat(montoTmp);
            float contador = i + 1;

            totalGastos += monto;

            entries_2.add(new Entry(contador, monto));
        }


        LineDataSet lineDataSet_1 = new LineDataSet(entries_1, "Ingresos");
        lineDataSet_1.setColor(Color.GREEN);

        LineDataSet lineDataSet_2 = new LineDataSet(entries_2, "Gastos");
        lineDataSet_2.setColor(Color.RED);

        //Varios datasets
        List<ILineDataSet> lineData_s = new ArrayList<>();
        lineData_s.add(lineDataSet_1);
        lineData_s.add(lineDataSet_2);

        LineData lineData = new LineData(lineData_s);

        Description d = new Description();
        d.setText("Transacciones tipo Ingreso y Gastos a lo largo del tiempo");
        lineChart.setDescription(d);


        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh

        //TODAS LAS POSIBLES INTERACCIONES TOUCH CON EL CHART
        lineChart.setScaleEnabled(true);

        tv_fragment_finanzas_total_ingresos.setText("" + totalIngresos);
        tv_fragment_finanzas_total_gastos.setText("" + totalGastos);


        cargarBarChart();
    }

    public void cargarBarChart() {

        cargarDiccionarioCategoriasId();
        cargarDiccionarioCategoriasNombre();

        List<BarEntry> entries_ingresos = new ArrayList<>();
        List<BarEntry> entries_gastos = new ArrayList<>();

        /*
        for (int i = 0; i < 5; i++) {
            entries_ingresos.add(new BarEntry(i, (int) (Math.random() * 10 + 1)));
            entries_gastos.add(new BarEntry(i, (int) (Math.random() * 10 + 1)));
        }
        */



        Enumeration e = diccionarioIdCategoriasIngresos.keys();
        String clave;
        Integer valor;
        while (e.hasMoreElements()) {
            clave = (String) e.nextElement();
            valor = diccionarioIdCategoriasIngresos.get(clave);

            entries_ingresos.add(new BarEntry(Float.parseFloat(clave), valor));
        }



        e = diccionarioIdCategoriasGastos.keys();
        while (e.hasMoreElements()) {
            clave = (String) e.nextElement();
            valor = diccionarioIdCategoriasGastos.get(clave);

            entries_gastos.add(new BarEntry(Float.parseFloat(clave), valor));

        }


        Log.e("TAMAÑO INGRESOS", "Tamaño " + entries_ingresos.size());
        Log.e("TAMAÑO GASTOS", "Tamaño " + entries_gastos.size());

        BarDataSet set1 = new BarDataSet(entries_ingresos, "Ingresos divididao por categorías");
        BarDataSet set2 = new BarDataSet(entries_gastos, "Gastos divididas por categorías");

        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        set2.setColors(ColorTemplate.MATERIAL_COLORS);

        float groupSpace = 0.08f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.4f; // x2 dataset

        BarData data = new BarData(set1, set2);
        data.setBarWidth(barWidth);
        barChart.setData(data);
        barChart.groupBars(0.7f, groupSpace, barSpace);

        barChart.setFitBars(true); // make the x-axis fit exactly all bars

        Description d = new Description();
        d.setText(" ");
        barChart.setDescription(d);

        barChart.invalidate(); // refresh

        //XAxis xAxis = barChart.getXAxis();
        //xAxis.setCenterAxisLabels(true);

        //Escalas de las X y Y
        barChart.setScaleEnabled(true);

    }

    public void cargarDiccionarioCategoriasId() {

        diccionarioIdCategorias = new Hashtable<String, Integer>();
        diccionarioIdCategoriasIngresos = new Hashtable<String, Integer>();
        diccionarioIdCategoriasGastos = new Hashtable<String, Integer>();

        String categoriaIdTmp;

        //Cuentas el número de cada categoria de la lista de transacciones de ingresos
        for (int i = 0; i < listaTransaccionesIngresos.size(); i++) {

            //Para PieChart
            categoriaIdTmp = listaTransaccionesIngresos.get(i).getCategoriaID();
            if (diccionarioIdCategorias.get(categoriaIdTmp) == null) {
                diccionarioIdCategorias.put(categoriaIdTmp, 1);
            } else {
                diccionarioIdCategorias.put(categoriaIdTmp, diccionarioIdCategorias.get(categoriaIdTmp) + 1);
            }

            //Para Bar Chart parte Ingresos
            if (diccionarioIdCategoriasIngresos.get(categoriaIdTmp) == null) {
                diccionarioIdCategoriasIngresos.put(categoriaIdTmp, 1);
            } else {
                diccionarioIdCategoriasIngresos.put(categoriaIdTmp, diccionarioIdCategoriasIngresos.get(categoriaIdTmp) + 1);
            }

        }

        //Cuentas el número de cada categoria de la lista de transacciones de gastos
        for (int i = 0; i < listaTransaccionesGastos.size(); i++) {

            categoriaIdTmp = listaTransaccionesGastos.get(i).getCategoriaID();
            if (diccionarioIdCategorias.get(categoriaIdTmp) == null) {
                diccionarioIdCategorias.put(categoriaIdTmp, 1);
            } else {
                diccionarioIdCategorias.put(categoriaIdTmp, diccionarioIdCategorias.get(categoriaIdTmp) + 1);
            }

            //Para Bar Chart parte Gastos
            if (diccionarioIdCategoriasGastos.get(categoriaIdTmp) == null) {
                diccionarioIdCategoriasGastos.put(categoriaIdTmp, 1);
            } else {
                diccionarioIdCategoriasGastos.put(categoriaIdTmp, diccionarioIdCategoriasGastos.get(categoriaIdTmp) + 1);
            }

        }
    }

    public void cargarDiccionarioCategoriasNombre() {

        diccionarioNombreCategorias = new Hashtable<String, String>();

        database.getReference().child(CHILD_CATEGORIAS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Enumeration e = diccionarioIdCategorias.keys();
                String clave;
                Integer valor;

                while (e.hasMoreElements()) {
                    clave = (String) e.nextElement();
                    valor = diccionarioIdCategorias.get(clave);

                    Categoria categoriaTmp;
                    for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                        categoriaTmp = hijo.getValue(Categoria.class);

                        if (categoriaTmp.getCategoriaID().equals(clave)) {
                            diccionarioNombreCategorias.put(clave, categoriaTmp.getNombre());
                        }
                        //SI HAY SUBCATEGORÍAS, QUE AÑADA EL NOMBRE
                        if(categoriaTmp.getSubCategoria() != null){
                            ArrayList<Categoria> list = categoriaTmp.getSubCategoria();
                            for (Categoria categoria: list){
                                if(categoria.getCategoriaID().equals(clave)){
                                    diccionarioNombreCategorias.put(clave, categoria.getNombre());
                                }
                            }

                        }


                    }
                }
                cargarPieChart();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void cargarPieChart() {

        //Piechart
        final List<PieEntry> entries = new ArrayList<>();

        Enumeration e = diccionarioIdCategorias.keys();
        String clave;
        Integer valor;

        while (e.hasMoreElements()) {
            clave = (String) e.nextElement();
            valor = diccionarioIdCategorias.get(clave);

            entries.add(new PieEntry(valor, diccionarioNombreCategorias.get(clave)));

        }

        PieDataSet set = new PieDataSet(entries, "dataset piechart");
        set.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(set);
        pieChart.setData(data);

        Description d = new Description();
        d.setText("Todas las transacciones del usuario agrupadas por categorías");
        pieChart.setDescription(d);

        pieChart.invalidate(); // refresh

        //Se acaba el dialogo de progreso
        progressDialog.dismiss();
    }


    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }
}
