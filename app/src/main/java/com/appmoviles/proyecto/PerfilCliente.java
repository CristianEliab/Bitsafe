package com.appmoviles.proyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Usuario;
import com.appmoviles.proyecto.util.BaseActivity;
import com.appmoviles.proyecto.util.Constantes;
import com.appmoviles.proyecto.util.UtilDomi;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.UUID;


public class PerfilCliente extends BaseActivity implements Serializable, View.OnClickListener {

    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider";
    private static final int CAMERA_CALLBACK_ID = 100;
    private static final int GALLERY_CALLBACK_ID = 101;

    private TextView tv_nombre_iniciales;
    private TextView tv_nombre;
    private TextView tv_telefono;
    private ImageView image_atras;
    private TextView tv_micuenta_perfil_cliente;
    private TextView tv_ajustes_perfil_cliente;
    private TextView tv_ayuda_perfil_cliente;
    private TextView tv_informacion_perfil_cliente;
    private ImageView iv_fragment_perfil_clientes_return;
    private TextView tv_nombre_cliente_perfil_cliente;
    private TextView tv_numero_telefono_perfil_cliente;
    private TextView tv_cerrar_perfil_cliente_txt;
    private ImageView iv_cerrar_perfil_cliente;
    private ImageView iv_perfil_cliente;

    private Button btn_open_gal;
    private Usuario me;

    FirebaseStorage storage;
    private GoogleApiClient mGoogleApiClient;



    FirebaseAuth auth;
    FirebaseDatabase rtdb;
    Fragment fragment;
    private File photoFile;

    private String volver_a;
    private String donde_viene;

    public PerfilCliente() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_perfil_cliente);

        // Iniciar todos los fragmentes
        Intent i = getIntent();
        if (i != null) {
            volver_a = i.getStringExtra(Constantes.GO_TO_PERFIL);
            fragment = (Fragment) i.getSerializableExtra(Constantes.FRAGMENT);
            donde_viene = i.getStringExtra(Constantes.DONDE_VIENE);
        }

        rtdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();


        iv_perfil_cliente = findViewById(R.id.iv_perfil_cliente);
        iv_perfil_cliente.setOnClickListener(this);
        tv_nombre = findViewById(R.id.tv_nombre_cliente_perfil_cliente);
        tv_telefono = findViewById(R.id.tv_numero_telefono_perfil_cliente);
        image_atras = findViewById(R.id.iv_cerrar_perfil_cliente);
        iv_fragment_perfil_clientes_return = findViewById(R.id.iv_fragment_perfil_clientes_return);
        tv_nombre_cliente_perfil_cliente = findViewById(R.id.tv_nombre_cliente_perfil_cliente);
        tv_numero_telefono_perfil_cliente = findViewById(R.id.tv_numero_telefono_perfil_cliente);
        tv_cerrar_perfil_cliente_txt = findViewById(R.id.tv_cerrar_perfil_cliente_txt);
        iv_cerrar_perfil_cliente = findViewById(R.id.iv_cerrar_perfil_cliente);
        btn_open_gal = findViewById(R.id.btn_open_gal);
        btn_open_gal.setOnClickListener(this);

        iv_fragment_perfil_clientes_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                if (volver_a.equals(Constantes.FRAGMENT_FINANZAS)) {
                    Intent i = new Intent(PerfilCliente.this, HomeCliente.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_FINANZAS);
                    startActivity(i);
                    finish();
                }
                if (volver_a.equals(Constantes.FRAGMENT_PLANES)) {
                    Intent i = new Intent(PerfilCliente.this, HomeCliente.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_PLANES);
                    startActivity(i);
                    finish();
                }
                if (volver_a.equals(Constantes.FRAGMENT_CUENTAS)) {
                    Intent i = new Intent(PerfilCliente.this, HomeCliente.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CUENTAS);
                    startActivity(i);
                    finish();
                }
                if (volver_a.equals(Constantes.FRAGMENT_CLIENTE)) {
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CLIENTE);
                    startActivity(i);
                    finish();
                }
                if (volver_a.equals(Constantes.FRAGMENT_ESTADISTICAS)) {
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_ESTADISTICAS);
                    startActivity(i);
                    finish();
                }
                if (volver_a.equals(Constantes.FRAGMENT_TRANSACCION)) {
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_TRANSACCION);
                    startActivity(i);
                    finish();
                }
                if (volver_a.equals(Constantes.FRAGMENT_CARGAR)) {
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CARGAR);
                    startActivity(i);
                    finish();
                }
                if (volver_a.equals(Constantes.DATOS_FRAGMENT)) {
                    Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                    i.putExtra(Constantes.FRAGMENT, Constantes.DATOS_FRAGMENT);
                    i.putExtra(Constantes.DONDE_VIENE, donde_viene);
                    startActivity(i);
                    finish();
                }
            }


        });


        tv_micuenta_perfil_cliente = findViewById(R.id.tv_micuenta_perfil_cliente);
        tv_micuenta_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilCliente.this, MiCuentaPerfil.class);
                i.putExtra(Constantes.DONDE_VIENE, volver_a);
                startActivity(i);
            }
        });

        tv_ajustes_perfil_cliente = findViewById(R.id.tv_ajustes_perfil_cliente);
        tv_ajustes_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilCliente.this, AjustesContrasena.class);
                startActivity(i);
            }
        });

        iv_cerrar_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();

                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleApiClient = new GoogleApiClient.Builder(PerfilCliente.this)
                        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                        .build();

                // Google sign out
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
            }
        });

        tv_cerrar_perfil_cliente_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(PerfilCliente.this, Splash.class);
                startActivity(i);
                finish();
            }
        });


        rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID).child(auth.getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        tv_nombre_cliente_perfil_cliente.setText(usuario.getNombre());
                        if (usuario.getTelefono() != null) {
                            tv_numero_telefono_perfil_cliente.setText(usuario.getTelefono());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


        tv_ayuda_perfil_cliente = findViewById(R.id.tv_ayuda_perfil_cliente);
        tv_ayuda_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ayuda = new AlertDialog.Builder(getApplicationContext());
                ayuda.setTitle(R.string.title_ayuda);
                ayuda.setMessage(R.string.comunicacion_ayuda);
                ayuda.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ayuda.show();
            }
        });

        tv_informacion_perfil_cliente = findViewById(R.id.tv_informacion_perfil_cliente);
        tv_informacion_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(getApplicationContext());
                info.setTitle(R.string.terminos_y_condiciones);
                info.setMessage(R.string.mensaje_terminos_condiciones);
                info.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.show();
            }
        });

        rtdb.getReference().child(Constantes.CHILD_USUARIOS_ID).child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                me = dataSnapshot.getValue(Usuario.class);
                cargarFotoPerfil();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent i = null;
        switch (v.getId()) {
            case R.id.iv_perfil_cliente:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoFile = new File(Environment.getExternalStorageDirectory() + "/" + UUID.randomUUID().toString() + ".png");
                Uri uri = FileProvider.getUriForFile(this, getPackageName(), photoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(i, CAMERA_CALLBACK_ID);
                break;
            case R.id.btn_open_gal:
                i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_CALLBACK_ID);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Luego de tomar la foto y guardarla
        if (requestCode == CAMERA_CALLBACK_ID && resultCode == RESULT_OK) {
            subirImagen();
        }
        if (requestCode == GALLERY_CALLBACK_ID && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            photoFile = new File(UtilDomi.getPath(this, uri));
            subirImagen();
        }
    }

    private void subirImagen() {
        try {
            if (me != null) {
                if (me.getTelefono() != null) {
                    StorageReference ref = storage.getReference().child(Constantes.CHILD_IMAGENES_PERFIL).child(me.getTelefono());
                    FileInputStream fis = new FileInputStream(photoFile);
                    ref.putStream(fis).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            showProgressDialog(PerfilCliente.this);
                            cargarFotoPerfil();
                            hideProgressDialog();
                        }
                    });
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void cargarFotoPerfil() {
        if (me.getTelefono() != null) {
            StorageReference ref = storage.getReference().child(Constantes.CHILD_IMAGENES_PERFIL).child(me.getTelefono());
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(PerfilCliente.this).load(uri).into(iv_perfil_cliente);
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (volver_a == null) {
            Toast.makeText(PerfilCliente.this, "Arreglar!!!!!", Toast.LENGTH_SHORT).show();
        } else {
            if (volver_a.equals(Constantes.FRAGMENT_FINANZAS)) {
                Intent i = new Intent(PerfilCliente.this, HomeCliente.class);
                i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_FINANZAS);
                startActivity(i);
                finish();
            }
            if (volver_a.equals(Constantes.FRAGMENT_PLANES)) {
                Intent i = new Intent(PerfilCliente.this, HomeCliente.class);
                i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_PLANES);
                startActivity(i);
                finish();
            }
            if (volver_a.equals(Constantes.FRAGMENT_CUENTAS)) {
                Intent i = new Intent(PerfilCliente.this, HomeCliente.class);
                i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CUENTAS);
                startActivity(i);
                finish();
            }
            if (volver_a.equals(Constantes.FRAGMENT_CLIENTE)) {
                Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CLIENTE);
                startActivity(i);
                finish();
            }
            if (volver_a.equals(Constantes.FRAGMENT_ESTADISTICAS)) {
                Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_ESTADISTICAS);
                startActivity(i);
                finish();
            }
            if (volver_a.equals(Constantes.FRAGMENT_TRANSACCION)) {
                Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_TRANSACCION);
                startActivity(i);
                finish();
            }
            if (volver_a.equals(Constantes.FRAGMENT_CARGAR)) {
                Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                i.putExtra(Constantes.FRAGMENT, Constantes.FRAGMENT_CARGAR);
                startActivity(i);
                finish();
            }
            if (volver_a.equals(Constantes.DATOS_FRAGMENT)) {
                Intent i = new Intent(PerfilCliente.this, HomeAdministrador.class);
                i.putExtra(Constantes.FRAGMENT, Constantes.DATOS_FRAGMENT);
                i.putExtra(Constantes.DONDE_VIENE, donde_viene);
                startActivity(i);
                finish();
            }
        }
    }
}
