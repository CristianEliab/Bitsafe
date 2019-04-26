package com.appmoviles.proyecto.modelo;

public class PlanAhorro {

    private String planAhorroID;
    private String usuarioID;
    private String fechaFinal;
    private String fechaInicio;
    private String cuotas;
    private String monto;
    private String periodo;
    private String descripcion;
    private String estadoPlanAhorroID;

    public PlanAhorro(String planAhorroID, String usuarioID, String fechaFinal, String fechaInicio, String cuotas, String monto, String periodo, String descripcion, String estadoPlanAhorroID) {
        this.planAhorroID = planAhorroID;
        this.usuarioID = usuarioID;
        this.fechaFinal = fechaFinal;
        this.fechaInicio = fechaInicio;
        this.cuotas = cuotas;
        this.monto = monto;
        this.periodo = periodo;
        this.descripcion = descripcion;
        this.estadoPlanAhorroID = estadoPlanAhorroID;
    }

    public PlanAhorro() {
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

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getCuotas() {
        return cuotas;
    }

    public void setCuotas(String cuotas) {
        this.cuotas = cuotas;
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
