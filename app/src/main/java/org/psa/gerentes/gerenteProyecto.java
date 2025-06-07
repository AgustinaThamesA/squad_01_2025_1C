package src.main.gerentes;

import src.main.java.psa.modelo.Proyecto;
import src.main.java.psa.modelo.Tarea;

import java.time.LocalDate;

public class gerenteProyecto {
    public void planificarProyecto(Proyecto proyecto, LocalDate fechaInicio, LocalDate fechaFin) {
        proyecto.planificarFechas(fechaInicio, fechaFin);
    }

    public void crearTarea(Proyecto proyecto, String titulo, String descripcion, Tarea.Prioridad prioridad) {
        Tarea tarea = new Tarea(String.valueOf(proyecto.getId()) + "." +
                String.valueOf(proyecto.getTareas().size()+1), titulo, descripcion, prioridad);
        proyecto.agregarTarea(tarea);
    }

    public void planificarTarea(Tarea tarea, LocalDate fechaInicio, LocalDate fechaFin) {
        tarea.planificarFechas(fechaInicio, fechaFin);
    }

}
