# Anexo de notas tomadas de la reunión con Tomás Bruneleschi

* Fecha: 08/05/2025
* Hora: 20:18 a 20:44hs
* Lugar: Zoom Meetings


### Temas tratados
* Módulos a desarrollar: proyectos y soporte.
  * Se tienen que poder manejar proyectos. Todos los proyectos tienen tareas y gente asignada con roles definidos.
  Tiene que haber una clara visibilización de proyectos atrasados, mientras antes se descubra que un proyecto está 
  atrasado, antes se puede empezar a resolver el atraso. Se deben poder administrar los riesgos y tener el registro de 
  cómo evolucionan a través de las fases e iteraciones
  * La entidad principal en el área de Soporte es el ticket. Cada consulta o problema tiene que ser traducida a un 
  ticket. Se debe poder estandarizar la severidad y prioridad de los tickets. Todos los tickets están asociados a un 
  cliente y a una versión de un producto determinado. En caso de ser necesario, se debería poder escalar un problema a 
  Ingeniería. Se deberían tomar acciones para prevenir clientes insatisfechos.
* Alcance del módulo de proyectos - cosas deseadas por el PO:
  * Cuando se entra al sistema, se debería poder entrar al módulo de proyectos y ver un listado con todos los proyectos
  abiertos (le gustaría que hubiera un filtro para poder ver proyectos finalizados). Se tiene que poder entrar a 
  cualquier proyecto y ver la información general del mismo así como también sus tareas y personas asignadas a estas 
  tareas.
  * Crear y borrar proyectos. Los proyectos tienen que tener un líder de proyecto y las tareas tienen desarrolladores 
  asignados. No es necesario desarrollar la asignación de recursos, la empresa cuenta con una aplicación externa que 
  provee los recursos (en este caso, personas).
  * Integración con el módulo de soporte y de recursos
  * No es necesario implementar permisos para el MVP
* Alcance del módulo de soporte - cosas deseadas por el PO:
  * Cuando se entra al módulo de soporte, se deberían poder ver todos los productos con todas las versiones que tiene
  PSA. Al entrar a una versión, se deberían poder ver todos los tickets abiertos (con un filtro para los tickets 
  cerrados, de forma similar a los proyectos). Todos los tickets se generan para una versión específica del producto.
  * En caso de que el ticket sea por un error en el producto, se debe derivar el caso a Ingeniería, creando una tarea
  en un proyecto para la resolución del problema.
  * Hay tickets relacionados a tareas, cuya resolución puede significar modificar tareas en uno o múltiples proyectos.
  * Los tickets siempre están asociados a una persona. Para conocer a la persona, el módulo de recursos provee la 
  aplicación que provee a proyectos.
  * No es necesario desarrollar para el MVP:
    * Reportes de clientes
    * Configuración de permisos
    * Conseguir nuevos clientes.
    * No es necesario desarrollar el ABM de productos y versiones.
