package com.appmoviles.proyecto.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appmoviles.proyecto.R;
import com.appmoviles.proyecto.modelo.Cuenta;

import java.util.ArrayList;

public class AdapterDatosCuentas  extends ArrayAdapter<Cuenta> {

    public AdapterDatosCuentas(Context context, ArrayList<Cuenta> bancos) {
        super(context, 0, bancos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Cuenta cuenta = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.renglon_cuentas, parent, false);
        }
        // Lookup view for data population

        TextView tvNombre = (TextView) convertView.findViewById(R.id.tv_datos_cuentas_nombre);
        // Populate the data into the template view using the data object
        tvNombre.setText(cuenta.getNumeroCuenta());


        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return initView(position, convertView);
    }

    private View initView(int position, View convertView) {
        if (convertView == null)
            convertView = View.inflate(getContext(), R.layout.renglon_list,
                    null);
        TextView tvText1 = (TextView) convertView.findViewById(R.id.tv_datos_banco_lista);
        tvText1.setText(getItem(position).getNumeroCuenta());
        return convertView;
    }

}
