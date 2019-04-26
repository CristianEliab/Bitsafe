package com.appmoviles.proyecto.modelo;

public class RolActividad {

    private String rolID;
    private String actividadID;

    public RolActividad(String rolID, String actividadID) {
        this.rolID = rolID;
        this.actividadID = actividadID;
    }

    public RolActividad() {
    }

    public String getRolID() {
        return rolID;
    }

    public void setRolID(String rolID) {
        this.rolID = rolID;
    }

    public String getActividadID() {
        return actividadID;
    }

    public void setActividadID(String actividadID) {
        this.actividadID = actividadID;
    }
}
