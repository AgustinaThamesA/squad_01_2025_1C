package org.psa.repository;

import org.psa.model.Ticket.EstadoTicket;
import org.psa.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    // Buscar tickets por estado
    List<Ticket> findByEstado(EstadoTicket estado);
    
    // Buscar tickets por responsable
    List<Ticket> findByIdResponsable(String idResponsable);
    
    // Tickets que tienen al menos una tarea asignada
    @Query("SELECT t FROM Ticket t WHERE SIZE(t.tareasAsignadas) > 0")
    List<Ticket> findTicketsConTareas();
    
    // Tickets sin tareas asignadas (para mostrar en proyectos)
    @Query("SELECT t FROM Ticket t WHERE SIZE(t.tareasAsignadas) = 0")
    List<Ticket> findTicketsSinAsignar();
    
    // Buscar tickets por cliente
    List<Ticket> findByIdCliente(String idCliente);
    
    // Buscar tickets por producto
    List<Ticket> findByIdProducto(String idProducto);
}
