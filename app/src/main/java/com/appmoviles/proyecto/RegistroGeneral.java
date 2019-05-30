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

import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.BaseActivity;
import com.appmoviles.proyecto.util.Constantes;
import com.appmoviles.proyecto.util.SlidesAdapter;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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

        initializeGPlusSettings();
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
            case R.id.btn_sign_in_google:
                /*signIn();*/
                break;
        }
    }

    private void initializeGPlusSettings() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
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

                    rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID).push().setValue(usuario);

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

        /*
        showProgressDialog();
        */

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

                    rtdb.getReference().child(Constantes.CHILD_USUARIOS).push().setValue(usuario);

                    /*hideProgressDialog();*/

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
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            /*// Google Sign In was successful, authenticate with Firebase
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.e(TAG, "Google sign in failed", e);
            }*/
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
