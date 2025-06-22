package org.psa.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class US03CargaHorasSteps {

    enum RolUsuario {
        GERENTE_PROYECTO,
        OTRO
    }

    private RolUsuario rolUsuario;
    private String estadoProyecto;
    private String estadoTarea;
    private boolean cargaExitosa;
    private boolean horasAsociadas;
    private String mensaje;

    @Given("un gerente de proyecto con permiso para cargar horas")
    public void unGerenteConPermiso() {
        rolUsuario = RolUsuario.GERENTE_PROYECTO;
    }

    @Given("un gerente de proyecto sin permiso para cargar horas")
    public void unGerenteSinPermiso() {
        rolUsuario = RolUsuario.OTRO;
    }

    @Given("el proyecto está en estado {string}")
    public void elProyectoEstaEnEstado(String estado) {
        estadoProyecto = estado.toLowerCase();
    }

    @Given("la tarea está en estado {string}")
    public void laTareaEstaEnEstado(String estado) {
        estadoTarea = estado.toLowerCase();
    }

    @When("el gerente de proyecto carga {int} horas en esa tarea")
    public void cargarHoras(int horas) {
        validarCarga();
    }

    @When("el gerente de proyecto intenta cargar horas en una tarea del proyecto")
    public void intentaCargarEnProyectoFinalizado() {
        validarCarga();
    }

    @When("intenta registrar horas en una tarea")
    public void intentaRegistrarHorasSinPermiso() {
        validarCarga();
    }

    private void validarCarga() {
        if (!tienePermiso()) {
            cargaExitosa = false;
            mensaje = "Falta de permisos para cargar horas";
            horasAsociadas = false;
        } else if ("finalizado".equals(estadoProyecto)) {
            cargaExitosa = false;
            mensaje = "No se pueden cargar horas en proyectos finalizados";
            horasAsociadas = false;
        } else {
            cargaExitosa = true;
            mensaje = null;
            horasAsociadas = true;
        }
    }

    @Then("la carga se registra exitosamente")
    public void cargaExitosa() {
        Assert.assertTrue("La carga debería ser exitosa", cargaExitosa);
        Assert.assertNull("No debería haber mensaje de error", mensaje);
    }

    @Then("las horas quedan asociadas al gerente de proyecto y a la tarea")
    public void horasAsociadasCorrectamente() {
        Assert.assertTrue("Las horas deberían estar asociadas", horasAsociadas);
    }

    @Then("la carga es bloqueada")
    public void cargaBloqueada() {
        Assert.assertFalse("La carga debería estar bloqueada", cargaExitosa);
    }

    @Then("se muestra un mensaje que indica que no se pueden cargar horas en proyectos finalizados")
    public void mensajeProyectoFinalizado() {
        Assert.assertEquals("No se pueden cargar horas en proyectos finalizados", mensaje);
    }

    @Then("la acción es rechazada")
    public void accionRechazada() {
        Assert.assertFalse("La acción debería ser rechazada", cargaExitosa);
    }

    @Then("se muestra un mensaje indicando falta de permisos")
    public void mensajeFaltaPermisos() {
        Assert.assertTrue(mensaje.toLowerCase().contains("permiso") || mensaje.toLowerCase().contains("falta"));
    }

    private boolean tienePermiso() {
        return rolUsuario == RolUsuario.GERENTE_PROYECTO;
    }
}
