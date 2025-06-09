package src.test.java.org.psa.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.psa.manager.GerentePortafolio;
import org.psa.model.Proyecto;
import org.psa.model.Fase;
import org.psa.model.Tarea;

public class GerentePortafolioTest {
    private static GerentePortafolio gerentePortafolio;
    private static Proyecto proyecto1;
    private static Proyecto proyecto2;
    private static Proyecto proyecto3;
    private Proyecto proyecto4;

    @BeforeAll
    public static void setUp() {
        gerentePortafolio = new GerentePortafolio();
        proyecto1 = gerentePortafolio.crearProyecto("Proyecto test", "Proyecto ejemplo", "Desarrollador 1");
        proyecto2 =  gerentePortafolio.crearProyecto("Proyecto test 2", "Proyecto ejemplo 2",
                "Desarrollador 2");
        proyecto3 =  gerentePortafolio.crearProyecto("Proyecto test 3", "Proyecto ejemplo 3",
                "Desarrollador 3");
    }

    @Test
    public void crearProyectoTest() {
        String nombre = "Proyecto test";
        String descripcion = "Proyecto ejemplo";
        String responsable = "Desarrollador 1";
        Assertions.assertNotNull(proyecto1);
        Assertions.assertEquals(nombre, proyecto1.getNombre());
        Assertions.assertEquals(descripcion, proyecto1.getDescripcion());
        Assertions.assertEquals(responsable, proyecto1.getLiderProyecto());
    }

    @Test
    public void obtenerProyectosTest() {
        var proyectos = gerentePortafolio.obtenerTodosLosProyectos();
        Assertions.assertNotNull(proyectos);
        Assertions.assertEquals(3, proyectos.size());
        assert proyectos.contains(proyecto1);
        assert proyectos.contains(proyecto2);
        assert proyectos.contains(proyecto3);
    }

    @Test
    public void totalProyectosTest() {
        assert gerentePortafolio.getTotalProyectos() == 3;
    }

    @Test
    public void filtrarProyectosPausadosTest() {
        proyecto1.pausarProyecto();
        var proyectosPausados = gerentePortafolio.filtrarProyectosPorEstado(Proyecto.Estado.PAUSADO);
        assert proyectosPausados.size() == 1;
        assert proyectosPausados.contains(proyecto1);
        assert !proyectosPausados.contains(proyecto2);
        assert !proyectosPausados.contains(proyecto3);
    }

    @Test
    public void filtrarProyectosCerradosTest() {
        proyecto2.cerrarProyecto();
        var proyectosCerrados = gerentePortafolio.filtrarProyectosPorEstado(Proyecto.Estado.CERRADO);
        assert proyectosCerrados.size() == 1;
        assert !proyectosCerrados.contains(proyecto1);
        assert proyectosCerrados.contains(proyecto2);
        assert !proyectosCerrados.contains(proyecto3);
    }

    @Test
    public void filtrarProyectosActivosTest() {
        proyecto1.pausarProyecto();
        proyecto2.cerrarProyecto();
        var proyectosActivos = gerentePortafolio.filtrarProyectosPorEstado(Proyecto.Estado.ACTIVO);
        assert proyectosActivos.size() == 1;
        assert !proyectosActivos.contains(proyecto1);
        assert !proyectosActivos.contains(proyecto2);
        assert proyectosActivos.contains(proyecto3);
    }

    @Test
    public void buscarProyectoPorIdTest() {
        int id = proyecto1.getIdProyecto();
        var proyecto = gerentePortafolio.buscarProyectoPorId(id);
        Assertions.assertEquals(proyecto1, proyecto);
    }

    @Test
    public void eliminarProyectoTest() {
        proyecto4 = gerentePortafolio.crearProyecto("Proyecto 4", "Proyecto 4 ejemplo",
                "Desarrollador 4");
        int id = proyecto4.getIdProyecto();
        gerentePortafolio.eliminarProyecto(id);
        Assertions.assertNull(gerentePortafolio.buscarProyectoPorId(id));
    }

    @Test
    public void calcularPorcentajePortafolioCompletoTest() {
        Fase fase1 = new Fase("Fase 1", 1);
        Fase fase2 = new Fase("Fase 2", 2);
        Fase fase3 = new Fase("Fase 3", 3);
        Tarea tarea1 = new Tarea("Tarea 1", "Tarea ejemplo 1", Tarea.Prioridad.BAJA,
                "Desarrollador 1");
        Tarea tarea2 = new Tarea("Tarea 2", "Tarea ejemplo 2", Tarea.Prioridad.MEDIA,
                "Desarrollador 2");
        Tarea tarea3 = new Tarea("Tarea 3", "Tarea ejemplo 3", Tarea.Prioridad.ALTA,
                "Desarrollador 3");
        fase1.agregarTarea(tarea1);
        tarea1.completarTarea();
        fase2.agregarTarea(tarea2);
        tarea2.completarTarea();
        fase3.agregarTarea(tarea3);
        tarea3.completarTarea();
        proyecto1.agregarFase(fase1);
        proyecto2.agregarFase(fase2);
        proyecto3.agregarFase(fase3);
        double esperado = 100;
        Assertions.assertEquals(esperado, gerentePortafolio.calcularPorcentajeAvancePortafolio());
    }

    @Test
    public void calcularPorcentajePortafolioIncompletoTest() {
        Fase fase = new Fase("Fase", 1);
        Tarea tarea = new Tarea("Tarea test", "Tarea ejemplo", Tarea.Prioridad.BAJA,
                "Desarrollador X");
        fase.agregarTarea(tarea);
        tarea.completarTarea();
        proyecto1.agregarFase(fase);
        double esperado = (double) 100 / 3;
        Assertions.assertEquals(esperado, gerentePortafolio.calcularPorcentajeAvancePortafolio());
    }


}
