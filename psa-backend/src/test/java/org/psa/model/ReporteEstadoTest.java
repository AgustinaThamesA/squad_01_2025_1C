package org.psa.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class ReporteEstadoTest {
    private static ReporteEstado reporteEstado;
    private static ReporteEstado reporteEstado2;

    @BeforeAll
    public static void setUpClass() {
        reporteEstado = new ReporteEstado(LocalDate.of(2025, 1, 1), 50,
                "Este es un reporte de estado");
        reporteEstado2 = new ReporteEstado(50, "Este es otro reporte de estado");
    }

    @Test
    public void crearReporteConFecha() {
        Assertions.assertEquals(LocalDate.of(2025, 1, 1), reporteEstado.getFecha());
        /* Al igual que paso con tarea, GerenteProyectoTest y ProyectoTest utilizan ReporteEstado y se ejecuta antes que
        este test, por lo que corresponde arrancar a contar desde el 3
        * */
        Assertions.assertEquals(50, reporteEstado.getPorcentajeAvance());
        Assertions.assertEquals("Este es un reporte de estado", reporteEstado.getComentarios());
    }

    @Test
    public void crearReporteSinFecha() {
        Assertions.assertEquals(LocalDate.now(), reporteEstado2.getFecha());
    }

    @Test
    public void setPorcentajeAvanceTest() {
        double porcentajeAvance = 90;
        reporteEstado.setPorcentajeAvance(porcentajeAvance);
        Assertions.assertEquals(90, reporteEstado.getPorcentajeAvance());
    }

    @Test
    public void setComentariosTest() {
        String comentarioNuevo = "Nuevo comentario";
        reporteEstado2.setComentarios(comentarioNuevo);
        Assertions.assertEquals(comentarioNuevo, reporteEstado2.getComentarios());
    }

    @Test
    public void getFechaFormateadaTest() {
        String fechaFormateada = "01/01/2025";
        Assertions.assertEquals(fechaFormateada, reporteEstado.getFechaFormateada());
    }
}