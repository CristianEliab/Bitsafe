package com.appmoviles.proyecto.modelo;

import java.util.List;

public class Categoria {

    private String categoriaID;
    private String nombre;
    private Categoria SubCategoria;


    public Categoria() {
    }

    public Categoria(String categoriaID, String nombre, Categoria subCategoria) {
        this.categoriaID = categoriaID;
        this.nombre = nombre;
        SubCategoria = subCategoria;
    }

    public Categoria getSubCategoria() {
        return SubCategoria;
    }

    public void setSubCategoria(Categoria subCategoria) {
        SubCategoria = subCategoria;
    }

    public String getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(String categoriaID) {
        this.categoriaID = categoriaID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return nombre;
    }
}
