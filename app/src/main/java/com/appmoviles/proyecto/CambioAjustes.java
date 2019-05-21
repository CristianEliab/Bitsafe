package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CambioAjustes extends AppCompatActivity {

    private Button tv_guardar_cambios;
    private ImageView iv_fragment_cambio_ajustes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_ajustes);

        tv_guardar_cambios = findViewById(R.id.tv_guardar_cambios);
        iv_fragment_cambio_ajustes = findViewById(R.id.iv_fragment_cambio_ajustes);

        iv_fragment_cambio_ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CambioAjustes.this, AjustesContrasena.class);
                startActivity(i);
                finish();
            }
        });

        tv_guardar_cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CambioAjustes.this, PerfilCliente.class);
                startActivity(i);
                finish();
            }
        });
    }
}
