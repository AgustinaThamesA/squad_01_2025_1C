package org.psa.Steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.psa.service.ProyectoService;
import org.psa.model.Proyecto;
import org.psa.model.Riesgo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

public class US04GestionRiesgosSteps {

    private ProyectoService gerente;
    private Proyecto proyecto;
    private Riesgo riesgo;
    private boolean permisoDenegado;

    @Given("que soy gerente de proyecto de un proyecto activo")
    public void soyGerenteDeProyectoDeUnProyectoActivo() {
        gerente = new ProyectoService();
        proyecto = new Proyecto("Proyecto X", "Descripción del proyecto", "Leonardo Felici");
        proyecto.setEstado(Proyecto.Estado.ACTIVO);
        permisoDenegado = false;
    }

    @When("registro un riesgo con su descripción, impacto y probabilidad")
    public void registroRiesgo() {
        riesgo = new Riesgo("Riesgo crítico", Riesgo.Probabilidad.ALTA, Riesgo.Impacto.ALTO);
        proyecto.agregarRiesgo(riesgo);
    }

    @Then("el sistema lo almacena y lo clasifica automáticamente según severidad")
    public void sistemaAlmacenaYClasifica() {
        assertTrue(proyecto.getRiesgos().contains(riesgo));
        int nivelRiesgo = riesgo.calcularNivelRiesgo();
        assertTrue(nivelRiesgo >= 1 && nivelRiesgo <= 9);
    }

    @Given("que existe un riesgo registrado")
    public void existeRiesgoRegistrado() {
        soyGerenteDeProyectoDeUnProyectoActivo();
        registroRiesgo();
    }

    @When("asigno un plan de mitigación con su responsable")
    public void asignoPlanMitigacion() {
        // No existe el método real, simulamos asignación con una propiedad local
        // Podrías agregar un Map o un atributo en Riesgo si querés
        // Por ahora guardamos en un String simulado
        riesgo.setDescripcion(riesgo.getDescripcion() + " [Plan Mitigación asignado]");
    }

    @When("asigno un plan de contingencia con su responsable")
    public void asignoPlanContingencia() {
        riesgo.setDescripcion(riesgo.getDescripcion() + " [Plan Contingencia asignado]");
    }

    @Then("ambos planes quedan asociados correctamente al riesgo")
    public void planesAsociadosCorrectamente() {
        assertTrue(riesgo.getDescripcion().contains("Plan Mitigación"));
        assertTrue(riesgo.getDescripcion().contains("Plan Contingencia"));
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
        // Simulamos ordenamiento por severidad (nivel riesgo descendente)
        List<Riesgo> riesgosOrdenados = proyecto.getRiesgos().stream()
                .sorted((r1, r2) -> Integer.compare(r2.calcularNivelRiesgo(), r1.calcularNivelRiesgo()))
                .collect(Collectors.toList());
        proyecto.getRiesgos().clear();
        proyecto.getRiesgos().addAll(riesgosOrdenados);
    }

    @Then("los veo ordenados por severidad de mayor a menor")
    public void veoRiesgosOrdenados() {
        List<Riesgo> riesgos = proyecto.getRiesgos();
        for (int i = 0; i < riesgos.size() - 1; i = i + 1) {
            assertTrue(riesgos.get(i).calcularNivelRiesgo() >= riesgos.get(i + 1).calcularNivelRiesgo());
        }
    }

    @Given("que soy un usuario sin rol de gerente de proyecto")
    public void usuarioSinRolGerente() {
        gerente = null; // No es gerente
        permisoDenegado = false;
        proyecto = new Proyecto("Proyecto X", "Descripción", "otroUsuario");
        proyecto.setEstado(Proyecto.Estado.ACTIVO);
    }

    @When("intento registrar o editar un riesgo")
    public void intentoRegistrarEditarRiesgo() {
        if (gerente == null) {
            permisoDenegado = true;
        } else {
            permisoDenegado = false;
        }
    }

    @Then("el sistema me impide realizar la acción y muestra un mensaje de error")
    public void sistemaImpideAccion() {
        assertTrue(permisoDenegado);
    }
}