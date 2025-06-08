package org.psa.manager;

import org.psa.model.Fase;
import org.psa.model.Proyecto;
import org.psa.model.ReporteEstado;
import org.psa.model.Riesgo;
import org.psa.model.Tarea;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class GerenteProyecto {

    // Gestión de planificación
    public void planificarProyecto(Proyecto proyecto, LocalDate fechaInicio, LocalDate fechaFin) {
        proyecto.planificarFechas(fechaInicio, fechaFin);
    }

    // Gestión de fases
    public Fase crearFase(Proyecto proyecto, String nombre, int orden) {
        Fase fase = new Fase(nombre, orden);
        proyecto.agregarFase(fase);
        return fase;
    }

    public void planificarFase(Fase fase, LocalDate fechaInicio, LocalDate fechaFin) {
        fase.planificarFechas(fechaInicio, fechaFin);
    }

    // Gestión de tareas con relación n:m
    public Tarea crearTarea(String titulo, String descripcion, Tarea.Prioridad prioridad, String responsable) {
        return new Tarea(titulo, descripcion, prioridad, responsable);
    }

    public void asignarTareaAFase(Tarea tarea, Fase fase) {
        fase.agregarTarea(tarea);
    }

    public void asignarTareaAMultiplesFases(Tarea tarea, List<Fase> fases) {
        for (Fase fase : fases) {
            fase.agregarTarea(tarea);
        }
    }

    public void planificarTarea(Tarea tarea, LocalDate fechaInicio, LocalDate fechaFin) {
        tarea.planificarFechas(fechaInicio, fechaFin);
    }

    public void iniciarTarea(Tarea tarea) {
        tarea.iniciarTarea();
    }

    public void completarTarea(Tarea tarea) {
        tarea.completarTarea();
    }

    // Gestión de riesgos
    public Riesgo crearRiesgo(Proyecto proyecto, String descripcion, 
                             Riesgo.Probabilidad probabilidad, Riesgo.Impacto impacto) {
        Riesgo riesgo = new Riesgo(descripcion, probabilidad, impacto);
        proyecto.agregarRiesgo(riesgo);
        return riesgo;
    }

    public void mitigarRiesgo(Riesgo riesgo) {
        riesgo.setEstado(Riesgo.Estado.MITIGADO);
    }

    // Gestión de reportes
    public ReporteEstado generarReporte(Proyecto proyecto, String comentarios) {
        double porcentajeAvance = proyecto.calcularPorcentajeAvance();
        ReporteEstado reporte = new ReporteEstado(porcentajeAvance, comentarios);
        proyecto.agregarReporte(reporte);
        return reporte;
    }

    public ReporteEstado generarReporte(Proyecto proyecto, LocalDate fecha, double porcentaje, String comentarios) {
        ReporteEstado reporte = new ReporteEstado(fecha, porcentaje, comentarios);
        proyecto.agregarReporte(reporte);
        return reporte;
    }

    // Consultas y análisis
    public List<Tarea> obtenerTareasVencidas(Proyecto proyecto) {
        return proyecto.getFases().stream()
            .flatMap(fase -> fase.getTareas().stream())
            .filter(Tarea::estaVencida)
            .collect(Collectors.toList());
    }

    public List<Riesgo> obtenerRiesgosAltos(Proyecto proyecto) {
        return proyecto.getRiesgos().stream()
            .filter(Riesgo::esRiesgoAlto)
            .collect(Collectors.toList());
    }

    public List<Tarea> obtenerTareasMultifase(Proyecto proyecto) {
        return proyecto.getFases().stream()
            .flatMap(fase -> fase.getTareas().stream())
            .filter(Tarea::esMultifase)
            .distinct()
            .collect(Collectors.toList());
    }

    public void pausarProyecto(Proyecto proyecto) {
        proyecto.pausarProyecto();
    }

    public void cerrarProyecto(Proyecto proyecto) {
        proyecto.cerrarProyecto();
    }
}
