package src.test.java.org.psa.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.psa.model.Proyecto;
import org.psa.model.Fase;
import org.psa.model.Riesgo;
import org.psa.model.ReporteEstado;
import org.psa.model.Tarea;

import java.time.LocalDate;


public class ProyectoTest {
    private static Proyecto proyecto;
    private static Fase fase;

    @BeforeAll
    public static void setup() {
        String nombre = "Proyecto test";
        String descripcion = "Este es un proyecto de prueba";
        String lider = "Persona random";
        proyecto = new Proyecto(nombre, descripcion, lider);
        fase = new Fase("Fase test", 1);
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

    @Test
    public void setLiderProyectoTest() {
        String nuevoLider = "Nuevo Lider";
        proyecto.setLiderProyecto(nuevoLider);
        Assertions.assertEquals(nuevoLider, proyecto.getLiderProyecto());
    }

    @Test
    public void calcularPorcentajeAvanceProyectoSinFases() {
        double esperado = 0.0;
        Assertions.assertEquals(esperado, proyecto.calcularPorcentajeAvance());
    }

    @Test
    public void agregarFaseTest() {
        proyecto.agregarFase(fase);
        Assertions.assertEquals(1, proyecto.getFases().size());
        assert proyecto.getFases().contains(fase);
    }

    @Test
    public void agregarRiesgoTest() {
        Riesgo riesgo = new Riesgo("Riesgo prueba", Riesgo.Probabilidad.ALTA, Riesgo.Impacto.BAJO);
        proyecto.agregarRiesgo(riesgo);
        Assertions.assertEquals(1, proyecto.getRiesgos().size());
        assert proyecto.getRiesgos().contains(riesgo);
    }

    @Test
    public void agregarReporteTest() {
        ReporteEstado reporteEstado = new ReporteEstado(40, "Reporte ejemplo");
        proyecto.agregarReporte(reporteEstado);
        Assertions.assertEquals(1, proyecto.getReportes().size());
        assert proyecto.getReportes().contains(reporteEstado);
    }

    @Test
    public void calcularPorcentajeAvanceFaseConTarea() {
        Tarea tarea = new Tarea("Tarea prueba", "Tarea de ejemplo", Tarea.Prioridad.BAJA,
                "Desarrollador 1");
        fase.agregarTarea(tarea);
        tarea.completarTarea();
        double esperado = 100;
        Assertions.assertEquals(esperado, proyecto.calcularPorcentajeAvance());

    }

}
