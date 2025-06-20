Feature: Visualización de proyectos en tablero
#  Como desarrollador/a del proyecto
#  Quiero ver un tablero visual con todos los proyectos
#  Para identificar fácilmente su estado y acceder rápidamente a su información

  Scenario: Mostrar tablero con proyectos activos
    Given existen proyectos cargados en el sistema
    When el usuario accede al módulo de proyectos
    Then se debe mostrar un tablero con las tarjetas de los proyectos
    And cada tarjeta debe mostrar el nombre, estado, fechas y líder del proyecto

  Scenario: Mostrar en tablero cuando no hay proyectos activos
    Given no existen proyectos activos en el sistema
    When el usuario accede al módulo de proyectos
    Then se debe mostrar en el tablero que no hay proyectos para mostrar
    And no se deben mostrar tarjetas vacías

  Scenario: Filtrar proyectos cerrados
    Given existen proyectos en estado cerrado
    When el usuario aplica el filtro de cerrado
    Then se deben mostrar únicamente las tarjetas correspondientes a proyectos cerrados

  Scenario: Proyectos sin datos completos
    Given existe un proyecto sin fechas de finalización o sin líder asignado
    When el usuario accede al módulo de proyectos
    Then la tarjeta del proyecto debe mostrarse igualmente
    And se debe indicar "No asignado" o "Sin definir" en los campos faltantes
