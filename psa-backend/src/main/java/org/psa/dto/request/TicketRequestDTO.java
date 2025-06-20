package org.psa.dto.request;

public class TicketRequestDTO {
    
    private String nombre;
    private String prioridad;    // "HIGH_PRIORITY", "MEDIUM_PRIORITY", "LOW_PRIORITY"
    private String severidad;    // "LEVEL_1", "LEVEL_2", "LEVEL_3", "LEVEL_4"
    private String idCliente;
    private String idProducto;
    private String version;
    private String descripcion;
    private String idResponsable;
    
    // CONSTRUCTORES
    public TicketRequestDTO() {}
    
    public TicketRequestDTO(String nombre, String prioridad, String severidad, 
                           String idCliente, String idProducto, String version, 
                           String descripcion, String idResponsable) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.severidad = severidad;
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.version = version;
        this.descripcion = descripcion;
        this.idResponsable = idResponsable;
    }
    
    // GETTERS Y SETTERS
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
