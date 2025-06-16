Feature: Visualización de proyectos en tablero
#  Como desarrollador/a del proyecto
#  Quiero ver un tablero visual con todos los proyectos
#  Para identificar fácilmente su estado y acceder rápidamente a su información

  Scenario: Mostrar tablero con proyectos activos
    Given existen proyectos cargados en el sistema
    When el usuario accede al módulo de proyectos
    Then se debe mostrar un tablero con las tarjetas de los proyectos
    And cada tarjeta debe mostrar el nombre, estado, fechas y líder del proyecto