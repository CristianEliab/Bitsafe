package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.appmoviles.proyecto.util.Constantes;

public class MiCuentaPerfil extends AppCompatActivity {

    private ImageView iv_fragment_micuenta_clientes;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta_perfil);


        Intent i = getIntent();
        Bundle bundle =  i.getBundleExtra(Constantes.GO_TO_PERFIL);
        fragment = (Fragment) bundle.getSerializable(Constantes.FRAGMENT);

        iv_fragment_micuenta_clientes = findViewById(R.id.iv_fragment_micuenta_clientes);


        iv_fragment_micuenta_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerfilCliente perfilCliente = new PerfilCliente();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.contenido_cliente, fragment);
                transaction.commit();
            }
        });
    }
}
