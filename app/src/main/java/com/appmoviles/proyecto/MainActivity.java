package com.appmoviles.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.appmoviles.proyecto.util.JsonParse;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_main_cliente;
    private ImageView iv_main_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        iv_main_admin = findViewById(R.id.iv_main_admin);
        iv_main_cliente = findViewById(R.id.iv_main_cliente);

        iv_main_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  // Para mantener la sesión
                //if (auth.getCurrentUser() != null) {
                Intent i = new Intent(MainActivity.this, LoginCorreo.class);
                startActivity(i);
                //}
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);*/
            }
        });

        iv_main_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Login_huella.class);
                startActivity(i);
            }
        });

    }
}
