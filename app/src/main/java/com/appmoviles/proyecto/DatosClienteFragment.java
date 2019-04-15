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
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Cliente;
import com.appmoviles.proyecto.util.Constantes;

import java.util.ArrayList;
import java.util.List;


public class DatosClienteFragment extends Fragment {

    private Spinner sp_datos_cliente_bancos;
    private Spinner sp_datos_cliente_cuentas;
    private List<String> listaBancos;
    private List<String> listaCuentas;
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
        btn_fragment_dt_cliente_guardar = v.findViewById(R.id.btn_fragment_dt_cliente_guardar);

        //Banco bc1 = new Banco("Bancolombia");
        //Banco bc2 = new Banco("Banco Bogota");
        //Banco bc3 = new Banco("Davivienda");

        //Cuenta cb1 = new Cuenta("1234-1456-2345-4668");
        //Cuenta cb2 = new Cuenta("1234-1456-2345-4568");
        //Cuenta cb3 = new Cuenta("1234-1456-2345-4468");
        //Cuenta cb4 = new Cuenta("1234-1456-2345-4768");

        listaBancos = new ArrayList<String>();
        listaBancos.add("Bancolombia");
        listaBancos.add("Banco Bogota");
        listaBancos.add("Davivienda");

        listaCuentas = new ArrayList<String>();
        listaCuentas.add("1234-1456-2345-4668");
        listaCuentas.add("1234-1456-2345-4568");
        listaCuentas.add("1234-1456-2345-4468");
        listaCuentas.add("1234-1456-2345-4768");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.simple_spinner_item, listaBancos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_datos_cliente_bancos.setAdapter(dataAdapter);


        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(),
                R.layout.simple_spinner_item, listaCuentas);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_datos_cliente_cuentas.setAdapter(dataAdapter2);


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
