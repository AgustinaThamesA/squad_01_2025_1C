## Visión Módulo Proyecto (MVP) – Sistema PSA

## Introducción

El presente documento reúne la visión funcional de los módulos que componen el sistema de gestión para PSA.
Cada módulo fue analizado en base a entrevistas con líderes de área, identificando necesidades clave, alcance mínimo viable (MVP) y métricas de éxito.
Este enfoque permite desarrollar una solución modular, iterativa y alineada con los objetivos estratégicos de PSA, priorizando funcionalidades de alto impacto.
En esta oportunidad, les presentamos el módulo que corresponde al desarrollo de nuestra implementación.

## Visión del módulo

Consolidar la gestión de proyectos como el eje estructural de la operación de PSA, permitiendo visibilidad integral, control de tareas, riesgos, fases y configuración específica por cliente. El módulo busca asegurar la entrega de valor mediante una herramienta centralizada, colaborativa y escalable.

### Objetivos del MVP

* Crear, visualizar y editar proyectos.
* Gestionar las etapas del proyecto (inicio, desarrollo, transición).
* Registrar riesgos y planes asociados.
* Registrar la configuración del software por cliente.
* Clasificar los proyectos como desarrollo o implementación.
* Permitir la carga de horas en tareas por parte de los integrantes.
* Detectar tareas vencidas, proyectos atrasados y riesgos sin tratar.
* Visualizar y filtrar proyectos desde un tablero general.

### Alcance del MVP

**Incluye:**

* ABM de proyectos con atributos clave (nombre, fechas, líder, tipo, configuración).
* Gestión de tareas asociadas y carga de horas con permisos.
* Gestión integral de riesgos (con clasificación, mitigación y contingencia).
* Panel visual/tablero con tarjetas de proyectos y filtros.
* Asignación y control de etapas del proyecto.
* Configuración del software por cliente.
* Clasificación del tipo de proyecto (Desarrollo / Implementación).

**No incluye (aún):**

* Estimaciones de costos o rentabilidad.
* Métricas financieras cruzadas con recursos.
* Integración con módulos de Finanzas o Recursos.
* Alertas automáticas (en etapa de diseño futuro).

### Interesados clave

* Fernando Soluzzia (Gerente de Operaciones)
* Leonardo Felici (Project Manager)
* Tomás Bruneleschi (Product Owner)
* Juan Zeo (CEO, como interesado estratégico)

### Métricas de éxito

* +80% de proyectos gestionados desde el módulo.
* Reducción del 30% de tareas vencidas.
* 100% de proyectos activos con riesgos y configuración registrados.
* 100% de proyectos clasificados como desarrollo o implementación.
* Tiempo promedio de actualización de estado < 48h.

### Justificación estratégica

La gestión de proyectos es el núcleo de valor de PSA, ya que cada release y cada implementación cliente se organiza como proyecto. Mejorar esta área impacta directamente en eficiencia, calidad y percepción del cliente. La posibilidad de clasificar, configurar y controlar proyectos en detalle permite alinear la operación con las necesidades específicas de cada cliente, habilitando además la integración futura con soporte, finanzas y recursos.
