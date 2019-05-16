package com.appmoviles.proyecto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appmoviles.proyecto.modelo.Cliente;
import com.appmoviles.proyecto.util.AdapterTemplate_SlClientes;
import com.appmoviles.proyecto.util.Constantes;

import java.util.ArrayList;


public class SeleccionarClienteOrigenFragment extends Fragment implements AdapterTemplate_SlClientes.OnItemClickListener {

    private ImageView fb_fragment_sl_cliente_agregar_cliente;
    private RecyclerView libreta;
    private AdapterTemplate_SlClientes adapter;
    private ArrayList<Cliente> clientes;
    private DatosClienteFragment datosClienteFragment;
    private Cliente cliente;

    private ImageView iv_fragment_sl_clientes_return;

    public SeleccionarClienteOrigenFragment() {
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
        View v = inflater.inflate(R.layout.fragment_seleccionar_cliente_origen, container, false);

        clientes = new ArrayList<>();

        // Pruba de la vista de los clientes
        final Cliente cliente_p_1 = new Cliente("Cristian");
        Cliente cliente_p_2 = new Cliente("Steven");
        Cliente cliente_p_3 = new Cliente("Alejandra");
        Cliente cliente_p_4 = new Cliente("Johan");
        Cliente cliente_p_5 = new Cliente("Toto");

        clientes.add(cliente_p_1);
        clientes.add(cliente_p_2);
        clientes.add(cliente_p_3);
        clientes.add(cliente_p_4);
        clientes.add(cliente_p_5);

        libreta = v.findViewById(R.id.lista_sl_clientes);
        adapter = new AdapterTemplate_SlClientes();
        libreta.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        libreta.setLayoutManager(manager);
        libreta.setAdapter(adapter);

        adapter.setListener(this);


        iv_fragment_sl_clientes_return = v.findViewById(R.id.iv_fragment_sl_clientes_return);
        iv_fragment_sl_clientes_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datosClienteFragment = new DatosClienteFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido, datosClienteFragment);
                transaction.commit();
            }
        });

        fb_fragment_sl_cliente_agregar_cliente = v.findViewById(R.id.fb_fragment_sl_cliente_agregar_cliente);
        fb_fragment_sl_cliente_agregar_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datosClienteFragment = new DatosClienteFragment();
                //listener.onPassClickClienteOrigen(cliente);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Utilizado para enviar variables entre dos fragments
                Bundle parametro = new Bundle();
                if (cliente != null) {
                    parametro.putString(Constantes.CLIENTE_ORIGEN_KEY_NOMBRE, cliente.nombre);
                    datosClienteFragment.setArguments(parametro);
                }
                transaction.replace(R.id.contenido, datosClienteFragment);
                transaction.commit();
            }
        });
        agregarClientes();
        return v;
    }

    private void agregarClientes() {
        for (Cliente cliente : clientes) {
            adapter.agregarCliente(cliente);
        }
    }

    @Override
    public void onItemClick(Cliente amigo) {
        cliente = amigo;
        if (cliente != null) {
            fb_fragment_sl_cliente_agregar_cliente.setEnabled(true);
            fb_fragment_sl_cliente_agregar_cliente.setBackgroundResource(R.drawable.fragment_cliente_circular_seleccion);
        } else {
            fb_fragment_sl_cliente_agregar_cliente.setEnabled(false);
            fb_fragment_sl_cliente_agregar_cliente.setBackgroundResource(R.drawable.fragment_cliente_circular);
        }
    }


    //OBSERVER
    public interface OnItemPassClienteOrigen {
        void onPassClickClienteOrigen(String monto);
    }
    private OnItemPassClienteOrigen listener;

    public void setListener(OnItemPassClienteOrigen listener) {
        this.listener = listener;
    }
}
