package org.psa.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

     // ENUMS INTERNOS (menos archivos!)
    public enum PrioridadTicket {
        HIGH_PRIORITY, MEDIUM_PRIORITY, LOW_PRIORITY
    }
    
    public enum SeveridadTicket {
        LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4
    }
    
    public enum EstadoTicket {
        RECIBIDO, ASIGNADO, EN_PROCESO, RESUELTO
    }
    
    // CAMPOS DE LA ENTIDAD
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_externo_id")
    private String ticketExternoId;  // El internalId de soporte

    @Column(name = "codigo")
    private String codigo;  // El código SAP-1, etc.

    // Getters y setters:
    public String getTicketExternoId() { return ticketExternoId; }
    public void setTicketExternoId(String ticketExternoId) { this.ticketExternoId = ticketExternoId; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    @Column(nullable = false)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadTicket prioridad;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeveridadTicket severidad;
    
    private String idCliente;
    private String idProducto;
    private String version;
    
    @Column(length = 1000)
    private String descripcion;
    
    private String idResponsable;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTicket estado;
    
    // RELACIÓN: Un ticket puede tener múltiples tareas asignadas
    @OneToMany(mappedBy = "ticketAsociado", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tarea> tareasAsignadas = new ArrayList<>();
    
    // CONSTRUCTORES
    public Ticket() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoTicket.RECIBIDO;
    }
    
    public Ticket(String nombre, PrioridadTicket prioridad, SeveridadTicket severidad, 
                  String idCliente, String idProducto, String version, 
                  String descripcion, String idResponsable) {
        this();
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
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public PrioridadTicket getPrioridad() { return prioridad; }
    public void setPrioridad(PrioridadTicket prioridad) { this.prioridad = prioridad; }
    
    public SeveridadTicket getSeveridad() { return severidad; }
    public void setSeveridad(SeveridadTicket severidad) { this.severidad = severidad; }
    
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
    
    public EstadoTicket getEstado() { return estado; }
    public void setEstado(EstadoTicket estado) { this.estado = estado; }
    
    public List<Tarea> getTareasAsignadas() { return tareasAsignadas; }
    public void setTareasAsignadas(List<Tarea> tareasAsignadas) { this.tareasAsignadas = tareasAsignadas; }
    
    // MÉTODOS ÚTILES
    public boolean tieneTareasAsignadas() {
        return tareasAsignadas != null && !tareasAsignadas.isEmpty();
    }
    
    public int getCantidadTareasAsignadas() {
        return tareasAsignadas != null ? tareasAsignadas.size() : 0;
    }
}
