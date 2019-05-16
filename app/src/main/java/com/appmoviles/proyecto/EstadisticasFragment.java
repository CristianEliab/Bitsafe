package com.appmoviles.proyecto;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;


public class EstadisticasFragment extends Fragment implements Serializable {

    private ImageView iv_fragment_estadisticas_perfil;


    public EstadisticasFragment() {
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
        View v = inflater.inflate(R.layout.fragment_estadisticas, container, false);

        iv_fragment_estadisticas_perfil = v.findViewById(R.id.iv_fragment_estadisticas_perfil);
        iv_fragment_estadisticas_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onViewPerfilEstadisticas();
            }
        });

        return v;
    }

    //OBSERVER
    public interface OnViewPerfilEstadisticas {
        void onViewPerfilEstadisticas();
    }
    private OnViewPerfilEstadisticas listener;

    public void setListener(OnViewPerfilEstadisticas listener) {
        this.listener = listener;
    }
}
