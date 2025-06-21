package org.psa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recursos")
public class Recurso {
    
    @Id
    @JsonProperty("id") // Para que Spring Boot mapee automáticamente desde la API
    private String id;
    
    @Column(nullable = false)
    @JsonProperty("nombre")
    private String nombre;
    
    @Column(nullable = false)
    @JsonProperty("apellido") 
    private String apellido;
    
    @Column(nullable = false, unique = true)
    @JsonProperty("dni")
    private String dni;
    
    @JsonProperty("rolId")
    private String rolId;
    
    // Campos internos (no vienen de la API)
    private LocalDateTime fechaSincronizacion;
    private Boolean activo = true;
    
    // Constructor vacío
    public Recurso() {}
    
    // Constructor completo
    public Recurso(String id, String nombre, String apellido, String dni, String rolId) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.rolId = rolId;
        this.fechaSincronizacion = LocalDateTime.now();
    }
    
    // Getters y Setters (todos)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    
    public String getRolId() { return rolId; }
    public void setRolId(String rolId) { this.rolId = rolId; }
    
    public LocalDateTime getFechaSincronizacion() { return fechaSincronizacion; }
    public void setFechaSincronizacion(LocalDateTime fechaSincronizacion) { 
        this.fechaSincronizacion = fechaSincronizacion; 
    }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    // Métodos útiles
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    public void marcarComoSincronizado() {
        this.fechaSincronizacion = LocalDateTime.now();
    }
}
