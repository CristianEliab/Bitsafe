package com.appmoviles.proyecto;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appmoviles.proyecto.modelo.Categoria;
import com.appmoviles.proyecto.modelo.Transaccion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.appmoviles.proyecto.util.Constantes.BUNDLE_TIPO_I_O;
import static com.appmoviles.proyecto.util.Constantes.CHILD_CATEGORIAS;
import static com.appmoviles.proyecto.util.Constantes.CHILD_TRANSACCIONES;

public class DialogoEscogerCategoria extends DialogFragment {

    FirebaseDatabase database;
    FirebaseAuth auth;


    private TextView tv_dialogo_escoger_categoria_titulo;

    private Spinner spinner_dialogo_escoger_categoria_principal;
    private Spinner spinner_dialogo_escoger_categoria_secundaria;

    private Button btn_dialogo_escoger_categoria_cancelar;
    private Button btn_dialogo_escoger_categoria_guardar;

    private List<Categoria> listaCategoriasPrincipal;

    private String tipo_ingreso_o_gasto;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        View v = inflater.inflate(R.layout.dialogo_escoger_categoria, container);

        spinner_dialogo_escoger_categoria_principal = v.findViewById(R.id.spinner_dialogo_escoger_categoria_principal);
        spinner_dialogo_escoger_categoria_secundaria = v.findViewById(R.id.spinner_dialogo_escoger_categoria_secundaria);
        tv_dialogo_escoger_categoria_titulo = v.findViewById(R.id.tv_dialogo_escoger_categoria_titulo);
        btn_dialogo_escoger_categoria_cancelar = v.findViewById(R.id.btn_dialogo_escoger_categoria_cancelar);
        btn_dialogo_escoger_categoria_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoEscogerCategoria.this.getDialog().cancel();
            }
        });
        btn_dialogo_escoger_categoria_guardar = v.findViewById(R.id.btn_dialogo_escoger_categoria_guardar);
        btn_dialogo_escoger_categoria_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Categoria categoriaPrincipal = (Categoria) spinner_dialogo_escoger_categoria_principal.getSelectedItem();
                Categoria categoriaSecundaria = (Categoria) spinner_dialogo_escoger_categoria_secundaria.getSelectedItem();

                if (categoriaSecundaria != null) {
                    listener.categoriaSeleccionada(categoriaSecundaria);
                } else {
                    listener.categoriaSeleccionada(categoriaPrincipal);
                }
                DialogoEscogerCategoria.this.getDialog().dismiss();
            }
        });


        if (this.getArguments() != null) {
            tipo_ingreso_o_gasto = (String) getArguments().get(BUNDLE_TIPO_I_O);

            tv_dialogo_escoger_categoria_titulo.setText("Seleccione una categoría de " + tipo_ingreso_o_gasto);

        }

        cargarCategorias(v);

        spinner_dialogo_escoger_categoria_principal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoriaSeleccionada = (Categoria) parent.getItemAtPosition(position);

                List<Categoria> listaCategoriasSecundaria = new ArrayList<>();

                if (categoriaSeleccionada.getSubCategoria() != null) {
                    ArrayList<Categoria> list = categoriaSeleccionada.getSubCategoria();
                    for (Categoria categoria: list){
                        listaCategoriasSecundaria.add(categoria);
                    }
                }
                cambiarSubCategorias(view, listaCategoriasSecundaria);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public void cargarCategorias(final View v) {

        listaCategoriasPrincipal = new ArrayList<>();

        database.getReference().child(CHILD_CATEGORIAS).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Categoria categoriaTmp;
                for (DataSnapshot hijo : dataSnapshot.getChildren()) {
                    categoriaTmp = hijo.getValue(Categoria.class);
                    listaCategoriasPrincipal.add(categoriaTmp);
                }

                //Se asignan los adaptadores a cada spinner
                ArrayAdapter<Categoria> adapter_principal = new ArrayAdapter<Categoria>(v.getContext(), android.R.layout.simple_spinner_item, listaCategoriasPrincipal);
                adapter_principal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_dialogo_escoger_categoria_principal.setAdapter(adapter_principal);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void cambiarSubCategorias(View v, List<Categoria> ListaSubCategorias) {

        ArrayAdapter<Categoria> adapter_secundario = new ArrayAdapter<Categoria>(v.getContext(), android.R.layout.simple_spinner_item, ListaSubCategorias);
        adapter_secundario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dialogo_escoger_categoria_secundaria.setAdapter(adapter_secundario);
    }


    //Patrón observer para identifcar cual transacción se seleccionó

    public interface DialogoComunicacion {
        void categoriaSeleccionada(Categoria categoria);
    }

    private DialogoComunicacion listener;

    public void setListener(DialogoComunicacion listener) {

        this.listener = listener;
    }

    //Hasta aquí patrón observer


    public void mostrarMensaje(String texto) {
        Toast.makeText(getActivity(), texto, Toast.LENGTH_LONG).show();
    }
}
