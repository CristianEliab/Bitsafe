package com.appmoviles.proyecto;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import static com.appmoviles.proyecto.util.Constantes.BUNDLE_ID_BANCO;
import static com.appmoviles.proyecto.util.Constantes.CHILD_BANCOS;
import static com.appmoviles.proyecto.util.Constantes.CHILD_CUENTAS;


public class FinanzasBancosFragment extends Fragment implements AdapterTemplate_Bancos.OnItemClickListener {

    FirebaseDatabase database;
    FirebaseAuth auth;

    private RecyclerView rv_fragment_finanzas_bancos_lista_;
    private AdapterTemplate_Bancos adapterTemplate_bancos;

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

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        View v = inflater.inflate(R.layout.fragment_finanzas_bancos, container, false);

        rv_fragment_finanzas_bancos_lista_ = v.findViewById(R.id.rv_fragment_finanzas_bancos_lista_);
        rv_fragment_finanzas_bancos_lista_.setHasFixedSize(true);
        rv_fragment_finanzas_bancos_lista_.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterTemplate_bancos = new AdapterTemplate_Bancos();
        rv_fragment_finanzas_bancos_lista_.setAdapter(adapterTemplate_bancos);
        adapterTemplate_bancos.setListener(this);

        cargarBancos();

        return v;
    }

    @Override
    public void onItemClick(Banco banco) {

        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_ID_BANCO, banco.getBancoID());

        FinanzasCuentasFragment finanzasCuentasFragment = new FinanzasCuentasFragment();
        finanzasCuentasFragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contenido_cliente, finanzasCuentasFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }

    public void cargarBancos(){
        database.getReference().child(CHILD_BANCOS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Banco bancoTmp;
                for (DataSnapshot hijo: dataSnapshot.getChildren()){
                    bancoTmp = hijo.getValue(Banco.class);
                    adapterTemplate_bancos.agregarBanco(bancoTmp);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
