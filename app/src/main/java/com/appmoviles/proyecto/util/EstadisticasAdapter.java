package com.appmoviles.proyecto.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appmoviles.proyecto.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;


public class EstadisticasAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private ArrayList<Grafica> listaEstadisticas;

    public EstadisticasAdapter(Context context) {
        this.context = context;
        this.listaEstadisticas = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return listaEstadisticas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == (LinearLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pie_chart_layout, container, false);
        PieChart pieChart = view.findViewById(R.id.pc_fragment_estadisticas);
        BarChart barChart = view.findViewById(R.id.bc_fragment_finanzas_gastos);
        TextView textView = view.findViewById(R.id.et_fragment_estadisticas_descripcion_grafica);
        TextView textView1 = view.findViewById(R.id.titulo_descripcion);
        textView1.setText(listaEstadisticas.get(position).getDescripcion());

        //BarChart
        final String[] quarters = new String[]{"En", "Fe", "Ma", "Ab", "My", "Jn", "Jl", "Ag", "Sp", "Oc", "No", "Dc"};

        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return quarters[(int) value];
            }
        };
        if (listaEstadisticas.get(position).getTipo().equals(Constantes.TIPO_BAR)) {
            barChart.setVisibility(View.VISIBLE);
            listaEstadisticas.get(position).getBarData().setBarWidth(0.9f); // set custom bar width
            barChart.setData(listaEstadisticas.get(position).getBarData());
            barChart.setFitBars(true); // make the x-axis fit exactly all bars
            barChart.invalidate(); // refresh
            barChart.setScaleEnabled(false);//escalas de las X y Y
            barChart.getXAxis().setValueFormatter(formatter);
            textView.setHint(R.string.descripcion_grafica_clientes);
        }
        if (listaEstadisticas.get(position).getTipo().equals(Constantes.TIPO_PIE)) {
            pieChart.setVisibility(View.VISIBLE);
            pieChart.setData(listaEstadisticas.get(position).getPieData());
            pieChart.invalidate();
            textView.setHint(R.string.descripcion_grafica_generos);
        }

        // refresh
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }

    public void agregarEstadistica(Grafica pieChart) {
        listaEstadisticas.add(pieChart);
        notifyDataSetChanged();
    }
}
