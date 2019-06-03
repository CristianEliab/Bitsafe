package com.appmoviles.proyecto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Transaccion;
import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;


public class TransaccionesFragment extends Fragment implements View.OnClickListener, Serializable, OnFragmentInteractionListener {

    public static final String TRANSACCION = "TRANSACCION";
    private Button btn_fragment_transacciones_agregar_monto;
    private Button btn_fragment_transacciones_clientes_origen;
    private Button btn_fragment_transacciones_clientes_destino;
    private Button btn_fragment_transacciones_agregar_fecha;
    private EditText et_fragment_transacciones_descripcion_clientes;
    private ImageView tv_fragment_transacciones_foto_origen;
    private ImageView tv_fragment_transacciones_foto_destino;
    private ImageView iv_fragment_transacciones_perfil;
    private ImageView guardar_transacciones;

    private String monto;
    private Usuario usuario_origen;
    private Usuario usuario_destino;
    private String fecha;
    private String descripcion = "";
    private int diaSeleccionado = 0;
    private int mesSeleccionado = 0;
    private int anioSeleccionado = 0;

    private SharedPreferences myPreferences;
    FirebaseDatabase rtdb;


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

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        btn_fragment_transacciones_agregar_monto = v.findViewById(R.id.btn_fragment_transacciones_agregar_monto);
        btn_fragment_transacciones_clientes_origen = v.findViewById(R.id.btn_fragment_transacciones_clientes_origen);
        btn_fragment_transacciones_clientes_destino = v.findViewById(R.id.btn_fragment_transacciones_clientes_destino);
        btn_fragment_transacciones_agregar_fecha = v.findViewById(R.id.btn_fragment_transacciones_agregar_fecha);
        et_fragment_transacciones_descripcion_clientes = v.findViewById(R.id.et_fragment_transacciones_descripcion_clientes);
        tv_fragment_transacciones_foto_origen = v.findViewById(R.id.tv_fragment_transacciones_foto_origen);
        tv_fragment_transacciones_foto_destino = v.findViewById(R.id.tv_fragment_transacciones_foto_destino);
        iv_fragment_transacciones_perfil = v.findViewById(R.id.iv_fragment_transacciones_perfil);
        guardar_transacciones = v.findViewById(R.id.guardar_transacciones);


        // Base de Datos
        rtdb = FirebaseDatabase.getInstance();


        // Pedir la información
        myPreferences
                = (SharedPreferences) PreferenceManager.getDefaultSharedPreferences(getContext());

        if (usuario_origen != null) {
            btn_fragment_transacciones_clientes_origen.setHint(usuario_origen.getNombre());
        }
        if (usuario_destino != null) {
            btn_fragment_transacciones_clientes_destino.setHint(usuario_destino.getNombre());
        }
        if (monto != null) {
            btn_fragment_transacciones_agregar_monto.setHint(monto);
        }
        if (fecha != null) {
            btn_fragment_transacciones_agregar_fecha.setHint(fecha);
        }
        if (descripcion != null) {
            et_fragment_transacciones_descripcion_clientes.setText(descripcion);
        }


        // Acciones
        btn_fragment_transacciones_clientes_origen.setOnClickListener(this);
        btn_fragment_transacciones_clientes_destino.setOnClickListener(this);
        btn_fragment_transacciones_agregar_monto.setOnClickListener(this);
        btn_fragment_transacciones_agregar_fecha.setOnClickListener(this);
        iv_fragment_transacciones_perfil.setOnClickListener(this);
        guardar_transacciones.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        HomeAdministrador activity = (HomeAdministrador) getActivity();
        switch (v.getId()) {
            case R.id.btn_fragment_transacciones_clientes_origen:
                activity = (HomeAdministrador) getActivity();
                activity.llamarFragmentClienteOrigin();
                break;
            case R.id.btn_fragment_transacciones_clientes_destino:
                activity = (HomeAdministrador) getActivity();
                activity.llamarFragmentClienteDestino();
                break;
            case R.id.btn_fragment_transacciones_agregar_monto:
                activity = (HomeAdministrador) getActivity();
                activity.llamarFragmentAgregarMonto();
                break;
            case R.id.btn_fragment_transacciones_agregar_fecha:
                DatePickerFragment date = new DatePickerFragment();
                date.setInteractionListener(this);
                Calendar calender = Calendar.getInstance();
                Bundle args = new Bundle();
                args.putInt("year", calender.get(Calendar.YEAR));
                args.putInt("month", calender.get(Calendar.MONTH));
                args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
                date.setArguments(args);
                date.setCallBack(ondate);

                FragmentManager manager = getActivity().getSupportFragmentManager();
                date.show(manager, "Date Picker");
                break;
            case R.id.iv_fragment_transacciones_perfil:
                Intent i = new Intent(getActivity(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.FRAGMENT_TRANSACCION);
                usuario_destino = null;
                usuario_origen = null;
                monto = null;
                fecha = null;
                descripcion = null;
                startActivity(i);
                getActivity().finish();
                break;
            case R.id.guardar_transacciones:
                guardarTransaccion();
                break;
        }
    }

    private void guardarTransaccion() {
        if (comprobarInformacion()) {

            final Transaccion envio = crearTransaccion(Constantes.CATEGORIA_ENVIO_ID);
            final Transaccion recepcion = crearTransaccion(Constantes.CATEGORIA_RECEPCION_ID);

            AlertDialog.Builder info = new AlertDialog.Builder(getContext());
            info.setTitle(R.string.guardar);
            info.setMessage(R.string.confirmacion);
            info.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    rtdb.getReference().child(Constantes.CHILD_TRANSACCIONES).push().setValue(envio);
                    rtdb.getReference().child(Constantes.CHILD_TRANSACCIONES).push().setValue(recepcion);
                    usuario_origen = null;
                    usuario_destino = null;
                    monto = null;
                    fecha = null;
                    descripcion = null;

                    HomeAdministrador activity = (HomeAdministrador) getActivity();
                    Toast.makeText(getContext(), "Se creo satisfactoriamente", Toast.LENGTH_LONG).show();
                    activity.llamarFragmentClienteDestino();
                }
            });
            info.show();


        }
    }

    private Transaccion crearTransaccion(String categoriaID) {
        Transaccion transaccionNueva = new Transaccion();
        if (categoriaID.equals(Constantes.CATEGORIA_ENVIO_ID)) {
            transaccionNueva.setTransaccionID(UUID.randomUUID().toString());
            transaccionNueva.setMontoTransaccion(monto);
            descripcion = et_fragment_transacciones_descripcion_clientes.getText().toString();
            transaccionNueva.setDescripcion(descripcion);
            transaccionNueva.setFechaTransaccion(anioSeleccionado + "-" + mesSeleccionado + "-" + diaSeleccionado);
            transaccionNueva.setCategoriaID(Constantes.CATEGORIA_ENVIO_ID);
            // Cuentas
            transaccionNueva.setCuentaOrigenID(usuario_origen.getListaCuentas().get(0).getCuentaID());
            transaccionNueva.setCuentaDestinoID(Constantes.CUENTA_DESTINO_ID);
        }
        if (categoriaID.equals(Constantes.CATEGORIA_RECEPCION_ID)) {
            transaccionNueva.setTransaccionID(UUID.randomUUID().toString());
            transaccionNueva.setMontoTransaccion(monto);
            descripcion = et_fragment_transacciones_descripcion_clientes.getText().toString();
            transaccionNueva.setDescripcion(descripcion);
            transaccionNueva.setFechaTransaccion(anioSeleccionado + "-" + mesSeleccionado + "-" + diaSeleccionado);
            transaccionNueva.setCategoriaID(Constantes.CATEGORIA_RECEPCION_ID);
            // Cuentas
            transaccionNueva.setCuentaOrigenID(Constantes.CUENTA_ORIGEN_ID);
            transaccionNueva.setCuentaDestinoID(usuario_destino.getListaCuentas().get(0).getCuentaID());
        }
        return transaccionNueva;
    }

    private boolean comprobarInformacion() {
        boolean agregar = false;
        if (!monto.equals("") &&
                usuario_origen != null &&
                usuario_destino != null &&
                diaSeleccionado != 0) {
            agregar = true;
        } else {
            Toast.makeText(getContext(), "Ingrese cada uno de los campos", Toast.LENGTH_SHORT).show();
        }
        return agregar;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getActivity().getSharedPreferences(TRANSACCION, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constantes.MONTO_KEY, monto);
        if (usuario_origen != null) {
            editor.putString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE, usuario_origen.getNombre());
        }
        if (usuario_destino != null) {
            editor.putString(Constantes.CLIENTE_DESTINO_KEY_NOMBRE, usuario_destino.getNombre());
        }
        editor.putString(Constantes.FECHA_KEY, fecha);
        // Commit the edits!
        editor.commit();
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            diaSeleccionado = dayOfMonth;
            mesSeleccionado = monthOfYear;
            anioSeleccionado = year;
            btn_fragment_transacciones_agregar_fecha.setHint(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));
        }
    };

    @Override
    public void onAction(Object origin, Object message) {
        if (origin instanceof AgregarMontoFragment) {
            monto = (String) message;
        }
        if (origin instanceof DatePickerFragment) {
            fecha = (String) message;
        }
    }

    @Override
    public void onActionUsuario(String tipo, Object usuario) {
        if (tipo.equals(Constantes.USUARIO_ORIGEN)) {
            usuario_origen = (Usuario) usuario;
        }
        if (tipo.equals(Constantes.USUARIO_DESTINO)) {
            usuario_destino = (Usuario) usuario;
        }
    }


    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public Usuario getUsuario_origen() {
        return usuario_origen;
    }

    public void setUsuario_origen(Usuario usuario_origen) {
        this.usuario_origen = usuario_origen;
    }

    public Usuario getUsuario_destino() {
        return usuario_destino;
    }

    public void setUsuario_destino(Usuario usuario_destino) {
        this.usuario_destino = usuario_destino;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
