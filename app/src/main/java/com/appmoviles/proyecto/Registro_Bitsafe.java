package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public class Registro_Bitsafe extends AppCompatActivity {

    private Button btn_registro_registrarse;
    private EditText et_registro_nombre;
    private EditText et_registro_cedula;
    private EditText et_registro_celular;
    private EditText et_registro_correo;
    private EditText et_registro_ubicacion;
    private EditText et_registro_contrasenia;
    private EditText et_registro_confirmar_contrasenia;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_bitsafe);

        // Instanciar los componentes

        btn_registro_registrarse = findViewById(R.id.btn_registro_registrarse);
        et_registro_nombre = findViewById(R.id.et_registro_nombre);
        et_registro_cedula = findViewById(R.id.et_registro_cedula);
        et_registro_celular = findViewById(R.id.et_registro_celular);
        et_registro_correo = findViewById(R.id.et_registro_correo);
        et_registro_ubicacion = findViewById(R.id.et_registro_ubicacion);
        et_registro_contrasenia = findViewById(R.id.et_registro_contrasenia);
        et_registro_confirmar_contrasenia = findViewById(R.id.et_registro_confirmar_contrasenia);

        // Conectar con la base de datos

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        btn_registro_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String correo = et_registro_correo.getText().toString();
                final String pass = et_registro_contrasenia.getText().toString();
                final String repass = et_registro_confirmar_contrasenia.getText().toString();

                if (pass.equals(repass)) {
                    auth.createUserWithEmailAndPassword(correo, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            String uid = auth.getCurrentUser().getUid();
                            Usuario usuario = new Usuario(uid,
                                    et_registro_nombre.getText().toString(),
                                    et_registro_cedula.getText().toString(),
                                    et_registro_celular.getText().toString(),
                                    correo,
                                    et_registro_ubicacion.getText().toString());

                            rtdb.getReference().child("users").child(uid).setValue(usuario);

                            Intent i = new Intent(Registro_Bitsafe.this, HomeCliente.class);
                            startActivity(i);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registro_Bitsafe.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }
}
