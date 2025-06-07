package org.psa.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class US01VisualizacionProyectosSteps {

    @Given("existen proyectos cargados en el sistema")
    public void existenProyectosCargadosEnElSistema() {
        // Implementar: Crear proyectos de prueba en la base de datos
    }

    @When("el usuario accede al módulo de proyectos")
    public void elUsuarioAccedeAlModuloDeProyectos() {
        // Implementar: Navegar al módulo de proyectos
    }

    @Then("se debe mostrar un tablero con las tarjetas de los proyectos")
    public void seDebeMostrarUnTableroConLasTarjetasDelosProyectos() {
        // Implementar: Verificar que se muestra el tablero con tarjetas
    }

    @Then("cada tarjeta debe mostrar el nombre, estado, fechas y líder del proyecto")
    public void cadaTarjetaDebeMostrarElNombreEstadoFechasYLiderDelProyecto() {
        // Implementar: Verificar que cada tarjeta contiene la información requerida
    }
}
