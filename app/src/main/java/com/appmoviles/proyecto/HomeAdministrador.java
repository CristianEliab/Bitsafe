package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.appmoviles.proyecto.util.Constantes;

public class HomeAdministrador extends FragmentActivity implements ClientesFragment.OnViewPerfilCliente,
        EstadisticasFragment.OnViewPerfilEstadisticas,
        CargarDatosFragment.OnViewPerfilCargar,
        TransaccionesFragment.OnViewPerfiltransaccion {

    private ClientesFragment clientesFragment;
    private EstadisticasFragment estadisticasFragment;
    private TransaccionesFragment transaccionesFragment;
    private CargarDatosFragment cargarDatosFragment;
    private PerfilCliente perfilCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_administrador);

        clientesFragment = new ClientesFragment();
        clientesFragment.setListener(this);
        cargarDatosFragment = new CargarDatosFragment();
        cargarDatosFragment.setListener(this);
        estadisticasFragment = new EstadisticasFragment();
        estadisticasFragment.setListener(this);
        transaccionesFragment = new TransaccionesFragment();
        transaccionesFragment.setListener(this);
        perfilCliente = new PerfilCliente();

        BottomNavigationView navigation = findViewById(R.id.home_admin_navigation);

        navigation.setSelectedItemId(R.id.menubar_clientes);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenido, clientesFragment);
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
        super.onBackPressed();
    }

    @Override
    public void onViewPerfilCliente() {
        Bundle clave = new Bundle();
        clave.putString(Constantes.GO_TO_PERFIL, Constantes.CLIENTES);
        clave.putSerializable(Constantes.FRAGMENT, clientesFragment);
        perfilCliente.setArguments(clave);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenido, perfilCliente);
        transaction.commit();
    }

    @Override
    public void onViewPerfilEstadisticas() {
        Bundle clave = new Bundle();
        clave.putString(Constantes.GO_TO_PERFIL, Constantes.ESTADISTICAS);
        clave.putSerializable(Constantes.FRAGMENT, estadisticasFragment);
        perfilCliente.setArguments(clave);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenido, perfilCliente);
        transaction.commit();
    }

    @Override
    public void onViewPerfilCargar() {
        Bundle clave = new Bundle();
        clave.putString(Constantes.GO_TO_PERFIL, Constantes.CARGAR);
        clave.putSerializable(Constantes.FRAGMENT, cargarDatosFragment);
        perfilCliente.setArguments(clave);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenido, perfilCliente);
        transaction.commit();
    }

    @Override
    public void onViewPerfiltransaccion() {
        Bundle clave = new Bundle();
        clave.putString(Constantes.GO_TO_PERFIL, Constantes.TRANSACCIONES);
        clave.putSerializable(Constantes.FRAGMENT, transaccionesFragment);
        perfilCliente.setArguments(clave);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenido, perfilCliente);
        transaction.commit();
    }
}
