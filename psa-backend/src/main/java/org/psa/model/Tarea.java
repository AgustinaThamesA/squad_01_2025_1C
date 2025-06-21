package org.psa.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tareas")
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarea;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    private Estado estado;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private LocalDate fechaFinReal;
    
    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;
    
    private String responsable;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_recurso_id")
    private Recurso responsableRecurso;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tarea_fase",
        joinColumns = @JoinColumn(name = "tarea_id"),
        inverseJoinColumns = @JoinColumn(name = "fase_id")
    )
    @JsonManagedReference
    private List<Fase> fases;

    // ✅ AQUÍ ESTÁ EL PROBLEMA - AGREGAR @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    @JsonBackReference  // ✅ ESTO ROMPE EL CICLO
    private Ticket ticketAsociado;

    public Ticket getTicketAsociado() { return ticketAsociado; }
    public void setTicketAsociado(Ticket ticketAsociado) { this.ticketAsociado = ticketAsociado; }

    public enum Estado {
        PENDIENTE("Pendiente"),
        EN_PROGRESO("En Progreso"),
        COMPLETADA("Completada");
        
        private final String displayName;
        
        Estado(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }

    public enum Prioridad {
        BAJA("Baja"),
        MEDIA("Media"),
        ALTA("Alta");
        
        private final String displayName;
        
        Prioridad(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }

    public Tarea() {
        this.fases = new ArrayList<>();
    }

    // ✅ CONSTRUCTOR ORIGINAL (para compatibilidad)
    public Tarea(String titulo, String descripcion, Prioridad prioridad, String responsable) {
        this();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.responsable = responsable;
        this.estado = Estado.PENDIENTE;
    }
    
    // ✅ NUEVO CONSTRUCTOR CON RECURSO
    public Tarea(String titulo, String descripcion, Prioridad prioridad, Recurso responsableRecurso) {
        this();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.responsableRecurso = responsableRecurso;
        this.estado = Estado.PENDIENTE;
        // Sincronizar con campo legacy
        if (responsableRecurso != null) {
            this.responsable = responsableRecurso.getNombreCompleto();
        }
    }

    // Getters existentes (sin cambios)
    public Long getIdTarea() { return idTarea; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public Estado getEstado() { return estado; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFinEstimada() { return fechaFinEstimada; }
    public LocalDate getFechaFinReal() { return fechaFinReal; }
    public Prioridad getPrioridad() { return prioridad; }
    public List<Fase> getFases() { return fases; }
    
    // ✅ GETTERS/SETTERS PARA RECURSO
    public Recurso getResponsableRecurso() { return responsableRecurso; }
    
    public void setResponsableRecurso(Recurso responsableRecurso) {
        this.responsableRecurso = responsableRecurso;
        // Sincronizar con campo legacy
        if (responsableRecurso != null) {
            this.responsable = responsableRecurso.getNombreCompleto();
        } else {
            this.responsable = null;
        }
    }
    
    // ✅ GETTER HÍBRIDO PARA COMPATIBILIDAD
    public String getResponsable() {
        // Priorizar recurso si existe, sino retornar string legacy
        if (responsableRecurso != null) {
            return responsableRecurso.getNombreCompleto();
        }
        return responsable;
    }
    
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    // Setters y métodos de negocio (SIN CAMBIOS)
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public void planificarFechas(LocalDate inicio, LocalDate fin) {
        this.fechaInicio = inicio;
        this.fechaFinEstimada = fin;
        this.estado = Estado.PENDIENTE;
    }

    public void iniciarTarea() {
        this.estado = Estado.EN_PROGRESO;
    }

    public void completarTarea() {
        this.fechaFinReal = LocalDate.now();
        this.estado = Estado.COMPLETADA;
    }

    // Métodos para relación n:m con fases (SIN CAMBIOS)
    public void agregarFase(Fase fase) {
        if (!this.fases.contains(fase)) {
            this.fases.add(fase);
        }
    }

    public void removerFase(Fase fase) {
        this.fases.remove(fase);
    }

    public boolean esMultifase() {
        return fases.size() > 1;
    }

    public boolean estaVencida() {
        return fechaFinEstimada != null &&
               LocalDate.now().isAfter(fechaFinEstimada) &&
               estado != Estado.COMPLETADA;
    }

    public String obtenerNombresFases() {
        return fases.stream()
            .map(fase -> fase.getNombre().split(":")[0].trim())
            .reduce((f1, f2) -> f1 + ", " + f2)
            .orElse("");
    }
    
    // ✅ MÉTODOS DE UTILIDAD PARA RECURSOS
    public String getResponsableNombreCompleto() {
        return getResponsable(); // Usa el getter híbrido
    }
    
    public String getResponsableRecursoId() {
        return responsableRecurso != null ? responsableRecurso.getId() : null;
    }
    
    public boolean tieneResponsableAsignado() {
        return responsableRecurso != null || (responsable != null && !responsable.isBlank());
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
