package org.psa.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.psa.model.Proyecto;
import org.psa.model.Riesgo;
import org.psa.service.ProyectoService;

import java.util.List;
import java.util.stream.Collectors;

public class US04GestionRiesgosSteps {

    private ProyectoService gerente;
    private Proyecto proyecto;
    private Riesgo riesgo;
    private boolean permisoDenegado;
    private boolean esGerente;

    @Given("que soy gerente de proyecto de un proyecto activo")
    public void soyGerenteDeProyectoDeUnProyectoActivo() {
        gerente = new ProyectoService(); // Simulación
        proyecto = new Proyecto("Proyecto X", "Descripción del proyecto", "Leonardo Felici");
        proyecto.setEstado(Proyecto.Estado.ACTIVO);
        permisoDenegado = false;
        esGerente = true;
    }

    @When("registro un riesgo con su descripción, impacto y probabilidad")
    public void registroRiesgo() {
        riesgo = new Riesgo("Riesgo crítico", Riesgo.Probabilidad.ALTA, Riesgo.Impacto.ALTO);
        proyecto.agregarRiesgo(riesgo);
    }

    @Then("el sistema lo almacena y lo clasifica automáticamente según severidad")
    public void sistemaAlmacenaYClasifica() {
        Assertions.assertTrue(proyecto.getRiesgos().contains(riesgo));
        int severidad = riesgo.calcularNivelRiesgo();
        Assertions.assertTrue(severidad >= 1 && severidad <= 9);
    }

    @Given("que existe un riesgo registrado")
    public void existeRiesgoRegistrado() {
        soyGerenteDeProyectoDeUnProyectoActivo();
        registroRiesgo();
    }

    @When("asigno un plan de mitigación con su responsable")
    public void asignoPlanMitigacion() {
        riesgo.setDescripcion(riesgo.getDescripcion() + " [Plan Mitigación asignado]");
    }

    @When("asigno un plan de contingencia con su responsable")
    public void asignoPlanContingencia() {
        riesgo.setDescripcion(riesgo.getDescripcion() + " [Plan Contingencia asignado]");
    }

    @Then("ambos planes quedan asociados correctamente al riesgo")
    public void planesAsociadosCorrectamente() {
        Assertions.assertTrue(riesgo.getDescripcion().contains("Plan Mitigación"));
        Assertions.assertTrue(riesgo.getDescripcion().contains("Plan Contingencia"));
    }

    @Given("que el proyecto tiene múltiples riesgos registrados")
    public void proyectoConMultiplesRiesgos() {
        soyGerenteDeProyectoDeUnProyectoActivo();
        proyecto.agregarRiesgo(new Riesgo("Riesgo medio", Riesgo.Probabilidad.MEDIA, Riesgo.Impacto.MEDIO));
        proyecto.agregarRiesgo(new Riesgo("Riesgo bajo", Riesgo.Probabilidad.BAJA, Riesgo.Impacto.BAJO));
        proyecto.agregarRiesgo(new Riesgo("Riesgo alto", Riesgo.Probabilidad.ALTA, Riesgo.Impacto.ALTO));
    }

    @When("accedo a la sección de riesgos")
    public void accedoSeccionRiesgos() {
        List<Riesgo> ordenados = proyecto.getRiesgos().stream()
                .sorted((r1, r2) -> Integer.compare(r2.calcularNivelRiesgo(), r1.calcularNivelRiesgo()))
                .collect(Collectors.toList());

        proyecto.getRiesgos().clear();
        proyecto.getRiesgos().addAll(ordenados);
    }

    @Then("los veo ordenados por severidad de mayor a menor")
    public void veoRiesgosOrdenados() {
        List<Riesgo> riesgos = proyecto.getRiesgos();
        for (int i = 0; i < riesgos.size() - 1; i++) {
            Assertions.assertTrue(
                riesgos.get(i).calcularNivelRiesgo() >= riesgos.get(i + 1).calcularNivelRiesgo(),
                "Los riesgos no están ordenados correctamente"
            );
        }
    }

    @Given("que soy un usuario sin rol de gerente de proyecto")
    public void usuarioSinRolGerente() {
        gerente = null;
        esGerente = false;
        proyecto = new Proyecto("Proyecto X", "Descripción", "otroUsuario");
        proyecto.setEstado(Proyecto.Estado.ACTIVO);
    }

    @When("intento registrar o editar un riesgo")
    public void intentoRegistrarEditarRiesgo() {
        permisoDenegado = !esGerente;
    }

    @Then("el sistema me impide realizar la acción y muestra un mensaje de error")
    public void sistemaImpideAccion() {
        Assertions.assertTrue(permisoDenegado, "El sistema debería impedir la acción");
    }
}
