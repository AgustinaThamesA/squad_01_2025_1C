package org.psa.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyecto;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private LocalDate fechaFinReal;
    
    @Enumerated(EnumType.STRING)
    private Estado estado;
    
    private String liderProyecto;
    
    // ✅ SOLUCIÓN: Agregar @JsonManagedReference para controlar la serialización
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("proyecto-fases") // ✅ Este lado SÍ se serializa
    private List<Fase> fases;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("proyecto-riesgos") // ✅ Este lado SÍ se serializa
    private List<Riesgo> riesgos;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference("proyecto-reportes") // ✅ Este lado SÍ se serializa
    private List<ReporteEstado> reportes;

    // Enum Estado (sin cambios)
    public enum Estado {
        ACTIVO("Activo"),
        PAUSADO("Pausado"),
        CERRADO("Cerrado");

        private final String displayName;
        Estado(String displayName) {
            this.displayName = displayName;
        }
        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructor por defecto (requerido por JPA)
    public Proyecto() {
        this.fases = new ArrayList<>();
        this.riesgos = new ArrayList<>();
        this.reportes = new ArrayList<>();
    }

    // Tu constructor original
    public Proyecto(String nombre, String descripcion, String liderProyecto) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = Estado.ACTIVO;
        this.liderProyecto = liderProyecto;
    }

    // Getters
    public Long getIdProyecto() {
        return idProyecto;
    }
    
    public void setIdProyecto(Long idProyecto) {
        this.idProyecto = idProyecto;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
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
    public Estado getEstado() {
        return estado;
    }
    public String getLiderProyecto() {
        return liderProyecto;
    }
    public List<Fase> getFases() {
        return fases;
    }
    public List<Riesgo> getRiesgos() {
        return riesgos;
    }
    public List<ReporteEstado> getReportes() {
        return reportes;
    }

    // Setters y métodos de negocio (SIN CAMBIOS)
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    public void setLiderProyecto(String liderProyecto) {
        this.liderProyecto = liderProyecto;
    }

    public void planificarFechas(LocalDate inicio, LocalDate fin) {
        this.fechaInicio = inicio;
        this.fechaFinEstimada = fin;
        if (this.estado != Estado.CERRADO) {
            this.estado = Estado.ACTIVO;
        }
    }

    public void cerrarProyecto() {
        this.estado = Estado.CERRADO;
        this.fechaFinReal = LocalDate.now();
    }

    public void pausarProyecto() {
        this.estado = Estado.PAUSADO;
    }

    public void agregarFase(Fase fase) {
        this.fases.add(fase);
        fase.setProyecto(this);
    }

    public void agregarRiesgo(Riesgo riesgo) {
        this.riesgos.add(riesgo);
        riesgo.setProyecto(this);
    }

    public void agregarReporte(ReporteEstado reporte) {
        this.reportes.add(reporte);
        reporte.setProyecto(this);
    }

    // Métodos de cálculo (SIN CAMBIOS)
    public double calcularPorcentajeAvance() {
        if (fases.isEmpty()) {
            return 0.0;
        }
        
        double totalAvance = 0.0;
        for (Fase fase : fases) {
            totalAvance += fase.calcularPorcentajeAvance();
        }
        
        return totalAvance / fases.size();
    }

    public int getTotalTareas() {
        return fases.stream().mapToInt(fase -> fase.getTareas().size()).sum();
    }

    public int getRiesgosActivos() {
        return (int) riesgos.stream()
            .filter(riesgo -> riesgo.getEstado() == Riesgo.Estado.ACTIVO)
            .count();
    }
    
    @Override
    public String toString() {
        return "Proyecto{" +
                "idProyecto=" + idProyecto +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", liderProyecto='" + liderProyecto + '\'' +
                '}';
    }
}
