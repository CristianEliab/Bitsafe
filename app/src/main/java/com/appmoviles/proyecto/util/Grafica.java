package com.appmoviles.proyecto.util;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieData;

public class Grafica {

    private String tipo;
    private PieData pieData;
    private BarData barData;
    private String descripcion;

    public Grafica(String tipo, PieData pieData, BarData barData, String descripcion) {
        this.tipo = tipo;
        this.pieData = pieData;
        this.descripcion = descripcion;
        this.barData = barData;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public BarData getBarData() {
        return barData;
    }

    public void setBarData(BarData barData) {
        this.barData = barData;
    }
}
