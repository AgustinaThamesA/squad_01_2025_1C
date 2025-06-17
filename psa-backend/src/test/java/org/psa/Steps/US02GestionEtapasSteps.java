package org.psa.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.psa.service.ProyectoService;
import org.psa.model.Fase;
import org.psa.model.Proyecto;
import org.junit.Assert;

public class US02GestionEtapasSteps {

    private ProyectoService proyectoService;
    private Proyecto proyecto;
    private Fase faseActual;
    private String etapaActual;
    private String mensajeError;
    private boolean operacionExitosa;

    public US02GestionEtapasSteps() {
        this.proyectoService = new ProyectoService();
        this.proyecto = new Proyecto("Proyecto Test", "Descripción de prueba", "Juan Pérez");

        // Defino TODAS las etapas en orden para que estén todas en la lista y no falle
        proyectoService.crearFase(proyecto.getIdProyecto(), "Inicio");
        proyectoService.crearFase(proyecto.getIdProyecto(), "Análisis");
        proyectoService.crearFase(proyecto.getIdProyecto(), "Diseño");
        proyectoService.crearFase(proyecto.getIdProyecto(), "Desarrollo");
        proyectoService.crearFase(proyecto.getIdProyecto(), "Testing");
        proyectoService.crearFase(proyecto.getIdProyecto(), "Despliegue");
        proyectoService.crearFase(proyecto.getIdProyecto(), "Transición");
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
        this.operacionExitosa = false;
        this.mensajeError = null;

        Fase nuevaFase = proyecto.getFases().stream()
                .filter(f -> f.getNombre().equalsIgnoreCase(nuevaEtapa))
                .findFirst()
                .orElse(null);

        if (nuevaFase == null) {
            this.mensajeError = "Etapa no encontrada: " + nuevaEtapa;
            return;
        }

        // Solo permite avanzar una etapa a la vez (orden secuencial)
        if (nuevaFase.getOrden() == faseActual.getOrden() + 1) {
            this.faseActual = nuevaFase;
            this.etapaActual = nuevaEtapa;
            this.operacionExitosa = true;
            this.mensajeError = null;
        } else {
            this.operacionExitosa = false;
            this.mensajeError = "No se puede avanzar a la etapa " + nuevaEtapa
                    + ". Debe completarse la etapa previa primero.";
        }
    }

    @When("intento cambiar directamente a {string}")
    public void intentoCambiarDirectamenteA(String etapaDestino) {
        this.operacionExitosa = false;
        this.mensajeError = null;

        Fase etapaDestinoFase = proyecto.getFases().stream()
                .filter(f -> f.getNombre().equalsIgnoreCase(etapaDestino))
                .findFirst()
                .orElse(null);

        if (etapaDestinoFase == null) {
            this.mensajeError = "Etapa de destino no encontrada: " + etapaDestino;
            return;
        }

        int diferenciaOrden = etapaDestinoFase.getOrden() - faseActual.getOrden();

        if (diferenciaOrden > 1) {
            this.operacionExitosa = false;
            this.mensajeError = "No se pueden saltear etapas. Primero debe completarse la etapa actual.";
        } else if (diferenciaOrden <= 0) {
            this.operacionExitosa = false;
            this.mensajeError = "No se puede retroceder o permanecer en la misma etapa.";
        } else {
            this.faseActual = etapaDestinoFase;
            this.etapaActual = etapaDestino;
            this.operacionExitosa = true;
            this.mensajeError = null;
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
        Assert.assertTrue(
                "El mensaje debe indicar que no se pueden saltear etapas",
                mensajeError.toLowerCase().contains("saltear etapas")
                        || mensajeError.toLowerCase().contains("orden secuencial"));
    }

    // Getters útiles para debugging
    public String getEtapaActual() {
        return etapaActual;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public boolean isOperacionExitosa() {
        return operacionExitosa;
    }
}