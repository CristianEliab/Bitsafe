package com.appmoviles.proyecto.modelo;

public class Cliente {

    public String nombre;
    public String numero;
    public String[] bancos;

    public Cliente(){}

    public Cliente(String nombre, String numero, String[] bancos) {
        this.nombre = nombre;
        this.numero = numero;
        this.bancos = bancos;
    }
    public Cliente(String nombre) {
        this.nombre = nombre;
    }

}
