package com.appmoviles.proyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.Constantes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CambioAjustes extends AppCompatActivity {

    private Button tv_guardar_cambios;
    private ImageView iv_fragment_cambio_ajustes;
    private EditText tv_cambiar_nombre;
    private EditText tv_cambiar_cedula;
    private EditText tv_cambiar_celular;
    private EditText tv_cambiar_hogar;
    private EditText tv_cambiar_labor;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_ajustes);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        tv_guardar_cambios = findViewById(R.id.tv_guardar_cambios);
        iv_fragment_cambio_ajustes = findViewById(R.id.iv_fragment_cambio_ajustes);

        tv_cambiar_nombre = findViewById(R.id.tv_cambiar_nombre);
        tv_cambiar_cedula = findViewById(R.id.tv_cambiar_cedula);
        tv_cambiar_celular = findViewById(R.id.tv_cambiar_celular);
        tv_cambiar_hogar = findViewById(R.id.tv_cambiar_hogar);
        tv_cambiar_labor = findViewById(R.id.tv_cambiar_labor);

        iv_fragment_cambio_ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_guardar_cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(CambioAjustes.this);
                info.setTitle(R.string.guardar);
                info.setMessage(R.string.confirmacion_guardar_cambios);
                info.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        usuario.setNombre(tv_cambiar_nombre.getText().toString());
                        usuario.setCedula(tv_cambiar_cedula.getText().toString());
                        usuario.setTelefono(tv_cambiar_celular.getText().toString());
                        usuario.setUbicacion(tv_cambiar_hogar.getText().toString());
                        usuario.setLaborUsuario(tv_cambiar_labor.getText().toString());
                        rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID).child(usuario.getUsuarioID()).setValue(usuario);
                        Intent i = new Intent(CambioAjustes.this, PerfilCliente.class);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });
                info.show();
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
            tv_cambiar_nombre.setText(usuario.getNombre());
        }
        if (usuario.getCedula() != null) {
            tv_cambiar_cedula.setText(usuario.getCedula());
        }
        if (usuario.getTelefono() != null) {
            tv_cambiar_celular.setText(usuario.getTelefono());
        }
        if (usuario.getUbicacion() != null) {
            tv_cambiar_hogar.setText(usuario.getUbicacion());
        }
        if (usuario.getLaborUsuario() != null) {
            tv_cambiar_labor.setText(usuario.getLaborUsuario());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
