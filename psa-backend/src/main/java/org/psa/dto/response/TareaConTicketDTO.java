package org.psa.dto.response;

import java.time.LocalDateTime;

/**
 * DTO para enviar a SOPORTE - Tareas que tienen tickets asignados
 */
public class TareaConTicketDTO {
    
    // Info de la TAREA
    private Long tareaId;
    private String tareaTitulo;
    private String tareaDescripcion;
    private String tareaEstado;
    private String tareaPrioridad;
    private String tareaResponsable;
    private Long proyectoId;
    private String proyectoNombre;
    
    // Info del TICKET  
    private Long ticketId;
    private String ticketNombre;
    private String ticketPrioridad;
    private String ticketSeveridad;
    private String ticketEstado;
    private String ticketIdCliente;
    private String ticketIdProducto;
    private String ticketIdResponsable;
    private LocalDateTime ticketFechaCreacion;
    
    // CONSTRUCTORES
    public TareaConTicketDTO() {}
    
    // GETTERS Y SETTERS
    public Long getTareaId() { return tareaId; }
    public void setTareaId(Long tareaId) { this.tareaId = tareaId; }
    
    public String getTareaTitulo() { return tareaTitulo; }
    public void setTareaTitulo(String tareaTitulo) { this.tareaTitulo = tareaTitulo; }
    
    public String getTareaDescripcion() { return tareaDescripcion; }
    public void setTareaDescripcion(String tareaDescripcion) { this.tareaDescripcion = tareaDescripcion; }
    
    public String getTareaEstado() { return tareaEstado; }
    public void setTareaEstado(String tareaEstado) { this.tareaEstado = tareaEstado; }
    
    public String getTareaPrioridad() { return tareaPrioridad; }
    public void setTareaPrioridad(String tareaPrioridad) { this.tareaPrioridad = tareaPrioridad; }
    
    public String getTareaResponsable() { return tareaResponsable; }
    public void setTareaResponsable(String tareaResponsable) { this.tareaResponsable = tareaResponsable; }
    
    public Long getProyectoId() { return proyectoId; }
    public void setProyectoId(Long proyectoId) { this.proyectoId = proyectoId; }
    
    public String getProyectoNombre() { return proyectoNombre; }
    public void setProyectoNombre(String proyectoNombre) { this.proyectoNombre = proyectoNombre; }
    
    public Long getTicketId() { return ticketId; }
    public void setTicketId(Long ticketId) { this.ticketId = ticketId; }
    
    public String getTicketNombre() { return ticketNombre; }
    public void setTicketNombre(String ticketNombre) { this.ticketNombre = ticketNombre; }
    
    public String getTicketPrioridad() { return ticketPrioridad; }
    public void setTicketPrioridad(String ticketPrioridad) { this.ticketPrioridad = ticketPrioridad; }
    
    public String getTicketSeveridad() { return ticketSeveridad; }
    public void setTicketSeveridad(String ticketSeveridad) { this.ticketSeveridad = ticketSeveridad; }
    
    public String getTicketEstado() { return ticketEstado; }
    public void setTicketEstado(String ticketEstado) { this.ticketEstado = ticketEstado; }
    
    public String getTicketIdCliente() { return ticketIdCliente; }
    public void setTicketIdCliente(String ticketIdCliente) { this.ticketIdCliente = ticketIdCliente; }
    
    public String getTicketIdProducto() { return ticketIdProducto; }
    public void setTicketIdProducto(String ticketIdProducto) { this.ticketIdProducto = ticketIdProducto; }
    
    public String getTicketIdResponsable() { return ticketIdResponsable; }
    public void setTicketIdResponsable(String ticketIdResponsable) { this.ticketIdResponsable = ticketIdResponsable; }
    
    public LocalDateTime getTicketFechaCreacion() { return ticketFechaCreacion; }
    public void setTicketFechaCreacion(LocalDateTime ticketFechaCreacion) { this.ticketFechaCreacion = ticketFechaCreacion; }
}
