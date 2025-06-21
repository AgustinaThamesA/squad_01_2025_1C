package org.psa.dto.response;

/**
 * DTO básico de tarea para mostrar en listados de tickets
 */
public class TareaBasicaDTO {
    
    private Long id;
    private String titulo;
    private String estado;
    private String prioridad;
    private String responsable;
    private String proyectoNombre;
    
    // CONSTRUCTORES
    public TareaBasicaDTO() {}
    
    public TareaBasicaDTO(Long id, String titulo, String estado, String prioridad, String responsable, String proyectoNombre) {
        this.id = id;
        this.titulo = titulo;
        this.estado = estado;
        this.prioridad = prioridad;
        this.responsable = responsable;
        this.proyectoNombre = proyectoNombre;
    }
    
    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    
    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }
    
    public String getProyectoNombre() { return proyectoNombre; }
    public void setProyectoNombre(String proyectoNombre) { this.proyectoNombre = proyectoNombre; }
}
