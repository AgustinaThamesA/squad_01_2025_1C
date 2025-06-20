package org.psa.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class TicketResponseDTO {
    
    private Long id;
    private String nombre;
    private String prioridad;
    private String severidad;
    private String estado;
    private String idCliente;
    private String idProducto;
    private String version;
    private String descripcion;
    private String idResponsable;
    private LocalDateTime fechaCreacion;
    
    // Info de asignación
    private boolean asignado;
    private int cantidadTareasAsignadas;
    private List<TareaBasicaDTO> tareasAsignadas;
    
    // CONSTRUCTORES
    public TicketResponseDTO() {}
    
    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    
    public String getSeveridad() { return severidad; }
    public void setSeveridad(String severidad) { this.severidad = severidad; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
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
    
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    
    public boolean isAsignado() { return asignado; }
    public void setAsignado(boolean asignado) { this.asignado = asignado; }
    
    public int getCantidadTareasAsignadas() { return cantidadTareasAsignadas; }
    public void setCantidadTareasAsignadas(int cantidadTareasAsignadas) { this.cantidadTareasAsignadas = cantidadTareasAsignadas; }
    
    public List<TareaBasicaDTO> getTareasAsignadas() { return tareasAsignadas; }
    public void setTareasAsignadas(List<TareaBasicaDTO> tareasAsignadas) { this.tareasAsignadas = tareasAsignadas; }
}
