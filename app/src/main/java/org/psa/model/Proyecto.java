package org.psa.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    private static int contadorProyecto = 1;
    private int idProyecto;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private LocalDate fechaFinReal;
    private Estado estado;
    private String liderProyecto;
    private List<Fase> fases;
    private List<Riesgo> riesgos;
    private List<ReporteEstado> reportes;

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

    public Proyecto(String nombre, String descripcion, String liderProyecto) {
        this.idProyecto = contadorProyecto++;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = Estado.ACTIVO;
        this.liderProyecto = liderProyecto;
        this.fases = new ArrayList<>();
        this.riesgos = new ArrayList<>();
        this.reportes = new ArrayList<>();
    }

    // Getters
    public int getIdProyecto() {
        return idProyecto;
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

    // Setters y métodos de negocio
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
    }

    public void agregarRiesgo(Riesgo riesgo) {
        this.riesgos.add(riesgo);
    }

    public void agregarReporte(ReporteEstado reporte) {
        this.reportes.add(reporte);
    }

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
}
