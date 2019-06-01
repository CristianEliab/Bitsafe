package com.appmoviles.proyecto;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_BANCO;
import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_I_O;
import static com.appmoviles.proyecto.util.Constantes.CHILD_BANCOS;
import static com.appmoviles.proyecto.util.Constantes.CHILD_CUENTAS;

public class FinanzasBancosFragment extends Fragment implements AdapterTemplate_Bancos.OnItemClickListener, View.OnClickListener {

    FirebaseDatabase database;
    FirebaseAuth auth;

    private TextView tv_fragment_finanzas_bancos_actividad;
    private ImageView iv_fragment_finanzas_bancos_return;
    private RecyclerView rv_fragment_finanzas_bancos_lista_;
    private AdapterTemplate_Bancos adapterTemplate_bancos;
    private List<Cuenta> listaCuentas;

    private String tipo_ingreso_o_gasto;

    private ProgressDialog progressDialog;

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

        tv_fragment_finanzas_bancos_actividad = v.findViewById(R.id.tv_fragment_finanzas_bancos_actividad);

        iv_fragment_finanzas_bancos_return = v.findViewById(R.id.iv_fragment_finanzas_bancos_return);
        iv_fragment_finanzas_bancos_return.setOnClickListener(this);

        rv_fragment_finanzas_bancos_lista_ = v.findViewById(R.id.rv_fragment_finanzas_bancos_lista_);
        rv_fragment_finanzas_bancos_lista_.setHasFixedSize(true);
        rv_fragment_finanzas_bancos_lista_.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterTemplate_bancos = new AdapterTemplate_Bancos();
        rv_fragment_finanzas_bancos_lista_.setAdapter(adapterTemplate_bancos);
        adapterTemplate_bancos.setListener(this);

        if(this.getArguments() != null){
            //Corresponde a si la ventana se comportará como INGRESOS o GASTOS (para el título)
            tipo_ingreso_o_gasto = (String)getArguments().get(BUNDLE_TIPO_I_O);
            tv_fragment_finanzas_bancos_actividad.setText("Bancos " + tipo_ingreso_o_gasto);
        }

        progressDialog = new ProgressDialog(v.getContext());
        progressDialog.setMessage("Por favor espere mientras se cargan los datos");
        progressDialog.show();

        //Independientemente si quiere ver ingresos o gastos se cargan los bancos
        cargarCuentas();
        cargarBancos();

        return v;
    }

    @Override
    public void onItemClick(Banco banco) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_BANCO, banco);
        bundle.putString(BUNDLE_TIPO_I_O, tipo_ingreso_o_gasto);

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

    public void cargarBancos() {
        database.getReference().child(CHILD_BANCOS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Banco bancoTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    bancoTmp = hijo.getValue(Banco.class);

                    for (int i = 0; i < listaCuentas.size(); i++) {

                        String bancoID = listaCuentas.get(i).getBancoID();

                        //Solo los bancos que tienen cuentas del usuario registrado
                        if (bancoTmp.getBancoID().equals(bancoID) && !adapterTemplate_bancos.contains(bancoTmp)) {
                            adapterTemplate_bancos.agregarBanco(bancoTmp);
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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_fragment_finanzas_bancos_return:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
