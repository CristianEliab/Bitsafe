package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login_cero_celular;
    private TextView tv_login_registrarse;
    private Button btn_login_uno_celular;
    private Button btn_login_dos_celular;
    private Button btn_login_tres_celular;
    private Button btn_login_cuatro_celular;
    private Button btn_login_cinco_celular;
    private Button btn_login_seis_celular;
    private Button btn_login_siete_celular;
    private Button btn_login_ocho_celular;
    private Button btn_login_nueve_celular;
    private Button btn_login_borrar_celular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_login_registrarse = findViewById(R.id.tv_login_registrarse);

        tv_login_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Registro_Bitsafe.class);
                startActivity(i);
                finish();
            }
        });


    }

    private void inicializarBotones(View v) {
        btn_login_cero_celular = findViewById(R.id.btn_login_cero_celular);
        btn_login_uno_celular = v.findViewById(R.id.btn_agregar_monto_uno);
        btn_login_dos_celular = findViewById(R.id.btn_login_dos_celular);
        btn_login_tres_celular = v.findViewById(R.id.btn_agregar_monto_tres);
        btn_login_cuatro_celular = v.findViewById(R.id.btn_agregar_monto_cuatro);
        btn_login_cinco_celular = v.findViewById(R.id.btn_agregar_monto_cinco);
        btn_login_seis_celular = v.findViewById(R.id.btn_agregar_monto_seis);
        btn_login_siete_celular = v.findViewById(R.id.btn_agregar_monto_siete);
        btn_login_ocho_celular = v.findViewById(R.id.btn_agregar_monto_ocho);
        btn_login_nueve_celular = v.findViewById(R.id.btn_agregar_monto_nueve);
        btn_login_borrar_celular = v.findViewById(R.id.btn_agregar_monto_borrar);

        //btn_login_cero_celular.setOnClickListener(this);
        btn_login_cero_celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, HomeAdministrador.class);
                startActivity(i);
            }
        });
        btn_login_uno_celular.setOnClickListener(this);
        btn_login_dos_celular.setOnClickListener(this);
        btn_login_tres_celular.setOnClickListener(this);
        btn_login_cuatro_celular.setOnClickListener(this);
        btn_login_cinco_celular.setOnClickListener(this);
        btn_login_seis_celular.setOnClickListener(this);
        btn_login_siete_celular.setOnClickListener(this);
        btn_login_ocho_celular.setOnClickListener(this);
        btn_login_nueve_celular.setOnClickListener(this);
        btn_login_borrar_celular.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
