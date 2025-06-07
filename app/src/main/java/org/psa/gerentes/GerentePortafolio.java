package org.psa.manager;

import org.psa.model.Proyecto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerentePortafolio {
    private List<Proyecto> proyectos;

    public GerentePortafolio() {
        this.proyectos = new ArrayList<>();
    }

    public Proyecto crearProyecto(String nombre, String descripcion, String liderProyecto) {
        Proyecto proyecto = new Proyecto(nombre, descripcion, liderProyecto);
        proyectos.add(proyecto);
        return proyecto;
    }

    public List<Proyecto> obtenerTodosLosProyectos() {
        return new ArrayList<>(proyectos);
    }

    public Proyecto buscarProyectoPorId(int id) {
        return proyectos.stream()
            .filter(p -> p.getIdProyecto() == id)
            .findFirst()
            .orElse(null);
    }

    public List<Proyecto> filtrarProyectosPorEstado(Proyecto.Estado estado) {
        return proyectos.stream()
            .filter(p -> p.getEstado() == estado)
            .collect(Collectors.toList());
    }

    public void eliminarProyecto(int idProyecto) {
        proyectos.removeIf(p -> p.getIdProyecto() == idProyecto);
    }

    public double calcularPorcentajeAvancePortafolio() {
        if (proyectos.isEmpty()) return 0.0;
        
        double sumaAvances = proyectos.stream()
            .mapToDouble(Proyecto::calcularPorcentajeAvance)
            .sum();
        
        return sumaAvances / proyectos.size();
    }

    // Estadísticas para el dashboard
    public int getTotalProyectos() { return proyectos.size(); }
    
    public int getProyectosActivos() {
        return (int) proyectos.stream()
            .filter(p -> p.getEstado() == Proyecto.Estado.ACTIVO)
            .count();
    }
    
    public int getTotalFases() {
        return proyectos.stream().mapToInt(p -> p.getFases().size()).sum();
    }
    
    public int getTotalTareas() {
        return proyectos.stream().mapToInt(Proyecto::getTotalTareas).sum();
    }
    
    public int getRiesgosActivos() {
        return proyectos.stream().mapToInt(Proyecto::getRiesgosActivos).sum();
    }
}
