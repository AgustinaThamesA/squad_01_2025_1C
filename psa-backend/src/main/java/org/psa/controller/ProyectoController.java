package org.psa.controller;

import org.psa.model.*;
import org.psa.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "http://localhost:3000") // Para conectar con React
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService; // Tu GerenteProyecto convertido

    // ========================================
    // GESTIÓN BÁSICA DE PROYECTOS
    // ========================================

    // GET /api/proyectos - Obtener todos los proyectos
    @GetMapping
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoService.obtenerTodosLosProyectos();
    }

    // GET /api/proyectos/1 - Obtener proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
        if (proyecto != null) {
            return ResponseEntity.ok(proyecto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/proyectos/1/planificar - Tu método: planificarProyecto
    @PutMapping("/{id}/planificar")
    public ResponseEntity<Proyecto> planificarProyecto(@PathVariable Long id, @RequestBody PlanificacionRequest request) {
        Proyecto proyecto = proyectoService.planificarProyecto(id, request.getFechaInicio(), request.getFechaFin());
        if (proyecto != null) {
            return ResponseEntity.ok(proyecto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/proyectos/1/estado - Tu método: cambiarEstadoProyecto
    @PutMapping("/{id}/estado")
    public ResponseEntity<Proyecto> cambiarEstadoProyecto(@PathVariable Long id, @RequestBody EstadoRequest request) {
        Proyecto proyecto = proyectoService.cambiarEstadoProyecto(id, request.getEstado());
        if (proyecto != null) {
            return ResponseEntity.ok(proyecto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ========================================
    // GESTIÓN DE FASES (tu GerenteProyecto)
    // ========================================

    // GET /api/proyectos/1/fases - Obtener fases de un proyecto
    @GetMapping("/{id}/fases")
    public List<Fase> obtenerFasesDelProyecto(@PathVariable Long id) {
        return proyectoService.obtenerFasesDelProyecto(id);
    }

    // POST /api/proyectos/1/fases - Tu método: crearFase
    @PostMapping("/{id}/fases")
    public ResponseEntity<Fase> crearFase(@PathVariable Long id, @RequestBody CrearFaseRequest request) {
        Fase fase = proyectoService.crearFase(id, request.getNombre(), request.getOrden());
        if (fase != null) {
            return ResponseEntity.ok(fase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/fases/1/planificar - Tu método: planificarFase
    @PutMapping("/fases/{faseId}/planificar")
    public ResponseEntity<Fase> planificarFase(@PathVariable Long faseId, @RequestBody PlanificacionRequest request) {
        Fase fase = proyectoService.planificarFase(faseId, request.getFechaInicio(), request.getFechaFin());
        if (fase != null) {
            return ResponseEntity.ok(fase);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ========================================
    // GESTIÓN DE TAREAS (tu GerenteProyecto)
    // ========================================

    // POST /api/tareas - Tu método: crearTarea
    @PostMapping("/tareas")
    public Tarea crearTarea(@RequestBody CrearTareaRequest request) {
        return proyectoService.crearTarea(
            request.getTitulo(), 
            request.getDescripcion(), 
            request.getPrioridad(), 
            request.getResponsable()
        );
    }

    // PUT /api/tareas/1/asignar-fase - Tu método: asignarTareaAFase
    @PutMapping("/tareas/{tareaId}/asignar-fase")
    public ResponseEntity<Tarea> asignarTareaAFase(@PathVariable Long tareaId, @RequestBody AsignarFaseRequest request) {
        Tarea tarea = proyectoService.asignarTareaAFase(tareaId, request.getFaseId());
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/tareas/1/asignar-multiples-fases - Tu método: asignarTareaAMultiplesFases
    @PutMapping("/tareas/{tareaId}/asignar-multiples-fases")
    public ResponseEntity<Tarea> asignarTareaAMultiplesFases(@PathVariable Long tareaId, @RequestBody AsignarMultiplesFasesRequest request) {
        Tarea tarea = proyectoService.asignarTareaAMultiplesFases(tareaId, request.getFaseIds());
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/tareas/1/planificar - Tu método: planificarTarea
    @PutMapping("/tareas/{tareaId}/planificar")
    public ResponseEntity<Tarea> planificarTarea(@PathVariable Long tareaId, @RequestBody PlanificacionRequest request) {
        Tarea tarea = proyectoService.planificarTarea(tareaId, request.getFechaInicio(), request.getFechaFin());
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/tareas/1/iniciar - Tu método: iniciarTarea
    @PutMapping("/tareas/{tareaId}/iniciar")
    public ResponseEntity<Tarea> iniciarTarea(@PathVariable Long tareaId) {
        Tarea tarea = proyectoService.iniciarTarea(tareaId);
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/tareas/1/completar - Tu método: completarTarea
    @PutMapping("/tareas/{tareaId}/completar")
    public ResponseEntity<Tarea> completarTarea(@PathVariable Long tareaId) {
        Tarea tarea = proyectoService.completarTarea(tareaId);
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/tareas/1/responsable - Tu método: asignarResponsable
    @PutMapping("/tareas/{tareaId}/responsable")
    public ResponseEntity<Tarea> asignarResponsable(@PathVariable Long tareaId, @RequestBody ResponsableRequest request) {
        Tarea tarea = proyectoService.asignarResponsable(tareaId, request.getResponsable());
        if (tarea != null) {
            return ResponseEntity.ok(tarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ========================================
    // GESTIÓN DE RIESGOS (tu GerenteProyecto)
    // ========================================

    // GET /api/proyectos/1/riesgos - Obtener riesgos del proyecto
    @GetMapping("/{id}/riesgos")
    public ResponseEntity<List<Riesgo>> obtenerRiesgosDelProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
        if (proyecto != null) {
            return ResponseEntity.ok(proyecto.getRiesgos());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/proyectos/1/riesgos - Tu método: crearRiesgo
    @PostMapping("/{id}/riesgos")
    public ResponseEntity<Riesgo> crearRiesgo(@PathVariable Long id, @RequestBody CrearRiesgoRequest request) {
        Riesgo riesgo = proyectoService.crearRiesgo(id, request.getDescripcion(), request.getProbabilidad(), request.getImpacto());
        if (riesgo != null) {
            return ResponseEntity.ok(riesgo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT /api/riesgos/1/mitigar - Tu método: mitigarRiesgo
    @PutMapping("/riesgos/{riesgoId}/mitigar")
    public ResponseEntity<Riesgo> mitigarRiesgo(@PathVariable Long riesgoId) {
        Riesgo riesgo = proyectoService.mitigarRiesgo(riesgoId);
        if (riesgo != null) {
            return ResponseEntity.ok(riesgo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ========================================
    // CONSULTAS Y ANÁLISIS (tu GerenteProyecto)
    // ========================================

    // GET /api/proyectos/tareas/vencidas - Tu método: obtenerTareasVencidas
    @GetMapping("/tareas/vencidas")
    public List<Tarea> obtenerTareasVencidas() {
        return proyectoService.obtenerTareasVencidas();
    }

    // GET /api/proyectos/riesgos/altos - Tu método: obtenerRiesgosAltos
    @GetMapping("/riesgos/altos")
    public List<Riesgo> obtenerRiesgosAltos() {
        return proyectoService.obtenerRiesgosAltos();
    }

    // GET /api/proyectos/tareas/multifase - Tu método: obtenerTareasMultifase
    @GetMapping("/tareas/multifase")
    public List<Tarea> obtenerTareasMultifase() {
        return proyectoService.obtenerTareasMultifase();
    }

    // GET /api/proyectos/1/estadisticas - Estadísticas del proyecto
    @GetMapping("/{id}/estadisticas")
    public ResponseEntity<Object> obtenerEstadisticasProyecto(@PathVariable Long id) {
        Object estadisticas = proyectoService.obtenerEstadisticasProyecto(id);
        if (estadisticas != null) {
            return ResponseEntity.ok(estadisticas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ========================================
    // ENDPOINTS DEMO PARA PRUEBAS
    // ========================================

    // POST /api/proyectos/demo - Crear proyecto demo con datos completos
    @PostMapping("/demo")
    public Proyecto crearProyectoDemo() {
        // Crear proyecto
        Proyecto proyecto = new Proyecto(
            "Implementación SAP ERP", 
            "Migración completa a SAP ERP 7.51", 
            "Leonardo Felici"
        );
        proyecto.planificarFechas(LocalDate.now(), LocalDate.now().plusMonths(6));

        // Crear fases
        Fase fase1 = new Fase("Análisis", 1);
        fase1.planificarFechas(LocalDate.now(), LocalDate.now().plusMonths(2));
        
        Fase fase2 = new Fase("Desarrollo", 2);
        fase2.planificarFechas(LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(4));

        // Agregar fases al proyecto
        proyecto.agregarFase(fase1);
        proyecto.agregarFase(fase2);

        // Crear tareas
        Tarea tarea1 = new Tarea("Definir arquitectura", "Diseño del sistema", Tarea.Prioridad.ALTA, "Carlos Mendoza");
        tarea1.planificarFechas(LocalDate.now(), LocalDate.now().plusWeeks(2));
        
        Tarea tarea2 = new Tarea("Configurar base de datos", "Setup PostgreSQL", Tarea.Prioridad.MEDIA, "Ana García");
        tarea2.planificarFechas(LocalDate.now().plusWeeks(2), LocalDate.now().plusMonths(1));

        // Asignar tareas a fases
        fase1.agregarTarea(tarea1);
        fase1.agregarTarea(tarea2);

        // Crear riesgos
        Riesgo riesgo = new Riesgo("Retrasos en migración de datos", Riesgo.Probabilidad.MEDIA, Riesgo.Impacto.ALTO);
        proyecto.agregarRiesgo(riesgo);

        // Guardar proyecto (cascade guardará todo automáticamente)
        return proyectoService.guardarProyecto(proyecto);
    }
}

// ========================================
// CLASES AUXILIARES PARA REQUESTS
// ========================================

class PlanificacionRequest {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}

class EstadoRequest {
    private Proyecto.Estado estado;
    
    public Proyecto.Estado getEstado() { return estado; }
    public void setEstado(Proyecto.Estado estado) { this.estado = estado; }
}

class CrearFaseRequest {
    private String nombre;
    private Integer orden;
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}

class CrearTareaRequest {
    private String titulo;
    private String descripcion;
    private Tarea.Prioridad prioridad;
    private String responsable;
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Tarea.Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Tarea.Prioridad prioridad) { this.prioridad = prioridad; }
    
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
}

class AsignarFaseRequest {
    private Long faseId;
    
    public Long getFaseId() { return faseId; }
    public void setFaseId(Long faseId) { this.faseId = faseId; }
}

class AsignarMultiplesFasesRequest {
    private List<Long> faseIds;
    
    public List<Long> getFaseIds() { return faseIds; }
    public void setFaseIds(List<Long> faseIds) { this.faseIds = faseIds; }
}

class ResponsableRequest {
    private String responsable;
    
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
}

class CrearRiesgoRequest {
    private String descripcion;
    private Riesgo.Probabilidad probabilidad;
    private Riesgo.Impacto impacto;
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Riesgo.Probabilidad getProbabilidad() { return probabilidad; }
    public void setProbabilidad(Riesgo.Probabilidad probabilidad) { this.probabilidad = probabilidad; }
    
    public Riesgo.Impacto getImpacto() { return impacto; }
    public void setImpacto(Riesgo.Impacto impacto) { this.impacto = impacto; }
}
