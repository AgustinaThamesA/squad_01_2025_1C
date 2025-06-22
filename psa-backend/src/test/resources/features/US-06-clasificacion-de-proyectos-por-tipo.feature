Feature: Clasificación de proyectos por tipo

  Scenario: Crear proyecto tipo Desarrollo
    Given que soy gerente de proyecto creando un nuevo proyecto
    When ingreso el nombre "Sistema de gestión de inventario"
    And selecciono el tipo "Desarrollo"
    And completo los campos obligatorios
    And guardo el proyecto
    Then el proyecto se crea con tipo "Desarrollo"
    And se muestra así en el tablero

  Scenario: Crear proyecto tipo Implementación
    Given que soy gerente de proyecto creando un nuevo proyecto
    When ingreso el nombre "Implementación ERP Cliente XYZ"
    And selecciono el tipo "Implementación"
    And completo los campos obligatorios
    And guardo el proyecto
    Then el proyecto se crea con tipo "Implementación"
    And se muestra así en el tablero

  Scenario: Filtrar proyectos por tipo
    Given que existen proyectos de tipo "Desarrollo" y "Implementación"
    When aplico el filtro "Tipo: Desarrollo" en el tablero
    Then solo se muestran proyectos de tipo "Desarrollo"

  Scenario: Visualizar tipo en la tarjeta del proyecto
    Given que existe un proyecto de tipo "Desarrollo"
    When accedo al tablero
    Then la tarjeta del proyecto muestra el tipo "Desarrollo"
    And se distingue visualmente por color, icono o etiqueta

  Scenario: Tipo de proyecto no modificable
    Given que existe un proyecto ya creado de tipo "Desarrollo"
    When intento editarlo
    Then el campo tipo aparece como solo lectura

  Scenario: Validación obligatoria del tipo
    Given que estoy creando un nuevo proyecto
    When omito el campo tipo y guardo
    Then el sistema muestra un error indicando que el tipo es obligatorio
    And el proyecto no se crea

  Scenario: Visualizar tipo en vista detallada
    Given que existe un proyecto de tipo "Implementación"
    When accedo a su vista detallada
    Then se muestra claramente el tipo de proyecto
    And aparece destacado en la información básica

  Scenario: Filtro combinado por tipo y estado
    Given que existen proyectos con distintos tipos y estados
    When aplico filtros "Tipo: Desarrollo" y "Estado: En desarrollo"
    Then solo se muestran los proyectos que cumplen ambos criterios
    And el contador de resultados es correcto
