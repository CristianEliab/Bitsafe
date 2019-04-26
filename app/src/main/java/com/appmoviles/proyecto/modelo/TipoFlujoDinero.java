package com.appmoviles.proyecto.modelo;

public class TipoFlujoDinero {

    private String tipoFlujoeDineroID;
    private String flujoDinero;

    public TipoFlujoDinero(String tipoFlujoeDineroID, String flujoDinero) {
        this.tipoFlujoeDineroID = tipoFlujoeDineroID;
        this.flujoDinero = flujoDinero;
    }

    public TipoFlujoDinero() {
    }

    public String getTipoFlujoeDineroID() {
        return tipoFlujoeDineroID;
    }

    public void setTipoFlujoeDineroID(String tipoFlujoeDineroID) {
        this.tipoFlujoeDineroID = tipoFlujoeDineroID;
    }

    public String getFlujoDinero() {
        return flujoDinero;
    }

    public void setFlujoDinero(String flujoDinero) {
        this.flujoDinero = flujoDinero;
    }
}
