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
import android.widget.ImageView;

import com.appmoviles.proyecto.util.Constantes;

import java.io.Serializable;


public class TransaccionesFragment extends Fragment implements View.OnClickListener, Serializable {

    public static final String TRANSACCION = "TRANSACCION";
    private Button btn_fragment_transacciones_agregar_monto;
    private Button btn_fragment_transacciones_clientes_origen;
    private Button btn_fragment_transacciones_clientes_destino;
    private Button btn_fragment_transacciones_agregar_fecha;
    private EditText btn_fragment_transacciones_descripcion_clientes;
    private ImageView tv_fragment_transacciones_foto_origen;
    private ImageView tv_fragment_transacciones_foto_destino;
    private ImageView iv_fragment_transacciones_perfil;

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
        tv_fragment_transacciones_foto_origen = v.findViewById(R.id.tv_fragment_transacciones_foto_origen);
        tv_fragment_transacciones_foto_destino = v.findViewById(R.id.tv_fragment_transacciones_foto_destino);
        iv_fragment_transacciones_perfil = v.findViewById(R.id.iv_fragment_transacciones_perfil);

        // Pedir la información
        myPreferences
                = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(getContext());

        monto = myPreferences.getString(Constantes.MONTO_KEY, Constantes.MONTO_DEFAULT);
        nombre_cliente_origen = myPreferences.getString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE, Constantes.CLIENTE_ORIGEN_DEFAULT_NOMBRE);
        nombre_cliente_destino = myPreferences.getString(Constantes.CLIENTE_DESTINO_KEY_NOMBRE, Constantes.CLIENTE_DESTINO_DEFAULT_NOMBRE);
        fecha = myPreferences.getString(Constantes.FECHA_KEY, Constantes.FECHA_DEFAULT);


        // Setear información

        btn_fragment_transacciones_agregar_monto.setHint(monto);
        btn_fragment_transacciones_clientes_origen.setHint(nombre_cliente_origen);
        btn_fragment_transacciones_agregar_fecha.setHint(fecha);
        btn_fragment_transacciones_clientes_destino.setHint(nombre_cliente_destino);


        // Fotos
       /* if (nombre_cliente_origen != null && !nombre_cliente_origen.equals(Constantes.CLIENTE_ORIGEN_DEFAULT_NOMBRE)) {
            tv_fragment_transacciones_foto_origen.setVisibility(View.VISIBLE);
            tv_fragment_transacciones_foto_origen.setBackgroundResource(R.drawable.cliente_ic_boy);
        }
        if (nombre_cliente_destino != null && !nombre_cliente_destino.equals(Constantes.CLIENTE_DESTINO_DEFAULT_NOMBRE)) {
            tv_fragment_transacciones_foto_destino.setVisibility(View.VISIBLE);
            tv_fragment_transacciones_foto_destino.setBackgroundResource(R.drawable.cliente_ic_adult);
        }*/


        // Acciones
        btn_fragment_transacciones_clientes_origen.setOnClickListener(this);
        btn_fragment_transacciones_clientes_destino.setOnClickListener(this);
        btn_fragment_transacciones_agregar_monto.setOnClickListener(this);
        btn_fragment_transacciones_agregar_fecha.setOnClickListener(this);
        iv_fragment_transacciones_perfil.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        SharedPreferences settings = getActivity().getSharedPreferences(TRANSACCION, 0);
        SharedPreferences.Editor editor = settings.edit();
        switch (v.getId()) {
            case R.id.btn_fragment_transacciones_clientes_origen:
                editor.putString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE, Constantes.CLIENTE_ORIGEN_DEFAULT_NOMBRE);
                transaction.replace(R.id.contenido, seleccionarClienteOrigenFragment);
                transaction.commit();
                break;
            case R.id.btn_fragment_transacciones_clientes_destino:
                editor.putString(Constantes.CLIENTE_DESTINO_KEY_NOMBRE, Constantes.CLIENTE_DESTINO_DEFAULT_NOMBRE);
                transaction.replace(R.id.contenido, seleccionarClienteDestinoFragment);
                transaction.commit();
                break;
            case R.id.btn_fragment_transacciones_agregar_monto:
                editor.putString(Constantes.MONTO_KEY, Constantes.MONTO_DEFAULT);
                transaction.replace(R.id.contenido, agregarMontoFragment);
                transaction.commit();
                break;
            case R.id.btn_fragment_transacciones_agregar_fecha:
                editor.putString(Constantes.FECHA_KEY, Constantes.FECHA_DEFAULT);
                transaction.replace(R.id.contenido, agregarFechaFragment);
                transaction.commit();
                break;
            case R.id.iv_fragment_transacciones_perfil:
                listener.onViewPerfiltransaccion();
                break;
        }
    }

    /*private void displayFragmentFecha(FragmentTransaction transaction) {
        if (agregarFechaFragment.isAdded()) { // if the fragment is already in container
            transaction.show(agregarFechaFragment);
        } else { // fragment needs to be added to frame container
            transaction.add(R.id.contenido, agregarFechaFragment, "A");
        }
        // Hide fragment B
        if (agregarMontoFragment.isAdded()) {
            transaction.hide(agregarMontoFragment);
        }
        // Hide fragment C
        if (seleccionarClienteDestinoFragment.isAdded()) {
            transaction.hide(seleccionarClienteDestinoFragment);
        }
        // Hide fragment D
        if (seleccionarClienteOrigenFragment.isAdded()) {
            transaction.hide(seleccionarClienteOrigenFragment);
        }
        // Commit changes
        transaction.commit();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getActivity().getSharedPreferences(TRANSACCION, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constantes.MONTO_KEY, monto);
        editor.putString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE, nombre_cliente_origen);
        editor.putString(Constantes.CLIENTE_DESTINO_KEY_NOMBRE, nombre_cliente_destino);
        editor.putString(Constantes.FECHA_KEY, fecha);
        // Commit the edits!
        editor.commit();
    }

    //OBSERVER
    public interface OnViewPerfiltransaccion {
        void onViewPerfiltransaccion();
    }
    private OnViewPerfiltransaccion listener;

    public void setListener(OnViewPerfiltransaccion listener) {
        this.listener = listener;
    }



}
