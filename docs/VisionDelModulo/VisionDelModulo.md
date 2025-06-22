## Visiones por Módulo (MVP) – Sistema PSA

## Introducción

El presente documento reúne la visión funcional de los módulos que componen el sistema de gestión para PSA.

Cada módulo fue analizado en base a entrevistas con líderes de área, identificando necesidades clave, alcance mínimo viable (MVP) y métricas de éxito.

Este enfoque permite desarrollar una solución modular, iterativa y alineada con los objetivos estratégicos de PSA, priorizando funcionalidades de alto impacto.

## Módulo: Proyectos

### Visión del módulo

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

## Módulo: Soporte

### Visión del módulo

Centralizar el manejo de tickets e incidentes de clientes, garantizando trazabilidad, priorización efectiva y tiempos de respuesta ágiles que prevengan la insatisfacción del cliente y refuercen la reputación de PSA.

### Objetivos del MVP

* Registro y seguimiento de tickets.
* Priorización por tipo de problema.
* Clasificación por cliente y producto.
* Alertas de vencimiento.
* Derivación básica a ingeniería.

### Alcance del MVP

**Incluye:**

* ABM de tickets.
* Clasificación por tipo y producto.
* Alertas de vencimiento.
* Historial por cliente.

**No incluye (aún):**

* Escalamiento complejo.
* Integración con producto.
* Métricas automáticas de SLA.

### Interesados clave

* Augusto Aguanti (Soporte)
* Analistas de mesa de ayuda
* Fernando Soluzzia (Gerente de Operaciones)
* Tomás Bruneleschi (PO)

### Métricas de éxito

* 40% menos tickets vencidos.
* Resolución promedio < 48h.
* +90% tickets bien clasificados.
* Seguimiento completo en 95% de casos.

### Justificación estratégica

El soporte técnico es clave para evitar la pérdida de clientes y controlar el nivel de satisfacción. El sistema actual es poco sistemático y no permite visibilidad ni trazabilidad efectiva. Este módulo representa la primera línea de contención y permite detectar patrones de fallas, retroalimentar a desarrollo y mejorar el producto.

## Módulo: Finanzas

### Visión del módulo

Proveer visibilidad financiera integral vinculada a los proyectos, para tomar decisiones informadas sobre inversión, rentabilidad y eficiencia operativa, integrando costos reales, proyectados y extraordinarios.

### Objetivos del MVP

* Seguimiento de ingresos y egresos.
* Registro de gastos fijos y extraordinarios.
* Asociación de costos a tareas y recursos.
* Visualización de rentabilidad por proyecto.
* Soporte para facturación discriminada.

### Alcance del MVP

**Incluye:**

* ABM de ingresos, egresos y facturas.
* Asociación de costos a proyectos y tareas.
* Reportes básicos de rentabilidad.

**No incluye (aún):**

* Integración contable con sistemas externos.
* Análisis financiero predictivo.
* Presupuestación avanzada o análisis financiero predictivo.

### Interesados clave

* Roberto Ratio (Gerente de Finanzas)
* Administración y contadores
* Gerentes de proyecto (por costos)
* RRHH (por sueldos y licencias pagas)

### Métricas de éxito

* 100% de movimientos registrados.
* Reportes de rentabilidad < 48h.
* -25% de errores de facturación.
* Trazabilidad completa de costos extraordinarios.

### Justificación estratégica

Este módulo permite que PSA evalúe la rentabilidad real de cada proyecto, optimice el uso del presupuesto y tome decisiones rápidas ante desvíos financieros. También brinda transparencia a los socios y facilita el análisis económico integral.

## Módulo: Recursos

### Visión del módulo

Optimizar la gestión del capital humano permitiendo una asignación eficiente, trazabilidad de la carga laboral y control de horas trabajadas, como base para decisiones estratégicas en proyectos y planificación financiera.

### Objetivos del MVP

* Registro de recursos humanos.
* Asignación a tareas y proyectos.
* Carga de horas trabajadas.
* Registro de vacaciones y licencias.

### Alcance del MVP

**Incluye:**

* ABM de recursos.
* Módulo de carga de horas.
* Asociación a tareas.
* Registro básico de licencias.

**No incluye (aún):**

* Evaluación de desempeño.
* Legajos completos.
* Alertas de sobrecarga.

### Interesados clave

* RRHH (implícito)
* Gerentes de Proyecto
* Roberto Ratio (por el impacto financiero de licencias y sueldos)
* Consultores y desarrolladores

### Métricas de éxito

* 100% de carga semanal.
* +80% de licencias aprobadas < 48h.
* 20% de mejora en distribución de carga.
* -30% de sobreasignaciones.

### Justificación estratégica

Este módulo articula la información operativa de proyectos con la visión financiera y de gestión de talento. Una correcta asignación de recursos previene cuellos de botella y mejora el cumplimiento de plazos y presupuestos.

## Módulo: Marketing

### Visión del módulo

Centralizar la planificación, ejecución y seguimiento de campañas publicitarias en múltiples canales, permitiendo mejorar el alcance de PSA, medir el impacto y retroalimentar los esfuerzos comerciales.

### Objetivos del MVP

* Registro y planificación de campañas.
* Asociación con productos y servicios.
* Integración básica con herramientas SEO/SEM.
* Comunicación con analistas del área.

### Alcance del MVP

**Incluye:**

* ABM de campañas y canales.
* Asociación de campañas a módulos o productos.
* Registro de objetivos por campaña.
* Campos para registrar feedback de analistas.

**No incluye (aún):**

* Automatización de envíos.
* Reportes analíticos avanzados.
* Integración con redes sociales o CRMs.

### Interesados clave

* José Mercado (Gerente de Marketing)
* Analistas de marketing
* Gerencia comercial (ventas)

### Métricas de éxito

* 80% de campañas registradas desde el sistema.
* Al menos 3 canales activos por campaña.
* Incorporación de feedback en el 100% de campañas ejecutadas.
* Tiempos de planificación reducidos en un 25%.

### Justificación estratégica

El marketing es la primera línea de contacto con los futuros clientes. Este módulo permitirá estandarizar y profesionalizar la gestión de campañas, generar datos para analizar el ROI y alinear mejor los esfuerzos de visibilidad con las metas comerciales de PSA.

## Módulo: Ventas

### Visión del módulo

Organizar el proceso comercial desde la generación de oportunidades hasta el cierre de ventas, facilitando un seguimiento efectivo y una mayor conversión a través de la trazabilidad de interacciones y estados.

### Objetivos del MVP

* Registro de oportunidades.
* Gestión de clientes actuales y potenciales.
* Seguimiento de etapas de venta.
* Asociación con productos y campañas.

### Alcance del MVP

**Incluye:**

* ABM de clientes y oportunidades.
* Visualización del ciclo de vida comercial.
* Estados configurables de avance.
* Vinculación básica con campañas (desde marketing).

**No incluye (aún):**

* Integración con sistemas de facturación o CRM externos.
* Reportes de conversión o forecasting.
* Automatización de tareas comerciales.

### Interesados clave

* Juan Anvizzio (Gerente de Ventas)
* Vendedores senior
* Gerencia de Marketing (por vínculo con campañas)

### Métricas de éxito

* +70% de oportunidades gestionadas.
* +15% en tasa de conversión.
* 90% con seguimiento documentado.
* -30% de oportunidades perdidas por falta de seguimiento.

### Justificación estratégica

Este módulo permite sistematizar el ciclo de ventas, evitar pérdidas por falta de control, alinear el trabajo del equipo comercial con las campañas de marketing y generar datos clave para decidir con qué clientes avanzar o priorizar.

## Resumen

El documento de visiones por módulo define el alcance estratégico del sistema PSA en su versión MVP. Se busca implementar una solución modular, escalable y centrada en la eficiencia operativa de los equipos de la organización.

Cada módulo fue analizado en base a entrevistas con líderes de área y validado con el Product Owner, alineando necesidades reales con la propuesta técnica del sistema. Este enfoque asegura una primera entrega funcional, de alto impacto para PSA, y deja asentadas las bases para iteraciones futuras y crecimiento evolutivo.

### Próximos pasos:

* Consolidar la planificación del desarrollo por sprint.
* Priorizar el diseño de los módulos Proyectos y Soporte como ejes iniciales del MVP.
* Definir los criterios de validación conjunta por cada interesado clave.
* Establecer rutinas de retroalimentación con los usuarios internos durante el uso piloto.
