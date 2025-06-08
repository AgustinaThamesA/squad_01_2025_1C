package src.test.java.org.psa.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psa.model.Proyecto;

import java.time.LocalDate;


public class ProyectoTest {
    private Proyecto proyecto;

    @BeforeEach
    public void setup() {
        String nombre = "Proyecto test";
        String descripcion = "Este es un proyecto de prueba";
        String lider = "Persona random";
        proyecto = new Proyecto(nombre, descripcion, lider);
    }

    @Test
    public void crearProyectoTest() {
        Assertions.assertEquals("Proyecto test", proyecto.getNombre());
        Assertions.assertEquals("Este es un proyecto de prueba", proyecto.getDescripcion());
        Assertions.assertEquals("Persona random", proyecto.getLiderProyecto());
    }

    @Test
    public void planificarFechasTest() {
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now().plusDays(10);
        proyecto.planificarFechas(inicio, fin);
        Assertions.assertEquals(inicio, proyecto.getFechaInicio());
        Assertions.assertEquals(fin, proyecto.getFechaFinEstimada());
    }

    @Test
    public void cerrarProyectoTest() {
        proyecto.cerrarProyecto();
        Assertions.assertEquals(LocalDate.now(), proyecto.getFechaFinReal());
        Assertions.assertEquals(Proyecto.Estado.CERRADO, proyecto.getEstado());
    }

    @Test
    public void pausarProyectoTest() {
        proyecto.pausarProyecto();
        Assertions.assertEquals(Proyecto.Estado.PAUSADO, proyecto.getEstado());
    }



}
