package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private ImageView iv_main_cliente;
    private ImageView iv_main_admin;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        iv_main_admin = findViewById(R.id.iv_main_admin);
        iv_main_cliente = findViewById(R.id.iv_main_cliente);

        iv_main_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Para mantener la sesión
                //if (auth.getCurrentUser() != null) {
                    Intent i = new Intent(MainActivity.this, LoginCorreo.class);
                    startActivity(i);
                //}
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
