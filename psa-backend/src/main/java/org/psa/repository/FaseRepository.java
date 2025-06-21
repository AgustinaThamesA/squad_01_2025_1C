// ========================================
// FaseRepository.java
// ========================================
package org.psa.repository;

import org.psa.model.Fase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FaseRepository extends JpaRepository<Fase, Long> {
    
    // Obtener fases de un proyecto ordenadas por orden
    List<Fase> findByProyectoIdProyectoOrderByOrden(Long proyectoId);
    
    // AGREGADO: Contar fases de un proyecto (necesario para calcular el próximo orden)
    int countByProyectoIdProyecto(Long proyectoId);
    
    // Obtener fases por nombre
    List<Fase> findByNombreContainingIgnoreCase(String nombre);
    
    @Query("SELECT f FROM Fase f WHERE f.proyecto.idProyecto = ?1")
    List<Fase> buscarPorProyecto(Long proyectoId);
}