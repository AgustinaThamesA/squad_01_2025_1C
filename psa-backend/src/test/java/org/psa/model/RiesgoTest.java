package org.psa.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RiesgoTest {
    private static Riesgo riesgo;

    @BeforeAll
    public static void setUp() {
        riesgo = new Riesgo("Riesgo test", Riesgo.Probabilidad.BAJA, Riesgo.Impacto.BAJO);
    }

    @Test
    public void crearRiesgoTest() {
        Assertions.assertNotNull(riesgo);
        Assertions.assertEquals("Riesgo test", riesgo.getDescripcion());
        Assertions.assertEquals(Riesgo.Probabilidad.BAJA, riesgo.getProbabilidad());
        Assertions.assertEquals(Riesgo.Impacto.BAJO, riesgo.getImpacto());
        Assertions.assertEquals(Riesgo.Estado.ACTIVO, riesgo.getEstado());
    }

    @Test
    public void calcularNivelRiesgoTest() {
        Assertions.assertEquals(1, riesgo.calcularNivelRiesgo());
    }

    @Test
    public void calcularNivelRiesgoTest2() {
        Riesgo riesgo2 = new Riesgo("Riesgo 2", Riesgo.Probabilidad.ALTA, Riesgo.Impacto.ALTO);
        Assertions.assertEquals(9, riesgo2.calcularNivelRiesgo());
    }

    @Test
    public void noEsRiesgoAltoTest() {
        Assertions.assertFalse(riesgo.esRiesgoAlto());
    }

    @Test
    public void esRiesgoAltoTest() {
        Riesgo riesgo3 = new Riesgo("Riesgo 3", Riesgo.Probabilidad.ALTA, Riesgo.Impacto.MEDIO);
        assert (riesgo3.esRiesgoAlto());
    }
}