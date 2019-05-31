package com.appmoviles.proyecto.util;

import java.util.ArrayList;

public class EstadisticasMes {

    private float numeroClientes;
    private ArrayList<String> listaUsuariosID;

    public EstadisticasMes() {
    }

    public EstadisticasMes(int numeroClientes) {
        this.numeroClientes = numeroClientes;
        listaUsuariosID = new ArrayList<>();
    }

    public EstadisticasMes(int numeroClientes, ArrayList<String> listaUsuariosID) {
        this.numeroClientes = numeroClientes;
        this.listaUsuariosID = listaUsuariosID;
    }

    public float getNumeroClientes() {
        return numeroClientes;
    }

    public void setNumeroClientes(float numeroClientes) {
        this.numeroClientes = numeroClientes;
    }

    public ArrayList<String> getListaUsuariosID() {
        return listaUsuariosID;
    }

    public void setListaUsuariosID(ArrayList<String> listaUsuariosID) {
        this.listaUsuariosID = listaUsuariosID;
    }
}
