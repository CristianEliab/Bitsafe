package com.appmoviles.proyecto;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.appmoviles.proyecto.util.Constantes;

public class HomeCliente extends FragmentActivity implements FinanzasFragment.OnViewPerfil {

    private CuentasFragment cuentasFragment;
    private PlanesFragment planesFragment;
    private FinanzasFragment finanzasFragment;
    private PerfilCliente perfilCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        finanzasFragment = new FinanzasFragment();
        finanzasFragment.setListener(this);
        planesFragment = new PlanesFragment();
        cuentasFragment = new CuentasFragment();
        perfilCliente = new PerfilCliente();


        BottomNavigationView navigation = findViewById(R.id.home_clientes_navigation);

        navigation.setSelectedItemId(R.id.menubar_finanzas);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenido_cliente, finanzasFragment);
        transaction.commit();


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

    @Override
    public void onViewPerfil() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        Bundle clave = new Bundle();
        clave.putString(Constantes.GO_TO_PERFIL, Constantes.FINANZAS);
        clave.putSerializable(Constantes.FRAGMENT, finanzasFragment);
        perfilCliente.setArguments(clave);

        transaction.replace(R.id.contenido_cliente, perfilCliente);
        transaction.commit();

    }
}
