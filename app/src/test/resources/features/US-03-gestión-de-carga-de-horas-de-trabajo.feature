Feature: Gestión de carga de horas de trabajo
#  Como integrante del proyecto
#  Quiero poder registrar las horas que trabajo en las tareas asignadas
#  Para llevar un control preciso del tiempo dedicado y facilitar la facturación, sin errores por cargar horas en proyectos finalizados

  Scenario: Usuario carga horas en proyecto activo
    Given un usuario con permiso para cargar horas
    And el proyecto está en estado "desarrollo"
    And la tarea está en estado "iniciada"
    When el usuario carga 4 horas en esa tarea
    Then la carga se registra exitosamente
    And las horas quedan asociadas al usuario y a la tarea

  Scenario: Usuario intenta cargar horas en proyecto finalizado
    Given un usuario con permiso para cargar horas
    And el proyecto está en estado "finalizado"
    When el usuario intenta cargar horas en una tarea del proyecto
    Then la carga es bloqueada
    And se muestra un mensaje que indica que no se pueden cargar horas en proyectos finalizados

  Scenario: Usuario sin permiso intenta cargar horas
    Given un usuario sin permiso para cargar horas
    When intenta registrar horas en una tarea
    Then la acción es rechazada
    And se muestra un mensaje indicando falta de permisos
