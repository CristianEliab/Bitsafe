package com.appmoviles.proyecto.modelo;

public class Banco {

    private String bancoID;
    private String nombreBanco;
    private String IdBanco;
    public String nombre;
    public String icono;

    public Banco() {
    }

    public Banco(String nombre, String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }

    public Banco(String nombre) {
        this.nombre = nombre;
    }

    public Banco(String bancoID, String nombreBanco, String idBanco) {
        this.bancoID = bancoID;
        this.nombreBanco = nombreBanco;
        IdBanco = idBanco;
    }

    public String getBancoID() {
        return bancoID;
    }

    public void setBancoID(String bancoID) {
        this.bancoID = bancoID;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getIdBanco() {
        return IdBanco;
    }

    public void setIdBanco(String idBanco) {
        IdBanco = idBanco;
    }
}
