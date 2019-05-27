package com.appmoviles.proyecto.util;

import com.github.mikephil.charting.data.PieData;

public class Grafica {

    private PieData pieData;
    private String descripcion;

    public Grafica(PieData pieData, String descripcion) {
        this.pieData = pieData;
        this.descripcion = descripcion;
    }

    public PieData getPieData() {
        return pieData;
    }

    public void setPieData(PieData pieData) {
        this.pieData = pieData;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
