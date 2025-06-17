package org.psa.controller;

import org.psa.model.*;
import org.psa.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;

    // ========================================
    // GESTIÓN BÁSICA DE PROYECTOS
    // ========================================

    @GetMapping
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoService.obtenerTodosLosProyectos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
        return proyecto != null ? ResponseEntity.ok(proyecto) : ResponseEntity.notFound().build();
    }

    // AGREGAR: Crear proyecto (esto faltaba en tu ProyectoController)
    @PostMapping
    public Proyecto crearProyecto(@RequestBody CrearProyectoRequest request) {
        return proyectoService.crearProyecto(
            request.getNombre(), 
            request.getDescripcion(), 
            request.getLiderProyecto()
        );
    }

    @PutMapping("/{id}/planificar")
    public ResponseEntity<Proyecto> planificarProyecto(@PathVariable Long id, @RequestBody PlanificacionRequest request) {
        Proyecto proyecto = proyectoService.planificarProyecto(id, request.getFechaInicio(), request.getFechaFin());
        return proyecto != null ? ResponseEntity.ok(proyecto) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Proyecto> cambiarEstadoProyecto(@PathVariable Long id, @RequestBody EstadoRequest request) {
        Proyecto proyecto = proyectoService.cambiarEstadoProyecto(id, request.getEstado());
        return proyecto != null ? ResponseEntity.ok(proyecto) : ResponseEntity.notFound().build();
    }

    // AGREGAR: Eliminar proyecto (esto también faltaba)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        boolean eliminado = proyectoService.eliminarProyecto(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // ========================================
    // GESTIÓN DE FASES
    // ========================================

    @GetMapping("/{id}/fases")
    public List<Fase> obtenerFasesDelProyecto(@PathVariable Long id) {
        return proyectoService.obtenerFasesDelProyecto(id);
    }

    @PostMapping("/{id}/fases")
    public ResponseEntity<Fase> crearFase(@PathVariable Long id, @RequestBody CrearFaseRequest request) {
        Fase fase = proyectoService.crearFase(id, request.getNombre(), request.getOrden());
        return fase != null ? ResponseEntity.ok(fase) : ResponseEntity.notFound().build();
    }

    @PutMapping("/fases/{faseId}/planificar")
    public ResponseEntity<Fase> planificarFase(@PathVariable Long faseId, @RequestBody PlanificacionRequest request) {
        Fase fase = proyectoService.planificarFase(faseId, request.getFechaInicio(), request.getFechaFin());
        return fase != null ? ResponseEntity.ok(fase) : ResponseEntity.notFound().build();
    }

    // ========================================
    // GESTIÓN DE TAREAS
    // ========================================

    @PostMapping("/tareas")
    public Tarea crearTarea(@RequestBody CrearTareaRequest request) {
        return proyectoService.crearTarea(
            request.getTitulo(), 
            request.getDescripcion(), 
            request.getPrioridad(), 
            request.getResponsable()
        );
    }

    @PutMapping("/tareas/{tareaId}/asignar-fase")
    public ResponseEntity<Tarea> asignarTareaAFase(@PathVariable Long tareaId, @RequestBody AsignarFaseRequest request) {
        Tarea tarea = proyectoService.asignarTareaAFase(tareaId, request.getFaseId());
        return tarea != null ? ResponseEntity.ok(tarea) : ResponseEntity.notFound().build();
    }

    @PutMapping("/tareas/{tareaId}/asignar-multiples-fases")
    public ResponseEntity<Tarea> asignarTareaAMultiplesFases(@PathVariable Long tareaId, @RequestBody AsignarMultiplesFasesRequest request) {
        Tarea tarea = proyectoService.asignarTareaAMultiplesFases(tareaId, request.getFaseIds());
        return tarea != null ? ResponseEntity.ok(tarea) : ResponseEntity.notFound().build();
    }

    @PutMapping("/tareas/{tareaId}/planificar")
    public ResponseEntity<Tarea> planificarTarea(@PathVariable Long tareaId, @RequestBody PlanificacionRequest request) {
        Tarea tarea = proyectoService.planificarTarea(tareaId, request.getFechaInicio(), request.getFechaFin());
        return tarea != null ? ResponseEntity.ok(tarea) : ResponseEntity.notFound().build();
    }

    @PutMapping("/tareas/{tareaId}/iniciar")
    public ResponseEntity<Tarea> iniciarTarea(@PathVariable Long tareaId) {
        Tarea tarea = proyectoService.iniciarTarea(tareaId);
        return tarea != null ? ResponseEntity.ok(tarea) : ResponseEntity.notFound().build();
    }

    @PutMapping("/tareas/{tareaId}/completar")
    public ResponseEntity<Tarea> completarTarea(@PathVariable Long tareaId) {
        Tarea tarea = proyectoService.completarTarea(tareaId);
        return tarea != null ? ResponseEntity.ok(tarea) : ResponseEntity.notFound().build();
    }

    @PutMapping("/tareas/{tareaId}/responsable")
    public ResponseEntity<Tarea> asignarResponsable(@PathVariable Long tareaId, @RequestBody ResponsableRequest request) {
        Tarea tarea = proyectoService.asignarResponsable(tareaId, request.getResponsable());
        return tarea != null ? ResponseEntity.ok(tarea) : ResponseEntity.notFound().build();
    }

    // ========================================
    // GESTIÓN DE RIESGOS
    // ========================================

    @GetMapping("/{id}/riesgos")
    public ResponseEntity<List<Riesgo>> obtenerRiesgosDelProyecto(@PathVariable Long id) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
        return proyecto != null ? ResponseEntity.ok(proyecto.getRiesgos()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/riesgos")
    public ResponseEntity<Riesgo> crearRiesgo(@PathVariable Long id, @RequestBody CrearRiesgoRequest request) {
        Riesgo riesgo = proyectoService.crearRiesgo(id, request.getDescripcion(), request.getProbabilidad(), request.getImpacto());
        return riesgo != null ? ResponseEntity.ok(riesgo) : ResponseEntity.notFound().build();
    }

    @PutMapping("/riesgos/{riesgoId}/mitigar")
    public ResponseEntity<Riesgo> mitigarRiesgo(@PathVariable Long riesgoId) {
        Riesgo riesgo = proyectoService.mitigarRiesgo(riesgoId);
        return riesgo != null ? ResponseEntity.ok(riesgo) : ResponseEntity.notFound().build();
    }

    // ========================================
    // CONSULTAS Y ANÁLISIS
    // ========================================

    @GetMapping("/tareas/vencidas")
    public List<Tarea> obtenerTareasVencidas() {
        return proyectoService.obtenerTareasVencidas();
    }

    @GetMapping("/riesgos/altos")
    public List<Riesgo> obtenerRiesgosAltos() {
        return proyectoService.obtenerRiesgosAltos();
    }

    @GetMapping("/tareas/multifase")
    public List<Tarea> obtenerTareasMultifase() {
        return proyectoService.obtenerTareasMultifase();
    }

    @GetMapping("/{id}/estadisticas")
    public ResponseEntity<Object> obtenerEstadisticasProyecto(@PathVariable Long id) {
        Object estadisticas = proyectoService.obtenerEstadisticasProyecto(id);
        return estadisticas != null ? ResponseEntity.ok(estadisticas) : ResponseEntity.notFound().build();
    }

    // ========================================
    // FILTROS POR ESTADO (mantenemos esto porque es útil)
    // ========================================

    @GetMapping("/activos")
    public List<Proyecto> obtenerProyectosActivos() {
        return proyectoService.filtrarProyectosPorEstado(Proyecto.Estado.ACTIVO);
    }

    @GetMapping("/pausados") 
    public List<Proyecto> obtenerProyectosPausados() {
        return proyectoService.filtrarProyectosPorEstado(Proyecto.Estado.PAUSADO);
    }

    @GetMapping("/cerrados")
    public List<Proyecto> obtenerProyectosCerrados() {
        return proyectoService.filtrarProyectosPorEstado(Proyecto.Estado.CERRADO);
    }

    // ========================================
    // ENDPOINT DEMO PARA PRUEBAS
    // ========================================

    @PostMapping("/demo")
    public Proyecto crearProyectoDemo() {
        // Tu código de demo actual está perfecto, lo mantenemos igual
        Proyecto proyecto = new Proyecto(
            "Implementación SAP ERP", 
            "Migración completa a SAP ERP 7.51", 
            "Leonardo Felici"
        );
        proyecto.planificarFechas(LocalDate.now(), LocalDate.now().plusMonths(6));

        Fase fase1 = new Fase("Análisis", 1);
        fase1.planificarFechas(LocalDate.now(), LocalDate.now().plusMonths(2));
        
        Fase fase2 = new Fase("Desarrollo", 2);
        fase2.planificarFechas(LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(4));

        proyecto.agregarFase(fase1);
        proyecto.agregarFase(fase2);

        Tarea tarea1 = new Tarea("Definir arquitectura", "Diseño del sistema", Tarea.Prioridad.ALTA, "Carlos Mendoza");
        tarea1.planificarFechas(LocalDate.now(), LocalDate.now().plusWeeks(2));
        
        Tarea tarea2 = new Tarea("Configurar base de datos", "Setup PostgreSQL", Tarea.Prioridad.MEDIA, "Ana García");
        tarea2.planificarFechas(LocalDate.now().plusWeeks(2), LocalDate.now().plusMonths(1));

        fase1.agregarTarea(tarea1);
        fase1.agregarTarea(tarea2);

        Riesgo riesgo = new Riesgo("Retrasos en migración de datos", Riesgo.Probabilidad.MEDIA, Riesgo.Impacto.ALTO);
        proyecto.agregarRiesgo(riesgo);

        return proyectoService.guardarProyecto(proyecto);
    }
}

// ========================================
// CLASES AUXILIARES PARA REQUESTS
// ========================================

class CrearProyectoRequest {
    private String nombre;
    private String descripcion;
    private String liderProyecto;
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getLiderProyecto() { return liderProyecto; }
    public void setLiderProyecto(String liderProyecto) { this.liderProyecto = liderProyecto; }
}

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
