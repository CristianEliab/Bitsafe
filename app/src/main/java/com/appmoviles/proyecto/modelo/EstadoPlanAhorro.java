package com.appmoviles.proyecto.modelo;

public class EstadoPlanAhorro {

    private String planAhorroID;
    private String nombrePlan;
    private String estadoPlan;

    public EstadoPlanAhorro(String planAhorroID, String nombrePlan, String estadoPlan) {
        this.planAhorroID = planAhorroID;
        this.nombrePlan = nombrePlan;
        this.estadoPlan = estadoPlan;
    }

    public EstadoPlanAhorro() {
    }

    public String getPlanAhorroID() {
        return planAhorroID;
    }

    public void setPlanAhorroID(String planAhorroID) {
        this.planAhorroID = planAhorroID;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public String getEstadoPlan() {
        return estadoPlan;
    }

    public void setEstadoPlan(String estadoPlan) {
        this.estadoPlan = estadoPlan;
    }
}
