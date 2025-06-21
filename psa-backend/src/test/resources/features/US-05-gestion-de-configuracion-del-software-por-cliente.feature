Feature: Configuración del software por cliente

  Scenario: Asignar configuración default
    Given que soy gerente de proyecto autenticado en la vista de configuración de un proyecto activo
    When selecciono "Configuración Default"
    And guardo los cambios
    Then se muestra "Configuración Default" en la vista del proyecto

  Scenario: Asignar configuración específica de módulo
    Given que soy gerente de proyecto autenticado en la vista de configuración de un proyecto activo
    When selecciono "Configuración específica de módulo"
    And ingreso la descripción "Módulo de facturación personalizado para Argentina"
    And guardo los cambios
    Then la configuración queda registrada con la descripción proporcionada
    And se muestra en la vista del proyecto

  Scenario: Asignar customización particular
    Given que soy gerente de proyecto autenticado en la vista de configuración de un proyecto activo
    When selecciono "Customización particular"
    And ingreso la descripción "Integración especial con sistema SAP del cliente"
    And guardo los cambios
    Then la customización se registra correctamente
    And se muestra en la vista del proyecto

  Scenario: Visualizar configuración existente
    Given que el proyecto tiene asignada una configuración específica de módulo
    When accedo a la vista del proyecto
    Then se muestra el tipo de configuración asignada
    And se muestra la descripción asociada si corresponde

  Scenario: Control de permisos al modificar configuración
    Given que soy un usuario sin permisos de gerente de proyecto
    When intento modificar la configuración del proyecto
    Then el sistema impide la acción
    And muestra un mensaje de falta de permisos

  Scenario: Editar configuración existente
    Given que el proyecto tiene una configuración asignada
    When cambio el tipo de configuración
    And actualizo la descripción
    And guardo los cambios
    Then la configuración se actualiza correctamente
    And se registra el cambio realizado
