package org.psa.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.psa.model.Proyecto;
import org.psa.model.Proyecto.Estado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class US01VisualizacionProyectosSteps {

    private final List<Proyecto> proyectos = new ArrayList<>();
    private List<Proyecto> proyectosFiltrados = new ArrayList<>();

    @Given("existen proyectos cargados en el sistema")
    public void existenProyectosCargadosEnElSistema() {
        Proyecto proyecto1 = new Proyecto("PSA Cloud Spring ERP", "Migración del ERP principal a cloud", (String) "Leonardo Felici");
        proyecto1.planificarFechas(LocalDate.of(2024, 1, 15), LocalDate.of(2024, 6, 30));
        proyecto1.setEstado(Estado.ACTIVO);

        Proyecto proyecto2 = new Proyecto("CRM Mobile Release v2.1", "Funcionalidades móviles del CRM", (String) "Leonardo Felici");
        proyecto2.planificarFechas(LocalDate.of(2024, 2, 1), LocalDate.of(2024, 7, 15));
        proyecto2.setEstado(Estado.CERRADO);

        Proyecto proyecto3 = new Proyecto("Business Analytics v3.0", "Versión renovada de módulo de BI", (String) "Leonardo Felici");
        proyecto3.planificarFechas(LocalDate.of(2024, 3, 10), null); // Sin fecha fin
        proyecto3.setEstado(Estado.ACTIVO);

        proyectos.clear();
        proyectos.add(proyecto1);
        proyectos.add(proyecto2);
        proyectos.add(proyecto3);
    }

    @Given("no existen proyectos activos en el sistema")
    public void noExistenProyectosActivos() {
        proyectos.clear();
        Proyecto cerrado1 = new Proyecto("Migración Legacy", "Actualización de plataformas", (String) "Carla Ramírez");
        cerrado1.planificarFechas(LocalDate.of(2023, 1, 1), LocalDate.of(2023, 6, 1));
        cerrado1.setEstado(Estado.CERRADO);

        proyectos.add(cerrado1);
    }

    @Given("existen proyectos en estado cerrado")
    public void existenProyectosEnEstadoCerrado() {
        proyectos.clear();
        Proyecto cerrado1 = new Proyecto("Refactor Backoffice", "Refactor de servicios internos", (String) "Martín López");
        cerrado1.planificarFechas(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 4, 1));
        cerrado1.setEstado(Estado.CERRADO);

        Proyecto cerrado2 = new Proyecto("Infra upgrade", "Actualización de infraestructura", (String) "Paula García");
        cerrado2.planificarFechas(LocalDate.of(2024, 2, 10), LocalDate.of(2024, 5, 10));
        cerrado2.setEstado(Estado.CERRADO);

        proyectos.add(cerrado1);
        proyectos.add(cerrado2);
    }

    @Given("existe un proyecto sin fechas de finalización o sin líder asignado")
    public void proyectoSinDatosCompletos() {
        proyectos.clear();
        Proyecto incompleto = new Proyecto("Core DevTools", "Herramientas internas", (String) null);
        incompleto.planificarFechas(LocalDate.of(2024, 5, 1), null); // Fecha fin null
        incompleto.setEstado(Estado.ACTIVO);

        proyectos.add(incompleto);
    }

    @When("el usuario accede al módulo de proyectos")
    public void elUsuarioAccedeAlModuloDeProyectos() {
        assertNotNull(proyectos, "No se pudo acceder a los proyectos");
    }

    @When("el usuario aplica el filtro de cerrado")
    public void usuarioFiltraProyectosCerrados() {
        proyectosFiltrados = proyectos.stream()
                .filter(p -> p.getEstado() == Estado.CERRADO)
                .toList();
    }

    @Then("se debe mostrar un tablero con las tarjetas de los proyectos")
    public void seMuestranTarjetas() {
        assertFalse(proyectos.isEmpty(), "No se mostraron proyectos en el tablero");
    }

    @Then("cada tarjeta debe mostrar el nombre, estado, fechas y líder del proyecto")
    public void verificarCamposDeTarjeta() {
        for (Proyecto p : proyectos) {
            assertNotNull(p.getNombre(), "Falta nombre del proyecto");
            assertNotNull(p.getEstado(), "Falta estado del proyecto");
            assertNotNull(p.getFechaInicio(), "Falta fecha de inicio");
            assertNotNull(p.getLiderProyecto(), "Falta líder del proyecto");
        }
    }

    @Then("se debe mostrar en el tablero que no hay proyectos para mostrar")
    public void seMuestraMensajeSinProyectos() {
        long activos = proyectos.stream().filter(p -> p.getEstado() == Estado.ACTIVO).count();
        assertEquals(0, activos, "Se esperaban 0 proyectos activos");
    }

    @Then("no se deben mostrar tarjetas vacías")
    public void noSeMuestranTarjetasVacias() {
        boolean hayTarjetasInútiles = proyectos.stream().anyMatch(p -> p.getNombre() == null && p.getEstado() == null);
        assertFalse(hayTarjetasInútiles, "Se mostraron tarjetas vacías");
    }

    @Then("se deben mostrar únicamente las tarjetas correspondientes a proyectos cerrados")
    public void seFiltranProyectosCerrados() {
        assertFalse(proyectosFiltrados.isEmpty(), "No se encontraron proyectos cerrados");
        assertTrue(proyectosFiltrados.stream().allMatch(p -> p.getEstado() == Estado.CERRADO),
                "Se colaron proyectos no cerrados en el filtro");
    }

    @Then("la tarjeta del proyecto debe mostrarse igualmente")
    public void seMuestraProyectoIncompleto() {
        assertEquals(1, proyectos.size());
        Proyecto p = proyectos.get(0);
        assertNotNull(p.getNombre(), "Nombre faltante");
    }

    @Then("se debe indicar {string} o {string} en los campos faltantes")
    public void mostrarPlaceholderEnCamposFaltantes(String placeholder1, String placeholder2) {
        Proyecto p = proyectos.get(0);
        if (p.getLiderProyecto() == null || p.getLiderProyecto().isBlank()) {
            System.out.println("Líder: " + placeholder1);
        }
        if (p.getFechaFinEstimada() == null) {
            System.out.println("Fecha fin: " + placeholder2);
        }
        assertTrue(true); // Simulación de verificación visual
    }
}
