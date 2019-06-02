package com.appmoviles.proyecto;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.appmoviles.proyecto.util.Constantes.CHILD_CUENTAS;


public class CuentasFragment extends Fragment implements Serializable, AdapterTemplate_CuentasVinculadas.OnItemClickListener {

    private ImageView iv_fragment_cuentas_perfil;
    FirebaseDatabase database;
    FirebaseAuth auth;

    private ImageButton btn_vincular_cuenta;
    private RecyclerView rv_fragment_cuentas_bancos_lista_;
    private AdapterTemplate_CuentasVinculadas adapterTemplate_cuentasVinculadas;
    private List<Cuenta> listaCuentas;

    private ProgressDialog progressDialog;

    public CuentasFragment() {
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
        View v= inflater.inflate(R.layout.fragment_cuentas, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        btn_vincular_cuenta = v.findViewById(R.id.btn_vincular_cuenta);
        btn_vincular_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main, new VincularCuentaFragment()).addToBackStack(null).commit();
            }
        });


        rv_fragment_cuentas_bancos_lista_ = v.findViewById(R.id.rv_fragment_cuentas_bancos_lista_);
        rv_fragment_cuentas_bancos_lista_.setHasFixedSize(true);
        rv_fragment_cuentas_bancos_lista_.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterTemplate_cuentasVinculadas = new AdapterTemplate_CuentasVinculadas();
        rv_fragment_cuentas_bancos_lista_.setAdapter(adapterTemplate_cuentasVinculadas);
        adapterTemplate_cuentasVinculadas.setListener(this);

        iv_fragment_cuentas_perfil = v.findViewById(R.id.iv_fragment_cuentas_perfil);

        iv_fragment_cuentas_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.FRAGMENT_CUENTAS);
                startActivity(i);
                getActivity().finish();

            }
        });

        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setMessage("Por favor espere mientras se cargan los datos");
        progressDialog.show();
        return v;
    }



    public void cargarCuentas() {

        listaCuentas = new ArrayList<Cuenta>();
        database.getReference().child(CHILD_CUENTAS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cuenta cuentaTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    cuentaTmp = hijo.getValue(Cuenta.class);
                    //Solo las cuentas que pertenezcan al usuario logueado
                    if (cuentaTmp.getUsuarioID().equals(auth.getCurrentUser().getUid())) {
                        listaCuentas.add(cuentaTmp);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    public void onItemClick(Cuenta cuenta) {

    }
}