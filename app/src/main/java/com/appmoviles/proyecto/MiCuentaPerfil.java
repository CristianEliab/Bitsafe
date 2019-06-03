package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private Fragment fragment;

    private TextView tv_micuenta_nombre_nombre;
    private TextView tv_micuenta_nombre_id;
    private TextView tv_micuenta_telefono_numero;
    private TextView tv_micuenta_ubicacion_pregunta;
    private TextView tv_micuenta_trabajo_pregunta;
    //private RelativeLayout eliminar_cuenta;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta_perfil);

        iv_fragment_micuenta_clientes = findViewById(R.id.iv_fragment_micuenta_clientes);
        tv_micuenta_nombre_nombre = findViewById(R.id.tv_micuenta_nombre_nombre);
        tv_micuenta_nombre_id = findViewById(R.id.tv_micuenta_nombre_id);
        tv_micuenta_telefono_numero = findViewById(R.id.tv_micuenta_telefono_numero);
        tv_micuenta_ubicacion_pregunta = findViewById(R.id.tv_micuenta_ubicacion_pregunta);
        tv_micuenta_trabajo_pregunta = findViewById(R.id.tv_micuenta_trabajo_pregunta);
        //eliminar_cuenta = findViewById(R.id.eliminar_cuenta);


        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        iv_fragment_micuenta_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MiCuentaPerfil.this, PerfilCliente.class);
                startActivity(i);
                finish();
            }
        });

        if (auth.getCurrentUser() != null) {
            rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID).child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    usuario = dataSnapshot.getValue(Usuario.class);
                    cargaInfo();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    }

    private void cargaInfo() {
        if (usuario.getNombre() != null) {
            tv_micuenta_nombre_nombre.setText(usuario.getNombre());
        }
        if (usuario.getCedula() != null) {
            tv_micuenta_nombre_id.setText("Cedula: " + usuario.getCedula());
        }
        if (usuario.getTelefono() != null) {
            tv_micuenta_telefono_numero.setText("+57 " + usuario.getTelefono());
        }
        if (usuario.getUbicacion() != null) {
            tv_micuenta_ubicacion_pregunta.setText(usuario.getUbicacion());
        }
        if (usuario.getLaborUsuario() != null) {
            tv_micuenta_trabajo_pregunta.setText(usuario.getLaborUsuario());
        }
    }
}
