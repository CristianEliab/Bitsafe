package com.appmoviles.proyecto.modelo;

import java.io.Serializable;

public class PlanAhorro implements Serializable {

    private String planAhorroID;
    private String usuarioID;
    private String fechaFinal;
    private String fechaInicio;
    private String cuota;
    private String cantidadCuotas;
    private String monto;
    private String meta;
    private String periodo;
    private String descripcion;
    private String estadoPlanAhorroID;

    public PlanAhorro(String planAhorroID, String usuarioID, String fechaFinal, String fechaInicio, String cuota, String cantidadCuotas, String monto, String meta , String periodo, String descripcion, String estadoPlanAhorroID) {
        this.planAhorroID = planAhorroID;
        this.usuarioID = usuarioID;
        this.fechaFinal = fechaFinal;
        this.fechaInicio = fechaInicio;
        this.cuota = cuota;
        this.cantidadCuotas = cantidadCuotas;
        this.monto = monto;
        this.meta = meta;
        this.periodo = periodo;
        this.descripcion = descripcion;
        this.estadoPlanAhorroID = estadoPlanAhorroID;
    }

    public PlanAhorro() {
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getPlanAhorroID() {
        return planAhorroID;
    }

    public void setPlanAhorroID(String planAhorroID) {
        this.planAhorroID = planAhorroID;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public String getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(String cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoPlanAhorroID() {
        return estadoPlanAhorroID;
    }

    public void setEstadoPlanAhorroID(String estadoPlanAhorroID) {
        this.estadoPlanAhorroID = estadoPlanAhorroID;
    }
}
