// ========================================
// TareaRepository.java
// ========================================
package org.psa.repository;

import org.psa.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import org.psa.model.Ticket.EstadoTicket;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {
    
    // Tareas por estado
    List<Tarea> findByEstado(Tarea.Estado estado);
    
    // Tareas por responsable
    List<Tarea> findByResponsable(String responsable);
    
    // Tareas por prioridad
    List<Tarea> findByPrioridad(Tarea.Prioridad prioridad);

    List<Tarea> findByTicketAsociadoIsNotNull();
    List<Tarea> findByTicketAsociado_Id(Long ticketId);
    List<Tarea> findByTicketAsociado_Estado(EstadoTicket estadoTicket);
    long countByTicketAsociado_Id(Long ticketId);
    
    // Tareas vencidas (tu lógica original)
    @Query("SELECT t FROM Tarea t WHERE t.fechaFinEstimada < ?1 AND t.estado != 'COMPLETADA'")
    List<Tarea> findTareasVencidas(LocalDate fechaActual);
    
    // Tareas multifase (tu lógica original)
    @Query("SELECT t FROM Tarea t WHERE SIZE(t.fases) > 1")
    List<Tarea> findTareasMultifase();
    
    // Tareas de un proyecto específico
    @Query("SELECT DISTINCT t FROM Tarea t JOIN t.fases f WHERE f.proyecto.idProyecto = ?1")
    List<Tarea> findByProyectoId(Long proyectoId);
}
