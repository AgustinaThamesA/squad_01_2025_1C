package org.psa.controller;

import org.psa.dto.request.TicketRequestDTO;
import org.psa.dto.response.TareaConTicketDTO;
import org.psa.dto.response.TicketResponseDTO;
import org.psa.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {
    
    @Autowired
    private TicketService ticketService;
    
    // ========================================
    // 1. PARA SINCRONIZACIÓN: Obtener tickets desde API de soporte
    // ========================================
    @PostMapping("/sincronizar")
    public ResponseEntity<String> sincronizarTickets() {
        try {
            String resultado = ticketService.sincronizarTicketsDesdeAPI();
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error al sincronizar tickets: " + e.getMessage());
        }
    }
    
    // ========================================
    // 1. PARA SOPORTE: Consultar estado de ticket
    // ========================================
    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDTO> obtenerTicket(@PathVariable Long ticketId) {
        try {
            TicketResponseDTO response = ticketService.obtenerTicket(ticketId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ========================================
    // 2. PARA PROYECTOS: Asignar tareas existentes al ticket
    // ========================================
    @PostMapping("/{ticketId}/asignar-tareas")
    public ResponseEntity<TicketResponseDTO> asignarTareasAlTicket(
            @PathVariable Long ticketId,
            @RequestBody List<Long> tareaIds) {
        try {
            if (tareaIds == null || tareaIds.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            TicketResponseDTO response = ticketService.asignarTareasAlTicket(ticketId, tareaIds);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ========================================
    // 3. PARA PROYECTOS: Desasignar tareas del ticket
    // ========================================
    @PostMapping("/{ticketId}/desasignar-tareas")
    public ResponseEntity<TicketResponseDTO> desasignarTareasDelTicket(
            @PathVariable Long ticketId,
            @RequestBody List<Long> tareaIds) {
        try {
            if (tareaIds == null || tareaIds.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            TicketResponseDTO response = ticketService.desasignarTareasDelTicket(ticketId, tareaIds);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ========================================
    // 4. PARA SOPORTE: Ver todas las tareas asignadas a tickets
    // ========================================
    @GetMapping("/tareas-asignadas")
    public ResponseEntity<List<TareaConTicketDTO>> getTareasConTickets() {
        try {
            List<TareaConTicketDTO> response = ticketService.getTareasConTickets();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ========================================
    // 5. PARA PROYECTOS: Ver tickets sin asignar
    // ========================================
    @GetMapping("/sin-asignar")
    public ResponseEntity<List<TicketResponseDTO>> getTicketsSinAsignar() {
        try {
            List<TicketResponseDTO> response = ticketService.getTicketsSinAsignar();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ========================================
    // 6. PARA SOPORTE: Eliminar ticket (cuando lo eliminan desde soporte)
    // ========================================
    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> eliminarTicket(@PathVariable Long ticketId) {
        try {
            ticketService.eliminarTicket(ticketId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ========================================
    // 7. PARA PROYECTOS: Listar todos los tickets (VOS ves los que te enviaron)
    // ========================================
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> listarTodosLosTickets() {
        try {
            List<TicketResponseDTO> response = ticketService.listarTodosLosTickets();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ========================================
    // 8. PARA SOPORTE: Obtener tareas de un ticket específico (por ID externo)
    // ========================================
    @GetMapping("/externo/{ticketExternoId}/tareas")
    public ResponseEntity<List<TareaConTicketDTO>> getTareasDelTicketExterno(@PathVariable String ticketExternoId) {
        try {
            // Buscar ticket por su ID externo (internalId de soporte)
            List<TareaConTicketDTO> todasLasTareas = ticketService.getTareasConTickets();
            List<TareaConTicketDTO> tareasDelTicket = todasLasTareas.stream()
                .filter(tarea -> tarea.getTicketExternoId() != null && 
                               tarea.getTicketExternoId().equals(ticketExternoId))
                .toList();
            
            return ResponseEntity.ok(tareasDelTicket);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ========================================
    // 8b. PARA SOPORTE: Obtener ticket por ID externo  
    // ========================================
    @GetMapping("/externo/{ticketExternoId}")
    public ResponseEntity<TicketResponseDTO> obtenerTicketPorIdExterno(@PathVariable String ticketExternoId) {
        try {
            TicketResponseDTO response = ticketService.obtenerTicketPorIdExterno(ticketExternoId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // ========================================
    // 9. ENDPOINT DE PRUEBA/DEBUG
    // ========================================
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("TicketController funcionando correctamente");
    }
}
