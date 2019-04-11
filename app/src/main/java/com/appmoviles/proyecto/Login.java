package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private Button btn_login_cero_celular;
    private TextView tv_login_registrarse;
    private Button btn_login_dos_celular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login_cero_celular = findViewById(R.id.btn_login_cero_celular);
        tv_login_registrarse = findViewById(R.id.tv_login_registrarse);
        btn_login_dos_celular = findViewById(R.id.btn_login_dos_celular);

        tv_login_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Registro_Bitsafe.class);
                startActivity(i);
            }
        });

        btn_login_cero_celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, HomeAdministrador.class);
                startActivity(i);
            }
        });
    }
}
