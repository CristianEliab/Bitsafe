package com.appmoviles.proyecto.modelo;

public class Transaccion {

    private String transaccionID;
    private String cuentaOrigenID;
    private String cuentaDestinoID;
    private String tipoTransaccionID;
    private String categoriaID;
    private String descripcion;
    private String montoTransaccion;
    private String fechaTransaccion;

    public Transaccion(String transaccionID, String cuentaOrigenID, String cuentaDestinoID, String tipoTransaccionID, String categoriaID, String descripcion, String montoTransaccion, String fechaTransaccion) {
        this.transaccionID = transaccionID;
        this.cuentaOrigenID = cuentaOrigenID;
        this.cuentaDestinoID = cuentaDestinoID;
        this.tipoTransaccionID = tipoTransaccionID;
        this.categoriaID = categoriaID;
        this.descripcion = descripcion;
        this.montoTransaccion = montoTransaccion;
        this.fechaTransaccion = fechaTransaccion;
    }

    public Transaccion() {
    }

    public String getTransaccionID() {
        return transaccionID;
    }

    public void setTransaccionID(String transaccionID) {
        this.transaccionID = transaccionID;
    }

    public String getCuentaOrigenID() {
        return cuentaOrigenID;
    }

    public void setCuentaOrigenID(String cuentaOrigenID) {
        this.cuentaOrigenID = cuentaOrigenID;
    }

    public String getCuentaDestinoID() {
        return cuentaDestinoID;
    }

    public void setCuentaDestinoID(String cuentaDestinoID) {
        this.cuentaDestinoID = cuentaDestinoID;
    }

    public String getTipoTransaccionID() {
        return tipoTransaccionID;
    }

    public void setTipoTransaccionID(String tipoTransaccionID) {
        this.tipoTransaccionID = tipoTransaccionID;
    }

    public String getCategoriaID() {
        return categoriaID;
    }

    public void setCategoriaID(String categoriaID) {
        this.categoriaID = categoriaID;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMontoTransaccion() {
        return montoTransaccion;
    }

    public void setMontoTransaccion(String montoTransaccion) {
        this.montoTransaccion = montoTransaccion;
    }

    public String getFechaTransaccion() {
        return fechaTransaccion;
    }

    public void setFechaTransaccion(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }
}
