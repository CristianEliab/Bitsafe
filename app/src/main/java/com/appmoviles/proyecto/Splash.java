package com.appmoviles.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;

public class Splash extends AppCompatActivity {

    private Button btn_splash_empesar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btn_splash_empesar = findViewById(R.id.btn_splash_empesar);
        btn_splash_empesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
