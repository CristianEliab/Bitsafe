package com.appmoviles.proyecto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class PlanesFragment extends Fragment {

    private ImageButton btn_crear_ahorro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_planes, container, false);

        btn_crear_ahorro = (ImageButton) v.findViewById(R.id.btn_crear_ahorro);

        btn_crear_ahorro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.main, new CrearPlanFragment()).addToBackStack(null).commit();
            }
        });

        return v;
    }
}
