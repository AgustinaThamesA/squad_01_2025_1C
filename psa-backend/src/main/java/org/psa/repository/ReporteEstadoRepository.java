// ========================================
// ReporteEstadoRepository.java
// ========================================
package org.psa.repository;

import org.psa.model.ReporteEstado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReporteEstadoRepository extends JpaRepository<ReporteEstado, Long> {
    
    // Reportes de un proyecto
    List<ReporteEstado> findByProyectoIdProyectoOrderByFechaDesc(Long proyectoId);
    
    // Reportes por fecha
    List<ReporteEstado> findByFecha(LocalDate fecha);
    
    // Reportes en un rango de fechas
    List<ReporteEstado> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    // Último reporte de un proyecto
    @Query("SELECT r FROM ReporteEstado r WHERE r.proyecto.idProyecto = ?1 ORDER BY r.fecha DESC LIMIT 1")
    ReporteEstado findUltimoReportePorProyecto(Long proyectoId);
}
