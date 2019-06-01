package com.appmoviles.proyecto.modelo;

import java.util.ArrayList;

public class Categoria {

    private String categoriaID;
    private String nombre;
    private ArrayList<Categoria> SubCategoria;


    public Categoria() {
    }

    public Categoria(String categoriaID, String nombre,  ArrayList<Categoria> subCategoria) {
        this.categoriaID = categoriaID;
        this.nombre = nombre;
        SubCategoria = subCategoria;
    }

    public  ArrayList<Categoria>  getSubCategoria() {
        return SubCategoria;
    }

    public void setSubCategoria( ArrayList<Categoria>  subCategoria) {
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
