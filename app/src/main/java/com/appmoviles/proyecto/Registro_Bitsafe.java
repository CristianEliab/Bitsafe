package com.appmoviles.proyecto;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.BaseActivity;
import com.appmoviles.proyecto.util.Constantes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class Registro_Bitsafe extends BaseActivity implements View.OnClickListener {

    public static final String EMAIL_USER = "CorreoUsuario";

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
    private TextView tv_ter_con;
    private CheckBox checkbox_terminos;
    private int mYear, mMonth, mDay;
    private String genero;
    private SharedPreferences myPreferences;


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
        tv_ter_con = findViewById(R.id.tv_ter_con);
        checkbox_terminos = findViewById(R.id.checkbox_terminos);

        // Conectar con la base de datos

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        myPreferences = PreferenceManager.getDefaultSharedPreferences(Registro_Bitsafe.this);


        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        tv_ter_con.setOnClickListener(this);
        et_registro_fecha_nacimiento.setOnClickListener(this);
        btn_registro_registrarse.setOnClickListener(this);

    }

    private boolean verificarCampos(String correo, String pass, String repass, String nombre, String cedula, String celular, String ubicacion) {
        boolean correcto = false;
        if (nombre.trim().equals("")) {
            et_registro_nombre.setError("Ingrese el nombre");
            return false;
        }
        if (cedula.trim().equals("")) {
            et_registro_cedula.setError("Ingrese la cedula");
            return false;
        }
        if (celular.trim().equals("")) {
            et_registro_celular.setError("Ingrese el número celular");
            return false;
        }
        if (!correo.trim().equals("")
                && !pass.trim().equals("")
                && !repass.trim().equals("")
                && repass.equals(pass)) {
            correcto = true;
        } else {
            et_registro_correo.setError("Ingrese el correo");
            et_registro_contrasenia.setError("Ingrese la contraseña");
            et_registro_confirmar_contrasenia.setError("Confirme la contraseña");
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

    @Override
    public void onClick(View v) {
        AlertDialog.Builder info = new AlertDialog.Builder(this);
        switch (v.getId()) {
            case R.id.tv_ter_con:
                info.setTitle(R.string.terminos_y_condiciones);
                info.setMessage(R.string.mensaje_terminos_condiciones);
                info.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                info.setPositiveButton(R.string.aceptar,new DialogInterface.OnClickListener()

                    {
                        @Override
                        public void onClick (DialogInterface dialog,int which){
                        dialog.dismiss();
                    }
                    });
                info.show();
                break;
            case R.id.et_registro_fecha_nacimiento:
                    //
                    DatePickerFragment date = new DatePickerFragment();
                    /**
                     * Set Up Current Date Into dialog
                     */
                    Calendar calender = Calendar.getInstance();
                    Bundle args = new Bundle();
                args.putInt("year",calender.get(Calendar.YEAR));
                args.putInt("month",calender.get(Calendar.MONTH));
                args.putInt("day",calender.get(Calendar.DAY_OF_MONTH));
                date.setArguments(args);
                    /**
                     * Set Call back to capture selected date
                     */
                date.setCallBack(ondate);
                    FragmentManager manager = getSupportFragmentManager();
                date.show(manager,"Date Picker");
                break;
            case R.id.btn_registro_registrarse:

                    final String correo = et_registro_correo.getText().toString();
                    final String pass = et_registro_contrasenia.getText().toString();
                    final String repass = et_registro_confirmar_contrasenia.getText().toString();
                    final String nombre = et_registro_nombre.getText().toString();
                    final String cedula = et_registro_cedula.getText().toString();
                    final String celular = et_registro_celular.getText().toString();
                    final String ubicacion = et_registro_ubicacion.getText().toString();

                if(!checkbox_terminos.isChecked())

                    {
                        info = new AlertDialog.Builder(this);
                        info.setTitle(R.string.aceptar_terminos);
                        info.setMessage(R.string.mensaje_aceptar_y_terminos);
                        info.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        info.show();
                    } else

                    {
                        if (verificarCampos(correo, pass, repass, nombre, cedula, celular, ubicacion)) {
                            if (pass.equals(repass)) {
                                auth.createUserWithEmailAndPassword(correo, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        showProgressDialog(Registro_Bitsafe.this);
                                        String uid = auth.getCurrentUser().getUid();
                                        long fechaCreacion = auth.getCurrentUser().getMetadata().getCreationTimestamp();
                                        Usuario usuario = new Usuario(uid,
                                                nombre,
                                                cedula,
                                                celular,
                                                correo,
                                                ubicacion);

                                        usuario.setFecha_nacimiento(et_registro_fecha_nacimiento.getText().toString());
                                        usuario.setGenero(sp_registro_genero.getSelectedItem().toString());
                                        usuario.setFechaCreacion(fechaCreacion);

                                        generarCuentasBancarias(usuario);

                                        SharedPreferences.Editor myEditor = myPreferences.edit();
                                        myEditor.putString(EMAIL_USER, correo);
                                        myEditor.commit();

                                        rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID).child(usuario.getUsuarioID()).setValue(usuario);

                                        // Animación para dar el cambio de pantalla
                                        hideProgressDialog();

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
                break;
                }
        }
    }

    private void generarCuentasBancarias(Usuario usuario) {
        ArrayList<Banco> listaBancos = new ArrayList<>();
        ArrayList<Cuenta> cuentaArrayList = new ArrayList<>();

        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaID(UUID.randomUUID().toString());
        cuenta.setUsuarioID(usuario.getUsuarioID());
        int numero_1 = (int) (Math.random() * 8000) + 1000;
        int numero_2 = (int) (Math.random() * 8000) + 1000;
        int numero_3 = (int) (Math.random() * 8000) + 1000;
        int numero_4 = (int) (Math.random() * 8000) + 1000;

        cuenta.setNumeroCuenta(numero_1 + "-" + numero_2 + "-" + numero_3 + "-" + numero_4);

        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yy");
        try {
            Date d = f.parse(usuario.getFecha_cargar());
            long milliseconds = d.getTime();
            usuario.setFechaCreacion(milliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        cuenta.setFechaVinculacion(usuario.getFechaCreacion() + "");

        int saldo = (int) (Math.random() * 1000000) + 50000;

        cuenta.setSaldo(saldo + "");

        int tipoCuenta = (int) (Math.random() * 2) + 1;

        cuenta.setTipoCuentaID("0" + tipoCuenta);
        if (tipoCuenta == 0) {
            cuenta.setTipoCuentaNombre("Cuenta Credito");
        } else {
            cuenta.setTipoCuentaNombre("Cuenta Debito");
        }

        int bancoID = (int) (Math.random() * 4) + 1;
        cuenta.setBancoID("0" + bancoID);

        // Se crea la cuenta
        cuentaArrayList.add(cuenta);

        Banco banco1 = new Banco();

        switch (bancoID) {
            case 1:
                banco1.setIcono(Constantes.ICON_BANCO_DAVIVIENDA);
                banco1.setNombreBanco(Constantes.BANCO_DAVIVIENDA);
                banco1.setBancoID("0" + bancoID);
                banco1.setSaldo(saldo + "");
                break;
            case 2:
                banco1.setIcono(Constantes.ICON_BANCO_BOGOTA);
                banco1.setNombreBanco(Constantes.BANCO_BOGOTA);
                banco1.setBancoID("0" + bancoID);
                banco1.setSaldo(saldo + "");
                break;
            case 3:
                banco1.setIcono(Constantes.ICON_BANCO_BANCOLOMBIA);
                banco1.setNombreBanco(Constantes.BANCO_BANCOLOMBIA);
                banco1.setBancoID("0" + bancoID);
                banco1.setSaldo(saldo + "");
                break;
            case 4:
                banco1.setIcono(Constantes.ICON_BANCO_ITAU);
                banco1.setNombreBanco(Constantes.BANCO_ITAU);
                banco1.setBancoID("0" + bancoID);
                banco1.setSaldo(saldo + "");
                break;
        }


        listaBancos.add(banco1);


        usuario.setListaBancos(listaBancos);
        usuario.setListaCuentas(cuentaArrayList);


        rtdb.getReference().child(Constantes.CHILD_CUENTAS).push().setValue(cuenta);

    }
}
