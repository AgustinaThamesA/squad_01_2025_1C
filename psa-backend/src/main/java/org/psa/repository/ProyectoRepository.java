// ========================================
// ProyectoRepository.java
// ========================================
package org.psa.repository;

import org.psa.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    
    // Métodos automáticos que Spring Boot genera:
    // - findAll() - obtener todos los proyectos
    // - findById(Long id) - obtener proyecto por ID
    // - save(Proyecto) - guardar/actualizar proyecto
    // - deleteById(Long id) - eliminar proyecto
    
    // Métodos personalizados basados en tu lógica
    List<Proyecto> findByEstado(Proyecto.Estado estado);
    List<Proyecto> findByLiderProyecto(String liderProyecto);
    
    @Query("SELECT p FROM Proyecto p WHERE p.nombre LIKE %?1%")
    List<Proyecto> buscarPorNombre(String nombre);
    
    @Query("SELECT COUNT(p) FROM Proyecto p WHERE p.estado = 'ACTIVO'")
    long contarProyectosActivos();
}
