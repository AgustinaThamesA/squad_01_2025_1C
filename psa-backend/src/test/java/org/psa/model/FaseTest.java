
package org.psa.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;


public class FaseTest {
    private static Fase fase;
    private static Fase fase2;
    private static Fase fase3;
    private static Tarea tarea1;
    private static Tarea tarea2;

    @BeforeAll
    public static void setUp() {
        fase = new Fase("Fase Test", 1);
        fase2 = new Fase("Fase 2", 2);
        fase3 = new Fase("Fase 3", 3);
        tarea1 = new Tarea("Tarea", "Tarea de prueba", Tarea.Prioridad.ALTA,
                "Desarrollador 1");
        tarea2 = new Tarea("Tarea 2", "Tarea de prueba 2", Tarea.Prioridad.BAJA,
                "Desarrollador 2");
    }

    @Test
    public void crearFaseTest() {
        Assertions.assertNotNull(fase);
        Assertions.assertEquals("Fase Test", fase.getNombre());
        Assertions.assertEquals(1, fase.getOrden());
    }

    @Test
    public void planificarFaseTest() {
        LocalDate inicio = LocalDate.now();
        LocalDate fin = LocalDate.now().plusDays(1);
        fase.planificarFechas(inicio, fin);
        Assertions.assertEquals(inicio, fase.getFechaInicio());
        Assertions.assertEquals(fin, fase.getFechaFinEstimada());
    }

    @Test
    public void agregarTareaTest() {
        int tareasSize = fase.getTareas().size();
        fase.agregarTarea(tarea1);
        assert fase.getTareas().contains(tarea1);
        assert fase.getTareas().size() == tareasSize + 1;
    }

    @Test
    public void agregarOtraTareaTest() {
        int tareasSize = fase.getTareas().size();
        fase.agregarTarea(tarea2);
        assert fase.getTareas().contains(tarea1);
        assert fase.getTareas().contains(tarea2);
        assert fase.getTareas().size() == tareasSize + 1;
    }

    @Test
    public void removerTareaTest() {
        Tarea tarea3 = new Tarea("Tarea 3", "Tarea test 3", Tarea.Prioridad.BAJA,
                "Desarrollador 3");
        Tarea tarea4 = new Tarea("Tarea 4", "Tarea test 4", Tarea.Prioridad.MEDIA,
                "Desarrollador 4");
        fase.getTareas().add(tarea3);
        fase.getTareas().add(tarea4);
        fase.removerTarea(tarea3);
        Assertions.assertFalse(fase.getTareas().contains(tarea3));
        assert fase.getTareas().contains(tarea4);
        assert fase.getTareas().size() == 1;
    }

    @Test
    public void calcularPorcentajeFaseVaciaAvanceTest() {
        assert fase2.calcularPorcentajeAvance() == 0.0;
    }

    @Test
    public void calcularPorcentajeFaseCompletaTest() {
        fase3.agregarTarea(tarea1);
        tarea1.completarTarea();
        assert fase3.calcularPorcentajeAvance() == 100.0;
    }

    @Test
    public void calcularPorcentajeFasePorLaMitadTest() {
        assert fase.calcularPorcentajeAvance() == 50.0;
    }

    @Test
    public void fasePendienteTest() {
        Assertions.assertEquals("Pendiente", fase2.getEstadoDescriptivo());
    }

    @Test
    public void faseEnProgresoTest() {
        Assertions.assertEquals("En Progreso", fase.getEstadoDescriptivo());
    }

    @Test
    public void faseCompletadaTest() {
        Fase fase4 = new Fase("Fase 4", 4);
        Tarea tarea5 = new Tarea("Tarea 5", "Tarea test 5", Tarea.Prioridad.BAJA,
                "Desarrollador 5");
        fase4.agregarTarea(tarea5);
        tarea5.completarTarea();
        Assertions.assertEquals("Completada", fase4.getEstadoDescriptivo());
    }




}
