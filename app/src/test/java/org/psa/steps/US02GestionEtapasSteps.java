package org.psa.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class US02GestionEtapasSteps {

    @Given("que el proyecto está en etapa {string}")
    public void queElProyectoEstaEnEtapa(String etapa) {
        // Implementar: Configurar proyecto en la etapa especificada
    }

    @When("marco la etapa como {string}")
    public void marcoLaEtapaComo(String nuevaEtapa) {
        // Implementar: Cambiar la etapa del proyecto
    }

    @When("intento cambiar directamente a {string}")
    public void intentoCambiarDirectamenteA(String etapaDestino) {
        // Implementar: Intentar cambio de etapa saltando pasos
    }

    @Then("la etapa del proyecto debe actualizarse correctamente")
    public void laEtapaDelProyectoDebeActualizarseCorrectamente() {
        // Implementar: Verificar que la etapa se actualizó
    }

    @Then("el sistema debe mostrar un error indicando que no se puede saltear etapas")
    public void elSistemaDebeMostrarUnErrorIndicandoQueNoSePuedeSaltearEtapas() {
        // Implementar: Verificar mensaje de error por salto de etapas
    }
}
