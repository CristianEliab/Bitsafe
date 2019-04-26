package com.appmoviles.proyecto.modelo;

public class Rol {

    private String rolID;
    private String nombreRol;

    public Rol(String rolID, String nombreRol) {
        this.rolID = rolID;
        this.nombreRol = nombreRol;
    }

    public Rol() {
    }

    public String getRolID() {
        return rolID;
    }

    public void setRolID(String rolID) {
        this.rolID = rolID;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
