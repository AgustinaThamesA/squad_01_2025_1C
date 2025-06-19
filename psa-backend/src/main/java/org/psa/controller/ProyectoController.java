package org.psa.controller;

import org.psa.model.*;
import org.psa.service.ProyectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import org.psa.service.RecursoService;
import org.psa.repository.FaseRepository;
import org.psa.repository.TareaRepository;
import java.util.ArrayList;
import org.psa.repository.RiesgoRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "http://localhost:3000")
public class ProyectoController {

    @Autowired
    private ProyectoService proyectoService;
    @Autowired  
    private RecursoService recursoService;
    @Autowired
    private FaseRepository faseRepository;
    @Autowired
    private TareaRepository tareaRepository;
    @Autowired
    private RiesgoRepository riesgoRepository;

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

   // Reemplaza el método crearFase en tu ProyectoController.java
    @PostMapping("/{id}/fases")
    public ResponseEntity<Fase> crearFase(@PathVariable Long id, @RequestBody CrearFaseRequest request) {
        // Solo pasamos el nombre, el service calculará el orden automáticamente
        Fase fase = proyectoService.crearFase(id, request.getNombre());
        return fase != null ? ResponseEntity.ok(fase) : ResponseEntity.notFound().build();
    }

    // Y asegúrate de que la clase CrearFaseRequest esté definida al final del archivo
    // junto con las otras clases auxiliares. Si no existe, agrégala:

    public static class CrearFaseRequest {
        private String nombre;
        private Integer orden; // Este campo ahora es opcional
        
        public String getNombre() { 
            return nombre; 
        }
        
        public void setNombre(String nombre) { 
            this.nombre = nombre; 
        }
        
        public Integer getOrden() { 
            return orden; 
        }
        
        public void setOrden(Integer orden) { 
            this.orden = orden; 
        }
    }

    @PutMapping("/fases/{faseId}/planificar")
    public ResponseEntity<Fase> planificarFase(@PathVariable Long faseId, @RequestBody PlanificacionRequest request) {
        Fase fase = proyectoService.planificarFase(faseId, request.getFechaInicio(), request.getFechaFin());
        return fase != null ? ResponseEntity.ok(fase) : ResponseEntity.notFound().build();
    }

    // ========================================
    // GESTIÓN DE TAREAS
    // ========================================

    // 🔧 AGREGAR: Obtener tareas de un proyecto específico
    @GetMapping("/{id}/tareas")
    public List<Tarea> obtenerTareasDelProyecto(@PathVariable Long id) {
        return proyectoService.obtenerTareasDelProyecto(id);
    }

    // 🔧 CAMBIAR: El método crearTarea existente por este:
    @PostMapping("/{proyectoId}/tareas")
    public Tarea crearTarea(@PathVariable Long proyectoId, @RequestBody CrearTareaRequest request) {
        return proyectoService.crearTarea(
            proyectoId,
            request.getFaseId(), // Puede ser null
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

    @PostMapping("/con-recurso")
    public ResponseEntity<Proyecto> crearProyectoConRecurso(@RequestBody CrearProyectoConRecursoRequest request) {
        try {
            Proyecto proyecto = proyectoService.crearProyectoConRecurso(
                request.getNombre(),
                request.getDescripcion(), 
                request.getLiderRecursoId()
            );
            return ResponseEntity.ok(proyecto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/asignar-lider")
    public ResponseEntity<Proyecto> asignarLiderRecurso(@PathVariable Long id, @RequestBody AsignarLiderRequest request) {
        try {
            Proyecto proyecto = proyectoService.asignarLiderRecurso(id, request.getLiderRecursoId());
            return ResponseEntity.ok(proyecto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/remover-lider")
    public ResponseEntity<Proyecto> removerLiderRecurso(@PathVariable Long id) {
        try {
            Proyecto proyecto = proyectoService.removerLiderRecurso(id);
            return ResponseEntity.ok(proyecto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ========================================
    // CONSULTAS POR RECURSOS
    // ========================================

    @GetMapping("/lider/{liderRecursoId}")
    public ResponseEntity<List<Proyecto>> obtenerProyectosPorLider(@PathVariable String liderRecursoId) {
        try {
            List<Proyecto> proyectos = proyectoService.obtenerProyectosPorLider(liderRecursoId);
            return ResponseEntity.ok(proyectos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/sin-lider")
    public List<Proyecto> obtenerProyectosSinLider() {
        return proyectoService.obtenerProyectosSinLider();
    }

    @GetMapping("/estadisticas-recursos")
    public ProyectoService.EstadisticasRecursosProyectoDTO obtenerEstadisticasRecursos() {
        return proyectoService.obtenerEstadisticasRecursosEnProyectos();
    }

    // ========================================
    // GESTIÓN DE TAREAS CON RECURSOS
    // ========================================

    @PostMapping("/{proyectoId}/tareas/con-recurso")
    public ResponseEntity<Tarea> crearTareaConRecurso(@PathVariable Long proyectoId, @RequestBody CrearTareaConRecursoRequest request) {
        try {
            Tarea tarea = proyectoService.crearTareaConRecurso(
                proyectoId,
                request.getFaseId(),
                request.getTitulo(),
                request.getDescripcion(),
                request.getPrioridad(),
                request.getResponsableRecursoId()
            );
            return ResponseEntity.ok(tarea);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/tareas/{tareaId}/asignar-responsable")
    public ResponseEntity<Tarea> asignarResponsableRecurso(@PathVariable Long tareaId, @RequestBody AsignarResponsableRequest request) {
        try {
            Tarea tarea = proyectoService.asignarResponsableRecurso(tareaId, request.getResponsableRecursoId());
            return ResponseEntity.ok(tarea);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/tareas/{tareaId}/remover-responsable")
    public ResponseEntity<Tarea> removerResponsableRecurso(@PathVariable Long tareaId) {
        try {
            Tarea tarea = proyectoService.removerResponsableRecurso(tareaId);
            return ResponseEntity.ok(tarea);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ========================================
    // CONSULTAS POR RECURSOS EN TAREAS
    // ========================================

    @GetMapping("/tareas/responsable/{responsableRecursoId}")
    public ResponseEntity<List<Tarea>> obtenerTareasPorResponsable(@PathVariable String responsableRecursoId) {
        try {
            List<Tarea> tareas = proyectoService.obtenerTareasPorResponsable(responsableRecursoId);
            return ResponseEntity.ok(tareas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/tareas/sin-responsable")
    public List<Tarea> obtenerTareasSinResponsable() {
        return proyectoService.obtenerTareasSinResponsable();
    }

    @GetMapping("/{proyectoId}/tareas/responsable/{responsableRecursoId}")
    public ResponseEntity<List<Tarea>> obtenerTareasDelRecursoEnProyecto(@PathVariable Long proyectoId, @PathVariable String responsableRecursoId) {
        try {
            List<Tarea> tareas = proyectoService.obtenerTareasDelRecursoEnProyecto(responsableRecursoId, proyectoId);
            return ResponseEntity.ok(tareas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ========================================
    // ESTADÍSTICAS Y REPORTES DE RECURSOS
    // ========================================

    @GetMapping("/recursos/{recursoId}/carga-trabajo")
    public ResponseEntity<ProyectoService.CargaTrabajoRecursoDTO> obtenerCargaTrabajoRecurso(@PathVariable String recursoId) {
        try {
            ProyectoService.CargaTrabajoRecursoDTO cargaTrabajo = proyectoService.obtenerCargaTrabajoRecurso(recursoId);
            return ResponseEntity.ok(cargaTrabajo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/recursos/carga-trabajo")
    public List<ProyectoService.CargaTrabajoRecursoDTO> obtenerCargaTrabajoTodosLosRecursos() {
        return proyectoService.obtenerCargaTrabajoTodosLosRecursos();
    }

    @GetMapping("/recursos/disponibles-tareas")
    public List<Recurso> obtenerRecursosDisponiblesParaTareas() {
        return proyectoService.obtenerRecursosDisponiblesParaTareas();
    }

    @GetMapping("/tareas/estadisticas-recursos")
    public ProyectoService.EstadisticasRecursosTareasDTO obtenerEstadisticasRecursosEnTareas() {
        return proyectoService.obtenerEstadisticasRecursosEnTareas();
    }

    // ========================================
    // ENDPOINTS ESPECIALES PARA DASHBOARDS
    // ========================================

    @GetMapping("/dashboard/recurso/{recursoId}")
    public ResponseEntity<DashboardRecursoResponse> obtenerDashboardRecurso(@PathVariable String recursoId) {
        try {
            // Obtener datos del recurso
            Recurso recurso = recursoService.obtenerRecursoPorId(recursoId);
            
            // Obtener proyectos donde es líder
            List<Proyecto> proyectosLiderados = proyectoService.obtenerProyectosPorLider(recursoId);
            
            // Obtener carga de trabajo
            ProyectoService.CargaTrabajoRecursoDTO cargaTrabajo = proyectoService.obtenerCargaTrabajoRecurso(recursoId);
            
            DashboardRecursoResponse dashboard = new DashboardRecursoResponse(
                recurso,
                proyectosLiderados,
                cargaTrabajo
            );
            
            return ResponseEntity.ok(dashboard);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/dashboard/recursos-sobrecargados")
    public List<ProyectoService.CargaTrabajoRecursoDTO> obtenerRecursosSobrecargados() {
        return proyectoService.obtenerCargaTrabajoTodosLosRecursos().stream()
            .filter(carga -> carga.getTareasActivas() > 3) // Umbral configurable
            .collect(Collectors.toList());
    }

    // ========================================
    // ENDPOINTS DE EDICIÓN - AGREGAR AL ProyectoController.java
    // ========================================

    // ✅ EDITAR PROYECTO COMPLETO
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> editarProyecto(@PathVariable Long id, @RequestBody EditarProyectoRequest request) {
        try {
            Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
            
            // Actualizar campos existentes
            if (request.getNombre() != null && !request.getNombre().isBlank()) {
                proyecto.setNombre(request.getNombre());
            }
            if (request.getDescripcion() != null) {
                proyecto.setDescripcion(request.getDescripcion());
            }
            if (request.getLiderProyecto() != null) {
                proyecto.setLiderProyecto(request.getLiderProyecto());
            }
            
            // ✅ AGREGAR ESTO: Actualizar estado si se envía
            if (request.getEstado() != null) {
                proyecto.setEstado(request.getEstado());
            }
            
            Proyecto proyectoActualizado = proyectoService.actualizarProyecto(proyecto);
            return ResponseEntity.ok(proyectoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ EDITAR FASE
    @PutMapping("/fases/{faseId}")
    public ResponseEntity<Fase> editarFase(@PathVariable Long faseId, @RequestBody EditarFaseRequest request) {
        try {
            Fase fase = faseRepository.findById(faseId)
                .orElseThrow(() -> new IllegalArgumentException("Fase no encontrada"));
            
            // Actualizar campos
            if (request.getNombre() != null && !request.getNombre().isBlank()) {
                fase.setNombre(request.getNombre());
            }
            if (request.getOrden() != null) {
                fase.setOrden(request.getOrden());
            }
            
            Fase faseActualizada = faseRepository.save(fase);
            return ResponseEntity.ok(faseActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ EDITAR TAREA
    @PutMapping("/tareas/{tareaId}")
    public ResponseEntity<Tarea> editarTarea(@PathVariable Long tareaId, @RequestBody EditarTareaRequest request) {
        try {
            Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
            
            // Actualizar campos
            if (request.getTitulo() != null && !request.getTitulo().isBlank()) {
                tarea.setTitulo(request.getTitulo());
            }
            if (request.getDescripcion() != null) {
                tarea.setDescripcion(request.getDescripcion());
            }
            if (request.getPrioridad() != null) {
                tarea.setPrioridad(request.getPrioridad());
            }
            if (request.getResponsable() != null) {
                tarea.setResponsable(request.getResponsable());
            }
            
            Tarea tareaActualizada = tareaRepository.save(tarea);
            return ResponseEntity.ok(tareaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ========================================
    // ENDPOINTS DE ELIMINACIÓN - AGREGAR AL ProyectoController.java
    // ========================================

    // ✅ ELIMINAR FASE
    @DeleteMapping("/fases/{faseId}")
    public ResponseEntity<Void> eliminarFase(@PathVariable Long faseId) {
        try {
            Fase fase = faseRepository.findById(faseId)
                .orElseThrow(() -> new IllegalArgumentException("Fase no encontrada"));
            
            // Verificar si la fase tiene tareas
            if (!fase.getTareas().isEmpty()) {
                // Opción 1: No permitir eliminar fase con tareas
                return ResponseEntity.badRequest().build();
                
                // Opción 2: Eliminar las tareas también (descomenta si prefieres esto)
                // for (Tarea tarea : new ArrayList<>(fase.getTareas())) {
                //     tareaRepository.delete(tarea);
                // }
            }
            
            faseRepository.delete(fase);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ ELIMINAR TAREA
    @DeleteMapping("/tareas/{tareaId}")
    public ResponseEntity<Void> eliminarTarea(@PathVariable Long tareaId) {
        try {
            Tarea tarea = tareaRepository.findById(tareaId)
                .orElseThrow(() -> new IllegalArgumentException("Tarea no encontrada"));
            
            // Remover la tarea de todas las fases que la contengan
            for (Fase fase : new ArrayList<>(tarea.getFases())) {
                fase.removerTarea(tarea);
                faseRepository.save(fase);
            }
            
            // Eliminar la tarea
            tareaRepository.delete(tarea);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ ELIMINAR FASE CON FUERZA (elimina también las tareas)
    @DeleteMapping("/fases/{faseId}/forzar")
    public ResponseEntity<Void> eliminarFaseConFuerza(@PathVariable Long faseId) {
        try {
            Fase fase = faseRepository.findById(faseId)
                .orElseThrow(() -> new IllegalArgumentException("Fase no encontrada"));
            
            // Eliminar todas las tareas de la fase
            for (Tarea tarea : new ArrayList<>(fase.getTareas())) {
                // Remover la tarea de otras fases también
                for (Fase otraFase : new ArrayList<>(tarea.getFases())) {
                    if (!otraFase.getIdFase().equals(faseId)) {
                        otraFase.removerTarea(tarea);
                        faseRepository.save(otraFase);
                    }
                }
                tareaRepository.delete(tarea);
            }
            
            // Eliminar la fase
            faseRepository.delete(fase);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ========================================
    // ENDPOINTS DE VERIFICACIÓN ANTES DE ELIMINAR
    // ========================================

    // ✅ VERIFICAR SI FASE SE PUEDE ELIMINAR
    @GetMapping("/fases/{faseId}/puede-eliminar")
    public ResponseEntity<PuedeEliminarResponse> verificarSiFasePuedeEliminar(@PathVariable Long faseId) {
        try {
            Fase fase = faseRepository.findById(faseId)
                .orElseThrow(() -> new IllegalArgumentException("Fase no encontrada"));
            
            boolean puedeEliminar = fase.getTareas().isEmpty();
            int numeroTareas = fase.getTareas().size();
            
            PuedeEliminarResponse response = new PuedeEliminarResponse(
                puedeEliminar, 
                puedeEliminar ? "La fase se puede eliminar" : "La fase tiene " + numeroTareas + " tarea(s)",
                numeroTareas
            );
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ VERIFICAR SI PROYECTO SE PUEDE ELIMINAR
    @GetMapping("/{id}/puede-eliminar")
    public ResponseEntity<PuedeEliminarResponse> verificarSiProyectoPuedeEliminar(@PathVariable Long id) {
        try {
            Proyecto proyecto = proyectoService.obtenerProyectoPorId(id);
            
            int numeroFases = proyecto.getFases().size();
            int numeroTareas = proyecto.getTotalTareas();
            
            boolean puedeEliminar = numeroFases == 0 && numeroTareas == 0;
            
            String mensaje;
            if (puedeEliminar) {
                mensaje = "El proyecto se puede eliminar";
            } else {
                mensaje = "El proyecto tiene " + numeroFases + " fase(s) y " + numeroTareas + " tarea(s)";
            }
            
            PuedeEliminarResponse response = new PuedeEliminarResponse(
                puedeEliminar, 
                mensaje,
                numeroFases + numeroTareas
            );
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/riesgos/{riesgoId}")
    public ResponseEntity<Riesgo> editarRiesgo(@PathVariable Long riesgoId, @RequestBody EditarRiesgoRequest request) {
        try {
            Riesgo riesgo = proyectoService.actualizarRiesgo(
                riesgoId,
                request.getDescripcion(),
                request.getProbabilidad(),
                request.getImpacto()
            );
            return ResponseEntity.ok(riesgo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ ELIMINAR RIESGO
    @DeleteMapping("/riesgos/{riesgoId}")
    public ResponseEntity<Void> eliminarRiesgo(@PathVariable Long riesgoId) {
        try {
            boolean eliminado = proyectoService.eliminarRiesgo(riesgoId);
            return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // ✅ CAMBIAR ESTADO DE RIESGO (ACTIVAR/MITIGAR)
    @PutMapping("/riesgos/{riesgoId}/estado")
    public ResponseEntity<Riesgo> cambiarEstadoRiesgo(@PathVariable Long riesgoId, @RequestBody CambiarEstadoRiesgoRequest request) {
        try {
            Riesgo riesgo = proyectoService.cambiarEstadoRiesgo(riesgoId, request.getEstado());
            return ResponseEntity.ok(riesgo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ OBTENER RIESGO POR ID (para formularios de edición)
    @GetMapping("/riesgos/{riesgoId}")
    public ResponseEntity<Riesgo> obtenerRiesgoPorId(@PathVariable Long riesgoId) {
        try {
            Riesgo riesgo = riesgoRepository.findById(riesgoId)
                .orElseThrow(() -> new IllegalArgumentException("Riesgo no encontrado"));
            return ResponseEntity.ok(riesgo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
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
    private Long faseId; // 🔧 AGREGAR esta línea
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Tarea.Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Tarea.Prioridad prioridad) { this.prioridad = prioridad; }
    
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    
    // 🔧 AGREGAR estos dos métodos:
    public Long getFaseId() { return faseId; }
    public void setFaseId(Long faseId) { this.faseId = faseId; }
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

class CrearProyectoConRecursoRequest {
    private String nombre;
    private String descripcion;
    private String liderRecursoId;
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getLiderRecursoId() { return liderRecursoId; }
    public void setLiderRecursoId(String liderRecursoId) { this.liderRecursoId = liderRecursoId; }
}

class AsignarLiderRequest {
    private String liderRecursoId;
    
    public String getLiderRecursoId() { return liderRecursoId; }
    public void setLiderRecursoId(String liderRecursoId) { this.liderRecursoId = liderRecursoId; }
}

class CrearTareaConRecursoRequest {
    private String titulo;
    private String descripcion;
    private Tarea.Prioridad prioridad;
    private String responsableRecursoId;
    private Long faseId;
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Tarea.Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Tarea.Prioridad prioridad) { this.prioridad = prioridad; }
    
    public String getResponsableRecursoId() { return responsableRecursoId; }
    public void setResponsableRecursoId(String responsableRecursoId) { this.responsableRecursoId = responsableRecursoId; }
    
    public Long getFaseId() { return faseId; }
    public void setFaseId(Long faseId) { this.faseId = faseId; }
}

class AsignarResponsableRequest {
    private String responsableRecursoId;
    
    public String getResponsableRecursoId() { return responsableRecursoId; }
    public void setResponsableRecursoId(String responsableRecursoId) { this.responsableRecursoId = responsableRecursoId; }
}

class DashboardRecursoResponse {
    private Recurso recurso;
    private List<Proyecto> proyectosLiderados;
    private ProyectoService.CargaTrabajoRecursoDTO cargaTrabajo;
    
    public DashboardRecursoResponse(Recurso recurso, List<Proyecto> proyectosLiderados, 
                                   ProyectoService.CargaTrabajoRecursoDTO cargaTrabajo) {
        this.recurso = recurso;
        this.proyectosLiderados = proyectosLiderados;
        this.cargaTrabajo = cargaTrabajo;
    }
    
    public Recurso getRecurso() { return recurso; }
    public void setRecurso(Recurso recurso) { this.recurso = recurso; }
    
    public List<Proyecto> getProyectosLiderados() { return proyectosLiderados; }
    public void setProyectosLiderados(List<Proyecto> proyectosLiderados) { this.proyectosLiderados = proyectosLiderados; }
    
    public ProyectoService.CargaTrabajoRecursoDTO getCargaTrabajo() { return cargaTrabajo; }
    public void setCargaTrabajo(ProyectoService.CargaTrabajoRecursoDTO cargaTrabajo) { this.cargaTrabajo = cargaTrabajo; }
}

// ========================================
// CLASES AUXILIARES PARA REQUESTS DE EDICIÓN
// ========================================

class EditarProyectoRequest {
    private String nombre;
    private String descripcion;
    private String liderProyecto;
    private Proyecto.Estado estado;  // ✅ AGREGAR ESTA LÍNEA
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getLiderProyecto() { return liderProyecto; }
    public void setLiderProyecto(String liderProyecto) { this.liderProyecto = liderProyecto; }
    
    // ✅ AGREGAR ESTOS MÉTODOS:
    public Proyecto.Estado getEstado() { return estado; }
    public void setEstado(Proyecto.Estado estado) { this.estado = estado; }
}

class EditarFaseRequest {
    private String nombre;
    private Integer orden;
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}

class EditarTareaRequest {
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

// ========================================
// CLASE AUXILIAR PARA RESPUESTA DE VERIFICACIÓN
// ========================================

class PuedeEliminarResponse {
    private boolean puedeEliminar;
    private String mensaje;
    private int elementosRelacionados;
    
    public PuedeEliminarResponse(boolean puedeEliminar, String mensaje, int elementosRelacionados) {
        this.puedeEliminar = puedeEliminar;
        this.mensaje = mensaje;
        this.elementosRelacionados = elementosRelacionados;
    }
    
    public boolean isPuedeEliminar() { return puedeEliminar; }
    public void setPuedeEliminar(boolean puedeEliminar) { this.puedeEliminar = puedeEliminar; }
    
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    
    public int getElementosRelacionados() { return elementosRelacionados; }
    public void setElementosRelacionados(int elementosRelacionados) { this.elementosRelacionados = elementosRelacionados; }
}

// ========================================
// CLASES AUXILIARES PARA REQUESTS
// ========================================

class EditarRiesgoRequest {
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

class CambiarEstadoRiesgoRequest {
    private Riesgo.Estado estado;
    
    public Riesgo.Estado getEstado() { return estado; }
    public void setEstado(Riesgo.Estado estado) { this.estado = estado; }
}
