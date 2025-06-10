package src.test.java.org.psa.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.psa.model.Proyecto;
import org.psa.model.Fase;
import org.psa.model.Riesgo;
import org.psa.model.Tarea;
import org.psa.manager.GerenteProyecto;
import org.psa.model.ReporteEstado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GerenteProyectoTest {
    private static GerenteProyecto gerenteProyecto;
    private static Proyecto proyecto;
    private static Fase fase;
    private Fase fase2;
    private static Riesgo riesgo;
    private Riesgo riesgo2;
    private static Tarea tarea;
    private Tarea tarea2;
    private static Tarea tarea3;
    private ReporteEstado reporteEstado;

    @BeforeAll
    public static void setUp() {
        gerenteProyecto = new GerenteProyecto();
        proyecto = new Proyecto("Proyecto test", "Proyecto de ejemplo",
                "Desarrollador X");
        tarea = new Tarea("Tarea Test", "Tarea ejemplo", Tarea.Prioridad.BAJA,
                "Desarrollador 1");
        tarea3 = new Tarea("Tarea Test 3", "Tarea ejemplo 3", Tarea.Prioridad.ALTA,
                "Desarrollador 3");
        fase = new Fase("Fase Test", 1);
        riesgo = new Riesgo("Riesgo Test", Riesgo.Probabilidad.ALTA, Riesgo.Impacto.BAJO);
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
    public void agregarTareaAMultiplesFasesTest() {
        fase2 = new Fase("Fase 2", 2);
        List<Fase> fases;
        fases = new ArrayList<>();
        fases.add(fase);
        fases.add(fase2);
        gerenteProyecto.asignarTareaAMultiplesFases(tarea, fases);
        assert tarea.getFases().contains(fase);
        assert tarea.getFases().contains(fase2);
        assert fase.getTareas().contains(tarea);
        assert fase2.getTareas().contains(tarea);
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

    @Test
    public void crearRiesgoTest() {
        riesgo2 = gerenteProyecto.crearRiesgo(proyecto, "Riesgo Test", Riesgo.Probabilidad.BAJA,
                Riesgo.Impacto.BAJO);
        Assertions.assertNotNull(riesgo2);
        Assertions.assertEquals("Riesgo Test", riesgo2.getDescripcion());
        Assertions.assertEquals(Riesgo.Probabilidad.BAJA, riesgo2.getProbabilidad());
        Assertions.assertEquals(Riesgo.Impacto.BAJO, riesgo2.getImpacto());
        assert proyecto.getRiesgos().contains(riesgo2);
    }

    @Test
    public void mitigarRiesgoTest() {
        gerenteProyecto.mitigarRiesgo(riesgo);
        Assertions.assertEquals(Riesgo.Estado.MITIGADO, riesgo.getEstado());
    }

    @Test
    public void generarReporteTest() {
        reporteEstado = gerenteProyecto.generarReporte(proyecto, "Reporte Test");
        Assertions.assertNotNull(reporteEstado);
        assert proyecto.getReportes().contains(reporteEstado);
        Assertions.assertEquals("Reporte Test", reporteEstado.getComentarios());
    }

    @Test
    public void generarReporteTest2() {
        reporteEstado = gerenteProyecto.generarReporte(proyecto, LocalDate.of(2025, 1, 1),
                45, "Reporte Test2");
        Assertions.assertNotNull(reporteEstado);
        assert proyecto.getReportes().contains(reporteEstado);
        Assertions.assertEquals("Reporte Test2", reporteEstado.getComentarios());
        Assertions.assertEquals("01/01/2025", reporteEstado.getFechaFormateada());
    }

    @Test
    public void obtenerRiesgosAltosTest() {
        riesgo2 = gerenteProyecto.crearRiesgo(proyecto, "Riesgo 2", Riesgo.Probabilidad.ALTA,
                Riesgo.Impacto.BAJO);
        Riesgo riesgo3 = gerenteProyecto.crearRiesgo(proyecto, "Riesgo 3", Riesgo.Probabilidad.ALTA,
                Riesgo.Impacto.ALTO);
        var riesgosAltos = gerenteProyecto.obtenerRiesgosAltos(proyecto);
        assert !riesgosAltos.isEmpty();
        assert riesgosAltos.contains(riesgo3);
    }

    @Test
    public void obtenerTareasMultiFaseTest() {
        fase2 = new Fase("Fase 2", 2);
        proyecto.agregarFase(fase);
        proyecto.agregarFase(fase2);
        fase.agregarTarea(tarea);
        fase.agregarTarea(tarea3);
        fase2.agregarTarea(tarea);
        fase2.agregarTarea(tarea3);

        var tareasMultifase = gerenteProyecto.obtenerTareasMultifase(proyecto);
        assert tareasMultifase.size() == 2;
        assert tareasMultifase.contains(tarea);
        assert tareasMultifase.contains(tarea3);
    }
}
