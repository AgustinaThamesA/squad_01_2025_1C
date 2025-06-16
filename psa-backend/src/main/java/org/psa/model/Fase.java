package org.psa.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fases")
public class Fase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFase; // Cambio: int → Long
    
    @Column(nullable = false)
    private String nombre;
    
    private Integer orden;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    
    // Relación con Proyecto (lado "muchos" de la relación 1:n)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id")
    private Proyecto proyecto;
    
    // Relación n:m con Tareas (tu lógica original mantenida)
    @ManyToMany(mappedBy = "fases", fetch = FetchType.LAZY)
    private List<Tarea> tareas;

    // Constructor por defecto (requerido por JPA)
    public Fase() {
        this.tareas = new ArrayList<>();
    }

    // Tu constructor original (lógica mantenida, sin contador estático)
    public Fase(String nombre, int orden) {
        this();
        this.nombre = nombre;
        this.orden = orden;
    }

    // Getters (adaptados para Long)
    public Long getIdFase() { // Cambio: int → Long
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
    public Proyecto getProyecto() { // Getter para la relación JPA
        return proyecto;
    }

    // Setters y métodos de negocio (SIN CAMBIOS - tu lógica se mantiene)
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setOrden(Integer orden) {
        this.orden = orden;
    }
    public void setProyecto(Proyecto proyecto) { // Setter para la relación JPA
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

    // Tus métodos de cálculo (SIN CAMBIOS - perfectos)
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
