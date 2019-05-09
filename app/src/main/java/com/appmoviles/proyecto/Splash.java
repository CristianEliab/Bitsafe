package com.appmoviles.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.appmoviles.proyecto.util.JsonParse;
import com.google.firebase.FirebaseApp;

import java.io.IOException;
import java.io.InputStream;

public class Splash extends AppCompatActivity {

    public static final String GUARDO = "guardo";
    private AnimationDrawable animacion;
    private ImageView iv_splash_screen;
    private Animation transicion;
    private JsonParse jsonParse;

    private boolean cargodb;
    private SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        myPreferences = PreferenceManager.getDefaultSharedPreferences(Splash.this);
        myPreferences.edit().clear().commit();
        cargodb = myPreferences.getBoolean(GUARDO, true);

        // Carga la base de datos la primera vez.
        if (cargodb) {
            // Obtener el fichero desdes la carpeta raw
            InputStream in = getResources().openRawResource(R.raw.database);
            try {
                jsonParse = new JsonParse();
                jsonParse.readJsonStream(in);
                jsonParse.saveDataBase();
                cargodb = false;
                // We need an Editor object to make preference changes.
                // All objects are from android.context.Context
                SharedPreferences.Editor myEditor = myPreferences.edit();
                myEditor.putBoolean(GUARDO, cargodb);
                myEditor.commit();
            } catch (IOException e) {
                Toast.makeText(Splash.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (Exception e) {
                Toast.makeText(Splash.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

        FirebaseApp.initializeApp(this);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        iv_splash_screen = findViewById(R.id.iv_splash_screen);
        iv_splash_screen.setBackgroundResource(R.drawable.animacion_item);
        animacion = (AnimationDrawable) iv_splash_screen.getBackground();
        goToMain();
    }

    private void goToMain() {
        transicion = AnimationUtils.loadAnimation(this, R.anim.mitransicion);
        iv_splash_screen.startAnimation(transicion);
        transicion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                siguienteActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public void siguienteActivity() {
        animacion.stop(); //Paramos el AnimationDrawable
        Intent intento = new Intent(this, LoginCorreo.class); // Lanzamos SiguienteActivity
        startActivity(intento);
        finish(); //Finalizamos este activity
    }
}
