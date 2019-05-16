package com.appmoviles.proyecto.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appmoviles.proyecto.R;

import java.util.ArrayList;

public class AdapterArchivos extends ArrayAdapter<Archivos> {

    public AdapterArchivos(Context context, ArrayList<Archivos> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Archivos file = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.renglon_archivos, parent, false);
        }
        // Lookup view for data population

        ImageView tvFoto = null;
        if (file.getArchivo().contains(".json")
                || file.getArchivo().contains(".dex")
                || file.getArchivo().contains(".jpg")
                || file.getArchivo().contains(".png")
                || file.getArchivo().contains(".doc")
        ) {
            tvFoto = (ImageView) convertView.findViewById(R.id.iv_archivo_foto);
            tvFoto.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_file));
        } else {
            tvFoto = (ImageView) convertView.findViewById(R.id.iv_archivo_foto);
            tvFoto.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.fragment_cargar_ic_archivos));
        }

        TextView tvNombre = (TextView) convertView.findViewById(R.id.tv_archivo_nombre);
        // Populate the data into the template view using the data object
        tvNombre.setText(file.getArchivo());


        // Return the completed view to render on screen
        return convertView;
    }

}
