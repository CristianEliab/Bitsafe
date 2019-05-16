package com.appmoviles.proyecto.modelo;

import java.io.Serializable;

public class Cuenta implements Serializable {

    private String cuentaID;
    private String numeroCuenta;
    private String usuarioID;
    private String tipoCuentaID;
    private String tipoCuentaNombre;
    private String bancoID;
    private String saldo;
    private String fechaVinculacion;

    public Cuenta() {
    }

    public Cuenta(String cuentaID, String numeroCuenta, String usuarioID, String tipoCuentaID, String tipoCuentaNombre, String bancoID, String saldo, String fechaVinculacion) {
        this.cuentaID = cuentaID;
        this.numeroCuenta = numeroCuenta;
        this.usuarioID = usuarioID;
        this.tipoCuentaID = tipoCuentaID;
        this.tipoCuentaNombre = tipoCuentaNombre;
        this.bancoID = bancoID;
        this.saldo = saldo;
        this.fechaVinculacion = fechaVinculacion;
    }

    public String getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(String fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
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
