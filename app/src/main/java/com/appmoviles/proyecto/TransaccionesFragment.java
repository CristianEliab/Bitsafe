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
import android.widget.EditText;

import com.appmoviles.proyecto.util.Constantes;


public class TransaccionesFragment extends Fragment {

    private Button btn_fragment_transacciones_agregar_monto;
    private Button btn_fragment_transacciones_clientes_origen;
    private Button btn_fragment_transacciones_clientes_destino;
    private Button btn_fragment_transacciones_agregar_fecha;
    private EditText btn_fragment_transacciones_descripcion_clientes;

    private AgregarMontoFragment agregarMontoFragment;
    private SeleccionarClienteOrigenFragment seleccionarClienteOrigenFragment;
    private SeleccionarClienteDestinoFragment seleccionarClienteDestinoFragment;
    private AgregarFechaFragment agregarFechaFragment;

    private String monto;
    private String nombre_cliente_origen;
    private String nombre_cliente_destino;
    private String fecha;

    private SharedPreferences myPreferences;


    public TransaccionesFragment() {
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
        View v = inflater.inflate(R.layout.fragment_transacciones, container, false);

        //Fragments subsecuentes
        agregarMontoFragment = new AgregarMontoFragment();
        seleccionarClienteOrigenFragment = new SeleccionarClienteOrigenFragment();
        seleccionarClienteDestinoFragment = new SeleccionarClienteDestinoFragment();
        agregarFechaFragment = new AgregarFechaFragment();


        btn_fragment_transacciones_agregar_monto = v.findViewById(R.id.btn_fragment_transacciones_agregar_monto);
        btn_fragment_transacciones_clientes_origen = v.findViewById(R.id.btn_fragment_transacciones_clientes_origen);
        btn_fragment_transacciones_clientes_destino = v.findViewById(R.id.btn_fragment_transacciones_clientes_destino);
        btn_fragment_transacciones_agregar_fecha = v.findViewById(R.id.btn_fragment_transacciones_agregar_fecha);
        btn_fragment_transacciones_descripcion_clientes = v.findViewById(R.id.btn_fragment_transacciones_descripcion_clientes);

        // Con el método getArguments() obtengo la información enviada por parametro.
        savedInstanceState = this.getArguments();
        if (savedInstanceState != null) {

            myPreferences
                    = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(getContext());

            monto = savedInstanceState.getString(Constantes.MONTO_KEY, "" + monto);
            nombre_cliente_origen = savedInstanceState.getString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE, "" + nombre_cliente_origen);
            nombre_cliente_destino = savedInstanceState.getString(Constantes.CLIENTE_DESTINO_KEY_NOMBRE, ""+ nombre_cliente_destino);
            fecha = savedInstanceState.getString(Constantes.FECHA_KEY, "" + fecha);
        } else {
            monto = Constantes.MONTO_DEFAULT;
            nombre_cliente_origen = Constantes.CLIENTE_ORIGEN_DEFAULT_NOMBRE;
            nombre_cliente_destino = Constantes.CLIENTE_DESTINO_DEFAULT_NOMBRE;
            fecha = Constantes.FECHA_DEFAULT;
        }


        btn_fragment_transacciones_agregar_monto.setHint(monto);
        btn_fragment_transacciones_clientes_origen.setHint(nombre_cliente_origen);
        btn_fragment_transacciones_clientes_destino.setHint(nombre_cliente_destino);
        btn_fragment_transacciones_agregar_fecha.setHint(fecha);


        btn_fragment_transacciones_clientes_origen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido, seleccionarClienteOrigenFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_fragment_transacciones_clientes_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido, seleccionarClienteDestinoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_fragment_transacciones_agregar_monto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido, agregarMontoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btn_fragment_transacciones_agregar_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido, agregarFechaFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return v;
    }

}
