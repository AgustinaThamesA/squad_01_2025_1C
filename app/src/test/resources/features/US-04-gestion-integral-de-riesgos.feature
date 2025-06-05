Feature: Gestión integral de riesgos del proyecto
# Como project manager
# Quiero poder registrar, clasificar y gestionar los riesgos de un proyecto
# Para anticiparme a posibles problemas y mitigar su impacto en tiempos, costos o funcionalidades

  Scenario: Registrar un nuevo riesgo
    Given que soy project manager de un proyecto activo
    When registro un riesgo con su descripción, impacto y probabilidad
    Then el sistema lo almacena y lo clasifica automáticamente según severidad

  Scenario: Asignar planes de mitigación y contingencia
    Given que existe un riesgo registrado
    When asigno un plan de mitigación con su responsable
    And asigno un plan de contingencia con su responsable
    Then ambos planes quedan asociados correctamente al riesgo

  Scenario: Visualizar lista de riesgos ordenados
    Given que el proyecto tiene múltiples riesgos registrados
    When accedo a la sección de riesgos
    Then los veo ordenados por severidad de mayor a menor

  Scenario: Control de permisos para gestionar riesgos
    Given que soy un usuario sin rol de project manager
    When intento registrar o editar un riesgo
    Then el sistema me impide realizar la acción y muestra un mensaje de error