package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registro_Bitsafe extends AppCompatActivity {

    private Button btn_registro_registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_bitsafe);

        btn_registro_registrarse = findViewById(R.id.btn_registro_registrarse);

        btn_registro_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registro_Bitsafe.this, Login.class);
                startActivity(i);
            }
        });
    }
}
