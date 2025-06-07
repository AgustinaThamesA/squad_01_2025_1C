package org.psa.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class US04GestionRiesgosSteps {

    @Given("que soy project manager de un proyecto activo")
    public void queSoyProjectManagerDeUnProyectoActivo() {
        // Implementar: Configurar usuario como project manager de proyecto activo
    }

    @Given("que existe un riesgo registrado")
    public void queExisteUnRiesgoRegistrado() {
        // Implementar: Crear un riesgo existente en el sistema
    }

    @Given("que el proyecto tiene múltiples riesgos registrados")
    public void queElProyectoTieneMultiplesRiesgosRegistrados() {
        // Implementar: Crear múltiples riesgos con diferentes severidades
    }

    @Given("que soy un usuario sin rol de project manager")
    public void queSoyUnUsuarioSinRolDeProjectManager() {
        // Implementar: Configurar usuario sin permisos de project manager
    }

    @When("registro un riesgo con su descripción, impacto y probabilidad")
    public void registroUnRiesgoConSuDescripcionImpactoYProbabilidad() {
        // Implementar: Registrar nuevo riesgo con todos los datos
    }

    @When("asigno un plan de mitigación con su responsable")
    public void asignoUnPlanDeMitigacionConSuResponsable() {
        // Implementar: Asignar plan de mitigación a un riesgo
    }

    @When("asigno un plan de contingencia con su responsable")
    public void asignoUnPlanDeContingenciaConSuResponsable() {
        // Implementar: Asignar plan de contingencia a un riesgo
    }

    @When("accedo a la sección de riesgos")
    public void accedoALaSeccionDeRiesgos() {
        // Implementar: Navegar a la sección de gestión de riesgos
    }

    @When("intento registrar o editar un riesgo")
    public void intentoRegistrarOEditarUnRiesgo() {
        // Implementar: Intentar operación de riesgo sin permisos
    }

    @Then("el sistema lo almacena y lo clasifica automáticamente según severidad")
    public void elSistemaLoAlmacenaYLoClasificaAutomaticamenteSegunSeveridad() {
        // Implementar: Verificar almacenamiento y clasificación automática
    }

    @Then("ambos planes quedan asociados correctamente al riesgo")
    public void ambosPlayesQuedanAsociadosCorrectamenteAlRiesgo() {
        // Implementar: Verificar asociación de planes de mitigación y contingencia
    }

    @Then("los veo ordenados por severidad de mayor a menor")
    public void losVeoOrdenadosPorSeveridadDeMayorAMenor() {
        // Implementar: Verificar ordenamiento por severidad
    }

    @Then("el sistema me impide realizar la acción y muestra un mensaje de error")
    public void elSistemaMeImpideRealizarLaAccionYMuestraUnMensajeDeError() {
        // Implementar: Verificar bloqueo de acción y mensaje de error
    }
}
