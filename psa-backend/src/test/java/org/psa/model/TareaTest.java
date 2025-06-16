package org.psa.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class TareaTest {
    private static Tarea tarea;

    @BeforeAll
    public static void setUpClass() {
        String titulo = "Tarea test";
        String descripcion = "Tarea de ejemplo";
        Tarea.Prioridad prioridad = Tarea.Prioridad.BAJA;
        String responsable = "Desarrollador X";
        tarea = new Tarea(titulo, descripcion, prioridad, responsable);
    }

    @Test
    public void crearTareaTest() {
        Assertions.assertNotNull(tarea);
        Assertions.assertEquals("Tarea test", tarea.getTitulo());
        Assertions.assertEquals("Tarea de ejemplo", tarea.getDescripcion());
        Assertions.assertEquals(Tarea.Prioridad.BAJA, tarea.getPrioridad());
        Assertions.assertEquals("Desarrollador X", tarea.getResponsable());
        /* como los tests se corren en orden, para este punto ya se crearon 12 tareas en GerentePortafolioTest,
        GerenteProyectoTest, FaseTest y ProyectoTest, por lo que el contador de tareas no arranca en 1
        */
    }

    @Test
    public void nuevaTareaAumentaIdTest() {
        Tarea tarea2 = new Tarea("Tarea test 2", "Otra tarea", Tarea.Prioridad.ALTA,
                "Otra persona");
    }

    @Test
    public void planificarTareaTest() {
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now().plusDays(10);
        tarea.planificarFechas(inicio, fin);
        Assertions.assertEquals(inicio, tarea.getFechaInicio());
        Assertions.assertEquals(fin, tarea.getFechaFinEstimada());
        Assertions.assertEquals(Tarea.Estado.PENDIENTE, tarea.getEstado());
    }

    @Test
    public void iniciarTareaTest() {
        tarea.iniciarTarea();
        Assertions.assertEquals(Tarea.Estado.EN_PROGRESO, tarea.getEstado());
    }

    @Test
    public void tareaVencidaTest() {
        LocalDate inicio = LocalDate.now().minusDays(10);
        LocalDate fin = LocalDate.now().minusDays(1);
        tarea.planificarFechas(inicio, fin);
        assert (tarea.estaVencida());
    }

    @Test
    public void tareaNoVencidaTest() {
        LocalDate inicio = LocalDate.now().minusDays(1);
        LocalDate fin = LocalDate.now().plusDays(1);
        tarea.planificarFechas(inicio, fin);
        Assertions.assertFalse(tarea.estaVencida());
    }

}