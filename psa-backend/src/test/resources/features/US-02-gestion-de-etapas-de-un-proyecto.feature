Feature: Gestión de etapas de un proyecto
#  Como integrante del proyecto
#  Quiero que los proyectos tengan etapas definidas
#  Para poder hacer seguimiento del ciclo de vida del proyecto

  Scenario: Cambiar la etapa de un proyecto
    Given que el proyecto está en etapa "Inicio"
    When marco la etapa como "Análisis"
    Then la etapa del proyecto debe actualizarse correctamente

  Scenario: No permitir saltar etapas
    Given que el proyecto está en etapa "Inicio"
    When intento cambiar directamente a "Transición"
    Then el sistema debe mostrar un error indicando que no se puede saltear etapas