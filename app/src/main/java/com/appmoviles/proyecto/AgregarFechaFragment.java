package com.appmoviles.proyecto;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;

import com.appmoviles.proyecto.util.Constantes;

import java.util.Date;


public class AgregarFechaFragment extends Fragment {

    private Button btn_fragment_agregar_fecha_guardar_monto;
    private ImageView iv_fragment_agregar_fecha_return;
    private CalendarView cv_fragment_calendario;

    private TransaccionesFragment transaccionesFragment;
    private String date;

    private SharedPreferences myPreferences;


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

        myPreferences
                = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(getContext());
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

        iv_fragment_agregar_fecha_return = v.findViewById(R.id.iv_fragment_agregar_fecha_return);
        iv_fragment_agregar_fecha_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaccionesFragment = new TransaccionesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido, transaccionesFragment);
                transaction.addToBackStack(null);
                transaction.commit();
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
                    //Guardar el monto en la app.
                    SharedPreferences.Editor myEditor = myPreferences.edit();
                    myEditor.putString(Constantes.FECHA_KEY, date);
                    myEditor.commit();
                    listener.onPassFechaValue(date);
                }
                transaction.replace(R.id.contenido, transaccionesFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

    //OBSERVER
    public interface OnItemPassFecha {
        void onPassFechaValue(String monto);
    }
    private OnItemPassFecha listener;

    public void setListener(OnItemPassFecha listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences.Editor settings = myPreferences.edit();
        settings.putString(Constantes.FECHA_KEY, date);
        // Commit the edits!
        settings.commit();
    }

}
