package com.appmoviles.proyecto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.appmoviles.proyecto.util.Constantes;

import java.util.Date;


public class AgregarFechaFragment extends Fragment {

    private Button btn_fragment_agregar_fecha_guardar_monto;
    private CalendarView cv_fragment_calendario;

    private TransaccionesFragment transaccionesFragment;
    private String date;

    public AgregarFechaFragment() {
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
        View v = inflater.inflate(R.layout.fragment_agregar_fecha, container, false);

        date = null;
        cv_fragment_calendario = v.findViewById(R.id.cv_fragment_calendario);
        cv_fragment_calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                date = year + "/" + month + "/" + dayOfMonth;
                if (date == null) {
                    btn_fragment_agregar_fecha_guardar_monto.setEnabled(false);
                    btn_fragment_agregar_fecha_guardar_monto.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar));

                } else {
                    btn_fragment_agregar_fecha_guardar_monto.setEnabled(true);
                    btn_fragment_agregar_fecha_guardar_monto.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar_activo));
                }
            }
        });

        btn_fragment_agregar_fecha_guardar_monto = v.findViewById(R.id.btn_fragment_agregar_fecha_guardar_monto);
        btn_fragment_agregar_fecha_guardar_monto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaccionesFragment = new TransaccionesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Utilizado para enviar variables entre dos fragments
                Bundle parametro = new Bundle();
                if (date != null) {
                    parametro.putString(Constantes.FECHA_KEY, date);
                    transaccionesFragment.setArguments(parametro);
                }
                transaction.replace(R.id.contenido, transaccionesFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

}
