package com.appmoviles.proyecto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


public class DatosClienteFragment extends Fragment {

    private TextView tv_datos_cliente_registro_nombre;
    private TextView tv_fragment_dt_clientes_texto_actividad;
    private Spinner sp_datos_cliente_bancos;
    private Spinner sp_datos_cliente_cuentas;
    private ArrayList<Banco> listaBancos;
    private ArrayList<Cuenta> listaCuentas;
    private Cliente cliente;
    private Button btn_fragment_dt_cliente_guardar;
    private String cliente_origen = "";

    private TransaccionesFragment transaccionesFragment;

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

        sp_datos_cliente_bancos = v.findViewById(R.id.sp_datos_cliente_bancos);
        sp_datos_cliente_cuentas = v.findViewById(R.id.sp_datos_cliente_cuentas);
        tv_fragment_dt_clientes_texto_actividad = v.findViewById(R.id.tv_fragment_dt_clientes_texto_actividad);
        tv_datos_cliente_registro_nombre = v.findViewById(R.id.tv_datos_cliente_registro_nombre);
        btn_fragment_dt_cliente_guardar = v.findViewById(R.id.btn_fragment_dt_cliente_guardar);


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
        AdapterDatosBancos adapterDatosBancos = new AdapterDatosBancos(getContext(), listaBancos);
        sp_datos_cliente_bancos.setAdapter(adapterDatosBancos);
        // Adaptador cuentas
        AdapterDatosCuentas adapterDatosCuentas = new AdapterDatosCuentas(getContext(), listaCuentas);
        sp_datos_cliente_cuentas.setAdapter(adapterDatosCuentas);


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

        actualizarInfo();

        tv_datos_cliente_registro_nombre.setText(cliente_origen);
        tv_fragment_dt_clientes_texto_actividad.setText(Constantes.CLIENTE_CUENTA + cliente_origen);

        return v;
    }

    private void actualizarInfo() {
        // Con el método getArguments() obtengo la información enviada por parametro.
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            cliente_origen = bundle.getString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE);
        }
    }

}
