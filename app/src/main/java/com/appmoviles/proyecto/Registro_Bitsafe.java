package com.appmoviles.proyecto;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.Constantes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class Registro_Bitsafe extends AppCompatActivity {

    private Button btn_registro_registrarse;
    private EditText et_registro_nombre;
    private EditText et_registro_cedula;
    private EditText et_registro_celular;
    private EditText et_registro_correo;
    private EditText et_registro_ubicacion;
    private EditText et_registro_contrasenia;
    private EditText et_registro_confirmar_contrasenia;
    private Button et_registro_fecha_nacimiento;
    private Spinner sp_registro_genero;
    private int mYear, mMonth, mDay;
    private String genero;


    FirebaseAuth auth;
    FirebaseDatabase rtdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_bitsafe);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Instanciar los componentes

        btn_registro_registrarse = findViewById(R.id.btn_registro_registrarse);
        et_registro_nombre = findViewById(R.id.et_registro_nombre);
        et_registro_cedula = findViewById(R.id.et_registro_cedula);
        et_registro_celular = findViewById(R.id.et_registro_celular);
        et_registro_correo = findViewById(R.id.et_registro_correo);
        et_registro_ubicacion = findViewById(R.id.et_registro_ubicacion);
        et_registro_contrasenia = findViewById(R.id.et_registro_contrasenia);
        et_registro_confirmar_contrasenia = findViewById(R.id.et_registro_confirmar_contrasenia);
        et_registro_fecha_nacimiento = findViewById(R.id.et_registro_fecha_nacimiento);
        sp_registro_genero = findViewById(R.id.sp_registro_genero);

        // Conectar con la base de datos

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Get Current Date
        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        et_registro_fecha_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                DatePickerFragment date = new DatePickerFragment();
                /**
                 * Set Up Current Date Into dialog
                 */
                Calendar calender = Calendar.getInstance();
                Bundle args = new Bundle();
                args.putInt("year", calender.get(Calendar.YEAR));
                args.putInt("month", calender.get(Calendar.MONTH));
                args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
                date.setArguments(args);
                /**
                 * Set Call back to capture selected date
                 */
                date.setCallBack(ondate);
                FragmentManager manager = getSupportFragmentManager();
                date.show(manager, "Date Picker");
            }
        });


        /* Click al boton registrarse*/

        btn_registro_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String correo = et_registro_correo.getText().toString();
                final String pass = et_registro_contrasenia.getText().toString();
                final String repass = et_registro_confirmar_contrasenia.getText().toString();

                if (verificarCampos(correo, pass, repass)) {
                    if (pass.equals(repass)) {
                        auth.createUserWithEmailAndPassword(correo, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                String uid = auth.getCurrentUser().getUid();
                                Usuario usuario = new Usuario(uid,
                                        et_registro_nombre.getText().toString(),
                                        et_registro_cedula.getText().toString(),
                                        et_registro_celular.getText().toString(),
                                        correo,
                                        et_registro_ubicacion.getText().toString());

                                usuario.setFecha_nacimiento(et_registro_fecha_nacimiento.getText().toString());
                                usuario.setGenero(sp_registro_genero.getSelectedItem().toString());

                                rtdb.getReference().child(Constantes.CHILD_USUARIOS).push().setValue(usuario);

                                // Animación para dar el cambio de pantalla
                                animation();

                                Intent i = new Intent(Registro_Bitsafe.this, HomeCliente.class);
                                startActivity(i);
                                finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Registro_Bitsafe.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }


            }
        });

    }

    private boolean verificarCampos(String correo, String pass, String repass) {
        boolean correcto = false;

        if (correo != null && pass != null && repass != null
                && !correo.trim().equals("")
                && !pass.trim().equals("")
                && !repass.trim().equals("")
        ) {
            correcto = true;
        }

        return correcto;
    }


    private void animation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                animateRevealShow(btn_registro_registrarse);
            }

            @Override
            public void onTransitionEnd(Transition transition) {

            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    private void animateRevealShow(View viewRoot) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, cx, cy, 0, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.start();
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            et_registro_fecha_nacimiento.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
