package org.psa.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "reportes_estado")
public class ReporteEstado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReporte; // Cambio: int → Long
    
    @Column(nullable = false)
    private LocalDate fecha;
    
    @Column(name = "porcentaje_avance")
    private Double porcentajeAvance;
    
    @Column(columnDefinition = "TEXT")
    private String comentarios;
    
    // Relación con Proyecto (lado "muchos" de la relación 1:n)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;

    // Constructor por defecto (requerido por JPA)
    public ReporteEstado() {
    }

    // Tu constructor original con fecha actual (lógica mantenida)
    public ReporteEstado(double porcentajeAvance, String comentarios) {
        this.fecha = LocalDate.now();
        this.porcentajeAvance = porcentajeAvance;
        this.comentarios = comentarios;
    }

    // Tu constructor original con fecha específica (lógica mantenida)
    public ReporteEstado(LocalDate fecha, double porcentajeAvance, String comentarios) {
        this.fecha = fecha;
        this.porcentajeAvance = porcentajeAvance;
        this.comentarios = comentarios;
    }

    // Getters (adaptados para Long)
    public Long getIdReporte() { // Cambio: int → Long
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
    public Proyecto getProyecto() { // Getter para la relación JPA
        return proyecto;
    }

    // Setters (SIN CAMBIOS - tu lógica se mantiene)
    public void setPorcentajeAvance(Double porcentajeAvance) {
        this.porcentajeAvance = porcentajeAvance;
    }
    
    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
    
    public void setProyecto(Proyecto proyecto) { // Setter para la relación JPA
        this.proyecto = proyecto;
    }

    // Tu método de formateo (SIN CAMBIOS - perfecto)
    public String getFechaFormateada() {
        return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
