package org.psa.controller;

import org.psa.model.Recurso;
import org.psa.service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recursos")
@CrossOrigin(origins = "http://localhost:3000")
public class RecursoController {
    
    @Autowired
    private RecursoService recursoService;
    
    // ========================================
    // SINCRONIZACIÓN CON API EXTERNA
    // ========================================
    
    @PostMapping("/sincronizar")
    public ResponseEntity<List<Recurso>> sincronizarRecursos() {
        try {
            List<Recurso> recursos = recursoService.sincronizarRecursosDesdeAPIExterna();
            return ResponseEntity.ok(recursos);
        } catch (Exception e) {
            System.err.println("❌ Error en sincronización: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // ========================================
    // CONSULTAS BÁSICAS
    // ========================================
    
    @GetMapping
    public List<Recurso> obtenerTodosLosRecursos() {
        return recursoService.obtenerTodosLosRecursos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Recurso> obtenerRecursoPorId(@PathVariable String id) {
        try {
            Recurso recurso = recursoService.obtenerRecursoPorId(id);
            return ResponseEntity.ok(recurso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/dni/{dni}")
    public ResponseEntity<Recurso> obtenerRecursoPorDni(@PathVariable String dni) {
        try {
            Recurso recurso = recursoService.obtenerRecursoPorDni(dni);
            return recurso != null ? ResponseEntity.ok(recurso) : ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/rol/{rolId}")
    public ResponseEntity<List<Recurso>> obtenerRecursosPorRol(@PathVariable String rolId) {
        try {
            List<Recurso> recursos = recursoService.obtenerRecursosPorRol(rolId);
            return ResponseEntity.ok(recursos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/buscar")
    public List<Recurso> buscarRecursos(@RequestParam(required = false) String termino) {
        return recursoService.buscarRecursos(termino);
    }
    
    // ========================================
    // GESTIÓN DE ESTADO
    // ========================================
    
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Recurso> desactivarRecurso(@PathVariable String id) {
        try {
            Recurso recurso = recursoService.desactivarRecurso(id);
            return ResponseEntity.ok(recurso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/activar")
    public ResponseEntity<Recurso> activarRecurso(@PathVariable String id) {
        try {
            Recurso recurso = recursoService.activarRecurso(id);
            return ResponseEntity.ok(recurso);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // ========================================
    // VALIDACIONES PARA ASIGNACIÓN
    // ========================================
    
    @GetMapping("/{id}/disponible")
    public ResponseEntity<DisponibilidadResponse> verificarDisponibilidad(@PathVariable String id) {
        boolean disponible = recursoService.esRecursoDisponibleParaAsignacion(id);
        return ResponseEntity.ok(new DisponibilidadResponse(id, disponible));
    }
    
    @GetMapping("/disponibles")
    public List<Recurso> obtenerRecursosDisponibles() {
        return recursoService.obtenerTodosLosRecursos(); // Solo retorna activos
    }
    
    // ========================================
    // ESTADÍSTICAS Y REPORTES
    // ========================================
    
    @GetMapping("/estadisticas")
    public RecursoService.EstadisticasRecursosDTO obtenerEstadisticas() {
        return recursoService.obtenerEstadisticas();
    }
    
    @GetMapping("/count/activos")
    public ResponseEntity<Long> contarRecursosActivos() {
        long count = recursoService.contarRecursosActivos();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/count/total")
    public ResponseEntity<Long> contarTotalRecursos() {
        long count = recursoService.contarTotalRecursos();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/recientes")
    public List<Recurso> obtenerRecursosRecientementeSincronizados() {
        return recursoService.obtenerRecursosRecientementeSincronizados();
    }
    
    // ========================================
    // ENDPOINTS DE UTILIDAD
    // ========================================
    
    @GetMapping("/existe/dni/{dni}")
    public ResponseEntity<ExistenciaResponse> verificarExistenciaPorDni(@PathVariable String dni) {
        boolean existe = recursoService.existeRecursoPorDni(dni);
        return ResponseEntity.ok(new ExistenciaResponse(dni, existe));
    }
    
    // ========================================
    // ENDPOINT DE PRUEBA (para desarrollo)
    // ========================================
    
    @GetMapping("/test-api-externa")
    public ResponseEntity<String> testearAPIExterna() {
        try {
            // Solo verifica que la API externa responda, sin guardar nada
            List<Recurso> recursos = recursoService.sincronizarRecursosDesdeAPIExterna();
            return ResponseEntity.ok("✅ API externa funciona. Recursos encontrados: " + recursos.size());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                .body("❌ Error al conectar con API externa: " + e.getMessage());
        }
    }
    
    // ========================================
    // ENDPOINT INFO PARA FRONTEND
    // ========================================
    
    @GetMapping("/info")
    public InfoRecursosResponse obtenerInfoGeneral() {
        RecursoService.EstadisticasRecursosDTO stats = recursoService.obtenerEstadisticas();
        List<Recurso> recientes = recursoService.obtenerRecursosRecientementeSincronizados();
        
        return new InfoRecursosResponse(
            stats.getRecursosActivos(),
            stats.getTotalRegistros(),
            stats.getPorcentajeActivos(),
            recientes.size()
        );
    }
}

// ========================================
// CLASES AUXILIARES PARA RESPONSES
// ========================================

class DisponibilidadResponse {
    private String recursoId;
    private boolean disponible;
    
    public DisponibilidadResponse(String recursoId, boolean disponible) {
        this.recursoId = recursoId;
        this.disponible = disponible;
    }
    
    public String getRecursoId() { return recursoId; }
    public void setRecursoId(String recursoId) { this.recursoId = recursoId; }
    
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}

class ExistenciaResponse {
    private String dni;
    private boolean existe;
    
    public ExistenciaResponse(String dni, boolean existe) {
        this.dni = dni;
        this.existe = existe;
    }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public boolean isExiste() { return existe; }
    public void setExiste(boolean existe) { this.existe = existe; }
}

class InfoRecursosResponse {
    private long recursosActivos;
    private long totalRecursos;
    private double porcentajeActivos;
    private int recursosSincronizadosRecientes;
    
    public InfoRecursosResponse(long recursosActivos, long totalRecursos, 
                               double porcentajeActivos, int recursosSincronizadosRecientes) {
        this.recursosActivos = recursosActivos;
        this.totalRecursos = totalRecursos;
        this.porcentajeActivos = porcentajeActivos;
        this.recursosSincronizadosRecientes = recursosSincronizadosRecientes;
    }
    
    public long getRecursosActivos() { return recursosActivos; }
    public void setRecursosActivos(long recursosActivos) { this.recursosActivos = recursosActivos; }
    
    public long getTotalRecursos() { return totalRecursos; }
    public void setTotalRecursos(long totalRecursos) { this.totalRecursos = totalRecursos; }
    
    public double getPorcentajeActivos() { return porcentajeActivos; }
    public void setPorcentajeActivos(double porcentajeActivos) { this.porcentajeActivos = porcentajeActivos; }
    
    public int getRecursosSincronizadosRecientes() { return recursosSincronizadosRecientes; }
    public void setRecursosSincronizadosRecientes(int recursosSincronizadosRecientes) { 
        this.recursosSincronizadosRecientes = recursosSincronizadosRecientes; 
    }
}
