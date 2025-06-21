// ========================================
// RiesgoRepository.java
// ========================================
package org.psa.repository;

import org.psa.model.Riesgo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RiesgoRepository extends JpaRepository<Riesgo, Long> {
    
    // Riesgos por estado
    List<Riesgo> findByEstado(Riesgo.Estado estado);
    
    // Riesgos de un proyecto
    List<Riesgo> findByProyectoIdProyecto(Long proyectoId);
    
    // Riesgos por probabilidad
    List<Riesgo> findByProbabilidad(Riesgo.Probabilidad probabilidad);
    
    // Riesgos por impacto
    List<Riesgo> findByImpacto(Riesgo.Impacto impacto);
    
    // Riesgos altos (tu lógica original: >= 6 puntos)
    @Query("SELECT r FROM Riesgo r WHERE " +
           "(CASE r.probabilidad WHEN 'BAJA' THEN 1 WHEN 'MEDIA' THEN 2 WHEN 'ALTA' THEN 3 END) * " +
           "(CASE r.impacto WHEN 'BAJO' THEN 1 WHEN 'MEDIO' THEN 2 WHEN 'ALTO' THEN 3 END) >= 6")
    List<Riesgo> findRiesgosAltos();
}
