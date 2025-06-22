package org.psa.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class US05ConfiguracionSoftwareSteps {

    private String tipoConfiguracion;
    private String descripcionConfiguracion;
    private String configuracionVista;
    private boolean permisosGerente = true;
    private boolean cambioRegistrado = false;
    private boolean accionBloqueada = false;
    private boolean mensajeErrorVisible = false;

    @Given("que soy gerente de proyecto autenticado en la vista de configuración de un proyecto activo")
    public void soyGerenteAutenticadoEnConfiguracion() {
        permisosGerente = true;
    }

    @When("selecciono {string}")
    public void seleccionoTipoDeConfiguracion(String tipo) {
        tipoConfiguracion = tipo;
    }

    @When("ingreso la descripción {string}")
    public void ingresoDescripcion(String descripcion) {
        descripcionConfiguracion = descripcion;
    }

    @When("guardo los cambios")
    public void guardoCambiosConfiguracion() {
        if (permisosGerente) {
            configuracionVista = tipoConfiguracion;
            cambioRegistrado = true;
        } else {
            accionBloqueada = true;
            mensajeErrorVisible = true;
        }
    }

    @Then("se muestra {string} en la vista del proyecto")
    public void seMuestraConfiguracionEnVista(String tipoEsperado) {
        Assertions.assertEquals(tipoEsperado, configuracionVista);
    }

    @Then("la configuración queda registrada con la descripción proporcionada")
    public void configuracionRegistradaConDescripcion() {
        Assertions.assertNotNull(descripcionConfiguracion);
        Assertions.assertEquals("Módulo de facturación personalizado para Argentina", descripcionConfiguracion);
        Assertions.assertTrue(cambioRegistrado);
    }

    @Then("la customización se registra correctamente")
    public void customizacionRegistrada() {
        Assertions.assertEquals("Customización particular", tipoConfiguracion);
        Assertions.assertEquals("Integración especial con sistema SAP del cliente", descripcionConfiguracion);
        Assertions.assertTrue(cambioRegistrado);
    }

    @Then("se muestra en la vista del proyecto")
    public void seMuestraEnVista() {
        Assertions.assertNotNull(configuracionVista);
    }

    @Given("que el proyecto tiene asignada una configuración específica de módulo")
    public void proyectoConConfiguracionExistente() {
        tipoConfiguracion = "Configuración específica de módulo";
        descripcionConfiguracion = "Configuración heredada";
        configuracionVista = tipoConfiguracion;
    }

    @When("accedo a la vista del proyecto")
    public void accedoAVistaProyecto() {
        // Simula navegación: nada que hacer en esta implementación básica
    }

    @Then("se muestra el tipo de configuración asignada")
    public void mostrarTipoConfiguracion() {
        Assertions.assertNotNull(configuracionVista);
    }

    @Then("se muestra la descripción asociada si corresponde")
    public void mostrarDescripcionAsociada() {
        Assertions.assertNotNull(descripcionConfiguracion);
    }

    @Given("que soy un usuario sin permisos de gerente de proyecto")
    public void usuarioSinPermisos() {
        permisosGerente = false;
    }

    @When("intento modificar la configuración del proyecto")
    public void intentoModificarConfiguracion() {
        guardoCambiosConfiguracion();
    }

    @Then("el sistema impide la acción")
    public void sistemaImpideAccion() {
        Assertions.assertTrue(accionBloqueada);
    }

    @Then("muestra un mensaje de falta de permisos")
    public void mensajeFaltaPermisos() {
        Assertions.assertTrue(mensajeErrorVisible);
    }

    @Given("que el proyecto tiene una configuración asignada")
    public void proyectoConConfiguracion() {
        tipoConfiguracion = "Configuración Default";
        descripcionConfiguracion = "Config original";
    }

    @When("cambio el tipo de configuración")
    public void cambioTipoConfiguracion() {
        tipoConfiguracion = "Configuración específica de módulo";
    }

    @When("actualizo la descripción")
    public void actualizoDescripcion() {
        descripcionConfiguracion = "Actualización de configuración";
    }

    @Then("la configuración se actualiza correctamente")
    public void configuracionActualizadaCorrectamente() {
        Assertions.assertEquals("Configuración específica de módulo", tipoConfiguracion);
    }

    @Then("se registra el cambio realizado")
    public void seRegistraCambio() {
        Assertions.assertTrue(cambioRegistrado);
    }
}
