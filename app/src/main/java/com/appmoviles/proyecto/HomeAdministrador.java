package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.appmoviles.proyecto.util.Constantes;

import java.io.Serializable;

public class HomeAdministrador extends FragmentActivity implements
        Serializable {

    private ClientesFragment clientesFragment;
    private EstadisticasFragment estadisticasFragment;
    private TransaccionesFragment transaccionesFragment;
    private CargarDatosFragment cargarDatosFragment;
    private PerfilCliente perfilCliente;
    private String donde_viene = "";
    FragmentManager manager;


    // Sub fragments
    private AgregarMontoFragment agregarMontoFragment;
    private SeleccionarClienteFragment seleccionarClienteFragment;
    private DatosClienteFragment datosClienteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_administrador);

        Intent i = getIntent();
        if (i != null) {
            donde_viene = i.getStringExtra(Constantes.FRAGMENT);
        }

        clientesFragment = new ClientesFragment();
        cargarDatosFragment = new CargarDatosFragment();
        estadisticasFragment = new EstadisticasFragment();
        transaccionesFragment = new TransaccionesFragment();
        perfilCliente = new PerfilCliente();

        // Sub fragments
        agregarMontoFragment = new AgregarMontoFragment();
        agregarMontoFragment.setInteractionListener(transaccionesFragment);
        seleccionarClienteFragment = new SeleccionarClienteFragment();
        datosClienteFragment = new DatosClienteFragment();
        datosClienteFragment.setInteractionListener(transaccionesFragment);


        primerFragment();

        BottomNavigationView navigation = findViewById(R.id.home_admin_navigation);
        navigation.setSelectedItemId(R.id.menubar_clientes);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                switch (menuItem.getItemId()) {
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

    private void primerFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (donde_viene != null) {
            if (donde_viene.equals(Constantes.FRAGMENT_CLIENTE)) {
                transaction.replace(R.id.contenido, clientesFragment);
                transaction.commit();
            }
            if(donde_viene.equals(Constantes.FRAGMENT_ESTADISTICAS)){
                transaction.replace(R.id.contenido, estadisticasFragment);
                transaction.commit();
            }
            if(donde_viene.equals(Constantes.FRAGMENT_TRANSACCION)){
                transaction.replace(R.id.contenido, transaccionesFragment);
                transaction.commit();
            }
            if(donde_viene.equals(Constantes.FRAGMENT_CARGAR)){
                transaction.replace(R.id.contenido, cargarDatosFragment);
                transaction.commit();
            }
        } else {
            transaction.replace(R.id.contenido, clientesFragment);
            transaction.commit();
        }

    }

    public void llamarFragmentAgregarMonto() {
        manager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        Bundle parametro = new Bundle();
        parametro.putSerializable(Constantes.TRANSACCIONES, transaccionesFragment);
        agregarMontoFragment.setArguments(parametro);
        fragmentTransaction.replace(R.id.contenido, agregarMontoFragment);
        fragmentTransaction.commit();
    }

    public void llamarFragmentClienteOrigin() {
        manager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        Bundle parametro = new Bundle();
        parametro.putSerializable(Constantes.TRANSACCIONES, transaccionesFragment);
        parametro.putString(Constantes.USUARIO, Constantes.USUARIO_ORIGEN);
        seleccionarClienteFragment.setArguments(parametro);
        fragmentTransaction.replace(R.id.contenido, seleccionarClienteFragment);
        fragmentTransaction.commit();
    }

    public void llamarFragmentClienteDestino() {
        manager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        Bundle parametro = new Bundle();
        parametro.putSerializable(Constantes.TRANSACCIONES, transaccionesFragment);
        parametro.putString(Constantes.USUARIO, Constantes.USUARIO_DESTINO);
        seleccionarClienteFragment.setArguments(parametro);
        fragmentTransaction.replace(R.id.contenido, seleccionarClienteFragment);
        fragmentTransaction.commit();
    }

    public void llamarFragmentMain() {
        manager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.contenido, transaccionesFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
