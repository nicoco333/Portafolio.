# README - Pre-Enunciado Simulacro de Parcial (Backend de Aplicaciones)

## Propósito

Este documento sirve como guía paso a paso para enfrentar la resolución del simulacro de parcial de **Backend de Aplicaciones**.
El objetivo es que los estudiantes puedan comprender la estrategia de resolución aplicada en esta versión del pre-enunciado y prepararse técnicamente para la instancia evaluativa.

---

## 1. Comprensión del Enunciado

El pre-enunciado plantea desarrollar una aplicación de **consola en Java** que:

* Utilice **JDBC y JPA** sobre una base de datos **H2 en memoria**.
* Inicialice la estructura de la base a partir de un **script DDL** provisto (`ddl.sql`).
* Organice las entidades del dominio en clases JPA correctamente mapeadas.
* Prepare la infraestructura necesaria para una carga posterior de datos desde un archivo **CSV** (que será provisto el día del simulacro).

👉 En esta etapa no se realiza la carga del CSV, solo se prepara la estructura completa del proyecto para que el día del examen pueda ejecutarse la importación.

---

## 2. Estructura del Proyecto

El proyecto se desarrolla como un único módulo Maven con la siguiente organización:

```bash
utnfc.isi.back.sim
├── app                    # Aplicación de consola (Main)
├── domain                 # Entidades JPA (Juego, Genero, Desarrollador, Plataforma)
├── infra                  # Inicialización de la BD y EntityManager Provider
├── resources              # Archivos DDL y configuración de persistencia
└── test                   # Tests unitarios y de integración
```

Dependencias principales:

* `jakarta.persistence-api`
* `hibernate-core`
* `com.h2database:h2`
* `lombok`
* `junit-jupiter`

---

## 3. Paso 1 - Creación de la Estructura de BD

El archivo `ddl.sql` define las tablas **JUEGOS**, **DESARROLLADORES**, **PLATAFORMAS** y **GENEROS**, con sus secuencias e integridad referencial.

El script se ejecuta automáticamente al iniciar la aplicación mediante la clase `DatabaseInitializer`, que lee el DDL desde `resources` y lo aplica sobre una conexión JDBC H2 en memoria.

```java
DatabaseInitializer.recreateSchemaFromDdl();
```

Este paso permite reproducir siempre un entorno limpio y controlado para las pruebas.

---

## 4. Paso 2 - Configuración de JPA y H2

El archivo `META-INF/persistence.xml` define la unidad de persistencia `pu-backend`.

Configuraciones clave:

* Motor: `org.h2.Driver`
* Modo: `jdbc:h2:mem:backdb;DB_CLOSE_DELAY=-1;MODE=LEGACY`
* Propiedades Hibernate: `hbm2ddl.auto=none`, `show_sql=false`.

Se utiliza un `LocalEntityManagerProvider` que crea y mantiene un `EntityManagerFactory` único, con la opción de **recrearse automáticamente** si fue cerrado.

---

## 5. Paso 3 - Definición del Dominio

Las entidades del dominio son:

* `Desarrollador`
* `Genero`
* `Plataforma`
* `Juego`

Cada una corresponde a su tabla homónima definida en el DDL.

El mapeo de relaciones `ManyToOne` conecta `Juego` con las otras tres entidades.

Para `clasificacionEsrb`, se utiliza un **campo String persistido** (`clasificacionEsrbCode`) y un **getter/setter transitorio** que convierte a/desde el `enum ClasificacionEsrb`, evitando el uso de `@Converter`.

---

## 6. Paso 4 - Inicialización y Verificación

Los tests unitarios verifican:

* La correcta inicialización de la base (existencia de tablas y secuencias).
* La capacidad de crear y persistir entidades `Juego` con sus relaciones.
* El correcto comportamiento del `Enum` `ClasificacionEsrb` (mapeo y recuperación).

Ejemplo de ejecución de tests:

```bash
mvn test
```

---

## 7. Paso 5 - Prueba de la Aplicación

Para ejecutar la aplicación desde consola:

```bash
mvn -q exec:java -Dexec.mainClass=utnfc.isi.back.sim.app.MainApp
```

En esta instancia la aplicación simplemente inicializa la base de datos, confirma la estructura y se encuentra lista para recibir el importador CSV en la siguiente etapa del simulacro.

---

## 8. Conceptos Clave Practicados

* Creación de proyectos Maven de consola.
* Configuración de persistencia JPA sin frameworks adicionales.
* Ejecución de scripts DDL mediante JDBC.
* Uso de `EntityManager` y `EntityTransaction` manual.
* Mapeo de entidades y relaciones con anotaciones JPA.
* Encapsulamiento de conversión enum ↔ código.
* Verificación de estructura con INFORMATION_SCHEMA de H2.

---

## 9. Siguientes Pasos (Simulacro Final)

En la siguiente etapa (enunciado definitivo), se incorporará:

* Lectura de archivos CSV con **OpenCSV**.
* Transformación de datos y persistencia en bloque.
* Consultas con JPA (`JPQL`) para obtener estadísticas.
* Tests de integración con datos importados.

---

## 10. Recomendaciones para el Parcial

1. Entender el ciclo completo de vida del `EntityManager`.
2. Recordar que H2 en memoria se reinicia con cada ejecución.
3. Validar los mapeos antes de ejecutar tests.
4. Documentar los pasos y supuestos en un README propio.
5. Mantener la estructura modular, limpia y coherente con lo trabajado en clase.

---

**Universidad Tecnológica Nacional - Facultad Regional Córdoba**
*Cátedra Backend de Aplicaciones (3K2) - Simulacro de Parcial*
