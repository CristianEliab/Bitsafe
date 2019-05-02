package com.appmoviles.proyecto;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final String TAG = "PhoneAuthActivity";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;


    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private Button btn_login_cero_celular;
    private TextView tv_login_registrarse;
    private Button btn_login_uno_celular;
    private Button btn_login_dos_celular;
    private Button btn_login_tres_celular;
    private Button btn_login_cuatro_celular;
    private Button btn_login_cinco_celular;
    private Button btn_login_seis_celular;
    private Button btn_login_siete_celular;
    private Button btn_login_ocho_celular;
    private Button btn_login_nueve_celular;
    private Button btn_login_borrar_celular;
    private EditText et_login_numero_celular;

    private Button btn_login_loguearse;

    private String numeroTelefonico;


    FirebaseAuth auth;
    FirebaseDatabase rtdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_login_registrarse = findViewById(R.id.tv_login_registrarse);
        et_login_numero_celular = findViewById(R.id.et_login_numero_celular);
        btn_login_loguearse = findViewById(R.id.btn_login_loguearse);

        numeroTelefonico = et_login_numero_celular.getText().toString();
        // Conectar con la base de datos

        //rtdb = FirebaseDatabase.getInstance();
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();

        inicializarBotones();

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                //updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    et_login_numero_celular.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                //updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                //updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        // [END phone_auth_callbacks]
    }

    private void inicializarBotones() {

        btn_login_cero_celular = findViewById(R.id.btn_login_cero_celular);
        btn_login_uno_celular = findViewById(R.id.btn_login_uno_celular);
        btn_login_dos_celular = findViewById(R.id.btn_login_dos_celular);
        btn_login_tres_celular = findViewById(R.id.btn_login_tres_celular);
        btn_login_cuatro_celular = findViewById(R.id.btn_login_cuatro_celular);
        btn_login_cinco_celular = findViewById(R.id.btn_login_cinco_celular);
        btn_login_seis_celular = findViewById(R.id.btn_login_seis_celular);
        btn_login_siete_celular = findViewById(R.id.btn_login_siete_celular);
        btn_login_ocho_celular = findViewById(R.id.btn_login_ocho_celular);
        btn_login_nueve_celular = findViewById(R.id.btn_login_nueve_celular);
        btn_login_borrar_celular = findViewById(R.id.btn_login_borrar_celular);

        btn_login_cero_celular.setOnClickListener(this);
        btn_login_uno_celular.setOnClickListener(this);
        btn_login_dos_celular.setOnClickListener(this);
        btn_login_tres_celular.setOnClickListener(this);
        btn_login_cuatro_celular.setOnClickListener(this);
        btn_login_cinco_celular.setOnClickListener(this);
        btn_login_seis_celular.setOnClickListener(this);
        btn_login_siete_celular.setOnClickListener(this);
        btn_login_ocho_celular.setOnClickListener(this);
        btn_login_nueve_celular.setOnClickListener(this);
        btn_login_borrar_celular.setOnClickListener(this);
        tv_login_registrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int numero_caracteres = 0;
        switch (v.getId()) {
            case R.id.tv_login_registrarse:
                Intent i = new Intent(Login.this, Registro_Bitsafe.class);
                startActivity(i);
                break;
            case R.id.btn_login_borrar_celular:
                numeroTelefonico = et_login_numero_celular.getText().toString();
                numero_caracteres = numeroTelefonico.length();
                if (numero_caracteres >= 1) {
                    numeroTelefonico = numeroTelefonico.substring(0, numero_caracteres - 1);
                }
                break;
            case R.id.btn_login_loguearse:
                if (!validatePhoneNumber()) {
                    return;
                }

                startPhoneNumberVerification(et_login_numero_celular.getText().toString());
                break;
            default:
                numeroTelefonico = et_login_numero_celular.getText().toString();
                Button numero = (Button) v;
                numeroTelefonico = numeroTelefonico + numero.getText().toString();
        }
        et_login_numero_celular.setText(numeroTelefonico);
        et_login_numero_celular.setTextColor(Color.rgb(7, 106, 173));
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        //updateUI(currentUser);

        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(et_login_numero_celular.getText().toString());
        }
        // [END_EXCLUDE]
    }
    // [END on_start_check_user]

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // [START_EXCLUDE]
                            //updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                et_login_numero_celular.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            //updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void signOut() {
        auth.signOut();
    }


    private boolean validatePhoneNumber() {
        String phoneNumber = et_login_numero_celular.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            et_login_numero_celular.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

}
