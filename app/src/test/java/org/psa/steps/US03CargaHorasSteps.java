package org.psa.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class US03CargaHorasSteps {

    @Given("un usuario con permiso para cargar horas")
    public void unUsuarioConPermisoParaCargarHoras() {
        // Implementar: Configurar usuario con permisos apropiados
    }

    @Given("un usuario sin permiso para cargar horas")
    public void unUsuarioSinPermisoParaCargarHoras() {
        // Implementar: Configurar usuario sin permisos de carga
    }

    @Given("el proyecto está en estado {string}")
    public void elProyectoEstaEnEstado(String estado) {
        // Implementar: Configurar proyecto en el estado especificado
    }

    @Given("la tarea está en estado {string}")
    public void laTareaEstaEnEstado(String estadoTarea) {
        // Implementar: Configurar tarea en el estado especificado
    }

    @When("el usuario carga {int} horas en esa tarea")
    public void elUsuarioCargaHorasEnEsaTarea(int horas) {
        // Implementar: Registrar horas en la tarea
    }

    @When("el usuario intenta cargar horas en una tarea del proyecto")
    public void elUsuarioIntentaCargarHorasEnUnaTareaDelProyecto() {
        // Implementar: Intentar cargar horas en proyecto finalizado
    }

    @When("intenta registrar horas en una tarea")
    public void intentaRegistrarHorasEnUnaTarea() {
        // Implementar: Intento de registro sin permisos
    }

    @Then("la carga se registra exitosamente")
    public void laCargaSeRegistraExitosamente() {
        // Implementar: Verificar registro exitoso
    }

    @Then("las horas quedan asociadas al usuario y a la tarea")
    public void lasHorasQuedanAsociadasAlUsuarioYALaTarea() {
        // Implementar: Verificar asociación correcta de horas
    }

    @Then("la carga es bloqueada")
    public void laCargaEsBloqueada() {
        // Implementar: Verificar que la carga fue bloqueada
    }

    @Then("se muestra un mensaje que indica que no se pueden cargar horas en proyectos finalizados")
    public void seMuestraUnMensajeQueIndicaQueNoSePuedenCargarHorasEnProyectosFinalizados() {
        // Implementar: Verificar mensaje específico para proyectos finalizados
    }

    @Then("la acción es rechazada")
    public void laAccionEsRechazada() {
        // Implementar: Verificar rechazo de la acción
    }

    @Then("se muestra un mensaje indicando falta de permisos")
    public void seMuestraUnMensajeIndicandoFaltaDePermisos() {
        // Implementar: Verificar mensaje de falta de permisos
    }
}
