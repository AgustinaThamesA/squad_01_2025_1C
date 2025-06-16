package org.psa.controller;

import org.psa.model.Proyecto;
import org.psa.service.PortafolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portafolio")
@CrossOrigin(origins = "http://localhost:3000")
public class PortafolioController {

    @Autowired
    private PortafolioService portafolioService;

    // GET /api/portafolio/proyectos - Obtener todos los proyectos
    @GetMapping("/proyectos")
    public List<Proyecto> obtenerTodosLosProyectos() {
        return portafolioService.obtenerTodosLosProyectos();
    }

    // POST /api/portafolio/proyectos - Crear nuevo proyecto
    @PostMapping("/proyectos")
    public Proyecto crearProyecto(@RequestBody CrearProyectoRequest request) {
        return portafolioService.crearProyecto(
            request.getNombre(), 
            request.getDescripcion(), 
            request.getLiderProyecto()
        );
    }

    // GET /api/portafolio/proyectos/activos - Filtrar proyectos activos
    @GetMapping("/proyectos/activos")
    public List<Proyecto> obtenerProyectosActivos() {
        return portafolioService.filtrarProyectosPorEstado(Proyecto.Estado.ACTIVO);
    }

    // GET /api/portafolio/proyectos/pausados - Filtrar proyectos pausados
    @GetMapping("/proyectos/pausados")
    public List<Proyecto> obtenerProyectosPausados() {
        return portafolioService.filtrarProyectosPorEstado(Proyecto.Estado.PAUSADO);
    }

    // GET /api/portafolio/proyectos/cerrados - Filtrar proyectos cerrados
    @GetMapping("/proyectos/cerrados")
    public List<Proyecto> obtenerProyectosCerrados() {
        return portafolioService.filtrarProyectosPorEstado(Proyecto.Estado.CERRADO);
    }

    // DELETE /api/portafolio/proyectos/1 - Eliminar proyecto
    @DeleteMapping("/proyectos/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        boolean eliminado = portafolioService.eliminarProyecto(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/portafolio/estadisticas - Estadísticas para dashboard
    @GetMapping("/estadisticas")
    public Object obtenerEstadisticasPortafolio() {
        return portafolioService.obtenerEstadisticasPortafolio();
    }

    // GET /api/portafolio/avance - Porcentaje de avance del portafolio
    @GetMapping("/avance")
    public Object obtenerAvancePortafolio() {
        return new Object() {
            public final double porcentajeAvance = portafolioService.calcularPorcentajeAvancePortafolio();
        };
    }

    // GET /api/portafolio/proyectos/lider/{nombre} - Buscar por líder
    @GetMapping("/proyectos/lider/{nombre}")
    public List<Proyecto> buscarProyectosPorLider(@PathVariable String nombre) {
        return portafolioService.buscarProyectosPorLider(nombre);
    }
}

// Clase auxiliar para requests
class CrearProyectoRequest {
    private String nombre;
    private String descripcion;
    private String liderProyecto;
    
    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getLiderProyecto() { return liderProyecto; }
    public void setLiderProyecto(String liderProyecto) { this.liderProyecto = liderProyecto; }
}
