package com.appmoviles.proyecto;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class HomeAdministrador extends FragmentActivity{

    private ClientesFragment clientesFragment;
    private EstadisticasFragment estadisticasFragment;
    private TransaccionesFragment transaccionesFragment;
    private CargarDatosFragment cargarDatosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_administrador);

        clientesFragment = new ClientesFragment();
        cargarDatosFragment = new CargarDatosFragment();
        estadisticasFragment = new EstadisticasFragment();
        transaccionesFragment = new TransaccionesFragment();

        BottomNavigationView navigation = findViewById(R.id.home_admin_navigation);

        navigation.setSelectedItemId(R.id.menubar_clientes);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenido, clientesFragment);
        transaction.addToBackStack("");
        transaction.commit();

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.menubar_clientes:
                        transaction.replace(R.id.contenido, clientesFragment);
                        transaction.commit();
                        break;
                    case R.id.menubar_cargar:
                        transaction.replace(R.id.contenido, cargarDatosFragment);
                        transaction.commit();
                        break;
                    case R.id.menubar_estadisticas:
                        transaction.replace(R.id.contenido, estadisticasFragment);
                        transaction.commit();
                        break;
                    case R.id.menubar_transacciones:
                        transaction.replace(R.id.contenido, transaccionesFragment);
                        transaction.commit();
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }


}
