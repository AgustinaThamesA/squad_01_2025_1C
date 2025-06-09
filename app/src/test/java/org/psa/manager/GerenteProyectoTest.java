package src.test.java.org.psa.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.psa.model.Proyecto;
import org.psa.model.Fase;
import org.psa.model.Riesgo;
import org.psa.model.Tarea;
import org.psa.manager.GerenteProyecto;

import java.time.LocalDate;

public class GerenteProyectoTest {
    private static GerenteProyecto gerenteProyecto;
    private static Proyecto proyecto;
    private static Fase fase;
    private Fase fase2;
    private Riesgo riesgo;
    private static Tarea tarea;
    private Tarea tarea2;

    @BeforeAll
    public static void setUp() {
        gerenteProyecto = new GerenteProyecto();
        proyecto = new Proyecto("Proyecto test", "Proyecto de ejemplo",
                "Desarrollador X");
        tarea = new Tarea("Tarea Test", "Tarea ejemplo", Tarea.Prioridad.BAJA,
                "Desarrollador 1");
        fase = new Fase("Fase Test", 1);
    }

    @Test
    public void planificarProyecto() {
        LocalDate fechaInicio = LocalDate.of(2025, 1, 1);
        LocalDate fechaFin = LocalDate.of(2025, 2, 1);
        gerenteProyecto.planificarProyecto(proyecto, fechaInicio, fechaFin);
        Assertions.assertEquals(fechaInicio, proyecto.getFechaInicio());
        Assertions.assertEquals(fechaFin, proyecto.getFechaFinEstimada());
    }

    @Test
    public void crearFaseTest() {
        String nombre = "Fase ejemplo";
        int orden = 1;
        fase2 = gerenteProyecto.crearFase(proyecto, nombre, orden);
        Assertions.assertEquals(1, proyecto.getFases().size());
        assert proyecto.getFases().contains(fase2);
        Assertions.assertEquals(nombre, fase2.getNombre());
        Assertions.assertEquals(orden, fase2.getOrden());
    }

    @Test
    public void planificarFase() {
        LocalDate fechaInicio = LocalDate.of(2025, 1, 1);
        LocalDate fechaFin = LocalDate.of(2025, 2, 1);
        fase2 = gerenteProyecto.crearFase(proyecto, "Fase", 1);
        gerenteProyecto.planificarFase(fase2, fechaInicio, fechaFin);
        Assertions.assertEquals(fechaInicio, fase2.getFechaInicio());
        Assertions.assertEquals(fechaFin, fase2.getFechaFinEstimada());
    }

    @Test
    public void crearTareaTest() {
        tarea2 = gerenteProyecto.crearTarea("Tarea Test 2", "Tarea ejemplo 2", Tarea.Prioridad.BAJA,
                "Desarrollador 2");
        Assertions.assertNotNull(tarea2);
        Assertions.assertEquals("Tarea Test 2", tarea2.getTitulo());
        Assertions.assertEquals("Tarea ejemplo 2", tarea2.getDescripcion());
        Assertions.assertEquals(Tarea.Prioridad.BAJA, tarea2.getPrioridad());
        Assertions.assertEquals("Desarrollador 2", tarea2.getResponsable());
    }

    @Test
    public void planificarTareaTest() {
        LocalDate fechaInicio = LocalDate.of(2025, 1, 1);
        LocalDate fechaFin = LocalDate.of(2025, 2, 1);
        gerenteProyecto.planificarTarea(tarea, fechaInicio, fechaFin);
        Assertions.assertEquals(fechaInicio, tarea.getFechaInicio());
        Assertions.assertEquals(fechaFin, tarea.getFechaFinEstimada());
    }

    @Test
    public void asignarTareaAFaseTest() {
        gerenteProyecto.asignarTareaAFase(tarea, fase);
        assert tarea.getFases().contains(fase);
        assert fase.getTareas().contains(tarea);
    }

    @Test
    public void iniciarTareaTest() {
        gerenteProyecto.iniciarTarea(tarea);
        Assertions.assertEquals(Tarea.Estado.EN_PROGRESO, tarea.getEstado());
    }

    @Test
    public void pausarProyectoTest() {
        gerenteProyecto.pausarProyecto(proyecto);
        Assertions.assertEquals(Proyecto.Estado.PAUSADO, proyecto.getEstado());
    }

    @Test
    public void terminarProyectoTest() {
        gerenteProyecto.cerrarProyecto(proyecto);
        Assertions.assertEquals(Proyecto.Estado.CERRADO, proyecto.getEstado());
    }


}
