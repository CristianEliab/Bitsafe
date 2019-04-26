package com.appmoviles.proyecto.modelo;

public class Cuenta {

    private String cuentaID;
    private String numeroCuenta;
    private String usuarioID;
    private String tipoCuentaID;
    private String tipoCuentaNombre;
    private String bancoID;
    public String nombre;
    public String numero;
    public String[] bancos;

    public Cuenta() {
    }

    public Cuenta(String nombre, String numero, String[] bancos) {
        this.nombre = nombre;
        this.numero = numero;
        this.bancos = bancos;
    }

    public Cuenta(String numero) {
        this.numero = numero;
    }

    public Cuenta(String cuentaID, String numeroCuenta, String usuarioID, String tipoCuentaID, String tipoCuentaNombre, String bancoID) {
        this.cuentaID = cuentaID;
        this.numeroCuenta = numeroCuenta;
        this.usuarioID = usuarioID;
        this.tipoCuentaID = tipoCuentaID;
        this.tipoCuentaNombre = tipoCuentaNombre;
        this.bancoID = bancoID;
    }

    public String getCuentaID() {
        return cuentaID;
    }

    public void setCuentaID(String cuentaID) {
        this.cuentaID = cuentaID;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getTipoCuentaID() {
        return tipoCuentaID;
    }

    public void setTipoCuentaID(String tipoCuentaID) {
        this.tipoCuentaID = tipoCuentaID;
    }

    public String getTipoCuentaNombre() {
        return tipoCuentaNombre;
    }

    public void setTipoCuentaNombre(String tipoCuentaNombre) {
        this.tipoCuentaNombre = tipoCuentaNombre;
    }

    public String getBancoID() {
        return bancoID;
    }

    public void setBancoID(String bancoID) {
        this.bancoID = bancoID;
    }
}
