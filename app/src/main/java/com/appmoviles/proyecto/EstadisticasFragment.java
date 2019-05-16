package com.appmoviles.proyecto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class EstadisticasFragment extends Fragment implements Serializable {

    private ImageView iv_fragment_estadisticas_perfil;
    PieChart pieChart;

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


        pieChart = v.findViewById(R.id.pc_fragment_finanzas_gastos);

        iv_fragment_estadisticas_perfil = v.findViewById(R.id.iv_fragment_estadisticas_perfil);
        iv_fragment_estadisticas_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onViewPerfilEstadisticas();
            }
        });

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

        return v;
    }

    //OBSERVER
    public interface OnViewPerfilEstadisticas {
        void onViewPerfilEstadisticas();
    }
    private OnViewPerfilEstadisticas listener;

    public void setListener(OnViewPerfilEstadisticas listener) {
        this.listener = listener;
    }
}
