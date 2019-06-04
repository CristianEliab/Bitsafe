package com.appmoviles.proyecto;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

public class FrecuenciaPago extends DialogFragment {

    private Spinner spinner_diario;
    private Spinner spinner_semanal;
    private Spinner spinner_2semanas;
    private Spinner spinner_mensual;


    public FrecuenciaPago(){

    }

    private OnAddFrequencyPaymentListener callback;

    public interface OnAddFrequencyPaymentListener {
        public void OnAddFrequencyPaymentSubmit(String fpSelected);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (OnAddFrequencyPaymentListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling Fragment must implement OnAddFrequencyPaymentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment_frequency, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner_diario = view.findViewById(R.id.spinner_diario);
        spinner_semanal = view.findViewById(R.id.spinner_semanal);
        spinner_2semanas = view.findViewById(R.id.spinner_2semanas);
        spinner_mensual = view.findViewById(R.id.spinner_mensual);

        spinner_diario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String header = spinner_diario.getItemAtPosition(0).toString();
                String text = spinner_diario.getSelectedItem().toString();
                callback.OnAddFrequencyPaymentSubmit(header+"/"+text);
                //closeDialog(text);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_semanal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String header = spinner_semanal.getItemAtPosition(0).toString();
                String text = spinner_semanal.getSelectedItem().toString();
                callback.OnAddFrequencyPaymentSubmit(header+"/"+text);
                //closeDialog(text);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_2semanas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String header = spinner_2semanas.getItemAtPosition(0).toString();
                String text = spinner_2semanas.getSelectedItem().toString();
                callback.OnAddFrequencyPaymentSubmit(header+"/"+text);
                //closeDialog(text);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_mensual.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String header = spinner_mensual.getItemAtPosition(0).toString();
                String text = spinner_mensual.getSelectedItem().toString();
                callback.OnAddFrequencyPaymentSubmit(header+"/"+text);
                //closeDialog(text);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void closeDialog(String text){
        Log.e("///// Entrooo a close", text);
        if(text != "Diario" && text != "Semanal" && text != "Cada 2 Semanas" && text != "Mensual") {
            FrecuenciaPago.this.getDialog().dismiss();
        }
    }

}
