package src.main.java.psa.modelo;

import java.time.LocalDate;

public class Tarea {
    private String idTarea;
    private String titulo;
    private String descripcion;
    private Estado estado;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private LocalDate fechaFinReal;
    private Prioridad prioridad;

    public enum Estado{
        PLANIFICADA,
        EN_CURSO,
        FINALIZADA
    }
    public enum Prioridad {
        BAJA,
        MEDIA,
        ALTA
    }

    public Tarea(String id, String titulo, String descripcion, Prioridad prioridad) {
        this.idTarea = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
    }

    public String getId() {
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

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public void planificarFechas(LocalDate inicio, LocalDate fin) {
        this.fechaInicio = inicio;
        this.fechaFinEstimada = fin;
        this.estado = Estado.PLANIFICADA;
    }

    public void activarTarea(){
        estado = Estado.EN_CURSO;
    }

    public void desactivarTarea(){
        this.fechaFinReal = LocalDate.now();
        this.estado = Estado.FINALIZADA;
    }


}
