package org.psa.service;

import org.psa.model.*;
import org.psa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // 🔧 AGREGAR: Obtener todas las tareas de un proyecto
    public List<Tarea> obtenerTareasDelProyecto(Long proyectoId) {
        if (proyectoId == null) {
            throw new IllegalArgumentException("El ID del proyecto no puede ser nulo");
        }
        
        // Verificar que el proyecto existe
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        
        // Obtener todas las tareas del proyecto a través de sus fases
        return proyecto.getFases().stream()
                .flatMap(fase -> fase.getTareas().stream())
                .distinct() // Por si una tarea está en múltiples fases
                .collect(Collectors.toList());
    }

    // 🔧 AGREGAR: Crear tarea asociada a proyecto y opcionalmente a fase
    @Transactional
    public Tarea crearTarea(Long proyectoId, Long faseId, String titulo, String descripcion, 
                        Tarea.Prioridad prioridad, String responsable) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título de la tarea no puede ser vacío");
        }
        if (responsable == null || responsable.isBlank()) {
            throw new IllegalArgumentException("El responsable no puede ser vacío");
        }
        if (proyectoId == null) {
            throw new IllegalArgumentException("El ID del proyecto no puede ser nulo");
        }
        
        // Verificar que el proyecto existe
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        
        // Crear la tarea
        Tarea tarea = new Tarea(titulo, descripcion, prioridad, responsable);
        tarea = tareaRepository.save(tarea);
        
        // Si se especifica una fase, asignar la tarea a esa fase
        if (faseId != null) {
            Fase fase = faseRepository.findById(faseId)
                    .orElseThrow(() -> new IllegalArgumentException("Fase con ID " + faseId + " no encontrada"));
            
            // Verificar que la fase pertenece al proyecto
            if (!fase.getProyecto().getIdProyecto().equals(proyectoId)) {
                throw new IllegalArgumentException("La fase no pertenece al proyecto especificado");
            }
            
            fase.agregarTarea(tarea);
            faseRepository.save(fase);
        } else {
            // Si no se especifica fase, asignar a la primera fase del proyecto
            List<Fase> fases = proyecto.getFases();
            if (!fases.isEmpty()) {
                Fase primeraFase = fases.get(0);
                primeraFase.agregarTarea(tarea);
                faseRepository.save(primeraFase);
            }
        }
        
        return tarea;
    }

    @Autowired
    private RecursoService recursoService;

    // ========================================
    // GESTIÓN DE PROYECTOS CON RECURSOS
    // ========================================

    @Transactional
    public Proyecto crearProyectoConRecurso(String nombre, String descripcion, String liderRecursoId) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del proyecto no puede ser vacío");
        }
        if (liderRecursoId == null || liderRecursoId.isBlank()) {
            throw new IllegalArgumentException("El ID del líder no puede ser vacío");
        }
        
        // Validar y obtener el recurso
        recursoService.validarRecursoParaAsignacion(liderRecursoId, "liderazgo de proyecto");
        Recurso liderRecurso = recursoService.obtenerRecursoPorId(liderRecursoId);
        
        // Crear proyecto con recurso
        Proyecto proyecto = new Proyecto(nombre, descripcion, liderRecurso);
        return proyectoRepository.save(proyecto);
    }

    @Transactional  
    public Proyecto asignarLiderRecurso(Long proyectoId, String liderRecursoId) {
        if (liderRecursoId == null || liderRecursoId.isBlank()) {
            throw new IllegalArgumentException("El ID del líder no puede ser vacío");
        }
        
        // Validar recurso
        recursoService.validarRecursoParaAsignacion(liderRecursoId, "liderazgo de proyecto");
        Recurso liderRecurso = recursoService.obtenerRecursoPorId(liderRecursoId);
        
        // Actualizar proyecto
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        proyecto.setLiderRecurso(liderRecurso);
        
        return proyectoRepository.save(proyecto);
    }

    @Transactional
    public Proyecto removerLiderRecurso(Long proyectoId) {
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        proyecto.setLiderRecurso(null);
        return proyectoRepository.save(proyecto);
    }

    // ========================================
    // CONSULTAS CON RECURSOS
    // ========================================


    // ESTE ENDPOINT NO FUNCIONA BIEN ME DA UNA RE BRONCA
    @Transactional(readOnly = true)
    public List<Proyecto> obtenerProyectosPorLider(String liderRecursoId) {
        if (liderRecursoId == null || liderRecursoId.isBlank()) {
            throw new IllegalArgumentException("El ID del líder no puede ser vacío");
        }
        
        // ✅ ENFOQUE DIRECTO: Buscar directamente por el ID del recurso
        return proyectoRepository.findAll().stream()
            .filter(p -> p.getLiderRecursoId() != null && 
                        p.getLiderRecursoId().equals(liderRecursoId))
            .collect(Collectors.toList());
    }

    public List<Proyecto> obtenerProyectosSinLider() {
        return proyectoRepository.findAll().stream()
            .filter(p -> !p.tieneLiderAsignado())
            .collect(Collectors.toList());
    }

    // ========================================
    // ESTADÍSTICAS CON RECURSOS
    // ========================================

    public EstadisticasRecursosProyectoDTO obtenerEstadisticasRecursosEnProyectos() {
        List<Proyecto> todosLosProyectos = proyectoRepository.findAll();
        
        long proyectosConRecurso = todosLosProyectos.stream()
            .filter(p -> p.getLiderRecurso() != null)
            .count();
            
        long proyectosSinRecurso = todosLosProyectos.size() - proyectosConRecurso;
        
        return new EstadisticasRecursosProyectoDTO(
            proyectosConRecurso,
            proyectosSinRecurso,
            todosLosProyectos.size()
        );
    }

    // ========================================
    // DTO PARA ESTADÍSTICAS DE RECURSOS
    // ========================================

    public static class EstadisticasRecursosProyectoDTO {
        private final long proyectosConRecurso;
        private final long proyectosSinRecurso;
        private final long totalProyectos;

        public EstadisticasRecursosProyectoDTO(long proyectosConRecurso, long proyectosSinRecurso, long totalProyectos) {
            this.proyectosConRecurso = proyectosConRecurso;
            this.proyectosSinRecurso = proyectosSinRecurso;
            this.totalProyectos = totalProyectos;
        }

        public long getProyectosConRecurso() { return proyectosConRecurso; }
        public long getProyectosSinRecurso() { return proyectosSinRecurso; }
        public long getTotalProyectos() { return totalProyectos; }
        
        public double getPorcentajeConRecurso() {
            return totalProyectos > 0 ? (double) proyectosConRecurso / totalProyectos * 100 : 0;
        }
    }

    // ========================================
    // GESTIÓN DE TAREAS CON RECURSOS
    // ========================================

    @Transactional
    public Tarea crearTareaConRecurso(Long proyectoId, Long faseId, String titulo, String descripcion, 
                                     Tarea.Prioridad prioridad, String responsableRecursoId) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título de la tarea no puede ser vacío");
        }
        if (responsableRecursoId == null || responsableRecursoId.isBlank()) {
            throw new IllegalArgumentException("El ID del responsable no puede ser vacío");
        }
        if (proyectoId == null) {
            throw new IllegalArgumentException("El ID del proyecto no puede ser nulo");
        }
        
        // Validar y obtener el recurso
        recursoService.validarRecursoParaAsignacion(responsableRecursoId, "responsabilidad de tarea");
        Recurso responsableRecurso = recursoService.obtenerRecursoPorId(responsableRecursoId);
        
        // Verificar que el proyecto existe
        Proyecto proyecto = obtenerProyectoPorId(proyectoId);
        
        // Crear la tarea con recurso
        Tarea tarea = new Tarea(titulo, descripcion, prioridad, responsableRecurso);
        tarea = tareaRepository.save(tarea);
        
        // Asignar a fase si se especifica
        if (faseId != null) {
            Fase fase = faseRepository.findById(faseId)
                    .orElseThrow(() -> new IllegalArgumentException("Fase con ID " + faseId + " no encontrada"));
            
            // Verificar que la fase pertenece al proyecto
            if (!fase.getProyecto().getIdProyecto().equals(proyectoId)) {
                throw new IllegalArgumentException("La fase no pertenece al proyecto especificado");
            }
            
            fase.agregarTarea(tarea);
            faseRepository.save(fase);
        } else {
            // Si no se especifica fase, asignar a la primera fase del proyecto
            List<Fase> fases = proyecto.getFases();
            if (!fases.isEmpty()) {
                Fase primeraFase = fases.get(0);
                primeraFase.agregarTarea(tarea);
                faseRepository.save(primeraFase);
            }
        }
        
        return tarea;
    }

    @Transactional
    public Tarea asignarResponsableRecurso(Long tareaId, String responsableRecursoId) {
        if (responsableRecursoId == null || responsableRecursoId.isBlank()) {
            throw new IllegalArgumentException("El ID del responsable no puede ser vacío");
        }
        
        // Validar recurso
        recursoService.validarRecursoParaAsignacion(responsableRecursoId, "responsabilidad de tarea");
        Recurso responsableRecurso = recursoService.obtenerRecursoPorId(responsableRecursoId);
        
        // Actualizar tarea
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no encontrada"));
        tarea.setResponsableRecurso(responsableRecurso);
        
        return tareaRepository.save(tarea);
    }

    @Transactional
    public Tarea removerResponsableRecurso(Long tareaId) {
        Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no encontrada"));
        tarea.setResponsableRecurso(null);
        return tareaRepository.save(tarea);
    }

    // ========================================
    // CONSULTAS DE TAREAS CON RECURSOS
    // ========================================

    public List<Tarea> obtenerTareasPorResponsable(String responsableRecursoId) {
        if (responsableRecursoId == null || responsableRecursoId.isBlank()) {
            throw new IllegalArgumentException("El ID del responsable no puede ser vacío");
        }
        
        // Buscar tareas donde el responsable sea este recurso
        return tareaRepository.findAll().stream()
            .filter(t -> t.getResponsableRecursoId() != null && 
                        t.getResponsableRecursoId().equals(responsableRecursoId))
            .collect(Collectors.toList());
    }

    public List<Tarea> obtenerTareasSinResponsable() {
        return tareaRepository.findAll().stream()
            .filter(t -> !t.tieneResponsableAsignado())
            .collect(Collectors.toList());
    }

    public List<Tarea> obtenerTareasDelRecursoEnProyecto(String responsableRecursoId, Long proyectoId) {
        if (responsableRecursoId == null || responsableRecursoId.isBlank()) {
            throw new IllegalArgumentException("El ID del responsable no puede ser vacío");
        }
        if (proyectoId == null) {
            throw new IllegalArgumentException("El ID del proyecto no puede ser nulo");
        }
        
        // Verificar que el proyecto existe
        obtenerProyectoPorId(proyectoId);
        
        // Obtener tareas del proyecto filtradas por responsable
        return obtenerTareasDelProyecto(proyectoId).stream()
            .filter(t -> t.getResponsableRecursoId() != null && 
                        t.getResponsableRecursoId().equals(responsableRecursoId))
            .collect(Collectors.toList());
    }

    // ========================================
    // ESTADÍSTICAS DE TAREAS CON RECURSOS
    // ========================================

    public CargaTrabajoRecursoDTO obtenerCargaTrabajoRecurso(String responsableRecursoId) {
        if (responsableRecursoId == null || responsableRecursoId.isBlank()) {
            throw new IllegalArgumentException("El ID del responsable no puede ser vacío");
        }
        
        // Validar que el recurso existe
        Recurso recurso = recursoService.obtenerRecursoPorId(responsableRecursoId);
        
        List<Tarea> tareas = obtenerTareasPorResponsable(responsableRecursoId);
        
        long tareasPendientes = tareas.stream()
            .filter(t -> t.getEstado() == Tarea.Estado.PENDIENTE)
            .count();
            
        long tareasEnProgreso = tareas.stream()
            .filter(t -> t.getEstado() == Tarea.Estado.EN_PROGRESO)
            .count();
            
        long tareasCompletadas = tareas.stream()
            .filter(t -> t.getEstado() == Tarea.Estado.COMPLETADA)
            .count();
            
        long tareasVencidas = tareas.stream()
            .filter(Tarea::estaVencida)
            .count();
        
        return new CargaTrabajoRecursoDTO(
            recurso.getId(),
            recurso.getNombreCompleto(),
            tareas.size(),
            tareasPendientes,
            tareasEnProgreso,
            tareasCompletadas,
            tareasVencidas
        );
    }

    public EstadisticasRecursosTareasDTO obtenerEstadisticasRecursosEnTareas() {
        List<Tarea> todasLasTareas = tareaRepository.findAll();
        
        long tareasConRecurso = todasLasTareas.stream()
            .filter(t -> t.getResponsableRecurso() != null)
            .count();
            
        long tareasSinRecurso = todasLasTareas.size() - tareasConRecurso;
        
        return new EstadisticasRecursosTareasDTO(
            tareasConRecurso,
            tareasSinRecurso,
            todasLasTareas.size()
        );
    }

    // ========================================
    // REPORTES AVANZADOS
    // ========================================

    public List<CargaTrabajoRecursoDTO> obtenerCargaTrabajoTodosLosRecursos() {
        List<Recurso> todosLosRecursos = recursoService.obtenerTodosLosRecursos();
        
        return todosLosRecursos.stream()
            .map(recurso -> obtenerCargaTrabajoRecurso(recurso.getId()))
            .collect(Collectors.toList());
    }

    public List<Recurso> obtenerRecursosDisponiblesParaTareas() {
        // Recursos que tienen menos de 5 tareas activas (pendientes + en progreso)
        return recursoService.obtenerTodosLosRecursos().stream()
            .filter(recurso -> {
                List<Tarea> tareas = obtenerTareasPorResponsable(recurso.getId());
                long tareasActivas = tareas.stream()
                    .filter(t -> t.getEstado() == Tarea.Estado.PENDIENTE || 
                                t.getEstado() == Tarea.Estado.EN_PROGRESO)
                    .count();
                return tareasActivas < 5; // Umbral configurable
            })
            .collect(Collectors.toList());
    }

    // ========================================
    // DTOs PARA ESTADÍSTICAS DE TAREAS
    // ========================================

    public static class CargaTrabajoRecursoDTO {
        private final String recursoId;
        private final String nombreCompleto;
        private final long totalTareas;
        private final long tareasPendientes;
        private final long tareasEnProgreso;
        private final long tareasCompletadas;
        private final long tareasVencidas;

        public CargaTrabajoRecursoDTO(String recursoId, String nombreCompleto, long totalTareas,
                                     long tareasPendientes, long tareasEnProgreso, 
                                     long tareasCompletadas, long tareasVencidas) {
            this.recursoId = recursoId;
            this.nombreCompleto = nombreCompleto;
            this.totalTareas = totalTareas;
            this.tareasPendientes = tareasPendientes;
            this.tareasEnProgreso = tareasEnProgreso;
            this.tareasCompletadas = tareasCompletadas;
            this.tareasVencidas = tareasVencidas;
        }

        // Getters
        public String getRecursoId() { return recursoId; }
        public String getNombreCompleto() { return nombreCompleto; }
        public long getTotalTareas() { return totalTareas; }
        public long getTareasPendientes() { return tareasPendientes; }
        public long getTareasEnProgreso() { return tareasEnProgreso; }
        public long getTareasCompletadas() { return tareasCompletadas; }
        public long getTareasVencidas() { return tareasVencidas; }
        
        public double getPorcentajeCompletado() {
            return totalTareas > 0 ? (double) tareasCompletadas / totalTareas * 100 : 0;
        }
        
        public long getTareasActivas() {
            return tareasPendientes + tareasEnProgreso;
        }
    }

    public static class EstadisticasRecursosTareasDTO {
        private final long tareasConRecurso;
        private final long tareasSinRecurso;
        private final long totalTareas;

        public EstadisticasRecursosTareasDTO(long tareasConRecurso, long tareasSinRecurso, long totalTareas) {
            this.tareasConRecurso = tareasConRecurso;
            this.tareasSinRecurso = tareasSinRecurso;
            this.totalTareas = totalTareas;
        }

        public long getTareasConRecurso() { return tareasConRecurso; }
        public long getTareasSinRecurso() { return tareasSinRecurso; }
        public long getTotalTareas() { return totalTareas; }
        
        public double getPorcentajeConRecurso() {
            return totalTareas > 0 ? (double) tareasConRecurso / totalTareas * 100 : 0;
        }
    }


    @Transactional
    public boolean eliminarFase(Long faseId) {
        try {
            if (faseRepository.existsById(faseId)) {
                // 🔧 OBTENER EL ID DEL PROYECTO ANTES DE ELIMINAR
                Long proyectoId = faseRepository.findById(faseId)
                    .map(fase -> fase.getProyecto().getIdProyecto())
                    .orElse(null);
                
                faseRepository.deleteById(faseId);
                
                // 🔧 REFRESCAR EL PROYECTO PARA ACTUALIZAR getTotalTareas()
                if (proyectoId != null) {
                    proyectoRepository.findById(proyectoId).ifPresent(proyecto -> {
                        // Forzar la recarga de las fases
                        proyecto.getFases().size(); // Esto dispara el lazy loading
                    });
                }
                
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar fase: " + e.getMessage());
        }
    }

    @Transactional  
    public boolean eliminarTarea(Long tareaId) {
        try {
            if (tareaRepository.existsById(tareaId)) {
                // JPA maneja automáticamente la relación n:m
                tareaRepository.deleteById(tareaId);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar tarea: " + e.getMessage());
        }
    }

    public boolean puedeEliminarFase(Long faseId) {
        return faseRepository.findById(faseId)
            .map(fase -> fase.getTareas().isEmpty())
            .orElse(false);
    }

    public int contarTareasEnFase(Long faseId) {
        return faseRepository.findById(faseId)
            .map(fase -> fase.getTareas().size())
            .orElse(0);
    }

    @Transactional
    public Riesgo actualizarRiesgo(Long riesgoId, String descripcion, 
                                Riesgo.Probabilidad probabilidad, Riesgo.Impacto impacto) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción del riesgo no puede ser vacía");
        }
        
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new IllegalArgumentException("Riesgo con ID " + riesgoId + " no encontrado"));
        
        // Actualizar campos
        riesgo.setDescripcion(descripcion);
        if (probabilidad != null) {
            riesgo.setProbabilidad(probabilidad);
        }
        if (impacto != null) {
            riesgo.setImpacto(impacto);
        }
        
        return riesgoRepository.save(riesgo);
    }

    @Transactional
    public boolean eliminarRiesgo(Long riesgoId) {
        try {
            if (riesgoRepository.existsById(riesgoId)) {
                riesgoRepository.deleteById(riesgoId);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar riesgo: " + e.getMessage());
        }
    }

    @Transactional
    public Riesgo cambiarEstadoRiesgo(Long riesgoId, Riesgo.Estado nuevoEstado) {
        Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new IllegalArgumentException("Riesgo con ID " + riesgoId + " no encontrado"));
        
        riesgo.setEstado(nuevoEstado);
        return riesgoRepository.save(riesgo);
    }

}
