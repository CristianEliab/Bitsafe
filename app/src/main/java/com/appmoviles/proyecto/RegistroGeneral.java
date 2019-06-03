package com.appmoviles.proyecto;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Banco;
import com.appmoviles.proyecto.modelo.Cuenta;
import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.BaseActivity;
import com.appmoviles.proyecto.util.Constantes;
import com.appmoviles.proyecto.util.SlidesAdapter;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


public class RegistroGeneral extends BaseActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;
    private RelativeLayout rl_registro_general_panel_bitsafe;
    private ViewPager viewPager;
    private SlidesAdapter slidesAdapter;
    private SignInButton btn_sign_in_google;
    private LoginButton btn_registro_general_facebook;
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;
    FirebaseDatabase rtdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_general);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        rtdb = FirebaseDatabase.getInstance();


        rl_registro_general_panel_bitsafe = findViewById(R.id.rl_registro_general_panel_bitsafe);
        viewPager = findViewById(R.id.view_imagenes);
        btn_sign_in_google = findViewById(R.id.btn_sign_in_google);
        btn_registro_general_facebook = findViewById(R.id.btn_registro_general_facebook);
        btn_registro_general_facebook.setOnClickListener(this);
        rl_registro_general_panel_bitsafe.setOnClickListener(this);
        btn_sign_in_google.setOnClickListener(this);


        slidesAdapter = new SlidesAdapter(this);
        viewPager.setAdapter(slidesAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_registro_general_panel_bitsafe:
                Intent i = new Intent(RegistroGeneral.this, Registro_Bitsafe.class);
                startActivity(i);
                break;
            case R.id.btn_registro_general_facebook:
                btn_registro_general_facebook.setReadPermissions("email", "public_profile");
                btn_registro_general_facebook.registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                handleFacebookAccessToken(loginResult.getAccessToken());
                            }

                            @Override
                            public void onCancel() {
                                // App code
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                            }
                        });
                break;
            case R.id.btn_sign_in_google:
                initializeGPlusSettings();
                signIn();
                break;
        }
    }

    private void initializeGPlusSettings() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        showProgressDialog(this);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    String telefono = user.getPhoneNumber();
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    boolean emailVerified = user.isEmailVerified();
                    String uid = user.getUid();

                    Usuario usuario = new Usuario();
                    usuario.setNombre(name);
                    usuario.setCorreo(email);
                    usuario.setUsuarioID(uid);
                    usuario.setTelefono(telefono);

                    generarCuentasBancarias(usuario);

                    rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID).child(mAuth.getCurrentUser().getUid()).setValue(usuario);

                    hideProgressDialog();
                    Intent i = new Intent(RegistroGeneral.this, HomeCliente.class);
                    startActivity(i);
                    finish();

                } else {
                    Toast.makeText(RegistroGeneral.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        showProgressDialog(this);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    String telefono = user.getPhoneNumber();
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    Uri photoUrl = user.getPhotoUrl();
                    boolean emailVerified = user.isEmailVerified();
                    String uid = user.getUid();

                    Usuario usuario = new Usuario();
                    usuario.setNombre(name);
                    usuario.setCorreo(email);
                    usuario.setUsuarioID(uid);
                    usuario.setTelefono(telefono);

                    generarCuentasBancarias(usuario);

                    rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID).child(mAuth.getCurrentUser().getUid()).setValue(usuario);

                    hideProgressDialog();
                    Intent i = new Intent(RegistroGeneral.this, HomeCliente.class);
                    startActivity(i);
                    finish();

                } else {

                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // Google Sign In was successful, authenticate with Firebase
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed", e);
            }
        }
    }

    public void getGoogleCredentials() {
        String googleIdToken = "";
        // [START auth_google_cred]
        AuthCredential credential = GoogleAuthProvider.getCredential(googleIdToken, null);
        // [END auth_google_cred]
    }


    public void getFbCredentials() {
        AccessToken token = AccessToken.getCurrentAccessToken();
        // [START auth_fb_cred]
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        // [END auth_fb_cred]
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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