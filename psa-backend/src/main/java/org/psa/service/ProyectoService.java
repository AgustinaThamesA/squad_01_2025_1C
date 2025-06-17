package org.psa.service;

import org.psa.model.*;
import org.psa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private FaseRepository faseRepository;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private RiesgoRepository riesgoRepository;

    // ========================================
    // GESTIÓN DE PROYECTOS
    // ========================================

    @Transactional
    public Proyecto crearProyecto(String nombre, String descripcion, String liderProyecto) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede ser vacío");
        }
        if (liderProyecto == null || liderProyecto.isBlank()) {
            throw new IllegalArgumentException("El líder del proyecto no puede ser vacío");
        }
        Proyecto proyecto = new Proyecto(nombre, descripcion, liderProyecto);
        return proyectoRepository.save(proyecto);
    }

    @Transactional
    public Proyecto guardarProyecto(Proyecto proyecto) {
        if (proyecto == null) {
            throw new IllegalArgumentException("Proyecto inválido");
        }
        return proyectoRepository.save(proyecto);
    }


    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll();
    }

    public Proyecto obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Proyecto con ID " + id + " no encontrado"));
    }

    @Transactional
    public boolean eliminarProyecto(Long id) {
        if (proyectoRepository.existsById(id)) {
            proyectoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Proyecto> filtrarProyectosPorEstado(Proyecto.Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        return proyectoRepository.findByEstado(estado);
    }

    @Transactional
    public Proyecto cambiarEstadoProyecto(Long proyectoId, Proyecto.Estado nuevoEstado) {
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        switch (nuevoEstado) {
            case CERRADO -> proyecto.cerrarProyecto();
            case PAUSADO -> proyecto.pausarProyecto();
            default -> proyecto.setEstado(nuevoEstado);
        }
        return proyectoRepository.save(proyecto);
    }

    @Transactional
    public Proyecto planificarProyecto(Long proyectoId, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        proyecto.planificarFechas(fechaInicio, fechaFin);
        return proyectoRepository.save(proyecto);
    }

    // ========================================
    // GESTIÓN DE FASES
    // ========================================

    // Reemplaza el método crearFase en tu ProyectoService.java
    @Transactional
    public Fase crearFase(Long proyectoId, String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la fase no puede ser vacío");
        }
        
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            
            // Calculamos automáticamente el siguiente orden
            int nuevoOrden = faseRepository.countByProyectoIdProyecto(proyectoId) + 1;
            Fase fase = new Fase(nombre, nuevoOrden);
            
            proyecto.agregarFase(fase);
            proyectoRepository.save(proyecto);
            return fase;
        }
        return null;
    }

    public List<Fase> obtenerFasesDelProyecto(Long proyectoId) {
        return faseRepository.findByProyectoIdProyectoOrderByOrden(proyectoId);
    }

    @Transactional
    public Fase planificarFase(Long faseId, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        Fase fase = faseRepository.findById(faseId)
                .orElseThrow(() -> new IllegalArgumentException("Fase con ID " + faseId + " no encontrada"));
        fase.planificarFechas(fechaInicio, fechaFin);
        return faseRepository.save(fase);
    }

    // ========================================
    // GESTIÓN DE TAREAS
    // ========================================

    @Transactional
    public Tarea crearTarea(String titulo, String descripcion, Tarea.Prioridad prioridad, String responsable) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título de la tarea no puede ser vacío");
        }
        if (responsable == null || responsable.isBlank()) {
            throw new IllegalArgumentException("El responsable no puede ser vacío");
        }
        Tarea tarea = new Tarea(titulo, descripcion, prioridad, responsable);
        return tareaRepository.save(tarea);
    }

    @Transactional
    public Tarea asignarTareaAFase(Long tareaId, Long faseId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no encontrada"));
        Fase fase = faseRepository.findById(faseId)
                .orElseThrow(() -> new IllegalArgumentException("Fase con ID " + faseId + " no encontrada"));
        fase.agregarTarea(tarea);
        faseRepository.save(fase);
        return tarea;
    }

    @Transactional
    public Tarea asignarTareaAMultiplesFases(Long tareaId, List<Long> faseIds) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no encontrada"));
        for (Long faseId : faseIds) {
            Fase fase = faseRepository.findById(faseId)
                    .orElseThrow(() -> new IllegalArgumentException("Fase con ID " + faseId + " no encontrada"));
            fase.agregarTarea(tarea);
            faseRepository.save(fase);
        }
        return tarea;
    }

    @Transactional
    public Tarea planificarTarea(Long tareaId, LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no encontrada"));
        tarea.planificarFechas(fechaInicio, fechaFin);
        return tareaRepository.save(tarea);
    }

    @Transactional
    public Tarea iniciarTarea(Long tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no encontrada"));
        tarea.iniciarTarea();
        return tareaRepository.save(tarea);
    }

    @Transactional
    public Tarea completarTarea(Long tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no encontrada"));
        tarea.completarTarea();
        return tareaRepository.save(tarea);
    }

    @Transactional
    public Tarea cambiarEstadoTarea(Long tareaId, Tarea.Estado nuevoEstado) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no encontrada"));
        switch (nuevoEstado) {
            case EN_PROGRESO -> tarea.iniciarTarea();
            case COMPLETADA -> tarea.completarTarea();
            default -> tarea.setEstado(nuevoEstado);
        }
        return tareaRepository.save(tarea);
    }

    @Transactional
    public Tarea asignarResponsable(Long tareaId, String responsable) {
        if (responsable == null || responsable.isBlank()) {
            throw new IllegalArgumentException("El responsable no puede ser vacío");
        }
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no encontrada"));
        tarea.setResponsable(responsable);
        return tareaRepository.save(tarea);
    }

    public List<Tarea> obtenerTareasVencidas() {
        return tareaRepository.findTareasVencidas(LocalDate.now());
    }

    public List<Tarea> obtenerTareasMultifase() {
        return tareaRepository.findTareasMultifase();
    }

    // ========================================
    // GESTIÓN DE RIESGOS
    // ========================================

    @Transactional
    public Riesgo crearRiesgo(Long proyectoId, String descripcion,
                             Riesgo.Probabilidad probabilidad, Riesgo.Impacto impacto) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción del riesgo no puede ser vacía");
        }
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        Riesgo riesgo = new Riesgo(descripcion, probabilidad, impacto);
        proyecto.agregarRiesgo(riesgo);
        proyectoRepository.save(proyecto);
        return riesgo;
    }

    @Transactional
    public Riesgo mitigarRiesgo(Long riesgoId) {
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new IllegalArgumentException("Riesgo con ID " + riesgoId + " no encontrado"));
        riesgo.setEstado(Riesgo.Estado.MITIGADO);
        return riesgoRepository.save(riesgo);
    }

    public List<Riesgo> obtenerRiesgosAltos() {
        return riesgoRepository.findRiesgosAltos();
    }

    // ========================================
    // MÉTRICAS Y REPORTES
    // ========================================

    public double calcularPorcentajeAvanceProyecto(Long proyectoId) {
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        return proyecto.calcularPorcentajeAvance();
    }

    public EstadisticasProyectoDTO obtenerEstadisticasProyecto(Long proyectoId) {
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        return new EstadisticasProyectoDTO(
                proyecto.getIdProyecto(),
                proyecto.getNombre(),
                proyecto.getEstado().getDisplayName(),
                proyecto.calcularPorcentajeAvance(),
                proyecto.getTotalTareas(),
                proyecto.getRiesgosActivos(),
                proyecto.getFases().size()
        );
    }

    @Transactional
    public Proyecto actualizarProyecto(Proyecto proyecto) {
        if (proyecto == null || proyecto.getIdProyecto() == null) {
            throw new IllegalArgumentException("Proyecto inválido para actualizar");
        }
        return proyectoRepository.save(proyecto);
    }

    // DTO para estadísticas (reemplaza al objeto anónimo)
    public static class EstadisticasProyectoDTO {
        private final Long id;
        private final String nombre;
        private final String estado;
        private final double porcentajeAvance;
        private final int totalTareas;
        private final int riesgosActivos;
        private final int totalFases;

        public EstadisticasProyectoDTO(Long id, String nombre, String estado, double porcentajeAvance,
                                       int totalTareas, int riesgosActivos, int totalFases) {
            this.id = id;
            this.nombre = nombre;
            this.estado = estado;
            this.porcentajeAvance = porcentajeAvance;
            this.totalTareas = totalTareas;
            this.riesgosActivos = riesgosActivos;
            this.totalFases = totalFases;
        }

        public Long getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getEstado() {
            return estado;
        }

        public double getPorcentajeAvance() {
            return porcentajeAvance;
        }

        public int getTotalTareas() {
            return totalTareas;
        }

        public int getRiesgosActivos() {
            return riesgosActivos;
        }

        public int getTotalFases() {
            return totalFases;
        }
    }
}
