package com.appmoviles.proyecto;

import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.appmoviles.proyecto.util.Constantes;

import java.util.Locale;

public class AgregarMontoFragment extends Fragment implements View.OnClickListener {

    private ImageView iv_fragment_agregar_monto_return;
    private Button btn_fragment_agregar_monto_guardar_monto;
    private EditText et_agregar_monto_monto;

    // Teclado númerico
    private Button btn_agregar_monto_cero;
    private Button btn_agregar_monto_uno;
    private Button btn_agregar_monto_dos;
    private Button btn_agregar_monto_tres;
    private Button btn_agregar_monto_cuatro;
    private Button btn_agregar_monto_cinco;
    private Button btn_agregar_monto_seis;
    private Button btn_agregar_monto_siete;
    private Button btn_agregar_monto_ocho;
    private Button btn_agregar_monto_nueve;
    private Button btn_agregar_monto_borrar;

    private String monto_transaccion;
    private TransaccionesFragment transaccionesFragment;
    private SharedPreferences myPreferences;
    private Fragment fragment;


    public AgregarMontoFragment() {
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
        View v = inflater.inflate(R.layout.fragment_agregar_monto, container, false);

        if (getArguments() != null) {
            fragment = (Fragment) getArguments().getSerializable(Constantes.TRANSACCIONES);
        }

        myPreferences
                = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(getContext());

        iv_fragment_agregar_monto_return = v.findViewById(R.id.iv_fragment_agregar_monto_return);
        btn_fragment_agregar_monto_guardar_monto = v.findViewById(R.id.btn_fragment_agregar_monto_guardar_monto);
        et_agregar_monto_monto = v.findViewById(R.id.et_agregar_monto_monto);

        iv_fragment_agregar_monto_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido, fragment);
                transaction.commit();

            }
        });

        btn_fragment_agregar_monto_guardar_monto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*getActivity().onBackPressed();*/
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.putString(Constantes.MONTO_KEY, monto_transaccion);
                myEditor.commit();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
               /* transaccionesFragment = new TransaccionesFragment();
                // Put information
                listener.onPassClickValue(monto_transaccion);*/
                //Guardar el monto en la app.
                transaction.replace(R.id.contenido, fragment);
                transaction.commit();


            }
        });

        inicializarBotones(v);

        return v;
    }

    private void inicializarBotones(View v) {
        btn_agregar_monto_cero = v.findViewById(R.id.btn_agregar_monto_cero);
        btn_agregar_monto_uno = v.findViewById(R.id.btn_agregar_monto_uno);
        btn_agregar_monto_dos = v.findViewById(R.id.btn_agregar_monto_dos);
        btn_agregar_monto_tres = v.findViewById(R.id.btn_agregar_monto_tres);
        btn_agregar_monto_cuatro = v.findViewById(R.id.btn_agregar_monto_cuatro);
        btn_agregar_monto_cinco = v.findViewById(R.id.btn_agregar_monto_cinco);
        btn_agregar_monto_seis = v.findViewById(R.id.btn_agregar_monto_seis);
        btn_agregar_monto_siete = v.findViewById(R.id.btn_agregar_monto_siete);
        btn_agregar_monto_ocho = v.findViewById(R.id.btn_agregar_monto_ocho);
        btn_agregar_monto_nueve = v.findViewById(R.id.btn_agregar_monto_nueve);
        btn_agregar_monto_borrar = v.findViewById(R.id.btn_agregar_monto_borrar);

        btn_agregar_monto_cero.setOnClickListener(this);
        btn_agregar_monto_uno.setOnClickListener(this);
        btn_agregar_monto_dos.setOnClickListener(this);
        btn_agregar_monto_tres.setOnClickListener(this);
        btn_agregar_monto_cuatro.setOnClickListener(this);
        btn_agregar_monto_cinco.setOnClickListener(this);
        btn_agregar_monto_seis.setOnClickListener(this);
        btn_agregar_monto_siete.setOnClickListener(this);
        btn_agregar_monto_ocho.setOnClickListener(this);
        btn_agregar_monto_nueve.setOnClickListener(this);
        btn_agregar_monto_borrar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        monto_transaccion = et_agregar_monto_monto.getHint().toString();
        char primer_numero = '0';
        int numero_caracteres = 0;
        switch (v.getId()) {
            case R.id.btn_agregar_monto_cero:
                primer_numero = monto_transaccion.charAt(0);
                if (primer_numero != '0') {
                    monto_transaccion = monto_transaccion + "0";
                }
                break;
            case R.id.btn_agregar_monto_borrar:
                numero_caracteres = monto_transaccion.length();
                if (numero_caracteres > 1) {
                    monto_transaccion = monto_transaccion.substring(0, numero_caracteres - 1);
                }
                if (numero_caracteres == 1) {
                    monto_transaccion = "0";
                }
                break;
            default:
                Button numero = (Button) v;
                primer_numero = monto_transaccion.charAt(0);
                if (primer_numero == '0') {
                    monto_transaccion = numero.getText().toString();
                } else {
                    monto_transaccion = monto_transaccion + numero.getText().toString();
                }
        }

        // Formato Decimal
        Long longval;
        if (monto_transaccion.contains(Constantes.PUNTO)) {
            monto_transaccion = monto_transaccion.replaceAll(Constantes.PUNTO, "");
        }
        longval = Long.parseLong(monto_transaccion);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###");
        monto_transaccion = formatter.format(longval);


        if (monto_transaccion.length() > 1 | monto_transaccion.length() == 1 && !monto_transaccion.equals("0")) {
            btn_fragment_agregar_monto_guardar_monto.setEnabled(true);
            btn_fragment_agregar_monto_guardar_monto.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar_activo));
        } else {
            btn_fragment_agregar_monto_guardar_monto.setEnabled(false);
            btn_fragment_agregar_monto_guardar_monto.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar));
        }

        et_agregar_monto_monto.setHint(monto_transaccion);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences.Editor settings = myPreferences.edit();
        settings.putString(Constantes.MONTO_KEY, monto_transaccion);
        // Commit the edits!
        settings.commit();
    }


    //OBSERVER
    public interface OnItemPassListener {
        void onPassClickValue(String monto);
    }
    private OnItemPassListener listener;

    public void setListener(OnItemPassListener listener) {
        this.listener = listener;
    }


}
