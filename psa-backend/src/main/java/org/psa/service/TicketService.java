package org.psa.service;

import org.psa.dto.request.TicketRequestDTO;
import org.psa.dto.response.TareaBasicaDTO;
import org.psa.dto.response.TareaConTicketDTO;
import org.psa.dto.response.TicketResponseDTO;
import org.psa.model.Tarea;
import org.psa.model.Fase;
import org.psa.model.Ticket;
import org.psa.repository.TareaRepository;
import org.psa.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.ArrayList;

@Service
@Transactional
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private TareaRepository tareaRepository;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String SOPORTE_API_URL = "https://app-modulo-soporte-dev.onrender.com/support-module/ticket/data";
    
    // ========================================
    // SINCRONIZACIÓN CON API DE SOPORTE
    // ========================================
    
    /**
     * SINCRONIZAR tickets desde la API de soporte
     * Este método consulta la API externa y actualiza la BD local
     */
    @Transactional
    public String sincronizarTicketsDesdeAPI() {
        try {
            // 1. Llamar a la API de soporte
            TicketSoporteDTO[] ticketsArray = restTemplate.getForObject(SOPORTE_API_URL, TicketSoporteDTO[].class);
            
            if (ticketsArray == null) {
                return "No se pudieron obtener tickets de la API de soporte";
            }
            
            List<TicketSoporteDTO> ticketsSoporte = Arrays.asList(ticketsArray);
            
            // 2. Obtener tickets actuales en nuestra BD
            List<Ticket> ticketsActuales = ticketRepository.findAll();
            
            int nuevos = 0, actualizados = 0, eliminados = 0;
            
            // 3. Procesar tickets de soporte (crear/actualizar)
            for (TicketSoporteDTO ticketSoporte : ticketsSoporte) {
                Optional<Ticket> ticketExistente = ticketsActuales.stream()
                    .filter(t -> t.getId().equals(ticketSoporte.getId()))
                    .findFirst();
                
                if (ticketExistente.isPresent()) {
                    // Actualizar ticket existente
                    actualizarTicketExistente(ticketExistente.get(), ticketSoporte);
                    actualizados++;
                } else {
                    // Crear nuevo ticket
                    crearTicketDesdeSync(ticketSoporte);
                    nuevos++;
                }
            }
            
            // 4. Eliminar tickets que ya no existen en soporte
            List<Long> idsTicketsSoporte = ticketsSoporte.stream()
                .map(TicketSoporteDTO::getId)
                .collect(Collectors.toList());
            
            for (Ticket ticketLocal : ticketsActuales) {
                if (!idsTicketsSoporte.contains(ticketLocal.getId())) {
                    // Ticket eliminado en soporte, eliminar de nuestra BD
                    eliminarTicket(ticketLocal.getId());
                    eliminados++;
                }
            }
            
            return String.format("Sincronización completa: %d nuevos, %d actualizados, %d eliminados", 
                nuevos, actualizados, eliminados);
            
        } catch (RestClientException e) {
            throw new RuntimeException("Error al conectar con la API de soporte: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al sincronizar tickets: " + e.getMessage());
        }
    }
    
    /**
     * Crear ticket desde sincronización
     */
    private Ticket crearTicketDesdeSync(TicketSoporteDTO ticketSoporte) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketSoporte.getId()); // Usar el ID de soporte
        ticket.setNombre(ticketSoporte.getNombre());
        ticket.setPrioridad(mapearPrioridad(ticketSoporte.getPrioridad()));
        ticket.setSeveridad(mapearSeveridad(ticketSoporte.getSeveridad()));
        ticket.setIdCliente(ticketSoporte.getIdCliente());
        ticket.setIdProducto(ticketSoporte.getIdProducto());
        ticket.setVersion(ticketSoporte.getVersion());
        ticket.setDescripcion(ticketSoporte.getDescripcion());
        ticket.setIdResponsable(ticketSoporte.getIdResponsable());
        // Mantener estado actual si ya tiene tareas asignadas
        ticket.setEstado(Ticket.EstadoTicket.RECIBIDO);
        
        return ticketRepository.save(ticket);
    }
    
    /**
     * Actualizar ticket existente
     */
    private void actualizarTicketExistente(Ticket ticketLocal, TicketSoporteDTO ticketSoporte) {
        ticketLocal.setNombre(ticketSoporte.getNombre());
        ticketLocal.setPrioridad(mapearPrioridad(ticketSoporte.getPrioridad()));
        ticketLocal.setSeveridad(mapearSeveridad(ticketSoporte.getSeveridad()));
        ticketLocal.setIdCliente(ticketSoporte.getIdCliente());
        ticketLocal.setIdProducto(ticketSoporte.getIdProducto());
        ticketLocal.setVersion(ticketSoporte.getVersion());
        ticketLocal.setDescripcion(ticketSoporte.getDescripcion());
        ticketLocal.setIdResponsable(ticketSoporte.getIdResponsable());
        // NO actualizar estado si ya tiene tareas asignadas
        
        ticketRepository.save(ticketLocal);
    }
    
    /**
     * Mapear prioridad desde formato de soporte
     */
    private Ticket.PrioridadTicket mapearPrioridad(String prioridadSoporte) {
        try {
            return Ticket.PrioridadTicket.valueOf(prioridadSoporte);
        } catch (Exception e) {
            return Ticket.PrioridadTicket.MEDIUM_PRIORITY; // Default
        }
    }
    
    /**
     * Mapear severidad desde formato de soporte
     */
    private Ticket.SeveridadTicket mapearSeveridad(String severidadSoporte) {
        try {
            return Ticket.SeveridadTicket.valueOf(severidadSoporte);
        } catch (Exception e) {
            return Ticket.SeveridadTicket.LEVEL_3; // Default
        }
    }
    
    // DTO para recibir datos de la API de soporte
    public static class TicketSoporteDTO {
        private Long id;
        private String nombre;
        private String prioridad;
        private String severidad;
        private String idCliente;
        private String idProducto;
        private String version;
        private String descripcion;
        private String idResponsable;
        
        // Getters y setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getPrioridad() { return prioridad; }
        public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
        
        public String getSeveridad() { return severidad; }
        public void setSeveridad(String severidad) { this.severidad = severidad; }
        
        public String getIdCliente() { return idCliente; }
        public void setIdCliente(String idCliente) { this.idCliente = idCliente; }
        
        public String getIdProducto() { return idProducto; }
        public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
        
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        
        public String getIdResponsable() { return idResponsable; }
        public void setIdResponsable(String idResponsable) { this.idResponsable = idResponsable; }
    }
    
    // ========================================
    // MÉTODOS PRINCIPALES (sin crear ticket manual)
    // ========================================
    
    /**
     * 1. OBTENER TICKET - Para consultar estado
     */
    public TicketResponseDTO obtenerTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketId));
        return mapToResponseDTO(ticket);
    }
    
    /**
     * 2. ASIGNAR TAREAS AL TICKET - El core de la integración
     */
    public TicketResponseDTO asignarTareasAlTicket(Long ticketId, List<Long> tareaIds) {
        // Validar que el ticket existe
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketId));
        
        // Obtener las tareas
        List<Tarea> tareas = tareaRepository.findAllById(tareaIds);
        
        if (tareas.size() != tareaIds.size()) {
            throw new RuntimeException("Algunas tareas no fueron encontradas");
        }
        
        // Validar que las tareas no estén ya asignadas a otro ticket
        for (Tarea tarea : tareas) {
            if (tarea.getTicketAsociado() != null && !tarea.getTicketAsociado().getId().equals(ticketId)) {
                throw new RuntimeException("La tarea '" + tarea.getTitulo() + 
                    "' ya está asignada a otro ticket (ID: " + tarea.getTicketAsociado().getId() + ")");
            }
        }
        
        // Asignar el ticket a cada tarea
        for (Tarea tarea : tareas) {
            tarea.setTicketAsociado(ticket);
        }
        tareaRepository.saveAll(tareas);
        
        // Actualizar estado del ticket
        ticket.setEstado(Ticket.EstadoTicket.ASIGNADO);
        ticketRepository.save(ticket);
        
        return mapToResponseDTO(ticket);
    }
    
    /**
     * 4. DESASIGNAR TAREAS DEL TICKET
     */
    public TicketResponseDTO desasignarTareasDelTicket(Long ticketId, List<Long> tareaIds) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketId));
        
        List<Tarea> tareas = tareaRepository.findAllById(tareaIds);
        
        for (Tarea tarea : tareas) {
            if (tarea.getTicketAsociado() != null && tarea.getTicketAsociado().getId().equals(ticketId)) {
                tarea.setTicketAsociado(null);
            }
        }
        tareaRepository.saveAll(tareas);
        
        // Actualizar estado del ticket si no tiene más tareas
        long tareasRestantes = tareaRepository.countByTicketAsociado_Id(ticketId);
        if (tareasRestantes == 0) {
            ticket.setEstado(Ticket.EstadoTicket.RECIBIDO);
        }
        ticketRepository.save(ticket);
        
        return mapToResponseDTO(ticket);
    }
    
    /**
     * 5. OBTENER TODAS LAS TAREAS CON TICKETS - Para soporte
     */
    public List<TareaConTicketDTO> getTareasConTickets() {
        List<Tarea> tareasConTicket = tareaRepository.findByTicketAsociadoIsNotNull();
        return tareasConTicket.stream()
                .map(this::mapToTareaConTicketDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 6. OBTENER TICKETS SIN ASIGNAR - Para proyectos
     */
    public List<TicketResponseDTO> getTicketsSinAsignar() {
        List<Ticket> tickets = ticketRepository.findTicketsSinAsignar();
        return tickets.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * 7. ELIMINAR TICKET - Cuando soporte elimina un ticket
     */
    public void eliminarTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
            .orElseThrow(() -> new RuntimeException("Ticket no encontrado con ID: " + ticketId));
        
        // Desasociar todas las tareas primero
        List<Tarea> tareasAsociadas = tareaRepository.findByTicketAsociado_Id(ticketId);
        for (Tarea tarea : tareasAsociadas) {
            tarea.setTicketAsociado(null);
        }
        tareaRepository.saveAll(tareasAsociadas);
        
        // Eliminar el ticket
        ticketRepository.delete(ticket);
    }
    
    /**
     * 8. LISTAR TODOS LOS TICKETS
     */
    public List<TicketResponseDTO> listarTodosLosTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }
    
    // ================================
    // MÉTODOS DE MAPEO (DTOs)
    // ================================
    
    private TicketResponseDTO mapToResponseDTO(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setNombre(ticket.getNombre());
        dto.setPrioridad(ticket.getPrioridad().name());
        dto.setSeveridad(ticket.getSeveridad().name());
        dto.setEstado(ticket.getEstado().name());
        dto.setIdCliente(ticket.getIdCliente());
        dto.setIdProducto(ticket.getIdProducto());
        dto.setVersion(ticket.getVersion());
        dto.setDescripcion(ticket.getDescripcion());
        dto.setIdResponsable(ticket.getIdResponsable());
        dto.setFechaCreacion(ticket.getFechaCreacion());
        
        // Info de asignación
        dto.setAsignado(ticket.tieneTagreasAsignadas());
        dto.setCantidadTareasAsignadas(ticket.getCantidadTareasAsignadas());
        
        // Mapear tareas asignadas
        if (ticket.getTareasAsignadas() != null) {
            List<TareaBasicaDTO> tareasDTO = ticket.getTareasAsignadas().stream()
                    .map(this::mapToTareaBasicaDTO)
                    .collect(Collectors.toList());
            dto.setTareasAsignadas(tareasDTO);
        }
        
        return dto;
    }
    
    private TareaConTicketDTO mapToTareaConTicketDTO(Tarea tarea) {
        TareaConTicketDTO dto = new TareaConTicketDTO();
        
        // Info de la TAREA
        dto.setTareaId(tarea.getIdTarea());           // ✅ Correcto: getIdTarea()
        dto.setTareaTitulo(tarea.getTitulo());        
        dto.setTareaDescripcion(tarea.getDescripcion());
        dto.setTareaEstado(tarea.getEstado().name()); // ✅ Correcto: enum.name()
        dto.setTareaPrioridad(tarea.getPrioridad().name()); // ✅ Correcto: enum.name()
        dto.setTareaResponsable(tarea.getResponsable());
        
        // Info del PROYECTO - Tarea puede estar en múltiples fases
        if (tarea.getFases() != null && !tarea.getFases().isEmpty()) {
            Fase primeraFase = tarea.getFases().get(0); // Tomar la primera fase
            if (primeraFase != null && primeraFase.getProyecto() != null) {
                dto.setProyectoId(primeraFase.getProyecto().getIdProyecto()); // ✅ Correcto: getIdProyecto()
                dto.setProyectoNombre(primeraFase.getProyecto().getNombre());
            }
        }
        
        // Info del TICKET
        Ticket ticket = tarea.getTicketAsociado();
        if (ticket != null) {
            dto.setTicketId(ticket.getId());
            dto.setTicketNombre(ticket.getNombre());
            dto.setTicketPrioridad(ticket.getPrioridad().name());
            dto.setTicketSeveridad(ticket.getSeveridad().name());
            dto.setTicketEstado(ticket.getEstado().name());
            dto.setTicketIdCliente(ticket.getIdCliente());
            dto.setTicketIdProducto(ticket.getIdProducto());
            dto.setTicketIdResponsable(ticket.getIdResponsable());
            dto.setTicketFechaCreacion(ticket.getFechaCreacion());
        }
        
        return dto;
    }
    
    private TareaBasicaDTO mapToTareaBasicaDTO(Tarea tarea) {
        String proyectoNombre = "";
        if (tarea.getFases() != null && !tarea.getFases().isEmpty()) {
            Fase primeraFase = tarea.getFases().get(0);
            if (primeraFase != null && primeraFase.getProyecto() != null) {
                proyectoNombre = primeraFase.getProyecto().getNombre();
            }
        }
        
        return new TareaBasicaDTO(
            tarea.getIdTarea(),              // ✅ Correcto: getIdTarea() 
            tarea.getTitulo(),           
            tarea.getEstado().name(),        // ✅ Correcto: enum.name()
            tarea.getPrioridad().name(),     // ✅ Correcto: enum.name()
            tarea.getResponsable(),
            proyectoNombre
        );
    }
}
