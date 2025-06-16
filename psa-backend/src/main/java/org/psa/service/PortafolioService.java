package org.psa.service;

import org.psa.model.Proyecto;
import org.psa.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Service que reemplaza tu GerentePortafolio
 * Gestión centralizada de todos los proyectos
 */
@Service
public class PortafolioService {
    
    @Autowired
    private ProyectoRepository proyectoRepository;
    
    // ========================================
    // GESTIÓN DE PROYECTOS (tu lógica original de GerentePortafolio)
    // ========================================
    
    // Tu método original: crearProyecto
    public Proyecto crearProyecto(String nombre, String descripcion, String liderProyecto) {
        Proyecto proyecto = new Proyecto(nombre, descripcion, liderProyecto);
        return proyectoRepository.save(proyecto); // Se guarda automáticamente en PostgreSQL
    }
    
    // Tu método original: obtenerTodosLosProyectos
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll(); // Busca en PostgreSQL, no en memoria
    }
    
    // Tu método original: buscarProyectoPorId (adaptado para Long)
    public Proyecto buscarProyectoPorId(Long id) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(id);
        return proyecto.orElse(null);
    }
    
    // Tu método original: filtrarProyectosPorEstado
    public List<Proyecto> filtrarProyectosPorEstado(Proyecto.Estado estado) {
        return proyectoRepository.findByEstado(estado); // Query automática de Spring
    }
    
    // Tu método original: eliminarProyecto (adaptado para Long)
    public boolean eliminarProyecto(Long idProyecto) {
        if (proyectoRepository.existsById(idProyecto)) {
            proyectoRepository.deleteById(idProyecto);
            return true;
        }
        return false;
    }
    
    // Tu método original: calcularPorcentajeAvancePortafolio
    public double calcularPorcentajeAvancePortafolio() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        
        if (proyectos.isEmpty()) {
            return 0.0;
        }
        
        double sumaAvances = proyectos.stream()
            .mapToDouble(Proyecto::calcularPorcentajeAvance) // Tu método original
            .sum();
        
        return sumaAvances / proyectos.size();
    }
    
    // ========================================
    // ESTADÍSTICAS PARA DASHBOARD (tu lógica original)
    // ========================================
    
    // Tu método original: getTotalProyectos
    public int getTotalProyectos() {
        return (int) proyectoRepository.count();
    }
    
    // Tu método original: getProyectosActivos
    public int getProyectosActivos() {
        return proyectoRepository.findByEstado(Proyecto.Estado.ACTIVO).size();
    }
    
    // Tu método original: getTotalFases
    public int getTotalFases() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        return proyectos.stream().mapToInt(p -> p.getFases().size()).sum();
    }
    
    // Tu método original: getTotalTareas
    public int getTotalTareas() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        return proyectos.stream().mapToInt(Proyecto::getTotalTareas).sum(); // Tu método
    }
    
    // Tu método original: getRiesgosActivos
    public int getRiesgosActivos() {
        List<Proyecto> proyectos = proyectoRepository.findAll();
        return proyectos.stream().mapToInt(Proyecto::getRiesgosActivos).sum(); // Tu método
    }
    
    // Método combinado para dashboard (usando tus cálculos originales)
    public Object obtenerEstadisticasPortafolio() {
        return new Object() {
            public final int totalProyectos = getTotalProyectos();
            public final int proyectosActivos = getProyectosActivos();
            public final int totalFases = getTotalFases();
            public final int totalTareas = getTotalTareas();
            public final int riesgosActivos = getRiesgosActivos();
            public final double porcentajeAvancePortafolio = calcularPorcentajeAvancePortafolio();
        };
    }
    
    // Métodos adicionales que pueden ser útiles
    public List<Proyecto> buscarProyectosPorLider(String liderProyecto) {
        return proyectoRepository.findByLiderProyecto(liderProyecto);
    }
    
    public List<Proyecto> buscarProyectosPorNombre(String nombre) {
        return proyectoRepository.buscarPorNombre(nombre);
    }
    
    public long contarProyectosActivos() {
        return proyectoRepository.contarProyectosActivos();
    }
}
