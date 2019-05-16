package com.appmoviles.proyecto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.Transaccion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_BANCO;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_CUENTA;
import static com.appmoviles.proyecto.util.Constantes.CHILD_CUENTAS;
import static com.appmoviles.proyecto.util.Constantes.CHILD_TRANSACCIONES;

public class FinanzasCuentasFragment extends Fragment implements AdapterTemplate_Cuentas.OnItemClickListener {

    FirebaseDatabase database;
    FirebaseAuth auth;

    private RecyclerView rv_fragment_finanzas_cuentas_lista;
    private AdapterTemplate_Cuentas adapterTemplate_cuentas;
    private FinanzasTransaccionesFragment finanzasTransaccionesFragment;
    private TextView tv_fragment_finanzas_cuentas_titulo;

    private Banco bancoSeleccionado;

    public FinanzasCuentasFragment() {
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


        View v = inflater.inflate(R.layout.fragment_finanzas_cuentas, container, false);

        tv_fragment_finanzas_cuentas_titulo = v.findViewById(R.id.tv_fragment_finanzas_cuentas_titulo);

        rv_fragment_finanzas_cuentas_lista = v.findViewById(R.id.rv_fragment_finanzas_cuentas_lista);
        adapterTemplate_cuentas = new AdapterTemplate_Cuentas();
        adapterTemplate_cuentas.setListener(this);
        rv_fragment_finanzas_cuentas_lista.setHasFixedSize(true);
        rv_fragment_finanzas_cuentas_lista.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_fragment_finanzas_cuentas_lista.setAdapter(adapterTemplate_cuentas);


        if (this.getArguments() != null) {
            bancoSeleccionado  = (Banco) getArguments().get(BUNDLE_BANCO);

            tv_fragment_finanzas_cuentas_titulo.setText("Perteneciente al Banco: " + bancoSeleccionado.getNombreBanco());
        }

        cargarCuentas();

        return v;
    }

    @Override
    public void onItemClick(Cuenta cuenta) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_CUENTA, cuenta);

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

    public void cargarCuentas() {
        database.getReference().child(CHILD_CUENTAS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cuenta cuentaTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    cuentaTmp = hijo.getValue(Cuenta.class);
                    //Solo se agregan las cuentas del banco seleccionado y que pertenezcan al usuario logueado
                    if (cuentaTmp.getBancoID().equals(bancoSeleccionado.getBancoID()) && cuentaTmp.getUsuarioID().equals(auth.getCurrentUser().getUid())) {
                        cuentaTmp.setNumeroCuenta("Cuenta #: " + cuentaTmp.getNumeroCuenta());
                        adapterTemplate_cuentas.agregarCuenta(cuentaTmp);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
