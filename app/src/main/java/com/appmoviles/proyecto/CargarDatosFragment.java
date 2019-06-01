package com.appmoviles.proyecto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.appmoviles.proyecto.util.Constantes;
import com.appmoviles.proyecto.util.FileChooser;
import com.appmoviles.proyecto.util.JsonParse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;


public class CargarDatosFragment extends Fragment implements View.OnClickListener, Serializable {

    private ImageView iv_fragment_carga_seleccion_file;
    private ImageView iv_fragment_carga_perfil;


    public CargarDatosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cargar_datos, container, false);

        iv_fragment_carga_seleccion_file = v.findViewById(R.id.iv_fragment_carga_seleccion_file);
        iv_fragment_carga_perfil = v.findViewById(R.id.iv_fragment_carga_perfil);
        iv_fragment_carga_seleccion_file.setOnClickListener(this);


        iv_fragment_carga_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), PerfilCliente.class);
                i.putExtra(Constantes.GO_TO_PERFIL, Constantes.FRAGMENT_CARGAR);
                startActivity(i);
                getActivity().finish();
            }
        });

        return v;
    }

    private void processFile() {
        FileChooser fileChooser = new FileChooser(getActivity());

        fileChooser.setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(final File file) {
                // ....do something with the file
                cargarClientes(file);
                // then actually do something in another module

            }
        });
        // Set up and filter my extension I am looking for
        //fileChooser.setExtension("json");
        fileChooser.showDialog();
    }

    private void cargarClientes(File file) {
        try {

            InputStream in = new FileInputStream(file);
            JsonParse jsonParse = new JsonParse();
            jsonParse.readJsonStream(in);
            jsonParse.cargarDesdeArchivo();

            AlertDialog.Builder info = new AlertDialog.Builder(getContext());
            info = new AlertDialog.Builder(getContext());
            info.setTitle(R.string.inscripcion_correcto);
            info.setMessage(R.string.se_agrego_satisfactoriamente);
            info.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    HomeAdministrador activity = (HomeAdministrador) getActivity();
                    activity = (HomeAdministrador) getActivity();
                    activity.llamarClientesFragment();
                }
            });
            info.show();


        } catch (IOException e) {
            Toast.makeText(getContext(), "Error " + e.getMessage() + "-" + e.getCause() + "-" + e.getStackTrace(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error " + e.getMessage() + "-" + e.getCause() + "-" + e.getStackTrace(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fragment_carga_seleccion_file:
                processFile();
                break;
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
