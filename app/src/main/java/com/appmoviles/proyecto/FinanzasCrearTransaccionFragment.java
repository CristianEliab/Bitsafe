package com.appmoviles.proyecto;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Categoria;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.Transaccion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_CUENTA;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_GASTOS;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_INGRESOS;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_I_O;
import static com.appmoviles.proyecto.util.Constantes.CHILD_TRANSACCIONES;
import static com.appmoviles.proyecto.util.Constantes.CUENTA_DESTINO_ID;
import static com.appmoviles.proyecto.util.Constantes.CUENTA_ORIGEN_ID;
import static com.appmoviles.proyecto.util.Constantes.TRANSACCIONES;


public class FinanzasCrearTransaccionFragment extends Fragment implements Serializable, View.OnClickListener, DialogoEscogerCategoria.DialogoComunicacion, AgregarMontoFragment.InterfaceComunicacion {


    FirebaseDatabase database;
    FirebaseAuth auth;

    private TextView tv_fragment_finanzas_crear_transaccion_titulo;

    private Button btn_fragment_finanzas_crear_transaccion_agregar_monto;
    private Button btn_fragment_finanzas_crear_transaccion_agregar_cagetoria;
    private Button btn_fragment_finanzas_crear_transaccion_agregar_fecha;

    private EditText et_fragment_finanzas_crear_transaccion_descripcion;

    private Button btn_fragment_finanzas_crear_transaccion_cancelar;
    private Button btn_fragment_finanzas_crear_transaccion_guardar;

    private ImageView iv_fragment_finanzas_crear_transaccion_return;

    private AgregarMontoFragment agregarMontoFragment;

    private String tipo_ingreso_o_gasto;
    private Cuenta cuentaSeleccionada;

    private String montoDigitado;
    private Categoria categoriaSeleccionada;
    private int diaSeleccionado;
    private int mesSeleccionado;
    private int anioSeleccionado;
    private String descripcionEscrita;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        View v = inflater.inflate(R.layout.fragment_finanzas_crear_transaccion, container, false);

        tv_fragment_finanzas_crear_transaccion_titulo = v.findViewById(R.id.tv_fragment_finanzas_crear_transaccion_titulo);

        iv_fragment_finanzas_crear_transaccion_return = v.findViewById(R.id.iv_fragment_finanzas_crear_transaccion_return);
        iv_fragment_finanzas_crear_transaccion_return.setOnClickListener(this);

        btn_fragment_finanzas_crear_transaccion_agregar_monto = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_agregar_monto);
        btn_fragment_finanzas_crear_transaccion_agregar_cagetoria = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_agregar_cagetoria);
        btn_fragment_finanzas_crear_transaccion_agregar_fecha = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_agregar_fecha);
        et_fragment_finanzas_crear_transaccion_descripcion = v.findViewById(R.id.et_fragment_finanzas_crear_transaccion_descripcion);

        btn_fragment_finanzas_crear_transaccion_cancelar = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_cancelar);
        btn_fragment_finanzas_crear_transaccion_guardar = v.findViewById(R.id.btn_fragment_finanzas_crear_transaccion_guardar);


        btn_fragment_finanzas_crear_transaccion_agregar_monto.setOnClickListener(this);
        btn_fragment_finanzas_crear_transaccion_agregar_cagetoria.setOnClickListener(this);
        btn_fragment_finanzas_crear_transaccion_agregar_fecha.setOnClickListener(this);

        btn_fragment_finanzas_crear_transaccion_cancelar.setOnClickListener(this);
        btn_fragment_finanzas_crear_transaccion_guardar.setOnClickListener(this);

        diaSeleccionado = -1;
        mesSeleccionado = -1;
        anioSeleccionado = -1;


        if (this.getArguments() != null) {
            tipo_ingreso_o_gasto = (String) getArguments().get(BUNDLE_TIPO_I_O);
            cuentaSeleccionada = (Cuenta) getArguments().getSerializable(BUNDLE_CUENTA);

            tv_fragment_finanzas_crear_transaccion_titulo.setText("Registro de " + tipo_ingreso_o_gasto);
        }

        if (montoDigitado != null) {
            btn_fragment_finanzas_crear_transaccion_agregar_monto.setHint(montoDigitado);
        }


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_fragment_finanzas_crear_transaccion_return:
                getFragmentManager().popBackStack();
                break;

            case R.id.btn_fragment_finanzas_crear_transaccion_agregar_monto:
                agregarMontoFragment = new AgregarMontoFragment();
                agregarMontoFragment.setListenerComunicacion(this);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido_cliente, agregarMontoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;

            case R.id.btn_fragment_finanzas_crear_transaccion_agregar_cagetoria:
                mostrarDialogoEscogerCategoria();
                break;

            case R.id.btn_fragment_finanzas_crear_transaccion_agregar_fecha:
                mostrarDataPicker();
                break;

            case R.id.btn_fragment_finanzas_crear_transaccion_cancelar:
                getFragmentManager().popBackStack();
                break;


            case R.id.btn_fragment_finanzas_crear_transaccion_guardar:
                verificarCamposCompletosCrearTransaccion();
                break;

        }
    }

    private void verificarCamposCompletosCrearTransaccion() {

        boolean completos = true;

        if (montoDigitado == null) {
            completos = false;
            mostrarMensaje("Debe ingresar un monto válido");
        } else if (categoriaSeleccionada == null) {
            completos = false;
            mostrarMensaje("Debe seleccionar una categoría válida");
        }

        //Se verifica que ingrese una fecha válida
        else if (diaSeleccionado == -1 || mesSeleccionado == -1 || anioSeleccionado == -1) {
            completos = false;
            mostrarMensaje("Debe ingresar una fecha válida");
        } else {
            descripcionEscrita = et_fragment_finanzas_crear_transaccion_descripcion.getText().toString();
            if (descripcionEscrita == null || descripcionEscrita.equals("") || descripcionEscrita.trim().equals("")) {
                completos = false;
                mostrarMensaje("Debe ingresar una descripción válida");

            }
        }


        if (completos) {
            Transaccion transaccionNueva = new Transaccion();
            transaccionNueva.setTransaccionID(UUID.randomUUID().toString());
            transaccionNueva.setMontoTransaccion(montoDigitado);
            transaccionNueva.setDescripcion(descripcionEscrita);
            transaccionNueva.setFechaTransaccion(anioSeleccionado + "-" + mesSeleccionado + "-" + diaSeleccionado);
            transaccionNueva.setCategoriaID(categoriaSeleccionada.getCategoriaID());

            //Transacción tipo ingreso o gasto
            if (tipo_ingreso_o_gasto.equals(BUNDLE_TIPO_INGRESOS)) {
                //Ingreso que LLEGA a mi cuenta
                transaccionNueva.setCuentaOrigenID(CUENTA_ORIGEN_ID);
                transaccionNueva.setCuentaDestinoID(cuentaSeleccionada.getCuentaID());
            } else if (tipo_ingreso_o_gasto.equals(BUNDLE_TIPO_GASTOS)) {
                //Gasto que SALE de mi cuenta
                transaccionNueva.setCuentaOrigenID(cuentaSeleccionada.getCuentaID());
                transaccionNueva.setCuentaDestinoID(CUENTA_DESTINO_ID);
            }

            database.getReference().child(CHILD_TRANSACCIONES).push().setValue(transaccionNueva);
            getFragmentManager().popBackStack();
        }
    }

    private void mostrarDialogoEscogerCategoria() {

        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TIPO_I_O, tipo_ingreso_o_gasto);

        FragmentManager fm = getFragmentManager();
        DialogoEscogerCategoria dialogoEscogerCategoria = new DialogoEscogerCategoria();
        dialogoEscogerCategoria.setListener(this);
        dialogoEscogerCategoria.setArguments(bundle);
        dialogoEscogerCategoria.show(fm, "dialogoEscogerCategoria ");
    }

    public void mostrarDataPicker() {

        Calendar calendar = Calendar.getInstance();

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerFragment datePickerFragment = new DatePickerFragment();

        //El datepicker se setea en el día actual
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month", calendar.get(Calendar.MONTH));
        args.putInt("day", calendar.get(Calendar.DAY_OF_MONTH));
        datePickerFragment.setArguments(args);
        datePickerFragment.show(getFragmentManager(), "Date Picker");

        datePickerFragment.setCallBack(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                diaSeleccionado = dayOfMonth;
                mesSeleccionado = month;
                anioSeleccionado = year;

                btn_fragment_finanzas_crear_transaccion_agregar_fecha.setHint(anioSeleccionado + "-" + mesSeleccionado + "-" + diaSeleccionado);
            }
        });
    }

    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }

    @Override
    public void categoriaSeleccionada(Categoria categoria) {
        categoriaSeleccionada = categoria;
        btn_fragment_finanzas_crear_transaccion_agregar_cagetoria.setHint(categoriaSeleccionada.getNombre());
    }

    @Override
    public void montoDigitado(String monto) {
        montoDigitado = monto;
    }
}
