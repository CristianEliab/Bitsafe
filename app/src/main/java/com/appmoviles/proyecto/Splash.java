package com.appmoviles.proyecto;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;

public class Splash extends AppCompatActivity {

    /*private Button btn_splash_empesar;*/
    private AnimationDrawable animacion;
    private ImageView iv_splash_screen;
    private Animation transicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        iv_splash_screen = findViewById(R.id.iv_splash_screen);
        iv_splash_screen.setBackgroundResource(R.drawable.animacion_item);

        animacion = (AnimationDrawable) iv_splash_screen.getBackground();

        goToMain();

        /*btn_splash_empesar = findViewById(R.id.btn_splash_empesar);
        btn_splash_empesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
            }
        });*/
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
        Intent intento = new Intent(this, MainActivity.class); // Lanzamos SiguienteActivity
        startActivity(intento);
        finish(); //Finalizamos este activity
    }
}
