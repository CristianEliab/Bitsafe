package com.appmoviles.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AjustesContrasena extends AppCompatActivity {

    private ImageView iv_fragment_ajustes;
    private Button btn_login_huella_cero_celular;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuestes_contrasena);


        iv_fragment_ajustes = findViewById(R.id.iv_fragment_ajustes);
        btn_login_huella_cero_celular = findViewById(R.id.btn_login_huella_cero_celular);


        btn_login_huella_cero_celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AjustesContrasena.this, CambioAjustes.class);
                startActivity(i);
                finish();
            }
        });

        iv_fragment_ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AjustesContrasena.this, PerfilCliente.class);
                startActivity(i);
                finish();
            }
        });

    }
}

