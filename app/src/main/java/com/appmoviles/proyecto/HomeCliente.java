package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.appmoviles.proyecto.util.Constantes;

public class HomeCliente extends FragmentActivity {

    private CuentasFragment cuentasFragment;
    private PlanesFragment planesFragment;
    private FinanzasFragment finanzasFragment;
    private PerfilCliente perfilCliente;
    private String donde_viene = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        Intent i = getIntent();
        if (i != null) {
            donde_viene = i.getStringExtra(Constantes.FRAGMENT);
        }

        finanzasFragment = new FinanzasFragment();
        planesFragment = new PlanesFragment();
        cuentasFragment = new CuentasFragment();
        perfilCliente = new PerfilCliente();

        primerFragment();

        BottomNavigationView navigation = findViewById(R.id.home_clientes_navigation);
        navigation.setSelectedItemId(R.id.menubar_finanzas);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.menubar_finanzas:
                        transaction.replace(R.id.contenido_cliente, finanzasFragment);
                        transaction.commit();
                        break;
                    case R.id.menubar_planes:
                        transaction.replace(R.id.contenido_cliente, planesFragment);
                        transaction.commit();
                        break;
                    case R.id.menubar_cuentas:
                        transaction.replace(R.id.contenido_cliente, cuentasFragment);
                        transaction.commit();
                        break;
                }
                return true;
            }
        });
    }

    private void primerFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (donde_viene != null) {
            if (donde_viene.equals(Constantes.FRAGMENT_FINANZAS)) {
                transaction.replace(R.id.contenido_cliente, finanzasFragment);
                transaction.commit();
            }
            if(donde_viene.equals(Constantes.FRAGMENT_PLANES)){
                transaction.replace(R.id.contenido_cliente, planesFragment);
                transaction.commit();
            }
            if(donde_viene.equals(Constantes.FRAGMENT_CUENTAS)){
                transaction.replace(R.id.contenido_cliente, cuentasFragment);
                transaction.commit();
            }
        } else {
            transaction.replace(R.id.contenido_cliente, finanzasFragment);
            transaction.commit();
        }
    }
}
