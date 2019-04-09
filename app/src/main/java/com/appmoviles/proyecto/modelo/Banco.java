package com.appmoviles.proyecto.modelo;

public class Banco {

    public String nombre;
    public String icono;

    public Banco() {
    }

    public Banco(String nombre, String icono) {
        this.nombre = nombre;
        this.icono = icono;
    }

    public Banco(String icono) {
        this.icono = icono;
    }

}
