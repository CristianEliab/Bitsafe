package com.appmoviles.proyecto;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

public class HomeCliente  extends FragmentActivity {

    private CuentasFragment cuentasFragment;
    private PlanesFragment planesFragment;
    private FinanzasFragment finanzasFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);

        cuentasFragment = new CuentasFragment();
        planesFragment = new PlanesFragment();
        finanzasFragment = new FinanzasFragment();

        BottomNavigationView navigation = findViewById(R.id.home_clientes_navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.menubar_finanzas:
                        transaction.replace(R.id.contenido, finanzasFragment);
                        transaction.commit();
                        break;
                    case R.id.menubar_planes:
                        transaction.replace(R.id.contenido, planesFragment);
                        transaction.commit();
                        break;
                    case R.id.menubar_cuentas:
                        transaction.replace(R.id.contenido, cuentasFragment);
                        transaction.commit();
                        break;
                }
                return true;
            }
        });
    }
}
