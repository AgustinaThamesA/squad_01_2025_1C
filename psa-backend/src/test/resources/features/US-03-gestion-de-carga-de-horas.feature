Feature: Gestión de carga de horas de trabajo
  # Como integrante del proyecto
  # Quiero registrar las horas que trabajo en las tareas asignadas
  # Para controlar el tiempo y evitar errores al cargar horas en proyectos finalizados

  Scenario: Gerente de proyecto carga horas en proyecto activo
    Given un gerente de proyecto con permiso para cargar horas
    And el proyecto está en estado "desarrollo"
    And la tarea está en estado "iniciada"
    When el gerente de proyecto carga 4 horas en esa tarea
    Then la carga se registra exitosamente
    And las horas quedan asociadas al gerente de proyecto y a la tarea

  Scenario: Gerente de proyecto intenta cargar horas en proyecto finalizado
    Given un gerente de proyecto con permiso para cargar horas
    And el proyecto está en estado "finalizado"
    When el gerente de proyecto intenta cargar horas en una tarea del proyecto
    Then la carga es bloqueada
    And se muestra un mensaje que indica que no se pueden cargar horas en proyectos finalizados

  Scenario: Gerente de proyecto sin permiso intenta cargar horas
    Given un gerente de proyecto sin permiso para cargar horas
    When intenta registrar horas en una tarea
    Then la acción es rechazada
    And se muestra un mensaje indicando falta de permisos