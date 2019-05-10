package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AjuestesContrasena extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuestes_contrasena);

        //Cuando se compruebe la contaseña

        Intent i = new Intent(AjuestesContrasena.this, CambioAjustes.class);
        startActivity(i);
        finish();
    }
}
