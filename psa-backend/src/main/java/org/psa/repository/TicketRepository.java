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
    
    // Buscar tickets por cliente
    List<Ticket> findByIdCliente(String idCliente);
    
    // Buscar tickets por producto
    List<Ticket> findByIdProducto(String idProducto);
    
    // Tickets sin tareas asignadas (query más simple)
    @Query("SELECT t FROM Ticket t WHERE t.tareasAsignadas IS EMPTY")
    List<Ticket> findTicketsSinAsignar();
    
    // Tickets con tareas asignadas (query más simple)  
    @Query("SELECT t FROM Ticket t WHERE t.tareasAsignadas IS NOT EMPTY")
    List<Ticket> findTicketsConTareas();
}
