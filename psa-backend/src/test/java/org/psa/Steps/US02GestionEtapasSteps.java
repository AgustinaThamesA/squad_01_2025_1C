package org.psa.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.psa.model.Fase;
import org.psa.model.Proyecto;
import org.junit.Assert;

public class US02GestionEtapasSteps {

    private Proyecto proyecto;
    private Fase faseActual;
    private String etapaActual;
    private String mensajeError;
    private boolean operacionExitosa;

    public US02GestionEtapasSteps() {
        this.proyecto = new Proyecto("Proyecto Test", "Descripción de prueba", "Juan Pérez");

        // Creamos fases manualmente con orden
        proyecto.agregarFase(new Fase("Inicio", 1));
        proyecto.agregarFase(new Fase("Análisis", 2));
        proyecto.agregarFase(new Fase("Diseño", 3));
        proyecto.agregarFase(new Fase("Desarrollo", 4));
        proyecto.agregarFase(new Fase("Testing", 5));
        proyecto.agregarFase(new Fase("Despliegue", 6));
        proyecto.agregarFase(new Fase("Transición", 7));
    }

    @Given("que el proyecto está en etapa {string}")
    public void queElProyectoEstaEnEtapa(String etapa) {
        this.etapaActual = etapa;
        this.faseActual = proyecto.getFases().stream()
                .filter(f -> f.getNombre().equalsIgnoreCase(etapa))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Etapa no encontrada: " + etapa));
    }

    @When("marco la etapa como {string}")
    public void marcoLaEtapaComo(String nuevaEtapa) {
        cambiarEtapa(nuevaEtapa);
    }

    @When("intento cambiar directamente a {string}")
    public void intentoCambiarDirectamenteA(String etapaDestino) {
        cambiarEtapa(etapaDestino);
    }

    private void cambiarEtapa(String destino) {
        this.operacionExitosa = false;
        this.mensajeError = null;

        Fase nuevaFase = proyecto.getFases().stream()
                .filter(f -> f.getNombre().equalsIgnoreCase(destino))
                .findFirst()
                .orElse(null);

        if (nuevaFase == null) {
            this.mensajeError = "Etapa no encontrada: " + destino;
            return;
        }

        int diferenciaOrden = nuevaFase.getOrden() - faseActual.getOrden();

        if (diferenciaOrden == 1) {
            this.faseActual = nuevaFase;
            this.etapaActual = destino;
            this.operacionExitosa = true;
        } else if (diferenciaOrden > 1) {
            this.mensajeError = "No se pueden saltear etapas. Primero debe completarse la etapa actual.";
        } else {
            this.mensajeError = "No se puede retroceder o permanecer en la misma etapa.";
        }
    }

    @Then("la etapa del proyecto debe actualizarse correctamente")
    public void laEtapaDelProyectoDebeActualizarseCorrectamente() {
        Assert.assertTrue("La operación debería haber sido exitosa", operacionExitosa);
        Assert.assertNull("No debería haber mensajes de error", mensajeError);
        Assert.assertNotNull("La fase actual debe estar definida", faseActual);
        Assert.assertEquals("La etapa actual debe coincidir con la configurada", etapaActual, faseActual.getNombre());
    }

    @Then("el sistema debe mostrar un error indicando que no se puede saltear etapas")
    public void elSistemaDebeMostrarUnErrorIndicandoQueNoSePuedeSaltearEtapas() {
        Assert.assertFalse("La operación no debería haber sido exitosa", operacionExitosa);
        Assert.assertNotNull("Debe haber un mensaje de error", mensajeError);
        Assert.assertTrue("El mensaje debe indicar que no se pueden saltear etapas",
                mensajeError.toLowerCase().contains("saltear etapas"));
    }

    @Then("el sistema debe mostrar un error indicando que no se puede retroceder o permanecer en la misma etapa")
    public void elSistemaDebeMostrarUnErrorIndicandoQueNoSePuedeRetroceder() {
        Assert.assertFalse("La operación no debería haber sido exitosa", operacionExitosa);
        Assert.assertNotNull("Debe haber un mensaje de error", mensajeError);
        Assert.assertTrue("El mensaje debe indicar que no se puede retroceder o quedarse en la misma etapa",
                mensajeError.toLowerCase().contains("retroceder") || mensajeError.toLowerCase().contains("misma etapa"));
    }
}
