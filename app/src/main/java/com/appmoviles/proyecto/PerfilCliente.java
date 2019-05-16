package com.appmoviles.proyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class PerfilCliente extends Fragment {

    private TextView tv_nombre_iniciales;
    private TextView tv_nombre;
    private TextView tv_telefono;
    private ImageView image_atras;

    private TextView tv_micuenta_perfil_cliente;
    private TextView tv_ajustes_perfil_cliente;
    private TextView tv_ayuda_perfil_cliente;
    private TextView tv_informacion_perfil_cliente;

    Context contexto;

    public PerfilCliente() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_perfil_cliente, container, false);
        contexto = v.getContext();

        tv_nombre_iniciales = v.findViewById(R.id.iv_nombre_perfil_cliente);
        tv_nombre= v.findViewById(R.id.tv_nombre_cliente_perfil_cliente);
        tv_telefono= v.findViewById(R.id.tv_numero_telefono_perfil_cliente);
        image_atras = v.findViewById(R.id.iv_fragment_perfil_clientes);

        tv_micuenta_perfil_cliente = v.findViewById(R.id.tv_micuenta_perfil_cliente);
        tv_micuenta_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, MiCuentaPerfil.class );
                startActivity(i);
            }
        });

        tv_ajustes_perfil_cliente = v.findViewById(R.id.tv_ajustes_perfil_cliente);
        tv_ajustes_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, AjuestesContrasena.class);
                startActivity(i);
            }
        });
        tv_ajustes_perfil_cliente = v.findViewById(R.id.tv_ajustes_perfil_cliente);
        tv_ajustes_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(contexto, AjuestesContrasena.class);
                startActivity(i);
            }
        });

        tv_ayuda_perfil_cliente = v.findViewById(R.id.tv_ayuda_perfil_cliente);
        tv_ayuda_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ayuda = new AlertDialog.Builder(contexto);
                ayuda.setTitle("¿Necesitas ayuda?");
                ayuda.setMessage("Comunicate con nuestro centro de atención 01800 6784637");
                ayuda.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ayuda.show();
            }
        });
        tv_informacion_perfil_cliente = v.findViewById(R.id.tv_informacion_perfil_cliente);
        tv_informacion_perfil_cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder info = new AlertDialog.Builder(contexto);
                info.setTitle("Terminos y Condiciones");
                info.setMessage(" luego de la remisión o puesta en conocimiento,\n" +
                        "o publicación del reglamento o sus modificaciones o actualizaciones en la página Web del\n" +
                        "BANCO, este Reglamento se entenderá aceptado por EL CLIENTE con la contratación de\n" +
                        "productos y/o con la suscripción de la tarjeta de firmas en la que se establecen las condiciones\n" +
                        "de manejo de las cuentas y/o con el recibo de la Tarjeta Débito, Talonario o cualquier otro\n" +
                        "medio de manejo recibido por EL CLIENTE y/o con la utilización del producto, canal o\n" +
                        "servicio asociados y/o manteniendo de fondos disponibles en las cuentas y/o al mantener\n" +
                        "los productos y/o seguir realizando operaciones o utilizando servicios.");
                info.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                info.show();
            }
        });

        return v;
    }

}
