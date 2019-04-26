package com.appmoviles.proyecto.modelo;

public class Usuario {

    private String usuarioID;
    private String nombre;
    private String fecha_nacimiento;
    private String telefono;
    private String genero;
    private String estadoUsuarioID;
    private String loginID;
    private String configuracionID;

    public Usuario(String usuarioID, String nombre, String fecha_nacimiento, String telefono, String genero, String estadoUsuarioID, String loginID, String configuracionID) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.genero = genero;
        this.estadoUsuarioID = estadoUsuarioID;
        this.loginID = loginID;
        this.configuracionID = configuracionID;
    }

    public Usuario() {
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEstadoUsuarioID() {
        return estadoUsuarioID;
    }

    public void setEstadoUsuarioID(String estadoUsuarioID) {
        this.estadoUsuarioID = estadoUsuarioID;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getConfiguracionID() {
        return configuracionID;
    }

    public void setConfiguracionID(String configuracionID) {
        this.configuracionID = configuracionID;
    }
}
