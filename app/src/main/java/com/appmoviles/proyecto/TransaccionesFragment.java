package com.appmoviles.proyecto;

import android.os.Bundle;
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

        actualizarVariebles();

        return v;
    }

    private void actualizarVariebles() {
        // Con el método getArguments() obtengo la información enviada por parametro.
        String monto = "";
        String cliente_origen = "";
        String cliente_destino = "";
        String fecha = "";
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            // monto
            monto = bundle.getString(Constantes.MONTO_KEY);
            // Cliente Origen
            cliente_origen = bundle.getString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE);
            // Cliente Destino
            cliente_destino = bundle.getString(Constantes.CLIENTE_DESTINO_KEY_NOMBRE);
            // Fecha
            fecha = bundle.getString(Constantes.FECHA_KEY);
        }
        if (monto != null && !monto.trim().equals("")) {
            btn_fragment_transacciones_agregar_monto.setHint(monto);
        }

        if (cliente_origen != null && !cliente_origen.equals("")) {
            btn_fragment_transacciones_clientes_origen.setHint(cliente_origen);
        }

        if (cliente_destino != null && !cliente_destino.equals("")) {
            btn_fragment_transacciones_clientes_destino.setHint(cliente_destino);
        }

        if (fecha != null && !fecha.equals("")) {
            btn_fragment_transacciones_agregar_fecha.setHint(fecha);
        }
    }

}
