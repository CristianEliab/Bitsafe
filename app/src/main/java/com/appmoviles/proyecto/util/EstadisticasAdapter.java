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
import com.github.mikephil.charting.charts.PieChart;

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
        TextView textView = view.findViewById(R.id.et_fragment_estadisticas_descripcion_grafica);
        textView.setHint(listaEstadisticas.get(position).getDescripcion());
        pieChart.setData(listaEstadisticas.get(position).getPieData());
        pieChart.invalidate();
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
