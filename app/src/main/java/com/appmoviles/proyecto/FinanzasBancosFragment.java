package com.appmoviles.proyecto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Banco;

import org.w3c.dom.Text;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_ID_BANCO;


public class FinanzasBancosFragment extends Fragment implements AdapterTemplate_Bancos.OnItemClickListener {


    private RecyclerView rv_fragment_finanzas_bancos_lista_;
    private AdapterTemplate_Bancos adapterTemplate_bancos;
    private FinanzasTransaccionesFragment finanzasTransaccionesFragment;


    public FinanzasBancosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_finanzas_bancos, container, false);

        rv_fragment_finanzas_bancos_lista_ = v.findViewById(R.id.rv_fragment_finanzas_bancos_lista_);
        rv_fragment_finanzas_bancos_lista_.setHasFixedSize(true);
        rv_fragment_finanzas_bancos_lista_.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterTemplate_bancos = new AdapterTemplate_Bancos();
        rv_fragment_finanzas_bancos_lista_.setAdapter(adapterTemplate_bancos);
        adapterTemplate_bancos.setListener(this);

        Banco b1 = new Banco();
        b1.setNombreBanco("Banco 1");
        b1.setBancoID(" ID 1");

        Banco b2 = new Banco();
        b2.setNombreBanco("Banco 2");
        b2.setBancoID(" ID 2");

        Banco b3 = new Banco();
        b3.setNombreBanco("Banco 3");
        b3.setBancoID(" ID 3");

        Banco b4 = new Banco();
        b4.setNombreBanco("Banco 4");
        b4.setBancoID(" ID 4");

        Banco b5 = new Banco();
        b5.setNombreBanco("Banco 5");
        b5.setBancoID(" ID 5");

        Banco b6 = new Banco();
        b6.setNombreBanco("Banco 6");
        b6.setBancoID(" ID 6");

        Banco b7 = new Banco();
        b7.setNombreBanco("Banco 7");
        b7.setBancoID(" ID 7");

        Banco b8 = new Banco();
        b8.setNombreBanco("Banco 8");
        b8.setBancoID(" ID 8");

        Banco b9 = new Banco();
        b9.setNombreBanco("Banco 9");
        b9.setBancoID(" ID 9");

        Banco b10 = new Banco();
        b10.setNombreBanco("Banco 10");
        b10.setBancoID(" ID 10");

        adapterTemplate_bancos.agregarBanco(b1);
        adapterTemplate_bancos.agregarBanco(b2);
        adapterTemplate_bancos.agregarBanco(b3);
        adapterTemplate_bancos.agregarBanco(b4);
        adapterTemplate_bancos.agregarBanco(b5);
        adapterTemplate_bancos.agregarBanco(b6);
        adapterTemplate_bancos.agregarBanco(b7);
        adapterTemplate_bancos.agregarBanco(b8);
        adapterTemplate_bancos.agregarBanco(b9);
        adapterTemplate_bancos.agregarBanco(b10);

        return v;
    }

    @Override
    public void onItemClick(Banco banco) {

        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID_BANCO, banco.getBancoID());

        finanzasTransaccionesFragment = new FinanzasTransaccionesFragment();
        finanzasTransaccionesFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contenido_cliente, finanzasTransaccionesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }
}
