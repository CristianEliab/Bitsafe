package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginCorreo extends AppCompatActivity {

    private EditText et_login_correo_contrasenia;
    private EditText et_login_correo_confirmar_contrasenia;
    private Button btn_login_correo_iniciar;
    private TextView tv_login_correo_registrarse;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_correo);

        et_login_correo_contrasenia = findViewById(R.id.et_login_correo_contrasenia);
        et_login_correo_confirmar_contrasenia = findViewById(R.id.et_login_correo_confirmar_contrasenia);
        btn_login_correo_iniciar = findViewById(R.id.btn_login_correo_iniciar);
        tv_login_correo_registrarse = findViewById(R.id.tv_login_correo_registrarse);


        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        btn_login_correo_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = et_login_correo_contrasenia.getText().toString().trim();
                String pass = et_login_correo_confirmar_contrasenia.getText().toString().trim();

                if (!correo.trim().equals("") || !pass.trim().equals("")) {
                    auth.signInWithEmailAndPassword(correo, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            rtdb.getReference().child("admin")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            //Respuesta de firebase
                                            for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                                                //Si es admin, loguearse
                                            /*if () {

                                            }else{}*/
                                            }
                                            Intent i = new Intent(LoginCorreo.this, HomeAdministrador.class);
                                            startActivity(i);
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginCorreo.this, "No se pudo ingresar" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(LoginCorreo.this, "Ingrese el correo y contraseña", Toast.LENGTH_SHORT).show();
                }


            }
        });

        tv_login_correo_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginCorreo.this, Registro_Bitsafe.class);
                startActivity(i);
            }
        });


    }
}
