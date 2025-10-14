# Simulacro 3K2 2025 – Backend de Aplicaciones

## Propósito del repositorio

Este repositorio reúne todo el material trabajado en torno al **Simulacro del Parcial 3K2 2025** de la asignatura **Backend de Aplicaciones**.
El objetivo es que sirva como guía completa para comprender el proceso de resolución de un parcial práctico, desde el enunciado inicial hasta la solución final, mostrando cada una de las etapas evolutivas del trabajo.

El repositorio está pensado tanto para la **preparación del parcial** como para la **revisión del proceso de aprendizaje**: cómo se parte de un esquema básico de JPA y se llega a una solución que integra lectura de CSV, persistencia en bloque, consultas y reporting.

---

## Estructura general del repositorio

```
.
├── pre-enunciado
├── preview-resuelto
├── enunciado
├── data
└── simulacro-resuelto
```

### 📁 [pre-enunciado](pre-enunciado)

Contiene el **enunciado preparatorio** y la **estructura base de la base de datos**:

* `pre-enunciado-simulacro.md` → instrucciones para construir el proyecto inicial con JPA y JDBC.
* `ddl.sql` → script de creación de la base H2 (estructura de tablas y secuencias).

Este bloque representa el punto de partida del trabajo en clase, donde los estudiantes practican la configuración de una base en memoria y la inicialización de entidades JPA.

---

### 📁 [preview-resuelto](preview-resuelto)

Proyecto Maven con la **solución del pre-enunciado**:

* Implementa la inicialización completa de la BD desde `ddl.sql`.
* Incluye mapeos JPA, repositorios, proveedores de `EntityManager` y tests unitarios.
* Permite verificar la estructura antes de abordar la carga CSV.

> 📘 Dentro de esta carpeta hay un `README.md` que detalla paso a paso la lógica del pre-enunciado y los conceptos puestos en práctica (JDBC, JPA, repositorios, DDL, pruebas básicas).

---

### 📁 [enunciado](enunciado)

Incluye el enunciado definitivo del simulacro (idéntico en formato al del parcial real):

* `Enunciado Sim 3K2.md` → versión editable en Markdown.
* `Enunciado Sim 3K2.pdf` → versión distribuible para estudiantes.

El enunciado presenta el contexto, los requerimientos funcionales y las consultas a implementar. Sirve como referencia base para la evaluación.

---

### 📁 [data](data)

Contiene los datos utilizados durante la simulación:

* `games_data.csv` → archivo CSV con el conjunto de juegos a importar.
* `database-h2` → carpeta donde H2 genera sus trazas locales al ejecutarse.

> 💡 En la simulación, H2 se ejecuta **en memoria**, pero puede dejar trazas de depuración en este directorio.

---

### 📁 [simulacro-resuelto](simulacro-resuelto)

Proyecto Maven con la **solución del enunciado final del simulacro**:

* Integra lectura de CSV mediante **OpenCSV** (`CsvLoader`).
* Acumula entidades en memoria y las persiste en bloque utilizando `ImportService`.
* Ejecuta consultas agregadas según el enunciado (géneros más jugados, desarrolladores con más títulos, plataformas mejor rankeadas).
* Usa `H2` en memoria con la misma estructura definida en el `pre-enunciado`.

> 📗 También incluye su propio `README.md` con la explicación detallada del flujo y la estrategia de resolución.
> Es la **referencia final de estudio**: muestra cómo enfrentar un parcial desde un enfoque de arquitectura limpia y didáctica.

---

## Flujo sugerido de estudio

1. **Leer el pre-enunciado** para comprender la estructura de la base y el contexto del simulacro.
2. **Explorar el proyecto `preview-resuelto`**, ejecutando los tests para validar la inicialización y mapeos.
3. **Leer el enunciado final** para entender las consignas completas del parcial.
4. **Analizar el proyecto `simulacro-resuelto`**, que muestra la implementación paso a paso de la solución.
5. **Ejecutar el main lineal** con `mvn exec:java` para observar el proceso de carga y generación de reportes.

---

## Ejecución rápida

### ▶️ Para ejecutar la versión final del simulacro

```bash
cd simulacro-resuelto
mvn -q exec:java \
  -Dexec.mainClass=utnfc.isi.back.sim.app.MainApp \
  -Dexec.args="../data/games_data.csv"
```

El programa inicializa la base, importa los datos y muestra por consola los resultados solicitados.

### 📊 Resultados esperados

```
[OK] Esquema inicializado (H2 en memoria)
[OK] CSV leído: XXXX filas válidas
[OK] Persistidos: devs=AAA, géneros=BBB, plataformas=CCC, juegos=DDD

Top 5 Géneros por jugando...
Desarrolladores con > 30 juegos...
Plataforma mejor rankeada...
```

---

## Conceptos integrados en la práctica

* Configuración de **JPA con H2 en memoria**.
* Ejecución de scripts DDL y control de contexto de persistencia.
* Mapeo de entidades y relaciones `@ManyToOne`.
* Lectura de CSV con **OpenCSV** y conversión de datos.
* Orquestación de carga con **ImportService** y persistencia masiva.
* Consultas con JPQL y SQL nativo.
* Estructuración de proyectos Maven y buenas prácticas de empaquetado.

---

## Historial de versiones

| Versión | Descripción                                                | Carpeta asociada                           |
| ------- | ---------------------------------------------------------- | ------------------------------------------ |
| v1.0    | Pre-enunciado inicial (estructura BD y entidades)          | [`pre-enunciado`](pre-enunciado)           |
| v1.1    | Solución del pre-enunciado con repositorios y tests        | [`preview-resuelto`](preview-resuelto)     |
| v2.0    | Enunciado definitivo del simulacro (parcial)               | [`enunciado`](enunciado)                   |
| v2.1    | Datos de prueba y CSV utilizado en la simulación           | [`data`](data)                             |
| v3.0    | Solución final del simulacro con ImportService y consultas | [`simulacro-resuelto`](simulacro-resuelto) |

---

## Autoría y créditos

**Cátedra Backend de Aplicaciones – UTN FRC**
Profesor responsable: *Felipe Steffolani* (`FeliSteffo`)
Ciclo lectivo: **2025**

Este material forma parte del itinerario de prácticas y simulacros de la asignatura, orientado a consolidar el dominio del stack **Java + JPA + Hibernate + OpenCSV** dentro del marco de arquitectura didáctica de la cátedra.
