package src.main.java.psa.proyectos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Proyecto {
    private static int contadorProyecto = 1;
    private int id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFinEstimada;
    private LocalDate fechaFinReal;
    private Estado estado;
    private Etapa etapa;
    private List<Tarea> tareas;
    private String liderProyecto;

    enum Estado {
        PLANIFICADO,
        EN_CURSO,
        FINALIZADO
    }

    enum Etapa {
        INICIO,
        DESARROLLO,
        TRANSICION
    }

    Proyecto(String nombre, String descripcion, String liderProyecto) {
        // en el futuro debería ser obtener el id del último proyecto creado y sumarle 1
        this.id = contadorProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = Estado.PLANIFICADO;
        this.etapa = Etapa.INICIO;
        this.liderProyecto = liderProyecto;
        this.tareas = new ArrayList<>();
        contadorProyecto++;
    }

    public int getId() {
        return id;
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

    public Etapa getEtapa() {
        return etapa;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public String getLiderProyecto() {
        return liderProyecto;
    }

    public void setLiderProyecto(String liderProyecto) {
        this.liderProyecto = liderProyecto;
    }

    public void planificarFechas(LocalDate inicio, LocalDate fin){
        this.fechaInicio = inicio;
        this.fechaFinEstimada = fin;
        this.estado = Estado.EN_CURSO;
    }

    public void finalizarProyecto(){
        this.estado = Estado.FINALIZADO;
        this.fechaFinReal = LocalDate.now();
    }

    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

}
