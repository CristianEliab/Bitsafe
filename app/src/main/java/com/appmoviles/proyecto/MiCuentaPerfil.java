package com.appmoviles.proyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class MiCuentaPerfil extends AppCompatActivity {

    private ImageView iv_fragment_micuenta_clientes;

    private TextView tv_micuenta_nombre_nombre;
    private TextView tv_micuenta_nombre_id;
    private TextView tv_micuenta_telefono_numero;
    private TextView tv_micuenta_guardar;
    private ImageView iv_micuenta_ubicacion_flecha;
    private ImageView iv_micuenta_trabajo_flecha;


    private Fragment fragment;
    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta_perfil);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        Intent i = getIntent();
        Bundle bundle =  i.getBundleExtra(Constantes.GO_TO_PERFIL);
        fragment = (Fragment) bundle.getSerializable(Constantes.FRAGMENT);

        iv_fragment_micuenta_clientes = findViewById(R.id.iv_fragment_micuenta_clientes);
        tv_micuenta_nombre_nombre = findViewById(R.id.tv_micuenta_nombre_nombre);
        tv_micuenta_nombre_id = findViewById(R.id.tv_micuenta_nombre_id);
        tv_micuenta_telefono_numero = findViewById(R.id.tv_micuenta_telefono_numero);


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

        rtdb.getReference().child(Constantes.CHILD_USUARIOS)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Respuesta de firebase
                        for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                            //Si es admin, loguearse
                            Usuario usuario = hijo.getValue(Usuario.class);
                            if (usuario.getUsuarioID().equals(auth.getCurrentUser().getUid())) {
                                tv_micuenta_nombre_nombre.setText(usuario.getNombre());
                                tv_micuenta_nombre_id.setText("Cédula: "+usuario.getCedula());
                                tv_micuenta_telefono_numero.setText("+57 "+usuario.getTelefono());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
        tv_micuenta_guardar = findViewById(R.id.tv_micuenta_guardar);
        tv_micuenta_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder guardar = new AlertDialog.Builder(MiCuentaPerfil.this);
                guardar.setTitle("Guardar cambios");
                guardar.setMessage("Tus cambios han sido guardados con éxito");
                guardar.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                guardar.show();

            }
        });

      iv_micuenta_ubicacion_flecha = findViewById(R.id.iv_micuenta_ubicacion_flecha);
      iv_micuenta_ubicacion_flecha.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              //Aqui hago lo del alert dialog
          }
      });

        iv_micuenta_trabajo_flecha = findViewById(R.id.iv_micuenta_trabajo_flecha);
        iv_micuenta_trabajo_flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hago lo mismo que arriba
            }
        });





    }
}
