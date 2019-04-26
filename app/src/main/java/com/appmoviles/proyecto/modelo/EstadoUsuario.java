package com.appmoviles.proyecto.modelo;

public class EstadoUsuario {

    private String estadoID;
    private String nombre;

    public EstadoUsuario(String estadoID, String nombre) {
        this.estadoID = estadoID;
        this.nombre = nombre;
    }

    public EstadoUsuario() {
    }

    public String getEstadoID() {
        return estadoID;
    }

    public void setEstadoID(String estadoID) {
        this.estadoID = estadoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
