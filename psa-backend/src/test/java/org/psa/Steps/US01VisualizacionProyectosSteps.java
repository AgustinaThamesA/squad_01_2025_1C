package org.psa.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.psa.model.Proyecto;
import org.psa.model.Proyecto.Estado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class US01VisualizacionProyectosSteps {

    private final List<Proyecto> proyectos = new ArrayList<>();

    @Given("existen proyectos cargados en el sistema")
    public void existenProyectosCargadosEnElSistema() {
        Proyecto proyecto1 = new Proyecto(
                "PSA Cloud Spring ERP",
                "Migración del ERP principal a cloud",
                "Leonardo Felici"
        );
        proyecto1.planificarFechas(LocalDate.of(2024, 1, 15), LocalDate.of(2024, 6, 30));
        proyecto1.setEstado(Estado.ACTIVO);

        Proyecto proyecto2 = new Proyecto(
                "CRM Mobile Release v2.1",
                "Funcionalidades móviles del CRM",
                "Leonardo Felici"
        );
        proyecto2.planificarFechas(LocalDate.of(2024, 2, 1), LocalDate.of(2024, 7, 15));
        proyecto2.setEstado(Estado.ACTIVO);

        Proyecto proyecto3 = new Proyecto(
                "Business Analytics v3.0",
                "Versión renovada de módulo de BI",
                "Leonardo Felici"
        );
        proyecto3.planificarFechas(LocalDate.of(2024, 3, 10), LocalDate.of(2024, 8, 30));
        proyecto3.setEstado(Estado.PAUSADO);

        proyectos.add(proyecto1);
        proyectos.add(proyecto2);
        proyectos.add(proyecto3);
    }

    @When("el usuario accede al módulo de proyectos")
    public void elUsuarioAccedeAlModuloDeProyectos() {
        assertFalse(proyectos.isEmpty(), "No hay proyectos cargados en el sistema");
    }

    @Then("se debe mostrar un tablero con las tarjetas de los proyectos")
    public void seDebeMostrarUnTableroConLasTarjetasDelosProyectos() {
        proyectos.forEach(p -> System.out.println("Proyecto: " + p.getNombre()));
        assertTrue(proyectos.size() >= 1, "No se mostraron tarjetas de proyectos");
    }

    @Then("cada tarjeta debe mostrar el nombre, estado, fechas y líder del proyecto")
    public void cadaTarjetaDebeMostrarElNombreEstadoFechasYLiderDelProyecto() {
        for (Proyecto p : proyectos) {
            assertNotNull(p.getNombre(), "Nombre faltante");
            assertNotNull(p.getEstado(), "Estado faltante");
            assertNotNull(p.getFechaInicio(), "Fecha de inicio faltante");
            assertNotNull(p.getFechaFinEstimada(), "Fecha de fin estimada faltante");
            assertNotNull(p.getLiderProyecto(), "Líder de proyecto faltante");
        }
    }
}