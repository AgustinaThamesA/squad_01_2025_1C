package org.psa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reportes_estado")
public class ReporteEstado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReporte;
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(name = "porcentaje_avance")
    private Double porcentajeAvance;
    
    @Column(columnDefinition = "TEXT")
    private String comentarios;
    
    // ✅ SOLUCIÓN: Agregar @JsonBackReference para evitar bucle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id")
    @JsonBackReference("proyecto-reportes") // ✅ Este lado NO se serializa
    private Proyecto proyecto;
    
    // Constructor por defecto (requerido por JPA)
    public ReporteEstado() {
    }
    
    // Tu constructor original con fecha actual
    public ReporteEstado(double porcentajeAvance, String comentarios) {
        this.fecha = LocalDate.now();
        this.porcentajeAvance = porcentajeAvance;
        this.comentarios = comentarios;
    }
    
    // Tu constructor original con fecha específica
    public ReporteEstado(LocalDate fecha, double porcentajeAvance, String comentarios) {
        this.fecha = fecha;
        this.porcentajeAvance = porcentajeAvance;
        this.comentarios = comentarios;
    }
    
    // Getters
    public Long getIdReporte() {
        return idReporte;
    }
    
    public LocalDate getFecha() {
        return fecha;
    }
    
    public Double getPorcentajeAvance() {
        return porcentajeAvance;
    }
    
    public String getComentarios() {
        return comentarios;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    
    // Setters (SIN CAMBIOS)
    public void setPorcentajeAvance(Double porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }
    
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    // Tu método de formateo (SIN CAMBIOS - perfecto)
    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
