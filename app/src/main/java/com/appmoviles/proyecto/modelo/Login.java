package com.appmoviles.proyecto.modelo;

public class Login {

    private String loginID;
    private String correo;
    private String contrasenia;
    private String usuarioID;

    public Login(String loginID, String correo, String contrasenia, String usuarioID) {
        this.loginID = loginID;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.usuarioID = usuarioID;
    }

    public Login() {
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }
}
