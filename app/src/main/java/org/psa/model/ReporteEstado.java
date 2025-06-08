package org.psa.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReporteEstado {
    private static int contadorReporte = 1;
    private int idReporte;
    private LocalDate fecha;
    private double porcentajeAvance;
    private String comentarios;

    public ReporteEstado(double porcentajeAvance, String comentarios) {
        this.idReporte = contadorReporte++;
        this.fecha = LocalDate.now();
        this.porcentajeAvance = porcentajeAvance;
        this.comentarios = comentarios;
    }

    public ReporteEstado(LocalDate fecha, double porcentajeAvance, String comentarios) {
        this.idReporte = contadorReporte++;
        this.fecha = fecha;
        this.porcentajeAvance = porcentajeAvance;
        this.comentarios = comentarios;
    }

    // Getters
    public int getIdReporte() {
        return idReporte;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public double getPorcentajeAvance() {
        return porcentajeAvance;
    }
    public String getComentarios() {
        return comentarios;
    }

    // Setters
    public void setPorcentajeAvance(double porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }
    
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
