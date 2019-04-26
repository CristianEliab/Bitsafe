package com.appmoviles.proyecto.modelo;

public class RolUsuario {

    private String usuarioID;
    private String rolID;

    public RolUsuario(String usuarioID, String rolID) {
        this.usuarioID = usuarioID;
        this.rolID = rolID;
    }

    public RolUsuario() {
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getRolID() {
        return rolID;
    }

    public void setRolID(String rolID) {
        this.rolID = rolID;
    }
}
