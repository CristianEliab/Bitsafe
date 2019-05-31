package com.appmoviles.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.Constantes;
import com.appmoviles.proyecto.util.Consultas;
import com.appmoviles.proyecto.util.EstadisticasAdapter;
import com.appmoviles.proyecto.util.EstadisticasGenero;
import com.appmoviles.proyecto.util.EstadisticasMes;
import com.appmoviles.proyecto.util.Grafica;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class EstadisticasFragment extends Fragment implements Serializable, View.OnClickListener {

    private ImageView iv_fragment_estadisticas_perfil;
    private ViewPager view_estadisticas;
    private EstadisticasAdapter estadisticasAdapter;
    private ArrayList<Grafica> listaEstadisticas;
    FirebaseDatabase rtdb;
    Consultas consultas;
    private HashMap<String, EstadisticasMes> estadisticasMesHashMap;
    private HashMap<String, EstadisticasGenero> estadisticasGeneroHashMap;
    private ArrayList<Usuario> usuarioArrayList;

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

        rtdb = FirebaseDatabase.getInstance();
        listaEstadisticas = new ArrayList<>();

        view_estadisticas = v.findViewById(R.id.view_estadisticas);
        estadisticasAdapter = new EstadisticasAdapter(getContext());
        view_estadisticas.setAdapter(estadisticasAdapter);


        iv_fragment_estadisticas_perfil = v.findViewById(R.id.iv_fragment_estadisticas_perfil);
        iv_fragment_estadisticas_perfil.setOnClickListener(this);

        consultas = Consultas.getInstance();
        consultas.calcularRegistrosFecha();
        consultas.calcularGeneros();
        estadisticasMesHashMap = consultas.getDiccionario();
        estadisticasGeneroHashMap = consultas.getDiccionaioGenero();
        usuarioArrayList = consultas.getListaUsuario();

        agregarEstadisticas();

        return v;
    }


    public void agregarEstadisticas() {

        //Piechart
        List<PieEntry> entries_3 = new ArrayList<>();
        PieDataSet set_2 = new PieDataSet(entries_3, "Edades");
        // refresh
        float numero_femenino = estadisticasGeneroHashMap.get(Constantes.FEMENINO).getNumero_femeninos();
        float numero_masculino = estadisticasGeneroHashMap.get(Constantes.MASCULINO).getNumero_masculinos();
        float total = listaEstadisticas.size();
        float porcentajeFemenino = ((numero_femenino / total) * 100);
        float porcentajeMasculino = ((numero_masculino / total) * 100);
        entries_3 = new ArrayList<>();
        entries_3.add(new PieEntry(numero_femenino, "F"));
        entries_3.add(new PieEntry(numero_masculino, "M"));

        set_2 = new PieDataSet(entries_3, "Hombre y Mujeres");
        set_2.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data_2 = new PieData(set_2);
        // refresh
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, estadisticasMesHashMap.get("01").getNumeroClientes(), "En"));
        entries.add(new BarEntry(1f, estadisticasMesHashMap.get("02").getNumeroClientes(), "Fe"));
        entries.add(new BarEntry(2f, estadisticasMesHashMap.get("03").getNumeroClientes(), "Ma"));
        entries.add(new BarEntry(3f, estadisticasMesHashMap.get("04").getNumeroClientes(), "Ab"));
        entries.add(new BarEntry(4f, estadisticasMesHashMap.get("05").getNumeroClientes(), "Ma"));
        entries.add(new BarEntry(5f, estadisticasMesHashMap.get("06").getNumeroClientes(), "Jn"));
        entries.add(new BarEntry(6f, estadisticasMesHashMap.get("07").getNumeroClientes(), "Jl"));
        entries.add(new BarEntry(7f, estadisticasMesHashMap.get("08").getNumeroClientes(), "Ag"));
        entries.add(new BarEntry(8f, estadisticasMesHashMap.get("09").getNumeroClientes(), "Sp"));
        entries.add(new BarEntry(9f, estadisticasMesHashMap.get("10").getNumeroClientes(), "Oc"));
        entries.add(new BarEntry(10f, estadisticasMesHashMap.get("11").getNumeroClientes(), "No"));
        entries.add(new BarEntry(11f, estadisticasMesHashMap.get("12").getNumeroClientes(), "Dc"));
        BarDataSet set = new BarDataSet(entries, "Clientes por Mes");
        set.setColors(ColorTemplate.PASTEL_COLORS);
        BarData data_3 = new BarData(set);
        // end Barchart
        Grafica grafica2 = new Grafica(Constantes.TIPO_PIE, data_2, null, "Gráfica de Generos");
        Grafica grafica3 = new Grafica(Constantes.TIPO_BAR, null, data_3, "Gráfica de Clientes por Mes");

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
