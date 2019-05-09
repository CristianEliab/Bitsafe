package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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

                final String nombre = et_registro_nombre.getText().toString();
                final String cedula = et_registro_cedula.getText().toString();
                final String celular = et_registro_celular.getText().toString();
                final String ubicacion = et_registro_ubicacion.getText().toString();

                if (verificarCampos(nombre, cedula, celular, ubicacion)) {
                    if (pass.equals(repass)) {
                        auth.createUserWithEmailAndPassword(correo, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                String uid = auth.getCurrentUser().getUid();
                                Usuario usuario = new Usuario(uid,
                                        nombre,
                                        cedula,
                                        celular,
                                        correo,
                                        ubicacion);

                                rtdb.getReference().child("usuarios").child(uid).setValue(usuario);

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
                }else{
                    Toast.makeText(Registro_Bitsafe.this, "Ingrese cada uno de los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verificarCampos(String nombre, String cedula, String celular, String ubicacion) {
        boolean acceder = false;
        if (!nombre.trim().equals("") ||
                !cedula.trim().equals("") ||
                !celular.trim().equals("") ||
                !ubicacion.trim().equals("")) {
            acceder = true;
        }
        return acceder;
    }


}
