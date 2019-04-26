package com.appmoviles.proyecto.modelo;

public class TipoCuenta {

    private String tipoCuentaID;
    private String nombre;

    public TipoCuenta(String tipoCuentaID, String nombre) {
        this.tipoCuentaID = tipoCuentaID;
        this.nombre = nombre;
    }

    public TipoCuenta() {
    }

    public String getTipoCuentaID() {
        return tipoCuentaID;
    }

    public void setTipoCuentaID(String tipoCuentaID) {
        this.tipoCuentaID = tipoCuentaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
