package com.appmoviles.proyecto.modelo;

public class Categoria {

    private String categoriaID;
    private String nombre;
    private Categoria subCategoriaID;

    public Categoria(String categoriaID, String nombre, Categoria subCategoria) {
        this.categoriaID = categoriaID;
        this.nombre = nombre;
        this.subCategoriaID = subCategoria;
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

    public Categoria getSubCategoria() {
        return subCategoriaID;
    }

    public void setSubCategoria(Categoria idCategoria) {
        this.subCategoriaID = idCategoria;
    }
}
