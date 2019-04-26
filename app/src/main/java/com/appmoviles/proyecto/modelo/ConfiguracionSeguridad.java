package com.appmoviles.proyecto.modelo;

public class ConfiguracionSeguridad {

    private String configuracionSeguridadID;
    private String pin;
    private String huella;
    private String usuarioID;
    private String configuracionID;

    public ConfiguracionSeguridad(String configuracionSeguridadID, String pin, String huella, String usuarioID, String configuracionID) {
        this.configuracionSeguridadID = configuracionSeguridadID;
        this.pin = pin;
        this.huella = huella;
        this.usuarioID = usuarioID;
        this.configuracionID = configuracionID;
    }

    public ConfiguracionSeguridad() {
    }

    public String getConfiguracionSeguridadID() {
        return configuracionSeguridadID;
    }

    public void setConfiguracionSeguridadID(String configuracionSeguridadID) {
        this.configuracionSeguridadID = configuracionSeguridadID;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getHuella() {
        return huella;
    }

    public void setHuella(String huella) {
        this.huella = huella;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getConfiguracionID() {
        return configuracionID;
    }

    public void setConfiguracionID(String configuracionID) {
        this.configuracionID = configuracionID;
    }
}
