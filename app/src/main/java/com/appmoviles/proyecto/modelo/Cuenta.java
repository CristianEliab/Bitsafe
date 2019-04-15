package com.appmoviles.proyecto.modelo;

public class Cuenta {

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
}
