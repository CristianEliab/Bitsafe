package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login_huella extends AppCompatActivity {

    private Button btn_login_huella_cero_celular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_huella);

        btn_login_huella_cero_celular = findViewById(R.id.btn_login_huella_cero_celular);

        btn_login_huella_cero_celular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login_huella.this, HomeCliente.class);
                startActivity(i);
            }
        });
    }
}
