package org.psa.service;

import org.psa.model.Recurso;
import org.psa.repository.RecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecursoService {
    
    private static final String URL_API_EXTERNA = 
        "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/32c8fe38-22a6-4fbb-b461-170dfac937e4/recursos-api/1.0.1/m/recursos";
    
    @Autowired
    private RecursoRepository recursoRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    // ========================================
    // CONSUMIR API EXTERNA Y SINCRONIZAR
    // ========================================
    
    @Transactional
    public List<Recurso> sincronizarRecursosDesdeAPIExterna() {
        try {
            System.out.println("🔄 Iniciando sincronización con API externa...");
            
            // Consumir API externa - Spring Boot convierte automáticamente el JSON
            ResponseEntity<List<Recurso>> response = restTemplate.exchange(
                URL_API_EXTERNA,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Recurso>>() {}
            );
            
            List<Recurso> recursosExternos = response.getBody();
            
            if (recursosExternos == null || recursosExternos.isEmpty()) {
                throw new RuntimeException("No se recibieron recursos de la API externa");
            }
            
            System.out.println("📥 Recibidos " + recursosExternos.size() + " recursos de la API externa");
            
            // Procesar y guardar cada recurso
            for (Recurso recursoExterno : recursosExternos) {
                procesarYGuardarRecurso(recursoExterno);
            }
            
            // Retornar todos los recursos activos
            List<Recurso> recursosActualizados = recursoRepository.findByActivoTrue();
            System.out.println("✅ Sincronización completada. Total recursos activos: " + recursosActualizados.size());
            
            return recursosActualizados;
            
        } catch (Exception e) {
            System.err.println("❌ Error al sincronizar recursos: " + e.getMessage());
            throw new RuntimeException("Error al sincronizar recursos: " + e.getMessage(), e);
        }
    }
    
    private void procesarYGuardarRecurso(Recurso recursoExterno) {
        // Buscar si ya existe
        Recurso recursoExistente = recursoRepository.findById(recursoExterno.getId()).orElse(null);
        
        if (recursoExistente != null) {
            // Actualizar existente
            System.out.println("🔄 Actualizando recurso existente: " + recursoExterno.getNombreCompleto());
            recursoExistente.setNombre(recursoExterno.getNombre());
            recursoExistente.setApellido(recursoExterno.getApellido());
            recursoExistente.setDni(recursoExterno.getDni());
            recursoExistente.setRolId(recursoExterno.getRolId());
            recursoExistente.marcarComoSincronizado();
            recursoRepository.save(recursoExistente);
        } else {
            // Crear nuevo recurso
            System.out.println("➕ Creando nuevo recurso: " + recursoExterno.getNombreCompleto());
            recursoExterno.setActivo(true);
            recursoExterno.setFechaSincronizacion(LocalDateTime.now());
            recursoRepository.save(recursoExterno);
        }
    }
    
    // ========================================
    // GESTIÓN LOCAL DE RECURSOS
    // ========================================
    
    public List<Recurso> obtenerTodosLosRecursos() {
        return recursoRepository.findByActivoTrue();
    }
    
    public Recurso obtenerRecursoPorId(String id) {
        return recursoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Recurso con ID " + id + " no encontrado"));
    }
    
    public Recurso obtenerRecursoPorDni(String dni) {
        if (dni == null || dni.isBlank()) {
            throw new IllegalArgumentException("El DNI no puede ser vacío");
        }
        return recursoRepository.findByDni(dni);
    }
    
    public List<Recurso> obtenerRecursosPorRol(String rolId) {
        if (rolId == null || rolId.isBlank()) {
            throw new IllegalArgumentException("El ID del rol no puede ser vacío");
        }
        return recursoRepository.findByRolId(rolId);
    }
    
    public List<Recurso> buscarRecursos(String termino) {
        if (termino == null || termino.isBlank()) {
            return obtenerTodosLosRecursos();
        }
        return recursoRepository.buscarPorNombreOApellido(termino);
    }
    
    @Transactional
    public Recurso desactivarRecurso(String id) {
        Recurso recurso = obtenerRecursoPorId(id);
        recurso.setActivo(false);
        System.out.println("🔸 Recurso desactivado: " + recurso.getNombreCompleto());
        return recursoRepository.save(recurso);
    }
    
    @Transactional
    public Recurso activarRecurso(String id) {
        Recurso recurso = recursoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Recurso con ID " + id + " no encontrado"));
        recurso.setActivo(true);
        recurso.marcarComoSincronizado();
        System.out.println("🔹 Recurso activado: " + recurso.getNombreCompleto());
        return recursoRepository.save(recurso);
    }
    
    // ========================================
    // VALIDACIONES PARA ASIGNACIÓN
    // ========================================
    
    public boolean esRecursoDisponibleParaAsignacion(String recursoId) {
        try {
            Recurso recurso = obtenerRecursoPorId(recursoId);
            return recurso.getActivo();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    public void validarRecursoParaAsignacion(String recursoId, String contexto) {
        if (recursoId == null || recursoId.isBlank()) {
            throw new IllegalArgumentException("El ID del recurso no puede ser vacío para " + contexto);
        }
        
        if (!esRecursoDisponibleParaAsignacion(recursoId)) {
            throw new IllegalArgumentException("El recurso no está disponible para " + contexto);
        }
    }
    
    // ========================================
    // ESTADÍSTICAS Y REPORTES
    // ========================================
    
    public long contarRecursosActivos() {
        return recursoRepository.contarRecursosActivos();
    }
    
    public long contarTotalRecursos() {
        return recursoRepository.count();
    }
    
    public EstadisticasRecursosDTO obtenerEstadisticas() {
        long totalActivos = contarRecursosActivos();
        long totalRegistros = contarTotalRecursos();
        long totalInactivos = totalRegistros - totalActivos;
        
        return new EstadisticasRecursosDTO(totalActivos, totalInactivos, totalRegistros);
    }
    
    // ========================================
    // MÉTODOS DE UTILIDAD
    // ========================================
    
    public boolean existeRecursoPorDni(String dni) {
        return recursoRepository.findByDni(dni) != null;
    }
    
    public List<Recurso> obtenerRecursosRecientementeSincronizados() {
        // Obtener recursos sincronizados en las últimas 24 horas
        LocalDateTime hace24Horas = LocalDateTime.now().minusHours(24);
        return recursoRepository.findByActivoTrue().stream()
            .filter(r -> r.getFechaSincronizacion() != null && 
                        r.getFechaSincronizacion().isAfter(hace24Horas))
            .toList();
    }
    
    // ========================================
    // DTO PARA ESTADÍSTICAS
    // ========================================
    
    public static class EstadisticasRecursosDTO {
        private final long recursosActivos;
        private final long recursosInactivos;
        private final long totalRegistros;
        
        public EstadisticasRecursosDTO(long recursosActivos, long recursosInactivos, long totalRegistros) {
            this.recursosActivos = recursosActivos;
            this.recursosInactivos = recursosInactivos;
            this.totalRegistros = totalRegistros;
        }
        
        public long getRecursosActivos() { return recursosActivos; }
        public long getRecursosInactivos() { return recursosInactivos; }
        public long getTotalRegistros() { return totalRegistros; }
        
        public double getPorcentajeActivos() {
            return totalRegistros > 0 ? (double) recursosActivos / totalRegistros * 100 : 0;
        }
    }
}
