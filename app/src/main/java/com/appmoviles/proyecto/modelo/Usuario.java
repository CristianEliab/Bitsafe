package com.appmoviles.proyecto.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {

    private String usuarioID;
    private String nombre;
    private String fecha_nacimiento;
    private String telefono;
    private String genero;
    private String estadoUsuarioID;
    private String loginID;
    private String configuracionID;

    //
    private String cedula;
    private String ubicacion;
    private String correo;
    private String contrasenia;
    private ArrayList<Banco> listaBancos;
    private ArrayList<Cuenta> listaCuentas;
    private long fechaCreacion;
    private String fecha_cargar;

    public Usuario(String usuarioID, String nombre, String fecha_nacimiento, String telefono, String genero, String estadoUsuarioID, String loginID, String configuracionID, String cedula, String ubicacion, String correo, ArrayList<Banco> listaBancos, ArrayList<Cuenta> listaCuentas) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.fecha_nacimiento = fecha_nacimiento;
        this.telefono = telefono;
        this.genero = genero;
        this.estadoUsuarioID = estadoUsuarioID;
        this.loginID = loginID;
        this.configuracionID = configuracionID;
        this.cedula = cedula;
        this.ubicacion = ubicacion;
        this.correo = correo;
        this.listaBancos = listaBancos;
        this.listaCuentas = listaCuentas;
    }

    public Usuario(String usuarioID, String nombre, String cedula, String telefono, String correo, String ubicacion) {
        this.usuarioID = usuarioID;
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.correo = correo;
        this.ubicacion = ubicacion;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public ArrayList<Banco> getListaBancos() {
        return listaBancos;
    }

    public void setListaBancos(ArrayList<Banco> listaBancos) {
        this.listaBancos = listaBancos;
    }

    public ArrayList<Cuenta> getListaCuentas() {
        return listaCuentas;
    }

    public void setListaCuentas(ArrayList<Cuenta> listaCuentas) {
        this.listaCuentas = listaCuentas;
    }

    public long getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(long fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFecha_cargar() {
        return fecha_cargar;
    }

    public void setFecha_cargar(String fecha_cargar) {
        this.fecha_cargar = fecha_cargar;
    }
}
