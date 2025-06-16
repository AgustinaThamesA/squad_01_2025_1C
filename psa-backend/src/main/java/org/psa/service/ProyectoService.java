package org.psa.service;

import org.psa.model.*;
import org.psa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service que reemplaza tu GerenteProyecto
 * Contiene toda tu lógica de negocio original
 */
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
    // GESTIÓN DE PROYECTOS (tu lógica original)
    // ========================================
    
    public Proyecto crearProyecto(String nombre, String descripcion, String liderProyecto) {
        Proyecto proyecto = new Proyecto(nombre, descripcion, liderProyecto);
        return proyectoRepository.save(proyecto); // Se guarda en PostgreSQL automáticamente
    }
    
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll();
    }
    
    public Proyecto obtenerProyectoPorId(Long id) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(id);
        return proyecto.orElse(null);
    }
    
    public Proyecto cambiarEstadoProyecto(Long proyectoId, Proyecto.Estado nuevoEstado) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            
            if (nuevoEstado == Proyecto.Estado.CERRADO) {
                proyecto.cerrarProyecto(); // Tu método original
            } else if (nuevoEstado == Proyecto.Estado.PAUSADO) {
                proyecto.pausarProyecto(); // Tu método original
            } else {
                proyecto.setEstado(nuevoEstado);
            }
            
            return proyectoRepository.save(proyecto);
        }
        return null;
    }
    
    public Proyecto planificarProyecto(Long proyectoId, LocalDate fechaInicio, LocalDate fechaFin) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            proyecto.planificarFechas(fechaInicio, fechaFin); // Tu método original
            return proyectoRepository.save(proyecto);
        }
        return null;
    }
    
    // ========================================
    // GESTIÓN DE FASES (tu lógica original)
    // ========================================
    
    public Fase crearFase(Long proyectoId, String nombre, int orden) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            Fase fase = new Fase(nombre, orden);
            proyecto.agregarFase(fase); // Tu método original
            proyectoRepository.save(proyecto); // Cascade guarda la fase automáticamente
            return fase;
        }
        return null;
    }
    
    public List<Fase> obtenerFasesDelProyecto(Long proyectoId) {
        return faseRepository.findByProyectoIdProyectoOrderByOrden(proyectoId);
    }
    
    public Fase planificarFase(Long faseId, LocalDate fechaInicio, LocalDate fechaFin) {
        Optional<Fase> faseOpt = faseRepository.findById(faseId);
        if (faseOpt.isPresent()) {
            Fase fase = faseOpt.get();
            fase.planificarFechas(fechaInicio, fechaFin); // Tu método original
            return faseRepository.save(fase);
        }
        return null;
    }
    
    // ========================================
    // GESTIÓN DE TAREAS (tu lógica original de GerenteProyecto)
    // ========================================
    
    public Tarea crearTarea(String titulo, String descripcion, Tarea.Prioridad prioridad, String responsable) {
        Tarea tarea = new Tarea(titulo, descripcion, prioridad, responsable);
        return tareaRepository.save(tarea);
    }
    
    // Tu método original: asignarTareaAFase
    public Tarea asignarTareaAFase(Long tareaId, Long faseId) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        Optional<Fase> faseOpt = faseRepository.findById(faseId);
        
        if (tareaOpt.isPresent() && faseOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            Fase fase = faseOpt.get();
            
            fase.agregarTarea(tarea); // Tu método original
            faseRepository.save(fase);
            return tarea;
        }
        return null;
    }
    
    // Tu método original: asignarTareaAMultiplesFases
    public Tarea asignarTareaAMultiplesFases(Long tareaId, List<Long> faseIds) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            
            for (Long faseId : faseIds) {
                Optional<Fase> faseOpt = faseRepository.findById(faseId);
                if (faseOpt.isPresent()) {
                    Fase fase = faseOpt.get();
                    fase.agregarTarea(tarea); // Tu método original
                    faseRepository.save(fase);
                }
            }
            return tarea;
        }
        return null;
    }
    
    // Tu método original: planificarTarea
    public Tarea planificarTarea(Long tareaId, LocalDate fechaInicio, LocalDate fechaFin) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.planificarFechas(fechaInicio, fechaFin); // Tu método original
            return tareaRepository.save(tarea);
        }
        return null;
    }
    
    // Tu método original: iniciarTarea
    public Tarea iniciarTarea(Long tareaId) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.iniciarTarea(); // Tu método original
            return tareaRepository.save(tarea);
        }
        return null;
    }
    
    // Tu método original: completarTarea
    public Tarea completarTarea(Long tareaId) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.completarTarea(); // Tu método original
            return tareaRepository.save(tarea);
        }
        return null;
    }
    
    public Tarea cambiarEstadoTarea(Long tareaId, Tarea.Estado nuevoEstado) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            
            if (nuevoEstado == Tarea.Estado.EN_PROGRESO) {
                tarea.iniciarTarea(); // Tu método original
            } else if (nuevoEstado == Tarea.Estado.COMPLETADA) {
                tarea.completarTarea(); // Tu método original
            } else {
                tarea.setEstado(nuevoEstado);
            }
            
            return tareaRepository.save(tarea);
        }
        return null;
    }
    
    public Tarea asignarResponsable(Long tareaId, String responsable) {
        Optional<Tarea> tareaOpt = tareaRepository.findById(tareaId);
        if (tareaOpt.isPresent()) {
            Tarea tarea = tareaOpt.get();
            tarea.setResponsable(responsable); // Tu método original
            return tareaRepository.save(tarea);
        }
        return null;
    }
    
    public List<Tarea> obtenerTareasVencidas() {
        return tareaRepository.findTareasVencidas(LocalDate.now());
    }
    
    public List<Tarea> obtenerTareasMultifase() {
        return tareaRepository.findTareasMultifase();
    }
    
    // ========================================
    // GESTIÓN DE RIESGOS (tu lógica original)
    // ========================================
    
    public Riesgo crearRiesgo(Long proyectoId, String descripcion, 
                             Riesgo.Probabilidad probabilidad, Riesgo.Impacto impacto) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            Riesgo riesgo = new Riesgo(descripcion, probabilidad, impacto);
            proyecto.agregarRiesgo(riesgo); // Tu método original
            proyectoRepository.save(proyecto);
            return riesgo;
        }
        return null;
    }
    
    public Riesgo mitigarRiesgo(Long riesgoId) {
        Optional<Riesgo> riesgoOpt = riesgoRepository.findById(riesgoId);
        if (riesgoOpt.isPresent()) {
            Riesgo riesgo = riesgoOpt.get();
            riesgo.setEstado(Riesgo.Estado.MITIGADO);
            return riesgoRepository.save(riesgo);
        }
        return null;
    }
    
    public List<Riesgo> obtenerRiesgosAltos() {
        return riesgoRepository.findRiesgosAltos();
    }
    
    // ========================================
    // MÉTRICAS Y REPORTES (tu lógica original)
    // ========================================
    
    public double calcularPorcentajeAvanceProyecto(Long proyectoId) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        if (proyectoOpt.isPresent()) {
            return proyectoOpt.get().calcularPorcentajeAvance(); // Tu método original
        }
        return 0.0;
    }
    
    public Object obtenerEstadisticasProyecto(Long proyectoId) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(proyectoId);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            
            return new Object() {
                public final Long id = proyecto.getIdProyecto();
                public final String nombre = proyecto.getNombre();
                public final String estado = proyecto.getEstado().getDisplayName();
                public final double porcentajeAvance = proyecto.calcularPorcentajeAvance(); // Tu método
                public final int totalTareas = proyecto.getTotalTareas(); // Tu método
                public final int riesgosActivos = proyecto.getRiesgosActivos(); // Tu método
                public final int totalFases = proyecto.getFases().size();
            };
        }
        return null;
    }
    
    // ========================================
    // MÉTODO FALTANTE - Para guardar proyectos
    // ========================================
    
    public Proyecto guardarProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }
}
