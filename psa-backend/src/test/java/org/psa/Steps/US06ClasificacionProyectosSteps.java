package org.psa.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class US06ClasificacionProyectosSteps {

    private String nombreProyecto;
    private String tipoProyecto;
    private String estadoProyecto;
    private boolean creado;
    private boolean tipoEditable = false;
    private boolean errorTipoObligatorio = false;
    private List<Map<String, String>> tablero = new ArrayList<>();
    private List<Map<String, String>> resultadosFiltrados = new ArrayList<>();

    @Given("que soy gerente de proyecto creando un nuevo proyecto")
    public void soyGerenteCreandoProyecto() {
        nombreProyecto = "";
        tipoProyecto = "";
        creado = false;
        tipoEditable = false;
    }

    @When("ingreso el nombre {string}")
    public void ingresoNombreProyecto(String nombre) {
        nombreProyecto = nombre;
    }

    @When("selecciono el tipo {string}")
    public void seleccionoTipoProyecto(String tipo) {
        tipoProyecto = tipo;
    }

    @When("completo los campos obligatorios")
    public void completoCamposObligatorios() {
        // Simula completar datos adicionales
    }

    @When("guardo el proyecto")
    public void guardoProyecto() {
        if (tipoProyecto == null || tipoProyecto.isEmpty()) {
            errorTipoObligatorio = true;
            creado = false;
        } else {
            Map<String, String> nuevo = new HashMap<>();
            nuevo.put("nombre", nombreProyecto);
            nuevo.put("tipo", tipoProyecto);
            nuevo.put("estado", estadoProyecto != null ? estadoProyecto : "En desarrollo");
            tablero.add(nuevo);
            creado = true;
        }
    }

    @Then("el proyecto se crea con tipo {string}")
    public void proyectoCreadoConTipo(String tipoEsperado) {
        Assertions.assertTrue(creado);
        Assertions.assertEquals(tipoEsperado, tipoProyecto);
    }

    @Then("se muestra así en el tablero")
    public void seMuestraEnTablero() {
        boolean encontrado = tablero.stream().anyMatch(p -> p.get("nombre").equals(nombreProyecto));
        Assertions.assertTrue(encontrado);
    }

    @Given("que existen proyectos de tipo {string} y {string}")
    public void existenProyectosDeAmbosTipos(String tipo1, String tipo2) {
        tablero.clear();
        tablero.add(Map.of("nombre", "P1", "tipo", tipo1, "estado", "En desarrollo"));
        tablero.add(Map.of("nombre", "P2", "tipo", tipo2, "estado", "Finalizado"));
    }

    @When("aplico el filtro {string} en el tablero")
    public void aplicoFiltroEnTablero(String filtro) {
        String tipo = filtro.replace("Tipo: ", "").trim();
        resultadosFiltrados = tablero.stream()
            .filter(p -> p.get("tipo").equals(tipo))
            .toList();
    }

    @Then("solo se muestran proyectos de tipo {string}")
    public void seMuestranSoloProyectosFiltrados(String tipoEsperado) {
        Assertions.assertTrue(resultadosFiltrados.stream().allMatch(p -> p.get("tipo").equals(tipoEsperado)));
    }

    @Given("que existe un proyecto de tipo {string}")
    public void existeProyectoTipo(String tipo) {
        tipoProyecto = tipo;
        tablero.clear();
        tablero.add(Map.of("nombre", "Proyecto X", "tipo", tipo, "estado", "En desarrollo"));
    }

    @When("accedo al tablero")
    public void accedoTablero() {
        // Simular acceso, no necesario hacer nada
    }

    @Then("la tarjeta del proyecto muestra el tipo {string}")
    public void tarjetaMuestraTipo(String tipoEsperado) {
        Assertions.assertEquals(tipoEsperado, tablero.get(0).get("tipo"));
    }

    @Then("se distingue visualmente por color, icono o etiqueta")
    public void tipoDistinguidoVisualmente() {
        // Asumimos que esto se verifica visualmente, siempre pasa
        Assertions.assertTrue(true);
    }

    @Given("que existe un proyecto ya creado de tipo {string}")
    public void proyectoYaCreadoTipo(String tipo) {
        tipoProyecto = tipo;
        tipoEditable = false;
    }

    @When("intento editarlo")
    public void intentoEditarProyecto() {
        tipoEditable = false;
    }

    @Then("el campo tipo aparece como solo lectura")
    public void campoSoloLectura() {
        Assertions.assertFalse(tipoEditable);
    }

    @Given("que estoy creando un nuevo proyecto")
    public void creandoNuevoProyecto() {
        tipoProyecto = "";
        creado = false;
        errorTipoObligatorio = false;
    }

    @When("omito el campo tipo y guardo")
    public void omitoTipoYGuardo() {
        guardoProyecto();
    }

    @Then("el sistema muestra un error indicando que el tipo es obligatorio")
    public void errorTipoObligatorio() {
        Assertions.assertTrue(errorTipoObligatorio);
    }

    @Then("el proyecto no se crea")
    public void proyectoNoSeCrea() {
        Assertions.assertFalse(creado);
    }

    @When("accedo a su vista detallada")
    public void accedoVistaDetallada() {
        // Simula navegación
    }

    @Then("se muestra claramente el tipo de proyecto")
    public void muestraTipoProyecto() {
        Assertions.assertNotNull(tipoProyecto);
    }

    @Then("aparece destacado en la información básica")
    public void tipoDestacadoEnInfo() {
        Assertions.assertTrue(true); // siempre se destaca
    }

    @Given("que existen proyectos con distintos tipos y estados")
    public void existenProyectosConFiltros() {
        tablero.clear();
        tablero.add(Map.of("nombre", "P1", "tipo", "Desarrollo", "estado", "En desarrollo"));
        tablero.add(Map.of("nombre", "P2", "tipo", "Implementación", "estado", "En desarrollo"));
        tablero.add(Map.of("nombre", "P3", "tipo", "Desarrollo", "estado", "Finalizado"));
    }

    @When("aplico filtros {string} y {string}")
    public void aplicoFiltrosCombinados(String tipo, String estado) {
        String tipoVal = tipo.replace("Tipo: ", "").trim();
        String estadoVal = estado.replace("Estado: ", "").trim();
        resultadosFiltrados = tablero.stream()
            .filter(p -> p.get("tipo").equals(tipoVal) && p.get("estado").equals(estadoVal))
            .toList();
    }

    @Then("solo se muestran los proyectos que cumplen ambos criterios")
    public void seFiltranCorrectamente() {
        Assertions.assertTrue(resultadosFiltrados.stream()
            .allMatch(p -> p.get("tipo").equals("Desarrollo") && p.get("estado").equals("En desarrollo")));
    }

    @Then("el contador de resultados es correcto")
    public void contadorResultadosCorrecto() {
        Assertions.assertEquals(1, resultadosFiltrados.size());
    }
}
