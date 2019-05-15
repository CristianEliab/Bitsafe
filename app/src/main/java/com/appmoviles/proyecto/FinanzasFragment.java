package com.appmoviles.proyecto;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class FinanzasFragment extends Fragment implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    LineChart lineChart;
    BarChart barChart;
    PieChart pieChart;

    //Para navegar a otros fragments
    FinanzasBancosFragment finanzasBancosFragment;


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

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        //rtdb.getReference().child("usuarios").child("PRUEBA").setValue("MURIEL_2");


        lineChart = v.findViewById(R.id.lc_fragment_finanzas_ingresos);
        barChart = v.findViewById(R.id.bc_fragment_finanzas_gastos);
        pieChart = v.findViewById(R.id.pc_fragment_finanzas_gastos);

        //Linechart

        List<Entry> entries_1 = new ArrayList<>();
        entries_1.add(new Entry(0f, 1000f));
        entries_1.add(new Entry(1f, 1400f));
        entries_1.add(new Entry(2f, 1300f));
        entries_1.add(new Entry(3f, 800f));


        List<Entry> entries_2 = new ArrayList<>();
        entries_2.add(new Entry(0f, 500f));
        entries_2.add(new Entry(1f, 1800f));
        entries_2.add(new Entry(2f, 900f));
        entries_2.add(new Entry(3f, 1500f));

        LineDataSet lineDataSet_1 = new LineDataSet(entries_1, "datos 1");
        lineDataSet_1.setColor(Color.GREEN);
        //axis
        //lineDataSet_1.setAxisDependency(YAxis.AxisDependency.LEFT);

        LineDataSet lineDataSet_2 = new LineDataSet(entries_2, "datos 2");
        lineDataSet_2.setColor(Color.RED);
        //axis
        //lineDataSet_2.setAxisDependency(YAxis.AxisDependency.LEFT);

        //Varios datasets
        List<ILineDataSet> lineData_s = new ArrayList<>();
        lineData_s.add(lineDataSet_1);
        lineData_s.add(lineDataSet_2);

        LineData lineData = new LineData(lineData_s);

        lineChart.setData(lineData);
        lineChart.invalidate(); // refresh


        //Cambio de formato de las x Labels del lineChart
        final String[] quarters = new String[]{"Enero", "Febrero", "Marzo", "Abril"};

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);


        //TODAS LAS POSIBLES INTERACCIONES TOUCH CON EL CHART
        lineChart.setScaleEnabled(false);
        lineChart.setOnClickListener(this);


        //BarChart
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 30f));
        entries.add(new BarEntry(1f, 80f));
        entries.add(new BarEntry(2f, 60f));
        entries.add(new BarEntry(3f, 50f));
        // gap of 2f
        entries.add(new BarEntry(5f, 70f));
        entries.add(new BarEntry(6f, 60f));

        BarDataSet set = new BarDataSet(entries, "dataset barchar");
        set.setColors(ColorTemplate.PASTEL_COLORS);
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh

        //escalas de las X y Y
        barChart.setScaleEnabled(false);
        barChart.setOnClickListener(this);

        //Piechart

        List<PieEntry> entries_3 = new ArrayList<>();

        entries_3.add(new PieEntry(18.5f, "Green"));
        entries_3.add(new PieEntry(26.7f, "Yellow"));
        entries_3.add(new PieEntry(24.0f, "Red"));
        entries_3.add(new PieEntry(30.8f, "Blue"));

        PieDataSet set_2 = new PieDataSet(entries_3, "dataset piechart");
        set_2.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data_2 = new PieData(set_2);
        pieChart.setData(data_2);

        pieChart.invalidate(); // refresh

        //Así tbm se puede pero el chart no se mueve
        //pieChart.setOnTouchListener(null);
        //pieChart.setClickable(true);
        pieChart.setOnClickListener(this);
        pieChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                mostrarMensaje("Pie chart");
            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });


        return v;
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        switch (v.getId()) {


            case R.id.lc_fragment_finanzas_ingresos:
                finanzasBancosFragment = new FinanzasBancosFragment();
                transaction.replace(R.id.contenido_cliente, finanzasBancosFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case R.id.bc_fragment_finanzas_gastos:
                mostrarMensaje("Bar chart ");
                break;

            //Por alguna razón el Piechart no sirve así
            case R.id.pc_fragment_finanzas_gastos:
                mostrarMensaje("Pie chart ");
                break;
        }

    }

    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }
}
