package org.psa.steps;

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
    private String mensaje;
    private boolean horasAsociadas;

    @Given("un gerente de proyecto con permiso para cargar horas")
    public void gerenteDeProyectoConPermisoParaCargarHoras() {
        rolUsuario = RolUsuario.GERENTE_PROYECTO;
    }

    @Given("un gerente de proyecto sin permiso para cargar horas")
    public void gerenteDeProyectoSinPermisoParaCargarHoras() {
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
    public void elGerenteDeProyectoCargaHorasEnEsaTarea(int horas) {
        if (!tienePermiso()) {
            cargaExitosa = false;
            mensaje = "Falta de permisos para cargar horas";
            horasAsociadas = false;
            return;
        }
        if ("finalizado".equals(estadoProyecto)) {
            cargaExitosa = false;
            mensaje = "No se pueden cargar horas en proyectos finalizados";
            horasAsociadas = false;
            return;
        }
        cargaExitosa = true;
        mensaje = null;
        horasAsociadas = true;
    }

    @When("el gerente de proyecto intenta cargar horas en una tarea del proyecto")
    public void elGerenteDeProyectoIntentaCargarHorasEnUnaTareaDelProyecto() {
        if ("finalizado".equals(estadoProyecto)) {
            cargaExitosa = false;
            mensaje = "No se pueden cargar horas en proyectos finalizados";
            horasAsociadas = false;
        } else if (!tienePermiso()) {
            cargaExitosa = false;
            mensaje = "Falta de permisos para cargar horas";
            horasAsociadas = false;
        } else {
            cargaExitosa = true;
            mensaje = null;
            horasAsociadas = true;
        }
    }

    @When("intenta registrar horas en una tarea")
    public void intentaRegistrarHorasEnUnaTarea() {
        if (!tienePermiso()) {
            cargaExitosa = false;
            mensaje = "Falta de permisos para cargar horas";
            horasAsociadas = false;
        } else {
            cargaExitosa = true;
            mensaje = null;
            horasAsociadas = true;
        }
    }

    @Then("la carga se registra exitosamente")
    public void laCargaSeRegistraExitosamente() {
        Assert.assertTrue("La carga debería ser exitosa", cargaExitosa);
        Assert.assertNull("No debería haber mensaje de error", mensaje);
    }

    @Then("las horas quedan asociadas al gerente de proyecto y a la tarea")
    public void lasHorasQuedanAsociadasAlGerenteDeProyectoYALaTarea() {
        Assert.assertTrue("Las horas deberían estar asociadas", horasAsociadas);
    }

    @Then("la carga es bloqueada")
    public void laCargaEsBloqueada() {
        Assert.assertFalse("La carga debería estar bloqueada", cargaExitosa);
        Assert.assertNotNull("Debe haber un mensaje indicando bloqueo", mensaje);
    }

    @Then("se muestra un mensaje que indica que no se pueden cargar horas en proyectos finalizados")
    public void seMuestraUnMensajeQueIndicaQueNoSePuedenCargarHorasEnProyectosFinalizados() {
        Assert.assertFalse("La carga no debería ser exitosa", cargaExitosa);
        Assert.assertEquals("No se pueden cargar horas en proyectos finalizados", mensaje);
    }

    @Then("la acción es rechazada")
    public void laAccionEsRechazada() {
        Assert.assertFalse("La acción debería ser rechazada", cargaExitosa);
        Assert.assertNotNull("Debe haber mensaje de rechazo", mensaje);
    }

    @Then("se muestra un mensaje indicando falta de permisos")
    public void seMuestraUnMensajeIndicandoFaltaDePermisos() {
        Assert.assertFalse("La carga debería estar bloqueada por falta de permisos", cargaExitosa);
        Assert.assertTrue("El mensaje debe indicar falta de permisos",
            mensaje.toLowerCase().contains("permiso") || mensaje.toLowerCase().contains("falta"));
    }

    private boolean tienePermiso() {
        // Solo el gerente de proyecto tiene permiso
        return rolUsuario == RolUsuario.GERENTE_PROYECTO;
    }
}
