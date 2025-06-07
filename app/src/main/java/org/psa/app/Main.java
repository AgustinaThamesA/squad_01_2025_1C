package org.psa.app;

import org.psa.manager.GerentePortafolio;
import org.psa.manager.GerenteProyecto;
import org.psa.model.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Sistema PSA - Gestión de Proyectos");
        System.out.println("==================================\n");
        
        // Crear gerentes
        GerentePortafolio gerentePortafolio = new GerentePortafolio();
        GerenteProyecto gerenteProyecto = new GerenteProyecto();
        
        // 1. Crear proyecto
        Proyecto proyecto = gerentePortafolio.crearProyecto(
            "Sistema ERP Cloud", 
            "Migración a cloud", 
            "Leonardo Felici");
        System.out.println("Proyecto creado: " + proyecto.getNombre());
        
        // 2. Planificar y crear fases
        gerenteProyecto.planificarProyecto(proyecto, 
            LocalDate.of(2024, 1, 15), LocalDate.of(2024, 6, 30));
        
        Fase fase1 = gerenteProyecto.crearFase(proyecto, "Análisis", 1);
        Fase fase2 = gerenteProyecto.crearFase(proyecto, "Desarrollo", 2);
        Fase fase3 = gerenteProyecto.crearFase(proyecto, "Testing", 3);
        System.out.println(proyecto.getFases().size() + " fases creadas");
        
        // 3. Crear tareas (simple y multifase)
        Tarea tarea1 = gerenteProyecto.crearTarea(
            "Definir arquitectura", "Diseño del sistema", 
            Tarea.Prioridad.ALTA, "Carlos Mendoza");
        gerenteProyecto.asignarTareaAFase(tarea1, fase1);
        gerenteProyecto.completarTarea(tarea1);
        
        // Tarea MULTIFASE
        Tarea tareaMultifase = gerenteProyecto.crearTarea(
            "Migración de datos", "Proceso que abarca múltiples fases", 
            Tarea.Prioridad.ALTA, "Ana García");
        gerenteProyecto.asignarTareaAMultiplesFases(tareaMultifase, 
            Arrays.asList(fase1, fase2, fase3));
        gerenteProyecto.iniciarTarea(tareaMultifase);
        
        System.out.println("Tareas creadas:");
        System.out.println("  - " + tarea1.getTitulo() + " (1 fase)");
        System.out.println("  - " + tareaMultifase.getTitulo() + " (3 fases - MULTIFASE)");
        
        // 4. Gestionar riesgos
        Riesgo riesgo = gerenteProyecto.crearRiesgo(proyecto,
            "Retrasos en migración", 
            Riesgo.Probabilidad.ALTA, Riesgo.Impacto.ALTO);
        System.out.println("Riesgo creado - Nivel: " + riesgo.calcularNivelRiesgo() + "/9");
        
        // 5. Generar reporte
        ReporteEstado reporte = gerenteProyecto.generarReporte(proyecto,
            "Proyecto iniciado correctamente");
        System.out.println("Reporte generado - Avance: " + 
            String.format("%.1f", reporte.getPorcentajeAvance()) + "%");
        
        // Imprimir estado final
        System.out.println("\n=== ESTADO DEL PROYECTO ===");
        System.out.println("Proyecto: " + proyecto.getNombre());
        System.out.println("Fases: " + proyecto.getFases().size());
        System.out.println("Tareas: " + proyecto.getTotalTareas());
        System.out.println("Riesgos: " + proyecto.getRiesgos().size());
        System.out.println("Reportes: " + proyecto.getReportes().size());
        System.out.println("Progreso: " + String.format("%.1f", proyecto.calcularPorcentajeAvance()) + "%");
        
        System.out.println("\n Funcionalidades probadas:");
        System.out.println("• Creación de proyectos (GerentePortafolio)");
        System.out.println("• Gestión de fases y tareas (GerenteProyecto)");
        System.out.println("• Relación n:m entre tareas y fases");
        System.out.println("• Gestión de riesgos");
        System.out.println("• Generación de reportes");
    }
}