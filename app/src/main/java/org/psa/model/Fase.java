package org.psa.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Fase {
    private static int contadorFase = 1;
    private int idFase;
    private String nombre;
    private int orden;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private List<org.psa.model.Tarea> tareas; // Relación n:m con tareas

    public Fase(String nombre, int orden) {
        this.idFase = contadorFase++;
        this.nombre = nombre;
        this.orden = orden;
        this.tareas = new ArrayList<org.psa.model.Tarea>();
    }

    // Getters
    public int getIdFase() {
        return idFase;
    }
    public String getNombre() {
        return nombre;
    }
    public int getOrden() {
        return orden;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public LocalDate getFechaFinEstimada() {
        return fechaFinEstimada;
    }
    public List<org.psa.model.Tarea> getTareas() {
        return tareas;
    }

    // Setters y métodos de negocio
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setOrden(int orden) {
        this.orden = orden;
    }

    public void planificarFechas(LocalDate inicio, LocalDate fin) {
        this.fechaInicio = inicio;
        this.fechaFinEstimada = fin;
    }

    public void agregarTarea(org.psa.model.Tarea tarea) {
        if (!this.tareas.contains(tarea)) {
            this.tareas.add(tarea);
            tarea.agregarFase(this); // Mantener consistencia bidireccional
        }
    }

    public void removerTarea(org.psa.model.Tarea tarea) {
        this.tareas.remove(tarea);
        tarea.removerFase(this);
    }

    public double calcularPorcentajeAvance() {
        if (tareas.isEmpty()) {
            return 0.0;
        }
        long tareasCompletadas = tareas.stream()
            .filter(tarea -> tarea.getEstado() == org.psa.model.Tarea.Estado.COMPLETADA)
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
