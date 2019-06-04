package com.appmoviles.proyecto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.appmoviles.proyecto.modelo.PlanAhorro;
import com.appmoviles.proyecto.util.AdapterTemplate_PlanesAhorro;
import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_PLANES_AHORRO;
import static com.appmoviles.proyecto.util.Constantes.CHILD_PLANES_AHORRO;

import java.io.Serializable;


public class PlanesFragment extends Fragment implements Serializable,AdapterTemplate_PlanesAhorro.OnItemClickListener, View.OnClickListener{

    private ImageButton btn_crear_ahorro;
    private ImageView iv_fragment_planes_perfil;

    FirebaseDatabase database;
    FirebaseAuth auth;

    private String userId;
    private AdapterTemplate_PlanesAhorro adapterTemplate_planesAhorro;
    private RecyclerView rv__planes_ahorro_lista;

    public PlanesFragment() {
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
        View v = inflater.inflate(R.layout.fragment_planes, container, false);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        btn_crear_ahorro = (ImageButton) v.findViewById(R.id.btn_crear_ahorro);

        rv__planes_ahorro_lista = v.findViewById(R.id.rv_planes_ahorro_lista);
        rv__planes_ahorro_lista.setHasFixedSize(true);
        rv__planes_ahorro_lista.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterTemplate_planesAhorro = new AdapterTemplate_PlanesAhorro();
        rv__planes_ahorro_lista.setAdapter(adapterTemplate_planesAhorro);
        adapterTemplate_planesAhorro.setListener(this);

        cargarPlanes();

        btn_crear_ahorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearPlanFragment crearPlanFragment = new CrearPlanFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.contenido_cliente, crearPlanFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        iv_fragment_planes_perfil = v.findViewById(R.id.iv_fragment_planes_perfil);
        iv_fragment_planes_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cuando se presiona el botón perfil
                Intent i = new Intent(getContext(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.FRAGMENT_PLANES);
                startActivity(i);
                getActivity().finish();
            }
        });


        return v;
    }

    private void cargarPlanes() {

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            userId = firebaseUser.getUid();
        }

        database.getReference().child(CHILD_PLANES_AHORRO).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PlanAhorro plan;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    plan = hijo.getValue(PlanAhorro.class);
                    if (plan.getUsuarioID().equals(userId)) {
                        adapterTemplate_planesAhorro.agregarPlanAhorro(plan);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.e("entro a todo","////////////////////////////////////////////////////////***************************************************************");
        /*
        switch (v.getId()) {
            case R.id.iv_fragment_finanzas_bancos_return:
                getFragmentManager().popBackStack();
                break;
        }
        */
    }

    @Override
    public void onItemClick(PlanAhorro planAhorro) {
        Log.e("entro a item","////////////////////////////////////////////////////////***************************************************************");

        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_PLANES_AHORRO, planAhorro);

        EditarPlanFragment editarPlanFragment = new EditarPlanFragment();
        editarPlanFragment.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.contenido_cliente, editarPlanFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
