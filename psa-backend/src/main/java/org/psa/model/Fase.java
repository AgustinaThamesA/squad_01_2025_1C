package org.psa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fases")
public class Fase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFase;
    
    @Column(nullable = false)
    private String nombre;
    
    private Integer orden;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    
    // Relación con Proyecto (lado "muchos" de la relación 1:n)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id")
    @JsonBackReference("proyecto-fases") // ✅ Este lado NO se serializa (evita bucle)
    private Proyecto proyecto;
    
    @ManyToMany(mappedBy = "fases", fetch = FetchType.LAZY, 
           cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonBackReference
    private List<Tarea> tareas;
    
    // Constructor por defecto (requerido por JPA)
    public Fase() {
        this.tareas = new ArrayList<>();
    }
    
    // Tu constructor original
    public Fase(String nombre, int orden) {
        this();
        this.nombre = nombre;
        this.orden = orden;
    }
    
    // Getters (todos iguales)
    public Long getIdFase() {
        return idFase;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Integer getOrden() {
        return orden;
    }
    
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    
    public LocalDate getFechaFinEstimada() {
        return fechaFinEstimada;
    }
    
    public List<Tarea> getTareas() {
        return tareas;
    }
    
    public Proyecto getProyecto() {
        return proyecto;
    }
    
    // Setters y métodos de negocio (SIN CAMBIOS)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public void planificarFechas(LocalDate inicio, LocalDate fin) {
        this.fechaInicio = inicio;
        this.fechaFinEstimada = fin;
    }
    
    public void agregarTarea(Tarea tarea) {
        if (!this.tareas.contains(tarea)) {
            this.tareas.add(tarea);
            tarea.agregarFase(this); // Mantener consistencia bidireccional
        }
    }
    
    public void removerTarea(Tarea tarea) {
        this.tareas.remove(tarea);
        tarea.removerFase(this);
    }
    
    // Tus métodos de cálculo (SIN CAMBIOS)
    public double calcularPorcentajeAvance() {
        if (tareas.isEmpty()) {
            return 0.0;
        }
        long tareasCompletadas = tareas.stream()
                .filter(tarea -> tarea.getEstado() == Tarea.Estado.COMPLETADA)
                .count();
        return (double) tareasCompletadas / tareas.size() * 100;
    }
    
    public String getEstadoDescriptivo() {
        double progreso = calcularPorcentajeAvance();
        if (progreso == 100.0) {
            return "Completada";
        } else if (progreso > 0) {
            return "En Progreso";
        } else {
            return "Pendiente";
        }
    }

}
