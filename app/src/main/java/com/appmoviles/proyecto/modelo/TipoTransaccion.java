package com.appmoviles.proyecto.modelo;

public class TipoTransaccion {

    private String tipoTransaccionID;
    private String nombre;
    private String tipoFlujoDinero;

    public TipoTransaccion(String tipoTransaccionID, String nombre, String tipoFlujoDinero) {
        this.tipoTransaccionID = tipoTransaccionID;
        this.nombre = nombre;
        this.tipoFlujoDinero = tipoFlujoDinero;
    }

    public TipoTransaccion() {
    }

    public String getTipoTransaccionID() {
        return tipoTransaccionID;
    }

    public void setTipoTransaccionID(String tipoTransaccionID) {
        this.tipoTransaccionID = tipoTransaccionID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoFlujoDinero() {
        return tipoFlujoDinero;
    }

    public void setTipoFlujoDinero(String tipoFlujoDinero) {
        this.tipoFlujoDinero = tipoFlujoDinero;
    }
}
