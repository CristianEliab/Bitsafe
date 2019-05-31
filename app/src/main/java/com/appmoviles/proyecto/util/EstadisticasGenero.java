package com.appmoviles.proyecto.util;

import java.util.ArrayList;

public class EstadisticasGenero {

    private float numero_masculinos;
    private float numero_femeninos;
    private ArrayList<String> listaID;

    public EstadisticasGenero(float numero_masculinos) {
        this.numero_masculinos = numero_masculinos;
    }

    public EstadisticasGenero() {
    }

    public EstadisticasGenero(float numero_masculinos, float numero_femeninos) {
        this.numero_masculinos = numero_masculinos;
        this.numero_femeninos = numero_femeninos;
    }

    public EstadisticasGenero(float numero_masculinos, float numero_femeninos, ArrayList<String> listaID) {
        this.numero_masculinos = numero_masculinos;
        this.numero_femeninos = numero_femeninos;
        this.listaID = listaID;
    }

    public float getNumero_masculinos() {
        return numero_masculinos;
    }

    public void setNumero_masculinos(float numero_masculinos) {
        this.numero_masculinos = numero_masculinos;
    }

    public float getNumero_femeninos() {
        return numero_femeninos;
    }

    public void setNumero_femeninos(float numero_femeninos) {
        this.numero_femeninos = numero_femeninos;
    }

    public ArrayList<String> getListaID() {
        return listaID;
    }

    public void setListaID(ArrayList<String> listaID) {
        this.listaID = listaID;
    }
}
