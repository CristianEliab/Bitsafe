package com.appmoviles.proyecto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.appmoviles.proyecto.util.AdapterDatosBancos;
import com.appmoviles.proyecto.util.AdapterDatosCuentas;
import com.appmoviles.proyecto.util.Constantes;

import java.util.ArrayList;
import java.util.List;


public class DatosClienteFragment extends Fragment implements AdapterDatosCuentas.OnItemClickListener, AdapterDatosBancos.OnItemClickListener, View.OnClickListener {

    private RelativeLayout rl_fragment_clientes_toolbar;
    private EditText et_cuenta_seleccionado;
    private EditText et_banco_seleccionado;
    private TextView tv_fragment_dt_clientes_texto_nombre_cliente;
    private TextView tv_datos_cliente_registro_nombre;
    private TextView tv_fragment_dt_clientes_texto_actividad;
    private RecyclerView lista_bancos_cliente;
    private RecyclerView lista_cuentas_cliente;
    private ImageView iv_down_bancos;
    private ImageView iv_down_cuentas;
    private ArrayList<Banco> listaBancos;
    private ArrayList<Cuenta> listaCuentas;
    private Cliente cliente;
    private EditText btn_fragment_dt_cliente_guardar;
    private String cliente_origen = "";
    private boolean selecciono_banco;
    private boolean selecciono_cuenta;
    private boolean down_bancos;
    private boolean down_cuentas;

    private TransaccionesFragment transaccionesFragment;
    private AdapterDatosCuentas adapterDatosCuentas;
    private AdapterDatosBancos adapterDatosBancos;

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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_datos_cliente, container, false);

        lista_bancos_cliente = v.findViewById(R.id.lista_bancos_cliente);
        lista_cuentas_cliente = v.findViewById(R.id.lista_cuentas_cliente);
        et_banco_seleccionado = v.findViewById(R.id.et_banco_seleccionado);
        et_cuenta_seleccionado = v.findViewById(R.id.et_cuenta_seleccionado);
        iv_down_bancos = v.findViewById(R.id.iv_down_bancos);
        iv_down_cuentas = v.findViewById(R.id.iv_down_cuentas);
        rl_fragment_clientes_toolbar = v.findViewById(R.id.rl_fragment_clientes_toolbar);
        tv_fragment_dt_clientes_texto_actividad = v.findViewById(R.id.tv_fragment_dt_clientes_texto_actividad);
        tv_datos_cliente_registro_nombre = v.findViewById(R.id.tv_datos_cliente_registro_nombre);
        btn_fragment_dt_cliente_guardar = v.findViewById(R.id.btn_fragment_dt_cliente_guardar);
        tv_fragment_dt_clientes_texto_nombre_cliente = v.findViewById(R.id.tv_fragment_dt_clientes_texto_nombre_cliente);


        listaBancos = new ArrayList<Banco>();
        Banco b1 = new Banco();
        b1.setNombreBanco("Bancolombia");
        Banco b2 = new Banco();
        b2.setNombreBanco("Banco Bogota");
        Banco b3 = new Banco();
        b3.setNombreBanco("Davivienda");
        listaBancos.add(b1);
        listaBancos.add(b2);
        listaBancos.add(b3);

        listaCuentas = new ArrayList<Cuenta>();
        Cuenta c1 = new Cuenta();
        c1.setNumeroCuenta("2222-3333-4444-5555");
        Cuenta c2 = new Cuenta();
        c2.setNumeroCuenta("1234-1456-2345-4668");
        Cuenta c3 = new Cuenta();
        c3.setNumeroCuenta("5555-1231-5357-4564");
        Cuenta c4 = new Cuenta();
        c4.setNumeroCuenta("8888-1231-5357-4564");
        listaCuentas.add(c1);
        listaCuentas.add(c2);
        listaCuentas.add(c3);
        listaCuentas.add(c4);

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

        btn_fragment_dt_cliente_guardar.setFocusable(false);
        btn_fragment_dt_cliente_guardar.setClickable(false);
        btn_fragment_dt_cliente_guardar.setKeyListener(null);
        btn_fragment_dt_cliente_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaccionesFragment = new TransaccionesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Utilizado para enviar variables entre dos fragments
                Bundle parametro = new Bundle();
                if (cliente_origen != null) {
                    parametro.putString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE, cliente_origen);
                    transaccionesFragment.setArguments(parametro);
                }
                transaction.replace(R.id.contenido, transaccionesFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        iv_down_cuentas.setBackgroundResource(R.drawable.cuenta_informacion_ic_plus);
        iv_down_bancos.setBackgroundResource(R.drawable.cuenta_informacion_ic_plus);
        iv_down_bancos.setOnClickListener(this);
        iv_down_cuentas.setOnClickListener(this);


        actualizarInfo();
        agregarDatos();

        // Configuraciones
        selecciono_banco = false;
        selecciono_cuenta = false;
        tv_datos_cliente_registro_nombre.setText(cliente_origen);
        tv_fragment_dt_clientes_texto_nombre_cliente.setText(cliente_origen);


        return v;
    }

    private void agregarDatos() {
        for (Banco banco : listaBancos) {
            adapterDatosBancos.agregarBanco(banco);
        }
        for (Cuenta cuenta : listaCuentas) {
            adapterDatosCuentas.agregarCuenta(cuenta);
        }
    }

    private void actualizarInfo() {
        // Con el método getArguments() obtengo la información enviada por parametro.
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            cliente_origen = bundle.getString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE);
        }
    }

    @Override
    public void onItemClick(Cuenta cuenta) {
        et_cuenta_seleccionado.setText(cuenta.getNumeroCuenta());
        if (cuenta != null) {
            selecciono_cuenta = true;
            if (selecciono_banco && selecciono_cuenta) {
                btn_fragment_dt_cliente_guardar.setFocusable(true);
                btn_fragment_dt_cliente_guardar.setClickable(true);
                btn_fragment_dt_cliente_guardar.setEnabled(true);
                btn_fragment_dt_cliente_guardar.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar_activo));
            }
        } else {
            selecciono_cuenta = false;
            btn_fragment_dt_cliente_guardar.setFocusable(false);
            btn_fragment_dt_cliente_guardar.setEnabled(false);
            btn_fragment_dt_cliente_guardar.setClickable(false);
            btn_fragment_dt_cliente_guardar.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar));
        }

    }

    @Override
    public void onItemClick(Banco banco) {
        et_banco_seleccionado.setText(banco.getNombreBanco());
        if (banco != null) {
            selecciono_banco = true;
            if (selecciono_banco && selecciono_cuenta) {
                btn_fragment_dt_cliente_guardar.setFocusable(true);
                btn_fragment_dt_cliente_guardar.setClickable(true);
                btn_fragment_dt_cliente_guardar.setEnabled(true);
                btn_fragment_dt_cliente_guardar.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar_activo));
            }
        } else {
            selecciono_banco = false;
            btn_fragment_dt_cliente_guardar.setFocusable(false);
            btn_fragment_dt_cliente_guardar.setClickable(false);
            btn_fragment_dt_cliente_guardar.setEnabled(false);
            btn_fragment_dt_cliente_guardar.setBackground(getResources().getDrawable(R.drawable.fragment_agregar_monto_figura_btn_guardar));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_down_bancos:
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
                if (down_cuentas) {
                    down_cuentas = false;
                    lista_cuentas_cliente.setVisibility(View.GONE);
                    rl_fragment_clientes_toolbar.invalidate();
                    iv_down_cuentas.setBackgroundResource(R.drawable.cuenta_informacion_ic_plus);
                } else {
                    down_cuentas = true;
                    lista_cuentas_cliente.setVisibility(View.VISIBLE);
                    rl_fragment_clientes_toolbar.invalidate();
                    iv_down_cuentas.setBackgroundResource(R.drawable.cuenta_informacion_ic_minus);
                }
                break;
        }
    }
}
