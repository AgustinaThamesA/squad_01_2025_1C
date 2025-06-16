package org.psa.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tareas")
public class Tarea {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarea; // Cambio: int → Long
    
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
    
    // Relación n:m con Fases (tu lógica original mantenida)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "tarea_fase",
        joinColumns = @JoinColumn(name = "tarea_id"),
        inverseJoinColumns = @JoinColumn(name = "fase_id")
    )
    private List<Fase> fases;

    // Tus enums originales (SIN CAMBIOS - perfectos)
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

    // Constructor por defecto (requerido por JPA)
    public Tarea() {
        this.fases = new ArrayList<>();
    }

    // Tu constructor original (lógica mantenida, sin contador estático)
    public Tarea(String titulo, String descripcion, Prioridad prioridad, String responsable) {
        this();
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.responsable = responsable;
        this.estado = Estado.PENDIENTE;
    }

    // Getters (adaptados para Long)
    public Long getIdTarea() { // Cambio: int → Long
        return idTarea;
    }
    public String getTitulo() {
        return titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public Estado getEstado() {
        return estado;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public LocalDate getFechaFinEstimada() {
        return fechaFinEstimada;
    }
    public LocalDate getFechaFinReal() {
        return fechaFinReal;
    }
    public Prioridad getPrioridad() {
        return prioridad;
    }
    public String getResponsable() {
        return responsable;
    }
    public List<Fase> getFases() {
        return fases;
    }

    // Setters y métodos de negocio (SIN CAMBIOS - tu lógica se mantiene)
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }
    public void setResponsable(String responsable) {
        this.responsable = responsable;
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

    // Métodos para relación n:m con fases (SIN CAMBIOS - perfectos)
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
}
