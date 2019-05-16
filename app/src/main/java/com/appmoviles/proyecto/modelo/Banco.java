package com.appmoviles.proyecto.modelo;

import java.io.Serializable;

public class Banco implements Serializable {

    private String bancoID;
    private String nombreBanco;
    private String icono;
    private String saldo;

    public Banco() {
    }

    public Banco(String bancoID, String nombreBanco, String icono, String saldo) {
        this.bancoID = bancoID;
        this.nombreBanco = nombreBanco;
        this.icono = icono;
        this.saldo = saldo;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }


}
