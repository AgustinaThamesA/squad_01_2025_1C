# Matriz de Trazabilidad de Requisitos - Módulo Proyectos

Por cuestiones de organización, separamos los requisitos del módulo de Proyectos en esta matriz de trazabilidad.
En el archivo [`requisitos.md`](./requisitos.md) se encuentra la matriz de trazabilidad completa con todos los módulos.

## Requisitos con User Stories Asignadas

| ID | Requerimiento | Tipo | Interesado/s | Minuta | Módulo | Prioridad (MoSCoW) | User Stories |
|---|---|---|---|---|---|---|---|
| P-1 | El software tiene que ser visual | RNF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Could | **US-01** |
| P-2 | Los proyectos tienen 3 etapas: inicio, desarrollo y transición | RNF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-02** |
| P-3 | Cada integrante de un proyecto debería poder cargar sus horas trabajadas | RF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-03** |
| P-3.1 | No se deberían poder cargar horas en proyectos finalizados | RF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-03** |
| P-4 | Cada proyecto tiene un identificador, descripción, nombre, fechas de inicio y finalización, riesgos, líder de proyecto, estimación de las tareas y las horas cargadas, estimación total del proyecto, horas reales del proyecto | RNF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-01, US-04** |
| P-5 | Se deben poder identificar riesgos para el proyecto | RNF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-04** |
| P-5.1 | Los riesgos se ordenan por su probabilidad de ocurrencia y su severidad | RNF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-04** |
| P-5.2 | Cada riesgo tiene asociado un plan de mitigación | RNF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-04** |
| P-5.2.1 | Cada plan de mitigación tiene asignado una persona del proyecto como responsable | RNF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-04** |
| P-5.3 | Cada riesgo tiene asociado un plan de contingencia | RNF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-04** |
| P-5.3.1 | Cada plan de contingencia tiene asignado una persona del proyecto como responsable | RNF | Maximiliano Gandt | Reunión con Maximiliano Gandt | Proyectos | Must | **US-04** |
| P-6 | Se debe registrar la configuración del software de cada cliente: default, configuración específica de un módulo o customización particular para el cliente | RNF | Gustavo Cuccina | Reunión con Gustavo Cuccina | Proyectos | Must | **US-05** |
| P-7 | Los proyectos se deben identificar como proyectos de desarrollo o de implementación | RNF | Gustavo Cuccina | Reunión con Gustavo Cuccina | Proyectos | Must | **US-06** |
| P-8 | Se tienen que poder visibilizar claramente los proyectos atrasados | RN | Tomás Bruneleschi | Reunión con Tomás Bruneleschi | Proyectos | Must | **US-01** |
| P-9 | Al entrar al módulo de proyectos, se debería poder ver un listado con todos los proyectos abiertos | RNF | Tomás Bruneleschi | Reunión con Tomás Bruneleschi | Proyectos | Must | **US-01** |
| P-9.1 | Se deberían poder ver los proyectos finalizados con un filtro especial | RNF | Tomás Bruneleschi | Reunión con Tomás Bruneleschi | Proyectos | Could | **US-01** |
| P-10 | Debe existir un sistema de permisos tanto para la carga de horas como para la creación/eliminación de proyectos | RF | Tomás Bruneleschi | Reunión con Tomás Bruneleschi | Proyectos | Won't | **US-03, US-04** |
