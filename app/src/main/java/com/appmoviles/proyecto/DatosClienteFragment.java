package com.appmoviles.proyecto;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cliente;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.Transaccion;
import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.AdapterDatosBancos;
import com.appmoviles.proyecto.util.AdapterDatosCuentas;
import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.appmoviles.proyecto.util.Constantes.CHILD_TRANSACCIONES;


public class DatosClienteFragment extends Fragment implements Serializable,
        AdapterDatosCuentas.OnItemClickListener,
        AdapterDatosBancos.OnItemClickListener,
        View.OnClickListener, AdapterTemplate_Transacciones.OnItemClickListener {

    private RelativeLayout rl_fragment_clientes_toolbar;
    private EditText et_cuenta_seleccionado;
    private EditText et_banco_seleccionado;
    private TextView tv_fragment_dt_clientes_texto_nombre_cliente;
    private TextView tv_datos_cliente_registro_nombre;
    private RecyclerView lista_bancos_cliente;
    private RecyclerView lista_cuentas_cliente;
    private ImageView iv_down_bancos;
    private ImageView iv_down_cuentas;
    private ImageView iv_fragment_dt_clientes_return;
    private ImageView iv_fragment_dt_clientes_perfil;
    private ArrayList<Banco> listaBancos;
    private ArrayList<Cuenta> listaCuentas;
    private Button btn_fragment_dt_cliente_guardar;
    private boolean selecciono_banco;
    private boolean selecciono_cuenta;
    private boolean down_bancos;
    private boolean down_cuentas;
    private Usuario usuario;
    private Fragment fragment;
    private String donde_viene;
    private String tipo_usuario = "";
    private Banco bancoSeleccionado;

    private AdapterDatosCuentas adapterDatosCuentas;
    private AdapterDatosBancos adapterDatosBancos;
    private Fragment transaccion;
    FirebaseDatabase rtdb;


    // -------------------------
    private RecyclerView lista_transacciones;
    private AdapterTemplate_Transacciones adapterTemplate_transacciones;
    private Cuenta cuentaSeleccionada;
    private ProgressDialog progressDialog;

    // Envio de información
    private OnFragmentInteractionListener listener;


    public DatosClienteFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            usuario = (Usuario) getArguments().getSerializable(Constantes.USUARIO_SERIALIZABLE);
            tipo_usuario = getArguments().getString(Constantes.USUARIO);
            fragment = (Fragment) getArguments().getSerializable(Constantes.CLIENTES);
            transaccion = (Fragment) getArguments().getSerializable(Constantes.TRANSACCIONES_DATOS);
            donde_viene = getArguments().getString(Constantes.DONDE_VIENE);
        }

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_datos_cliente, container, false);

        rtdb = FirebaseDatabase.getInstance();

        lista_bancos_cliente = v.findViewById(R.id.lista_bancos_cliente);
        lista_cuentas_cliente = v.findViewById(R.id.lista_cuentas_cliente);
        et_banco_seleccionado = v.findViewById(R.id.et_banco_seleccionado);
        et_cuenta_seleccionado = v.findViewById(R.id.et_cuenta_seleccionado);
        iv_down_bancos = v.findViewById(R.id.iv_down_bancos);
        iv_down_cuentas = v.findViewById(R.id.iv_down_cuentas);
        rl_fragment_clientes_toolbar = v.findViewById(R.id.rl_fragment_clientes_toolbar);
        tv_datos_cliente_registro_nombre = v.findViewById(R.id.tv_datos_cliente_registro_nombre);
        btn_fragment_dt_cliente_guardar = v.findViewById(R.id.btn_fragment_dt_cliente_guardar);
        tv_fragment_dt_clientes_texto_nombre_cliente = v.findViewById(R.id.tv_fragment_dt_clientes_texto_nombre_cliente);
        iv_fragment_dt_clientes_return = v.findViewById(R.id.iv_fragment_dt_clientes_return);
        iv_fragment_dt_clientes_perfil = v.findViewById(R.id.iv_fragment_dt_clientes_perfil);

        // Lista de transacciones
        lista_transacciones = v.findViewById(R.id.lista_transacciones);
        lista_transacciones.setHasFixedSize(true);
        lista_transacciones.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterTemplate_transacciones = new AdapterTemplate_Transacciones();
        adapterTemplate_transacciones.setListener(this);
        lista_transacciones.setAdapter(adapterTemplate_transacciones);

        listaBancos = usuario.getListaBancos();
        listaCuentas = usuario.getListaCuentas();

        // Adaptador bancos
        adapterDatosBancos = new AdapterDatosBancos();
        lista_bancos_cliente.setHasFixedSize(true);
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext());
        lista_bancos_cliente.setLayoutManager(manager1);
        lista_bancos_cliente.setAdapter(adapterDatosBancos);
        adapterDatosBancos.setListener(this);

        // Adaptador cuentas
        adapterDatosCuentas = new AdapterDatosCuentas();
        adapterDatosCuentas.setListener(this);
        lista_cuentas_cliente.setHasFixedSize(true);
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext());
        lista_cuentas_cliente.setLayoutManager(manager2);
        lista_cuentas_cliente.setAdapter(adapterDatosCuentas);


        btn_fragment_dt_cliente_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    HomeAdministrador activity = (HomeAdministrador) getActivity();
                    if (tipo_usuario.equals(Constantes.USUARIO_DESTINO)) {
                        activity.llamarFragmentMain();
                        listener.onActionUsuario(tipo_usuario, usuario);
                    }
                    if (tipo_usuario.equals(Constantes.USUARIO_ORIGEN)) {
                        activity.llamarFragmentMain();
                        listener.onActionUsuario(tipo_usuario, usuario);
                    }
                }
            }
        });

        iv_down_cuentas.setBackgroundResource(R.drawable.cuenta_informacion_ic_plus);
        iv_down_bancos.setBackgroundResource(R.drawable.cuenta_informacion_ic_plus);
        iv_down_bancos.setOnClickListener(this);
        iv_down_cuentas.setOnClickListener(this);
        iv_fragment_dt_clientes_return.setOnClickListener(this);
        iv_fragment_dt_clientes_perfil.setOnClickListener(this);

        // Visualización si viene del FragmentClientes
        if (donde_viene != null) {
            if (donde_viene.equals(Constantes.FRAGMENT_CLIENTE)) {
                btn_fragment_dt_cliente_guardar.setVisibility(View.GONE);
            }
        }

        actualizarInfo();
        agregarDatos();
        // Configuraciones
        selecciono_banco = false;
        selecciono_cuenta = false;

        return v;
    }


    public void cargarTransacciones() {
        rtdb.getReference().child(CHILD_TRANSACCIONES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Transaccion transaccionTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    transaccionTmp = hijo.getValue(Transaccion.class);
                    if (cuentaSeleccionada != null) {
                        if (transaccionTmp.getCuentaDestinoID().equals(cuentaSeleccionada.getCuentaID())
                                || transaccionTmp.getCuentaOrigenID().equals(cuentaSeleccionada.getCuentaID())) {
                            adapterTemplate_transacciones.agregarTransaccion(transaccionTmp);
                        }
                    }
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void agregarDatos() {
        if (listaBancos != null) {
            for (Banco banco : listaBancos) {
                adapterDatosBancos.agregarBanco(banco);
            }
        }
        if (listaCuentas != null) {
            for (Cuenta cuenta : listaCuentas) {
                adapterDatosCuentas.agregarCuenta(cuenta);
            }
        }
    }

    private void actualizarInfo() {
        if (usuario != null) {
            tv_datos_cliente_registro_nombre.setText(usuario.getNombre());
            tv_fragment_dt_clientes_texto_nombre_cliente.setText(usuario.getNombre());
        }
    }

    @Override
    public void onItemClick(Cuenta cuenta) {
        et_cuenta_seleccionado.setText(cuenta.getNumeroCuenta());
        if (cuenta != null) {
            selecciono_cuenta = true;
            if (selecciono_banco && selecciono_cuenta) {
                btn_fragment_dt_cliente_guardar.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar_activo));
                cuentaSeleccionada = cuenta;

                // Cargar Datos
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Por favor espere mientras se cargan los datos");
                progressDialog.show();
                cargarTransacciones();
            }
        } else {
            selecciono_cuenta = false;
            btn_fragment_dt_cliente_guardar.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar));
        }

    }

    @Override
    public void onItemClick(Banco banco) {
        et_banco_seleccionado.setText(banco.getNombreBanco());
        if (banco != null) {
            bancoSeleccionado = banco;
            selecciono_banco = true;
            filtrarLista(banco);
            if (selecciono_banco && selecciono_cuenta) {
                btn_fragment_dt_cliente_guardar.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar_activo));
            }else{
                et_cuenta_seleccionado.setText("");
            }
        } else {
            selecciono_banco = false;
            btn_fragment_dt_cliente_guardar.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar));
        }
    }

    private void filtrarLista(Banco banco) {
        if (listaCuentas != null && listaCuentas.size() > 0) {
            ArrayList<Cuenta> listaCopia = new ArrayList<>();
            for (Cuenta cuenta : listaCuentas) {
                if (banco.getBancoID().equals(cuenta.getBancoID())) {
                    listaCopia.add(cuenta);
                }
            }
            listaCuentas = listaCopia;

            adapterDatosCuentas.removeAll();
            for (Cuenta cuenta : listaCuentas) {
                adapterDatosCuentas.agregarCuenta(cuenta);
            }
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.iv_down_bancos:
                listaCuentas = usuario.getListaCuentas();
                if (down_bancos) {
                    down_bancos = false;
                    lista_bancos_cliente.setVisibility(View.GONE);
                    rl_fragment_clientes_toolbar.invalidate();
                    iv_down_bancos.setBackgroundResource(R.drawable.cuenta_informacion_ic_plus);
                } else {
                    down_bancos = true;
                    lista_bancos_cliente.setVisibility(View.VISIBLE);
                    rl_fragment_clientes_toolbar.invalidate();
                    iv_down_bancos.setBackgroundResource(R.drawable.cuenta_informacion_ic_minus);
                }

                break;
            case R.id.iv_down_cuentas:
                if (selecciono_banco) {
                    filtrarLista(bancoSeleccionado);
                    if (down_cuentas) {
                        down_cuentas = false;
                        adapterTemplate_transacciones.removeAll();
                        lista_cuentas_cliente.setVisibility(View.GONE);
                        rl_fragment_clientes_toolbar.invalidate();
                        iv_down_cuentas.setBackgroundResource(R.drawable.cuenta_informacion_ic_plus);
                    } else {
                        down_cuentas = true;
                        lista_cuentas_cliente.setVisibility(View.VISIBLE);
                        rl_fragment_clientes_toolbar.invalidate();
                        iv_down_cuentas.setBackgroundResource(R.drawable.cuenta_informacion_ic_minus);
                    }
                }
                break;
            case R.id.iv_fragment_dt_clientes_return:
                if (donde_viene != null) {
                    if (donde_viene.equals(Constantes.FRAGMENT_CLIENTE)) {
                        transaction.replace(R.id.contenido, fragment);
                        transaction.commit();
                    }
                    if (donde_viene.equals(Constantes.TRANSACCIONES_DATOS)) {
                        transaction.replace(R.id.contenido, transaccion);
                        transaction.commit();
                    }
                }
                break;
            case R.id.iv_fragment_dt_clientes_perfil:
                Intent i = new Intent(getActivity(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.DATOS_FRAGMENT);
                i.putExtra(Constantes.DONDE_VIENE, donde_viene);
                DatosClienteFragment datosClienteFragment = new DatosClienteFragment();
                i.putExtra(Constantes.FRAGMENT, datosClienteFragment);
                startActivity(i);
                getActivity().finish();
                break;
        }
    }


    public void setInteractionListener(OnFragmentInteractionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(Transaccion transaccion) {

    }
}
