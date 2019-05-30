package com.appmoviles.proyecto;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.RolUsuario;
import com.appmoviles.proyecto.util.BaseActivity;
import com.appmoviles.proyecto.util.Constantes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginCorreo extends BaseActivity {

    private static final int REQUEST_CODE = 11;
    private static final String READ_EXTERNAL = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE_EXTERNAL = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    public static final String EMAIL_USER = "CorreoUsuario";

    private EditText et_login_correo_contrasenia;
    private EditText et_login_correo_confirmar_contrasenia;
    private Button btn_login_correo_iniciar;
    private TextView tv_login_correo_registrarse;
    private boolean admin;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    String correo_guardado;
    private SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_correo);

        verificarPermisos();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    private void verificarPermisos() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                READ_EXTERNAL) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    WRITE_EXTERNAL) == PackageManager.PERMISSION_GRANTED) {
                //Inicia la aplicación
                init();
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void init() {

        et_login_correo_contrasenia = findViewById(R.id.et_login_correo_contrasenia);
        et_login_correo_confirmar_contrasenia = findViewById(R.id.et_login_correo_confirmar_contrasenia);
        btn_login_correo_iniciar = findViewById(R.id.btn_login_correo_iniciar);
        tv_login_correo_registrarse = findViewById(R.id.tv_login_correo_registrarse);

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        myPreferences
                = PreferenceManager.getDefaultSharedPreferences(LoginCorreo.this);

        btn_login_correo_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String correo = et_login_correo_contrasenia.getText().toString().trim();
                String pass = et_login_correo_confirmar_contrasenia.getText().toString().trim();

                if (!correo.trim().equals("") && !pass.trim().equals("") && correo != null && pass != null) {
                    loguearUsuario(correo, pass);
                } else {
                    Toast.makeText(LoginCorreo.this, "Ingrese el correo y contraseña", Toast.LENGTH_SHORT).show();
                }


            }
        });

        tv_login_correo_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginCorreo.this, RegistroGeneral.class);
                startActivity(i);
            }
        });

        correo_guardado = myPreferences.getString(EMAIL_USER, "Correo");
        if (correo_guardado.equals("Correo")) {
            et_login_correo_contrasenia.setHint(correo_guardado);
            et_login_correo_contrasenia.setHintTextColor(Color.rgb(130, 130, 130));
        } else {
            et_login_correo_contrasenia.setText(correo_guardado);
            et_login_correo_contrasenia.setTextColor(Color.rgb(130, 130, 130));
        }


    }

    private void loguearUsuario(final String correo, String pass) {
        auth.signInWithEmailAndPassword(correo, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.putString(EMAIL_USER, correo);
                myEditor.commit();

                showProgressDialog(LoginCorreo.this);

                rtdb.getReference().child(Constantes.CHILD_ROL_USUARIO_ID).child(auth.getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                RolUsuario rolUsuario = dataSnapshot.getValue(RolUsuario.class);
                                admin = false;
                                if (rolUsuario.getRolID().equals("02") &&
                                        rolUsuario.getUsuarioID().equals(auth.getCurrentUser().getUid())) {
                                    admin = true;
                                }

                                hideProgressDialog();

                                if (admin) {
                                    Intent i = new Intent(LoginCorreo.this, HomeAdministrador.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    Intent i = new Intent(LoginCorreo.this, HomeCliente.class);
                                    startActivity(i);
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        }).

                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        et_login_correo_confirmar_contrasenia.setError("No se pudo ingresar. ");
                    }
                });

    }

    @Override
    protected void onPause() {
        hideProgressDialog();
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(EMAIL_USER, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("user_email", correo_guardado);
        // Commit the edits!
        editor.commit();
        hideProgressDialog();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(EMAIL_USER, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("user_email", correo_guardado);
        // Commit the edits!
        editor.commit();
        hideProgressDialog();
    }
}
