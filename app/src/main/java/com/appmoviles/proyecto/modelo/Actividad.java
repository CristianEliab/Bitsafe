package com.appmoviles.proyecto.modelo;

public class Actividad {

    private String actividadID;
    private String nombreActividad;

    public Actividad(String actividadID, String nombreActividad) {
        this.actividadID = actividadID;
        this.nombreActividad = nombreActividad;
    }

    public Actividad() {
    }

    public String getActividadID() {
        return actividadID;
    }

    public void setActividadID(String actividadID) {
        this.actividadID = actividadID;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }
}
