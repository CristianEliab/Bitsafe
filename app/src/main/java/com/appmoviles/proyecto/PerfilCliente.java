package com.appmoviles.proyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;


public class PerfilCliente extends AppCompatActivity implements Serializable {

    private TextView tv_nombre_iniciales;
    private TextView tv_nombre;
    private TextView tv_telefono;
    private ImageView image_atras;
    private TextView tv_micuenta_perfil_cliente;
    private TextView tv_ajustes_perfil_cliente;
    private TextView tv_ayuda_perfil_cliente;
    private TextView tv_informacion_perfil_cliente;
    private ImageView iv_fragment_perfil_clientes_return;
    private TextView tv_nombre_cliente_perfil_cliente;
    private TextView tv_numero_telefono_perfil_cliente;
    private TextView tv_cerrar_perfil_cliente_txt;
    private ImageView iv_cerrar_perfil_cliente;


    FirebaseAuth auth;
    FirebaseDatabase rtdb;
    Fragment fragment;

    private String volver_a;

    public PerfilCliente() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perfil_cliente);

        // Iniciar todos los fragmentes
        Intent i = getIntent();
        if (i != null) {
            volver_a = i.getStringExtra(Constantes.GO_TO_PERFIL);
            fragment = (Fragment) i.getSerializableExtra(Constantes.FRAGMENT);
        }

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        tv_nombre_iniciales = findViewById(R.id.iv_nombre_perfil_cliente);
        tv_nombre = findViewById(R.id.tv_nombre_cliente_perfil_cliente);
        tv_telefono = findViewById(R.id.tv_numero_telefono_perfil_cliente);
        image_atras = findViewById(R.id.iv_cerrar_perfil_cliente);
        iv_fragment_perfil_clientes_return = findViewById(R.id.iv_fragment_perfil_clientes_return);
        tv_nombre_cliente_perfil_cliente = findViewById(R.id.tv_nombre_cliente_perfil_cliente);
        tv_numero_telefono_perfil_cliente = findViewById(R.id.tv_numero_telefono_perfil_cliente);
        tv_cerrar_perfil_cliente_txt = findViewById(R.id.tv_cerrar_perfil_cliente_txt);
        iv_cerrar_perfil_cliente = findViewById(R.id.iv_cerrar_perfil_cliente);

        iv_fragment_perfil_clientes_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (volver_a.equals(Constantes.FRAGMENT_FINANZAS)) {
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_FINANZAS);
                    startActivity(i);
                    finish();
                }
                if (volver_a.equals(Constantes.FRAGMENT_PLANES)) {
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_PLANES);
                    startActivity(i);
                    finish();
                }
                if (volver_a.equals(Constantes.FRAGMENT_CUENTAS)) {
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CUENTAS);
                    startActivity(i);
                    finish();
                }
                if(volver_a.equals(Constantes.FRAGMENT_CLIENTE)){
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CLIENTE);
                    startActivity(i);
                    finish();
                }
                if(volver_a.equals(Constantes.FRAGMENT_ESTADISTICAS)){
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_ESTADISTICAS);
                    startActivity(i);
                    finish();
                }
                if(volver_a.equals(Constantes.FRAGMENT_TRANSACCION)){
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_TRANSACCION);
                    startActivity(i);
                    finish();
                }
                if(volver_a.equals(Constantes.FRAGMENT_CARGAR)){
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CARGAR);
                    startActivity(i);
                    finish();
                }
            }
        });


        tv_micuenta_perfil_cliente = findViewById(R.id.tv_micuenta_perfil_cliente);
        tv_micuenta_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilCliente.this, MiCuentaPerfil.class);
                startActivity(i);
                finish();
            }
        });

        tv_ajustes_perfil_cliente = findViewById(R.id.tv_ajustes_perfil_cliente);
        tv_ajustes_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilCliente.this, AjustesContrasena.class);
                startActivity(i);
                finish();
            }
        });

        iv_cerrar_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(PerfilCliente.this, Splash.class);
                startActivity(i);
                finish();
            }
        });

        tv_cerrar_perfil_cliente_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(PerfilCliente.this, Splash.class);
                startActivity(i);
                finish();
            }
        });


        rtdb.getReference().child(Constantes.CHILD_USUARIOS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Respuesta de firebase
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            //Si es admin, loguearse
                            Usuario usuario = hijo.getValue(Usuario.class);
                            if (usuario.getUsuarioID().equals(auth.getCurrentUser().getUid())) {
                                tv_nombre_cliente_perfil_cliente.setText(usuario.getNombre());
                                tv_numero_telefono_perfil_cliente.setText(usuario.getTelefono());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


        tv_ayuda_perfil_cliente = findViewById(R.id.tv_ayuda_perfil_cliente);
        tv_ayuda_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ayuda = new AlertDialog.Builder(getApplicationContext());
                ayuda.setTitle(R.string.title_ayuda);
                ayuda.setMessage("Comunicate con nuestro centro de atención 01800 6784637");
                ayuda.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ayuda.show();
            }
        });

        tv_informacion_perfil_cliente = findViewById(R.id.tv_informacion_perfil_cliente);
        tv_informacion_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(getApplicationContext());
                info.setTitle("Terminos y Condiciones");
                info.setMessage(" luego de la remisión o puesta en conocimiento,\n" +
                        "o publicación del reglamento o sus modificaciones o actualizaciones en la página Web del\n" +
                        "BANCO, este Reglamento se entenderá aceptado por EL CLIENTE con la contratación de\n" +
                        "productos y/o con la suscripción de la tarjeta de firmas en la que se establecen las condiciones\n" +
                        "de manejo de las cuentas y/o con el recibo de la Tarjeta Débito, Talonario o cualquier otro\n" +
                        "medio de manejo recibido por EL CLIENTE y/o con la utilización del producto, canal o\n" +
                        "servicio asociados y/o manteniendo de fondos disponibles en las cuentas y/o al mantener\n" +
                        "los productos y/o seguir realizando operaciones o utilizando servicios.");
                info.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.show();
            }
        });

    }

}
