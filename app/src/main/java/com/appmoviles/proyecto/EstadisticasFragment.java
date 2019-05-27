package com.appmoviles.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmoviles.proyecto.util.Constantes;
import com.appmoviles.proyecto.util.EstadisticasAdapter;
import com.appmoviles.proyecto.util.Grafica;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EstadisticasFragment extends Fragment implements Serializable, View.OnClickListener {

    private ImageView iv_fragment_estadisticas_perfil;
    private ViewPager view_estadisticas;
    private EstadisticasAdapter estadisticasAdapter;
    private ArrayList<Grafica> listaEstadisticas;

    public EstadisticasFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_estadisticas, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        listaEstadisticas = new ArrayList<>();

        view_estadisticas = v.findViewById(R.id.view_estadisticas);
        estadisticasAdapter = new EstadisticasAdapter(getContext());
        view_estadisticas.setAdapter(estadisticasAdapter);


        iv_fragment_estadisticas_perfil = v.findViewById(R.id.iv_fragment_estadisticas_perfil);
        iv_fragment_estadisticas_perfil.setOnClickListener(this);

        agregarEstadisticas();


        return v;
    }


    public void agregarEstadisticas() {

        //Piechart
        List<PieEntry> entries_3 = new ArrayList<>();
        entries_3.add(new PieEntry(33.3f, "Green"));
        entries_3.add(new PieEntry(33.3f, "Yellow"));
        entries_3.add(new PieEntry(33.3f, "Red"));
        PieDataSet set_2 = new PieDataSet(entries_3, "Gráfica 1");
        set_2.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data_1 = new PieData(set_2);
        // refresh
        entries_3 = new ArrayList<>();
        entries_3.add(new PieEntry(50f, "Blue"));
        entries_3.add(new PieEntry(50f, "Red"));
        set_2 = new PieDataSet(entries_3, "Hombre y Mujeres");
        set_2.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data_2 = new PieData(set_2);
        // refresh
        entries_3 = new ArrayList<>();
        entries_3.add(new PieEntry(18.5f, "Green"));
        entries_3.add(new PieEntry(26.7f, "Yellow"));
        entries_3.add(new PieEntry(24.0f, "Red"));
        entries_3.add(new PieEntry(30.8f, "Blue"));
        set_2 = new PieDataSet(entries_3, "Clientes");
        set_2.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data_3 = new PieData(set_2);

        Grafica grafica1 = new Grafica(data_1, "Gráfica 1");
        Grafica grafica2 = new Grafica(data_1, "Hombre y Mujeres");
        Grafica grafica3 = new Grafica(data_1, "Clientes");

        listaEstadisticas.add(grafica1);
        listaEstadisticas.add(grafica2);
        listaEstadisticas.add(grafica3);

        for (Grafica pie_data : listaEstadisticas) {
            estadisticasAdapter.agregarEstadistica(pie_data);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fragment_estadisticas_perfil:
                Intent i = new Intent(getActivity(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.FRAGMENT_ESTADISTICAS);
                startActivity(i);
                getActivity().finish();
                break;
        }
    }
}
