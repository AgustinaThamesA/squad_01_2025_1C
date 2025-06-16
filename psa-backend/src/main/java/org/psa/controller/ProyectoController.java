package org.psa.controller;

import org.psa.model.Proyecto;
import org.psa.model.Fase;
import org.psa.model.Tarea;
import org.psa.model.Riesgo;
import org.psa.repository.ProyectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/proyectos")
@CrossOrigin(origins = "http://localhost:3000") // Para conectar con React
public class ProyectoController {

    @Autowired
    private ProyectoRepository proyectoRepository;

    // GET /api/proyectos - Obtener todos los proyectos
    @GetMapping
    public List<Proyecto> obtenerTodosLosProyectos() {
        return proyectoRepository.findAll();
    }

    // GET /api/proyectos/1 - Obtener proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Proyecto> obtenerProyectoPorId(@PathVariable Long id) {
        Optional<Proyecto> proyecto = proyectoRepository.findById(id);
        if (proyecto.isPresent()) {
            return ResponseEntity.ok(proyecto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/proyectos - Crear nuevo proyecto
    @PostMapping
    public Proyecto crearProyecto(@RequestBody Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    // PUT /api/proyectos/1 - Actualizar proyecto
    @PutMapping("/{id}")
    public ResponseEntity<Proyecto> actualizarProyecto(@PathVariable Long id, @RequestBody Proyecto proyectoActualizado) {
        Optional<Proyecto> proyectoExistente = proyectoRepository.findById(id);
        if (proyectoExistente.isPresent()) {
            proyectoActualizado.setIdProyecto(id);
            return ResponseEntity.ok(proyectoRepository.save(proyectoActualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/proyectos/1 - Eliminar proyecto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProyecto(@PathVariable Long id) {
        if (proyectoRepository.existsById(id)) {
            proyectoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET /api/proyectos/activos - Filtrar proyectos activos
    @GetMapping("/activos")
    public List<Proyecto> obtenerProyectosActivos() {
        return proyectoRepository.findByEstado(Proyecto.Estado.ACTIVO);
    }

    // POST /api/proyectos/demo - Crear proyecto de prueba con datos completos
    @PostMapping("/demo")
    public Proyecto crearProyectoDemo() {
        // Crear proyecto
        Proyecto proyecto = new Proyecto(
            "Implementación SAP ERP", 
            "Migración completa a SAP ERP 7.51", 
            "Leonardo Felici"
        );
        proyecto.planificarFechas(LocalDate.now(), LocalDate.now().plusMonths(6));

        // Crear fases
        Fase fase1 = new Fase("Análisis", 1);
        fase1.planificarFechas(LocalDate.now(), LocalDate.now().plusMonths(2));
        
        Fase fase2 = new Fase("Desarrollo", 2);
        fase2.planificarFechas(LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(4));

        // Agregar fases al proyecto
        proyecto.agregarFase(fase1);
        proyecto.agregarFase(fase2);

        // Crear tareas
        Tarea tarea1 = new Tarea("Definir arquitectura", "Diseño del sistema", Tarea.Prioridad.ALTA, "Carlos Mendoza");
        tarea1.planificarFechas(LocalDate.now(), LocalDate.now().plusWeeks(2));
        
        Tarea tarea2 = new Tarea("Configurar base de datos", "Setup PostgreSQL", Tarea.Prioridad.MEDIA, "Ana García");
        tarea2.planificarFechas(LocalDate.now().plusWeeks(2), LocalDate.now().plusMonths(1));

        // Asignar tareas a fases
        fase1.agregarTarea(tarea1);
        fase1.agregarTarea(tarea2);

        // Crear riesgos
        Riesgo riesgo = new Riesgo("Retrasos en migración de datos", Riesgo.Probabilidad.MEDIA, Riesgo.Impacto.ALTO);
        proyecto.agregarRiesgo(riesgo);

        // Guardar proyecto (cascade guardará todo automáticamente)
        return proyectoRepository.save(proyecto);
    }

    // GET /api/proyectos/1/estadisticas - Obtener estadísticas del proyecto
    @GetMapping("/{id}/estadisticas")
    public ResponseEntity<Object> obtenerEstadisticas(@PathVariable Long id) {
        Optional<Proyecto> proyectoOpt = proyectoRepository.findById(id);
        if (proyectoOpt.isPresent()) {
            Proyecto proyecto = proyectoOpt.get();
            
            // Crear objeto con estadísticas usando tus métodos originales
            var estadisticas = new Object() {
                public final Long idProyecto = proyecto.getIdProyecto();
                public final String nombre = proyecto.getNombre();
                public final String estado = proyecto.getEstado().getDisplayName();
                public final double porcentajeAvance = proyecto.calcularPorcentajeAvance();
                public final int totalTareas = proyecto.getTotalTareas();
                public final int riesgosActivos = proyecto.getRiesgosActivos();
                public final int totalFases = proyecto.getFases().size();
                public final String liderProyecto = proyecto.getLiderProyecto();
            };
            
            return ResponseEntity.ok(estadisticas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
