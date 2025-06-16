package org.psa.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyecto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProyecto; // Cambio: int → Long (estándar JPA)
    
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
    
    // Relaciones JPA (mantiene tu lógica bidireccional)
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Fase> fases;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Riesgo> riesgos;
    
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReporteEstado> reportes;

    // Enum Estado (sin cambios - perfecto como está)
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

    // Tu constructor original (sin cambios en lógica)
    public Proyecto(String nombre, String descripcion, String liderProyecto) {
        this(); // Llama al constructor por defecto
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = Estado.ACTIVO;
        this.liderProyecto = liderProyecto;
    }

    // Getters (adaptados para Long)
    public Long getIdProyecto() { // Cambio: int → Long
        return idProyecto;
    }
    
    // Setter para ID (necesario para JPA)
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

    // Setters y métodos de negocio (SIN CAMBIOS - tu lógica se mantiene)
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
        fase.setProyecto(this); // Mantener consistencia bidireccional
    }

    public void agregarRiesgo(Riesgo riesgo) {
        this.riesgos.add(riesgo);
        riesgo.setProyecto(this); // Mantener consistencia bidireccional
    }

    public void agregarReporte(ReporteEstado reporte) {
        this.reportes.add(reporte);
        reporte.setProyecto(this); // Mantener consistencia bidireccional
    }

    // Tus métodos de cálculo (SIN CAMBIOS - perfectos)
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
