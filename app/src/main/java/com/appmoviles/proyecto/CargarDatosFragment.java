package com.appmoviles.proyecto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.appmoviles.proyecto.util.FileChooser;

import java.io.File;
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
                listener.onViewPerfilCargar();
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
                String filename = file.getName();
                Toast.makeText(getContext(), "Archivo: " + filename +" seleccionado!", Toast.LENGTH_SHORT).show();
                // then actually do something in another module

            }
        });
        // Set up and filter my extension I am looking for
        //fileChooser.setExtension("json");
        fileChooser.showDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fragment_carga_seleccion_file:
                processFile();
                break;
        }
    }

    //OBSERVER
    public interface OnViewPerfilCargar {
        void onViewPerfilCargar();
    }
    private OnViewPerfilCargar listener;

    public void setListener(OnViewPerfilCargar listener) {
        this.listener = listener;
    }
}
