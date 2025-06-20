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

  Scenario: No permitir retroceder de etapa
    Given que el proyecto está en etapa "Análisis"
    When intento cambiar directamente a "Inicio"
    Then el sistema debe mostrar un error indicando que no se puede retroceder o permanecer en la misma etapa

  Scenario: No permitir seleccionar la misma etapa
    Given que el proyecto está en etapa "Diseño"
    When intento cambiar directamente a "Diseño"
    Then el sistema debe mostrar un error indicando que no se puede retroceder o permanecer en la misma etapa

  Scenario: Cambiar etapa en orden correcto varias veces
    Given que el proyecto está en etapa "Inicio"
    When marco la etapa como "Análisis"
    And marco la etapa como "Diseño"
    Then la etapa del proyecto debe actualizarse correctamente
