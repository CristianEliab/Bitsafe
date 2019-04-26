package com.appmoviles.proyecto.modelo;

public class Categoria {

    private String categoriaID;
    private String nombre;
    private String subCategoria;

    public Categoria(String categoriaID, String nombre, String subCategoria) {
        this.categoriaID = categoriaID;
        this.nombre = nombre;
        this.subCategoria = subCategoria;
    }

    public Categoria() {
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

    public String getSubCategoria() {
        return subCategoria;
    }

    public void setSubCategoria(String idCategoria) {
        this.subCategoria = idCategoria;
    }
}
